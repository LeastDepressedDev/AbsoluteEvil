package me.qigan.abse.sync;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class Sync {
//The Catacombs

    public static boolean inDungeon = false;
    public static final int tickr = 40;
    public static int tick = 0;

    public static BlockPos playerPosAsBlockPos() {
        return new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
    }

    public static void ovrDungeon() {
        for (String str : Utils.getScoreboard()) {
            if (str.contains("The Catacombs")) {
                inDungeon = true;
                return;
            }
        }
        inDungeon = false;
    }

    /**
     * Checks whether the Skyblock Dungeon Map is in the player's hotbar
     * @return whether the map exists
     */
    public static boolean mapExists() {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack mapSlot = mc.thePlayer.inventory.getStackInSlot(8); //check last slot where map should be
        if (mapSlot == null || mapSlot.getItem() != Items.filled_map || !mapSlot.hasDisplayName()) return false; //make sure it is a map, not SB Menu or Spirit Bow, etc
        return mapSlot.getDisplayName().contains("Magical Map");
    }

    /**
     * Reads the hotbar map and converts it into a 2D Integer array of RGB colors which can be used by the rest of the
     * code
     *
     * @return null if map not found, otherwise 128x128 Array of the RGB Integer colors of each point on the map
     */
    public static Integer[][] updatedMap() {
        if (!mapExists()) return null; //make sure map exists
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack mapSlot = mc.thePlayer.inventory.getStackInSlot(8); //get map ItemStack
        MapData mapData = Items.filled_map.getMapData(mapSlot, mc.theWorld);
        if(mapData == null) return null;
        Integer[][] map = new Integer[128][128];

        //for loop code modified from net.minecraft.client.gui.MapItemRenderer.updateMapTexture()
        for (int i = 0; i < 16384; ++i) {
            int x = i % 128; //get x coordinate of pixel being read
            int y = i / 128; //get y coordinate of pixel being read
            int j = mapData.colors[i] & 255;
            int rgba;
            if (j / 4 == 0) {
                rgba = (i + i / 128 & 1) * 8 + 16 << 24;
            } else {
                rgba = MapColor.mapColorArray[j / 4].func_151643_b(j & 3);
            }
            map[x][y] = rgba & 0x00FFFFFF; //get rgb value from rgba
        }

        return map;
    }


    /**
     * This function finds the coordinates of the NW and NE corners of the entrance room on the hotbar map. This is
     * later used to determine the size of the room grid on the hotbar map. Different floors have slightly different
     * pixel widths of the rooms, so it is important for the mod to be able to identify the location and size of various
     * portions of the room grid. Since all rooms within a floor are the same size on the hotbar map and since the
     * entrance room is always there on the hotbar map, we get two corners from the entrance room to determine the
     * scaling of the map as soon as the player enters.
     *
     * This function works by iterating through the map and looking for a green entrance room pixel. Once it finds one
     * and determines that the map pixel above is a blank spot, it checks for map pixels on the left and right side.
     *
     * @return `entranceMapCorners[0]` is the coordinate of the left NW corner and `entranceMapCorners[1]` is the
     * coordinate of the right NE corner
     */
    public static Point[] entranceMapCorners(Integer[][] map) {
        if (map == null) return null;
        Point[] corners = new Point[2];

        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                if (map[x][y] != null && map[x][y] == 31744 && map[x][y-1] != null && map[x][y-1] == 0) { //check for Green entrance room pixels and make sure row above is blank
                    if (map[x - 1][y] != null && map[x - 1][y] == 0) {
                        corners[0] = new Point(x, y); //Left corner
                    } else if (map[x + 1][y] != null && map[x + 1][y] == 0) {
                        corners[1] = new Point(x, y); //Right Corner
                    }
                }
            }
            if (corners[0] != null && corners[1] != null) break;
        }
        return corners;
    }

    public static boolean isClear() {
        return inDungeon && mapExists();
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (tick >= tickr) {
            tick = 0;
            ovrDungeon();
        }
        else tick++;
    }
}
