package me.qigan.abse.fr.other;

import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.EDLogic;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.GhostUtils;
import me.qigan.abse.fr.SmoothAimControl;
import me.qigan.abse.fr.cbh.CombatHelperAim;
import me.qigan.abse.gui.overlay.ImportantChatOVR;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

public class Experimental extends Module implements EDLogic {

    public static int ij = 0;

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;
        try {
            BlockPos pos = new BlockPos(0, 90, 0);
            BlockPos from = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
            float[] angeles = Utils.getRotationsTo(from, pos, new float[]{Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.rotationPitch});
            SmoothAimControl.set(angeles, 30);
        } catch (Exception ex) {
            ex.printStackTrace();
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
        list.add(new SetsData<>("exp_button_1", "Test", ValType.BUTTON, (Runnable) () -> {
            ImportantChatOVR.add("Test " + ij);
            ij++;
        }));
        return list;
    }

    @Override
    public String description() {
        return "Being used for testing some crazy stuff";
    }

    @Override
    public void onEnable() {
        SmoothAimControl.OVERRIDE = true;
    }

    @Override
    public void onDisable() {
        SmoothAimControl.OVERRIDE = false;
    }
}
