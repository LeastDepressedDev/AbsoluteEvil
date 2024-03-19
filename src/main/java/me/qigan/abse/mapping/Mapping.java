package me.qigan.abse.mapping;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mapping {

    /**
     *
     * @param angle (IN DEGREES)
     */
    public static int[] transp(int x, int y, float angle) {
        angle = (float) Math.toRadians(angle);
        return new int[]{
                (int) ((float) x * Math.cos(angle) - (float) y * Math.sin(angle)),
                (int) ((float) x * Math.sin(angle) - (float) y * Math.cos(angle))
        };
    }

    public static int[] transp(int[] coord, float angle) {
        return transp(coord[0], coord[1], angle);
    }

    public static int rayDown(int x, int z, WorldClient world) {
        for (int y = 255; y >= 0; y--) {
            if (!MappingConstants.AIRABLE.contains(world.getBlockState(new BlockPos(x, y, z)).getBlock())) return y;
        }
        return 0;
    }

    public static int rayDown(int[] coord, WorldClient world) {
        return rayDown(coord[0], coord[1], world);
    }

    public static int[] cellToReal(int[] coord) {
        return cellToReal(coord[0], coord[1]);
    }

    public static int[] cellToReal(int i, int j) {
        return new int[]{
                MappingConstants.MAP_BOUNDS[0].getX()+((MappingConstants.ROOM_SIZE+2)*i),
                MappingConstants.MAP_BOUNDS[0].getZ()+((MappingConstants.ROOM_SIZE+2)*j)
        };
    }

    public static int[] realToCell(double x, double z) {
        return new int[]{
                (int) Math.floor((x-MappingConstants.MAP_BOUNDS[0].getX())/(double) (MappingConstants.ROOM_SIZE+2)),
                (int) Math.floor((z-MappingConstants.MAP_BOUNDS[0].getZ())/(double) (MappingConstants.ROOM_SIZE+2))
        };
    }

    public static boolean isFullyLoaded(int i, int j) {
        int[] coord = cellToReal(i, j);
        WorldClient client = Minecraft.getMinecraft().theWorld;
        return rayDown(coord[0], coord[1], client) != 0 && rayDown(coord[0]+MappingConstants.ROOM_SIZE, coord[1], client) != 0 &&
                rayDown(coord[0], coord[1]+MappingConstants.ROOM_SIZE, client) != 0 && rayDown(coord[0]+MappingConstants.ROOM_SIZE, coord[1]+MappingConstants.ROOM_SIZE, client) != 0;
    }

    private static int[][] req(int iter, int[][] re, int i, int j, WorldClient world) {
        int[] coord = cellToReal(i, j);
        //int subZ = coord[1];
        int subY = rayDown(coord[0]+15, coord[1]+15, world);
        if (subY == 0) return re;
        if (world.getBlockState(new BlockPos(coord[0]+15, subY, coord[1]+MappingConstants.ROOM_SIZE+1)).getBlock() != Blocks.air
                && rayDown(coord[0]+15, coord[1]+MappingConstants.ROOM_SIZE+2, world) != 0) {
            if (j+1 < 6 && re[i][j+1] == 0) {
                re[i][j+1] = iter;
                re = req(iter, re, i, j+1, world);
            }
        }
        if (world.getBlockState(new BlockPos(coord[0]+MappingConstants.ROOM_SIZE+1, subY, coord[1]+15)).getBlock() != Blocks.air
                && rayDown(coord[0]+MappingConstants.ROOM_SIZE+2, coord[1]+15, world) != 0) {
            if (i+1 < 6 && re[i+1][j] == 0) {
                re[i+1][j] = iter;
                re = req(iter, re, i+1, j, world);
            }
        }
        if (world.getBlockState(new BlockPos(coord[0]+15, subY, coord[1]-1)).getBlock() != Blocks.air
                && rayDown(coord[0]+15, coord[1]-2, world) != 0) {
            if (j-1 >= 0 && re[i][j-1] == 0) {
                re[i][j-1] = iter;
                re = req(iter, re, i, j-1, world);
            }
        }
        if (world.getBlockState(new BlockPos(coord[0]-1, subY, coord[1]+15)).getBlock() != Blocks.air
                && rayDown(coord[0]-2, coord[1]+15, world) != 0) {
            if (i-1 >= 0 && re[i-1][j] == 0) {
                re[i-1][j] = iter;
                re = req(iter, re, i-1, j, world);
            }
        }
        return re;
    }

    public static int[][] genMap() {
        int[][] map = new int[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int[] c = cellToReal(i, j);
                int subY = rayDown(c[0]+15, c[1]+15, Minecraft.getMinecraft().theWorld);
                map[i][j] = subY == 0 ? -1 : 0;
            }
        }
        return map;
    }

    public static int findHighestIter(int[][] map) {
        int mx = 0;
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                if (map[i][j] > mx) mx = map[i][j];
        return mx;
    }

    public static int[][] scanFull(int[] begin) {
        int iter = 2;
        int[][] map = genMap();
        //1 is always a spawn room
        map[begin[0]][begin[1]] = 1;
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

    public static int[][] sync(int[][] map) {
        int[][] newMap = genMap();
        int[] playerCell = realToCell(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posZ);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (map[i][j] == -1 && newMap[i][j] != -1) {
                    if (!isFullyLoaded(i, j)) continue;
                    map[i][j] = 0;
                    System.out.println(i + ":" + j + " - " + map[i][j] + ":" + newMap[i][j]);
                    if (i+1 < 6 && map[i+1][j] != -1) {
                        map = req(map[i+1][j], map, i+1, j, Minecraft.getMinecraft().theWorld);
                    }
                    if (j+1 < 6 && map[i][j+1] != -1) {
                        map = req(map[i][j+1], map, i, j+1, Minecraft.getMinecraft().theWorld);
                    }
                    if (i-1 >= 0 && map[i-1][j] != -1) {
                        map = req(map[i-1][j], map, i-1, j, Minecraft.getMinecraft().theWorld);
                    }
                    if (j-1 >= 0 && map[i][j-1] != -1) {
                        map = req(map[i][j-1], map, i, j-1, Minecraft.getMinecraft().theWorld);
                    }
                    if (map[i][j] == 0) {
                        map[i][j] = findHighestIter(map) + 1;
                        map = req(map[i][j], map, i, j, Minecraft.getMinecraft().theWorld);
                    }
                    return sync(map);
                }
            }
        }
        return map;
    }
}
