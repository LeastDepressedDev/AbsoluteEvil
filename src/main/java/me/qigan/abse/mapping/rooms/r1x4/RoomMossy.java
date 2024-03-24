package me.qigan.abse.mapping.rooms.r1x4;

import me.qigan.abse.config.AddressedData;
import me.qigan.abse.mapping.Room;
import me.qigan.abse.mapping.RoomTemplate;
import me.qigan.abse.mapping.routing.BBox;
import me.qigan.abse.mapping.routing.Route;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public class RoomMossy extends RoomTemplate {
    public RoomMossy() {
        super(Room.Shape.r1X4, 14, "Mossy");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(new AddressedData<>(new BlockPos(6, 73, 9), Blocks.iron_bars),
                new AddressedData<>(new BlockPos(28, 72, 66), Blocks.stone),
                new AddressedData<>(new BlockPos(1, 80, 97), Blocks.planks),
                new AddressedData<>(new BlockPos(7, 89, 23), Blocks.cobblestone_wall));
    }

    @Override
    public Route route() {
        return super.route().path(
                new BlockPos(22, 69, 7),
                new BlockPos(21, 69, 19),
                new BlockPos(20, 70, 38),
                new BlockPos(16, 70, 59),
                new BlockPos(6, 71, 63),
                new BlockPos(2, 71, 63),
                new BlockPos(6, 71, 63),
                new BlockPos(15, 70, 63),
                new BlockPos(17, 94, 63),
                new BlockPos(22, 89, 49)
        ).outlines(
                new AddressedData<>(new BlockPos(17, 93, 63), Color.cyan),
                new AddressedData<>(new BlockPos(26, 69, 2), Color.green),
                new AddressedData<>(new BlockPos(1, 70, 63), Color.red),
                new AddressedData<>(new BlockPos(15, 95, 63), Color.green),
                new AddressedData<>(new BlockPos(26, 89, 47), Color.green)
        ).blocks(
                new BBox(23, 90, 49, 24, 91, 47, Blocks.air),
                new BBox(22, 70, 4, 25, 70, 5, Blocks.air),
                new BBox(25, 69, 4, 25, 69, 4, Blocks.air)
        );
    }
}
