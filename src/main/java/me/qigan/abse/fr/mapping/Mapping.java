package me.qigan.abse.fr.mapping;

import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Mapping {

    public static boolean DEBUG = true;

    public static double startPosX;
    public static double startPosY;
    public static double startPosZ;

    @DebugThingy
    public static List<BlockPos> dRedstoneBlocks = new ArrayList<>();


    private String toRender() {
        return "x0: " + startPosX + "\n" +
                "y0: " + startPosY + "\n" +
                "z0: " + startPosZ + "\n";
    }

    @SubscribeEvent
    void onJoin(WorldEvent.Load e) {
        dRedstoneBlocks.clear();
        new Thread(() -> {
            try {
                Thread.sleep(200);
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                startPosX = player.posX;
                startPosY = player.posY;
                startPosZ = player.posZ;

                if (DEBUG) {
                    Thread.sleep(3000);
                    for (int i = -1000; i < 1000; i++) {
                        for (int j = -1000; j < 1000; j++) {
                            for (int r = 40; r < 120; r++) {
                                BlockPos pos = new BlockPos(i, r, j);
                                if (e.world.getBlockState(pos).getBlock() == Blocks.redstone_block) {
                                    dRedstoneBlocks.add(pos);
                                }
                            }
                        }
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }).start();
    }

    @DebugThingy
    @SubscribeEvent
    void renderDebugInfo(RenderGameOverlayEvent.Text e) {
        if (DEBUG) {
            char[] str = toRender().toCharArray();
            String nstr = "";
            int r = 0;
            for (int i = 0; i < str.length; i++) {
                if (str[i] == '\n') {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(nstr, 0, (r*10), 0xFFFFFF);
                    nstr = "";
                    r++;
                } else {
                    nstr += str[i];
                }
            }
        }
    }

    @SubscribeEvent
    void renderWorldLast(RenderWorldLastEvent e) {
        if (!DEBUG) return;
        try {
            for (BlockPos pos : dRedstoneBlocks) {
                Esp.autoBox3D(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 1, 1, new Color(255, 0, 0, 255), 2f, true);
            }
        } catch (ConcurrentModificationException ex) {
            ex.printStackTrace();
        }
    }
}
