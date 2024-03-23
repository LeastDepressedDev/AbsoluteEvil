package me.qigan.abse.mapping;

import me.qigan.abse.config.AddressedData;
import me.qigan.abse.mapping.routing.BBox;
import me.qigan.abse.mapping.routing.Route;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * !!!IMPORTANT!!!
 * For future me: update max id when routing
 *
 *
 */
public class Rooms {


    public static Map<Room.Shape, Collection<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>>> rooms = new HashMap<>();
    public static Map<Integer, Route> routes = new HashMap<>();

    public static void setup() {
        setupRooms();
        setupRoutes();
    }

    private static void setupRooms() {
        rooms.put(Room.Shape.r1X1, r1x1());
        rooms.put(Room.Shape.r1X2, r1x2());
        rooms.put(Room.Shape.r1X3, r1x3());
        rooms.put(Room.Shape.r1X4, r1x4());
        rooms.put(Room.Shape.r2X2, r2x2());
        rooms.put(Room.Shape.rL, rL());
    }

    /**
     *
     *
     *                                              ROOMS
     *
     *                  Current max free id is 5
     */
    //1x1
    private static List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> r1x1() {
        List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> temp = new ArrayList<>();
        //Scaffolding
        temp.add(new AddressedData<>(
                Arrays.asList(
                        new AddressedData<>(new BlockPos(3, 70, 8), Blocks.stone_brick_stairs),
                        new AddressedData<>(new BlockPos(24, 74, 22), Blocks.stonebrick),
                        new AddressedData<>(new BlockPos(18, 69, 3), Blocks.anvil),
                        new AddressedData<>(new BlockPos(8, 75, 3), Blocks.cobblestone_wall)
                ), 1));
        //Racoon
        temp.add(new AddressedData<>(
                Arrays.asList(
                        new AddressedData<>(new BlockPos(2, 70, 3), Blocks.torch),
                        new AddressedData<>(new BlockPos(28, 85, 24), Blocks.stonebrick),
                        new AddressedData<>(new BlockPos(2, 70, 24), Blocks.lever),
                        new AddressedData<>(new BlockPos(22, 72, 23), Blocks.stonebrick)
                ), 2));
        //Dueces
        temp.add(new AddressedData<>(
                Arrays.asList(
                        new AddressedData<>(new BlockPos(2, 73, 26), Blocks.bedrock),
                        new AddressedData<>(new BlockPos(21, 78, 2), Blocks.torch),
                        new AddressedData<>(new BlockPos(25, 70, 27), Blocks.leaves),
                        new AddressedData<>(new BlockPos(2, 73, 6), Blocks.stone_slab)
                ), 4));
        return temp;
    }

    //1x2
    private static List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> r1x2() {
        List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> temp = new ArrayList<>();

        //Bridges
        temp.add(new AddressedData<>(
                Arrays.asList(
                        new AddressedData<>(new BlockPos(2, 59, 54), Blocks.lever),
                        new AddressedData<>(new BlockPos(28, 65, 25), Blocks.cauldron),
                        new AddressedData<>(new BlockPos(14, 53, 53), Blocks.light_weighted_pressure_plate),
                        new AddressedData<>(new BlockPos(27, 81, 4), Blocks.tallgrass)
                ), 3));
        return temp;
    }

    //1x3
    private static List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> r1x3() {
        List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> temp = new ArrayList<>();

        return temp;
    }

    //1x4
    private static List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> r1x4() {
        List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> temp = new ArrayList<>();

        return temp;
    }

    //2x2
    private static List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> r2x2() {
        List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> temp = new ArrayList<>();

        return temp;
    }

    //L
    private static List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> rL() {
        List<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>> temp = new ArrayList<>();

        return temp;
    }


    /**
     *
     *
     *                                          ROUTING
     *
     * Coloring format:
     *              RED - click on it
     *              GREEN - secret
     *              CYAN - etherwarp in it
     *
     */
    private static void setupRoutes() {
        //Bridges
        routes.put(3, new Route()
                .path(
                    new AddressedData<>(new BlockPos(17, 64, 30), Color.orange),
                    new AddressedData<>(new BlockPos(5, 58, 52), Color.orange),
                    new AddressedData<>(new BlockPos(10, 59, 51), Color.orange),
                    new AddressedData<>(new BlockPos(15, 58, 51), Color.orange),
                    new AddressedData<>(new BlockPos(15, 52, 51), Color.orange),
                    new AddressedData<>(new BlockPos(13, 52, 42), Color.orange),
                    new AddressedData<>(new BlockPos(10, 52, 41), Color.orange),
                    new AddressedData<>(new BlockPos(13, 52, 39), Color.orange),
                    new AddressedData<>(new BlockPos(16, 52, 20), Color.orange),
                    new AddressedData<>(new BlockPos(26, 52, 15), Color.orange),
                    new AddressedData<>(new BlockPos(22, 53, 10), Color.orange),
                    new AddressedData<>(new BlockPos(16, 57, 11), Color.orange),
                    new AddressedData<>(new BlockPos(17, 64, 20), Color.orange),
                    new AddressedData<>(new BlockPos(21, 82, 26), Color.orange),
                    new AddressedData<>(new BlockPos(14, 81, 6), Color.orange),
                    new AddressedData<>(new BlockPos(7, 81, 19), Color.orange),
                    new AddressedData<>(new BlockPos(7, 81, 55), Color.orange),
                    new AddressedData<>(new BlockPos(12, 81, 55), Color.orange),
                    new AddressedData<>(new BlockPos(7, 81, 55), Color.orange),
                    new AddressedData<>(new BlockPos(21, 81, 47), Color.orange),
                    new AddressedData<>(new BlockPos(27, 93, 50), Color.orange)
                )
                .outlines(
                        new AddressedData<>(new BlockPos(2, 59, 54), Color.red),
                        new AddressedData<>(new BlockPos(10, 58, 51), Color.cyan),
                        new AddressedData<>(new BlockPos(13, 52, 53), Color.green),
                        new AddressedData<>(new BlockPos(17, 53, 53), Color.green),
                        new AddressedData<>(new BlockPos(9, 52, 41), Color.green),
                        new AddressedData<>(new BlockPos(15, 82, 3), Color.green),
                        new AddressedData<>(new BlockPos(27, 93, 49), Color.green)
                )
                .blocks(
                        new BBox(11, 59, 51, 15, 58, 51, Blocks.air),
                        new BBox(15, 57, 51, 15, 57, 51, Blocks.air)
                )
        );
        //Dueces
        routes.put(4, new Route()
                .outlines(
                        new AddressedData<>(new BlockPos(15, 73, 1), Color.cyan),
                        new AddressedData<>(new BlockPos(17, 71, 9), Color.cyan),
                        new AddressedData<>(new BlockPos(15, 78, 2), Color.green),
                        new AddressedData<>(new BlockPos(23, 71, 9), Color.green)
                )
                .blocks(
                        new BBox(18, 73, 9, 20, 72, 9, Blocks.air)
                )
        );
    }

    public static int match(Room rm) {
        for (AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer> rms : rooms.getOrDefault(rm.getShape(), new ArrayList<>())) {
            if (check(rms, rm)) return rms.getObject();
        }
        return -1;
    }

    private static boolean check(AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer> rms, Room rm) {
        for (AddressedData<BlockPos, Block> ele : rms.getNamespace()) {
            BlockPos pos = rm.transformInnerCoordinate(ele.getNamespace());
            if (Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() != ele.getObject()) return false;
        }
        return true;
    }

}
