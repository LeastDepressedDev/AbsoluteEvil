package me.qigan.abse.crp;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.mapping.Mapping;
import me.qigan.abse.mapping.MappingController;
import me.qigan.abse.mapping.Path;
import me.qigan.abse.sync.Sync;

import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

@AutoDisable
@DangerousModule
public class Experimental extends Module implements EDLogic {

    public static int[][] map = null;

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

//    @SubscribeEvent
//    void render(RenderWorldLastEvent e) {
//        if (isEnabled() && Minecraft.getMinecraft().theWorld != null) {
//            for (BlockPos pos : Mapping.debug) {
//                Esp.autoBox3D(pos, Color.red, 2f, true);
//            }
//        }
//    }



    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("exptl_but1", "Routing", ValType.BUTTON, (Runnable) () -> {
            if (isEnabled()) Index.MOVEMENT_CONTROLLER.go(new Path(Sync.playerPosAsBlockPos(), new BlockPos(10, 9, 7)).build());
        }));
        list.add(new SetsData<>("exptl_but2", "Mapping", ValType.BUTTON, (Runnable) () -> {
            if (isEnabled()) {
                map = map == null ? Mapping.scanFull(MappingController.calcPlayerCell()) : Mapping.sync(map);
                //RIGHT READING SEQUENCE
                String str = "\n";
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 6; j++) {
                        str += map[j][i] + " ";
                    }
                    str += "\n";
                }
                System.out.println(str);
            }
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
        Index.MOVEMENT_CONTROLLER.stop();
        map = null;
    }
}
