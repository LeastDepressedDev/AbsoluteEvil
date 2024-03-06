package me.qigan.abse.crp;

import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.fr.exc.SmoothAimControl;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEffectEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

@AutoDisable
@DangerousModule
public class Experimental extends Module implements EDLogic {

    public static Map<BlockPos, String> draw = new HashMap<>();

    @SubscribeEvent
    void hear(PlaySoundEvent e) {
        if (!isEnabled()) return;
        draw.put(new BlockPos(e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF()), e.name);
    }

    @SubscribeEvent
    void rend(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        for (Map.Entry<BlockPos, String> ele : draw.entrySet()) {
            BlockPos pos = ele.getKey();
            Esp.autoBox3D(pos, Color.cyan, 2f, false);
            Esp.renderTextInWorld(ele.getValue(), pos.getX(), pos.getY(), pos.getZ(), Color.cyan.getRGB(), e.partialTicks);
        }
    }

    @Override
    public String id() {
        return "exptl";
    }

    @Override
    public String fname() {
        char[] str = "Experimental".toCharArray();
        String nstr = "";
        for (int i = 0; i < str.length; i++) {
            nstr += (i % 2 == 0) ? ("\u00A7e" + str[i]) : ("\u00A77" + str[i]);
        }
        return nstr;
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        return list;
    }

    @Override
    public String description() {
        return "Being used for testing some crazy stuff";
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        draw.clear();
    }
}
