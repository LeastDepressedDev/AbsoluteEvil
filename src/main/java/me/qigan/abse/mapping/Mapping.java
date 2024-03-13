package me.qigan.abse.mapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class Mapping {

    public static List<BlockPos> debug = new ArrayList<>();

    private static int rayDown(int x, int z, WorldClient world) {
        for (int y = 255; y >= 0; y--) {
            if (world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.air) return y;
        }
        return 0;
    }

    public static int[] cellToReal(int i, int j) {
        return new int[]{MappingConstants.MAP_BOUNDS[0].getX()+((MappingConstants.ROOM_SIZE+2)*i),
                MappingConstants.MAP_BOUNDS[0].getZ()+((MappingConstants.ROOM_SIZE+2)*j)};
    }

    private static int[][] req(int iter, int[][] re, int i, int j, WorldClient world) {
        int[] coord = cellToReal(i, j);
        //int subZ = coord[1];
        int subY = rayDown(coord[0]+15, coord[1]+15, world);
        if (subY == 0) return re;
        if (world.getBlockState(new BlockPos(coord[0]+15, subY, coord[1]+MappingConstants.ROOM_SIZE+1)).getBlock() != Blocks.air) {
            if (j+1 < 6 && re[i][j+1] == 0) {
                re[i][j+1] = iter;
                re = req(iter, re, i, j+1, world);
                debug.add(new BlockPos(coord[0]+15, subY, coord[1]+MappingConstants.ROOM_SIZE+1));
            }
        }
        if (world.getBlockState(new BlockPos(coord[0]+MappingConstants.ROOM_SIZE+1, subY, coord[1]+15)).getBlock() != Blocks.air) {
            if (i+1 < 6 && re[i+1][j] == 0) {
                re[i+1][j] = iter;
                re = req(iter, re, i+1, j, world);
                debug.add(new BlockPos(coord[0]+MappingConstants.ROOM_SIZE+1, subY, coord[1]+15));
            }
        }
        if (world.getBlockState(new BlockPos(coord[0]+15, subY, coord[1]-1)).getBlock() != Blocks.air) {
            if (j-1 >= 0 && re[i][j-1] == 0) {
                re[i][j-1] = iter;
                re = req(iter, re, i, j-1, world);
                debug.add(new BlockPos(coord[0]+15, subY, coord[1]-1));
            }
        }
        if (world.getBlockState(new BlockPos(coord[0]-1, subY, coord[1]+15)).getBlock() != Blocks.air) {
            if (i-1 >= 0 && re[i-1][j] == 0) {
                re[i-1][j] = iter;
                re = req(iter, re, i-1, j, world);
                debug.add(new BlockPos(coord[0]-1, subY, coord[1]+15));
            }
        }
        return re;
    }

    public static int[][] scanFull() {
        int iter = 1;
        int[][] map = new int[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int[] c = cellToReal(i, j);
                int subY = rayDown(c[0]+15, c[1]+15, Minecraft.getMinecraft().theWorld);
                debug.add(new BlockPos(c[0], subY, c[1]));
                map[i][j] = subY == 0 ? -1 : 0;
            }
        }
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (map[i][j] == 0) {
                    map[i][j] = iter;
                    map = req(iter, map, i, j, Minecraft.getMinecraft().theWorld);
                    iter++;
                }
            }
        }
        return map;
    }
}
