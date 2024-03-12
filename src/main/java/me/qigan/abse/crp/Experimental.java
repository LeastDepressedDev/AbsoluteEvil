package me.qigan.abse.crp;

import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.mapping.Path;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.vecmath.Point3d;
import java.awt.*;
import java.util.*;
import java.util.List;

@AutoDisable
@DangerousModule
public class Experimental extends Module implements EDLogic {

    public static Path path = null;

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null || path == null) return;
        Esp.autoBox3D(path.from, Color.green, 2f, true);
        Esp.autoBox3D(path.to, Color.red, 2f, true);
        List<Point3d> points = new ArrayList<>();
        for (BlockPos pos : path.getPosPath()) {
            points.add(new Point3d(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5));
        }
        Esp.drawAsSingleLine(points, Color.cyan, 4f, false);
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
        list.add(new SetsData<>("exptl_but1", "Button", ValType.BUTTON, (Runnable) () -> {
            path = new Path(Sync.playerPosAsBlockPos(), new BlockPos(10, 9, 7)).build();
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7c" + path.isFailed()));
        }));
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
        path = null;
    }
}
