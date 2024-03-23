package me.qigan.abse.mapping;

import me.qigan.abse.config.AddressedData;
import me.qigan.abse.mapping.rooms.r1x1.RoomDueces;
import me.qigan.abse.mapping.rooms.r1x1.RoomRacoon;
import me.qigan.abse.mapping.rooms.r1x1.RoomScaffolding;
import me.qigan.abse.mapping.rooms.r1x2.RoomBridges;
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


    public static Map<Room.Shape, List<RoomTemplate>> rooms = new HashMap<>();
    public static Map<Integer, Route> routes = new HashMap<>();



    /**
     *
     *                                         ROOMS
     *
     *                                   Current max id is 5
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
    public static void setup() {
        rooms.put(Room.Shape.r1X1, new ArrayList<>());
        rooms.put(Room.Shape.r1X2, new ArrayList<>());
        rooms.put(Room.Shape.r1X3, new ArrayList<>());
        rooms.put(Room.Shape.r1X4, new ArrayList<>());
        rooms.put(Room.Shape.r2X2, new ArrayList<>());
        rooms.put(Room.Shape.rL, new ArrayList<>());

        registerRoom(new RoomScaffolding());
        registerRoom(new RoomRacoon());
        registerRoom(new RoomDueces());
        registerRoom(new RoomBridges());
    }

    public static void registerRoom(RoomTemplate temple) {
        rooms.get(temple.getShape()).add(temple);
        routes.put(temple.getId(), temple.route());
    }

    public static int match(Room rm) {
        if (!rooms.containsKey(rm.getShape())) return -1;
        for (RoomTemplate temple : rooms.get(rm.getShape())) {
            if (check(temple.hooks(), rm)) return temple.getId();
        }
        return -1;
    }

    private static boolean check(Collection<AddressedData<BlockPos, Block>> rms, Room rm) {
        for (AddressedData<BlockPos, Block> ele : rms) {
            BlockPos pos = rm.transformInnerCoordinate(ele.getNamespace());
            if (Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() != ele.getObject()) return false;
        }
        return true;
    }

}
