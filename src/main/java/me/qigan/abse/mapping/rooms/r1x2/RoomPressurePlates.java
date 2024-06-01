package me.qigan.abse.mapping.rooms.r1x2;

import me.qigan.abse.config.AddressedData;
import me.qigan.abse.mapping.Room;
import me.qigan.abse.mapping.RoomTemplate;
import me.qigan.abse.mapping.routing.BBox;
import me.qigan.abse.mapping.routing.Route;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public class RoomPressurePlates extends RoomTemplate {
    public RoomPressurePlates() {
        super(Room.Shape.r1X2, 26, "Pressure Plates");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(23, 68, 38), Blocks.stone),
                new AddressedData<>(new BlockPos(5, 98, 5), Blocks.wooden_slab),
                new AddressedData<>(new BlockPos(18, 80, 22), Blocks.stone),
                new AddressedData<>(new BlockPos(16, 92, 52), Blocks.stone_brick_stairs)
        );
    }

    @Override
    public Route route() {
        return new Route().blocks(
                new BBox(5, 69, 11, 9, 69, 12,
                        Blocks.stone_slab.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM)),
                new BBox(12, 69, 10, 11, 69, 4,
                        Blocks.stone_slab.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM)),
                new BBox(5, 70, 11, 9, 71, 11, Blocks.air.getDefaultState()),
                new BBox(11, 70, 10, 11, 71, 4, Blocks.air.getDefaultState()),
                new BBox(15, 70, 17, 15, 70, 18, Blocks.air.getDefaultState()),
                new BBox(17, 69, 32, 17, 78, 30, Blocks.stained_glass.getDefaultState()),
                new BBox(17, 77, 29, 17, 80, 29, Blocks.air.getDefaultState()),
                new BBox(18, 93, 59, 20, 94, 59, Blocks.air.getDefaultState()),
                new BBox(19, 92, 59, 19, 91, 59, Blocks.air.getDefaultState()),
                new BBox(20, 93, 59, 20, 93, 59, Blocks.oak_fence.getDefaultState())
        ).outlines(
                new AddressedData<>(new BlockPos(5, 71, 5), Color.red),
                new AddressedData<>(new BlockPos(7, 69, 6), Color.green),
                new AddressedData<>(new BlockPos(15, 70, 20), Color.green),
                new AddressedData<>(new BlockPos(23, 59, 23), Color.green),
                new AddressedData<>(new BlockPos(23, 68, 57), Color.green),
                new AddressedData<>(new BlockPos(15, 83, 21), Color.red),
                new AddressedData<>(new BlockPos(15, 94, 59), Color.green),
                new AddressedData<>(new BlockPos(23, 94, 60), Color.green),
                new AddressedData<>(new BlockPos(17, 77, 29), Color.magenta)
        ).comments(
                new AddressedData<>(new BlockPos(18, 58, 27), "\u00A7cJerry chine after pearl")
        );
    }
}
