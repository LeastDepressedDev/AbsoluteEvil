package me.qigan.abse.mapping.rooms.r1x3;

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

public class RoomGravel extends RoomTemplate {
    public RoomGravel() {
        super(Room.Shape.r1X3, 10, "Gravel");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(new AddressedData<>(new BlockPos(29, 72, 37), Blocks.cobblestone_wall),
                new AddressedData<>(new BlockPos(5, 70, 65), Blocks.hopper),
                new AddressedData<>(new BlockPos(8, 69, 21), Blocks.cobblestone_wall),
                new AddressedData<>(new BlockPos(15, 80, 2), Blocks.iron_bars));
    }

    @Override
    public Route route() {
        return super.route().path(
                new BlockPos(7, 69, 29),
                new BlockPos(7, 69, 39),
                new BlockPos(16, 69, 47),
                new BlockPos(26, 73, 50),
                new BlockPos(22, 69, 56),
                new BlockPos(26, 69, 59),
                new BlockPos(26, 73, 54),
                new BlockPos(26, 73, 50),
                new BlockPos(20, 81, 41),
                new BlockPos(17, 81, 39),
                new BlockPos(16, 81, 37),
                new BlockPos(16, 87, 37),
                new BlockPos(21, 69, 39),
                new BlockPos(23, 69, 6),
                new BlockPos(23, 70, 3),
                new BlockPos(25, 83, 6),
                new BlockPos(22, 83, 3),
                new BlockPos(21, 83, 3),
                new BlockPos(21, 88, 3),
                new BlockPos(19, 88, 4)
        ).outlines(
                new AddressedData<>(new BlockPos(20, 80, 41), Color.cyan),
                new AddressedData<>(new BlockPos(25, 82, 6), Color.cyan),
                new AddressedData<>(new BlockPos(4, 69, 31), Color.red),
                new AddressedData<>(new BlockPos(27, 69, 60), Color.green),
                new AddressedData<>(new BlockPos(22, 68, 56), Color.green),
                new AddressedData<>(new BlockPos(16, 87, 34), Color.green),
                new AddressedData<>(new BlockPos(18, 86, 36), Color.green),
                new AddressedData<>(new BlockPos(19, 69, 3), Color.green),
                new AddressedData<>(new BlockPos(19, 91, 7), Color.green)
        ).blocks(
                new BBox(5, 70, 30, 5, 69, 32, Blocks.air),
                new BBox(22, 71, 3, 19, 70, 4, Blocks.air)
        );
    }
}
