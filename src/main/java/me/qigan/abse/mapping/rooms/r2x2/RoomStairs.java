package me.qigan.abse.mapping.rooms.r2x2;

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

public class RoomStairs extends RoomTemplate {
    public RoomStairs() {
        super(Room.Shape.r2X2, 5, "Stairs");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(24, 71, 4), Blocks.stonebrick),
                new AddressedData<>(new BlockPos(-1, 73, 19), Blocks.stone_brick_stairs),
                new AddressedData<>(new BlockPos(-23, 70, 5), Blocks.spruce_fence),
                new AddressedData<>(new BlockPos(23, 69, 56), Blocks.melon_block)
        );
    }

    @Override
    public Route route() {
        return new Route().path(
                new BlockPos(23, 70, 6),
                new BlockPos(17, 73, 7),
                new BlockPos(5, 71, 12),
                new BlockPos(3, 79, 16),
                new BlockPos(-1, 79, 21),
                new BlockPos(-1, 79, 44),
                new BlockPos(3, 83, 44),
                new BlockPos(-2, 79, 40),
                new BlockPos(6, 89, 31),
                new BlockPos(-5, 87, 36)
        ).outlines(
                new AddressedData<>(new BlockPos(3, 78, 16), Color.cyan),
                new AddressedData<>(new BlockPos(3, 82, 44), Color.cyan),
                new AddressedData<>(new BlockPos(6, 88, 31), Color.cyan),
                new AddressedData<>(new BlockPos(-5, 86, 36), Color.cyan),
                new AddressedData<>(new BlockPos(17, 72, 7), Color.cyan),
                new AddressedData<>(new BlockPos(5, 70, 12), Color.cyan),
                new AddressedData<>(new BlockPos(21, 70, 8), Color.green),
                new AddressedData<>(new BlockPos(2, 88, 44), Color.green),
                new AddressedData<>(new BlockPos(8, 88, 31), Color.green),
                new AddressedData<>(new BlockPos(7, 86, 37), Color.green)
        ).blocks(
                new BBox(3, 86, 44, 2, 87, 44, Blocks.air)
        );
    }
}
