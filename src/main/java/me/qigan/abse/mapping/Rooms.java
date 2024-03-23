package me.qigan.abse.mapping;

import me.qigan.abse.config.AddressedData;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.util.*;

public class Rooms {

    public static Map<Room.Shape, Collection<AddressedData<Collection<AddressedData<BlockPos, Block>>, Integer>>> rooms = new HashMap<>();
    public static Map<Integer, Collection<BBox>> routes = new HashMap<>();

    public static void setup() {
        setupRooms();
    }

    private static void setupRooms() {
        rooms.put(Room.Shape.r1X1, r1x1());
    }

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
        return temp;
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
            System.out.println(pos + " : " + ele.getObject() + " / " + Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock());
            if (Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() != ele.getObject()) return false;
        }
        return true;
    }

}
