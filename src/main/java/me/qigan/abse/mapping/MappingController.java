package me.qigan.abse.mapping;

import me.qigan.abse.sync.Sync;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MappingController {

    /**
     * RIGHT READING SEQUENCE
     *                 String str = "\n";
     *                 for (int i w= 0; i < 6; i++) {
     *                     for (int j = 0; j < 6; j++) {
     *                         str += map[j][i] + " ";
     *                     }
     *                     str += "\n";
     *                 }
     *                 System.out.println(str);
     */

    public static final boolean DO_DEBUG_RENDER = true;

    public int[][] map = null;
    public Map<Integer, Room> roomMap = new HashMap<>();
    public int[] playerCell;

    public void update() {
        if (map != null) map = Mapping.sync(map);
    }

    public static int[] calcPlayerCell() {
        return Mapping.realToCell(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posZ);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    void onUnload(WorldEvent.Unload e) {
        map = null;
        playerCell = new int[]{-1, -1};
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    void onLoad(WorldEvent.Load e) {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                map = Mapping.scanFull(calcPlayerCell());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }).start();
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END || !Sync.inDungeon || Minecraft.getMinecraft().thePlayer == null) return;
        int[] newPos = calcPlayerCell();
        if (newPos[0] != playerCell[0] || newPos[1] != playerCell[1]) {
            playerCell = newPos;
            update();

            //TODO: FIX THIS VERY TEMPORARY SOLUTION
            if (map == null) return;
            if (playerCell[0] >= 0 && playerCell[0] < 6 && playerCell[1] >= 0 && playerCell[1] < 6) {
                int iter = map[playerCell[0]][playerCell[1]];
                if (!roomMap.containsKey(iter)) roomMap.put(iter, new Room(iter).define(map));

                //DEBUG
                Room rm = roomMap.get(iter);
                System.out.println(rm.iter + ":   " + rm.center[0] + "-" + rm.center[1] + "\n"
                        + rm.getShape() + "||" + rm.getType() + "||" + rm.getRotation() + "||" + rm.getHeight());
            }
        }
    }

    @SubscribeEvent
    void ovr(RenderGameOverlayEvent.Text e) {
        if (!DO_DEBUG_RENDER || map == null) return;
        Point pt = new Point(100, 300);
        int[] k = Mapping.realToCell(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posZ);
        Esp.drawOverlayString(k[0] + ":" + k[1], pt.x, pt.y-30, Color.cyan, S2Dtype.DEFAULT);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Esp.drawCenteredString((k[0] == i && k[1] == j ? "\u00A7a" : "\u00A7c") + map[i][j], pt.x+15*i, pt.y+15*j, 0xFFFFFF, S2Dtype.DEFAULT);
            }
        }
    }
}
