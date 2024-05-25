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

public class RoomRedBlue extends RoomTemplate {
    public RoomRedBlue() {
        super(Room.Shape.r1X3, 20, "RedBlue");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(16, 69, 79), Blocks.carpet),
                new AddressedData<>(new BlockPos(10, 70, 24), Blocks.stone_brick_stairs),
                new AddressedData<>(new BlockPos(9, 89, 46), Blocks.stone_stairs),
                new AddressedData<>(new BlockPos(10, 70, 42), Blocks.torch)
        );
    }

    @Override
    public Route route() {
        return new Route()
                .path(
                        new BlockPos(15, 69, 46),
                        new BlockPos(22, 69, 53),
                        new BlockPos(18, 69, 52),
                        new BlockPos(15, 69, 49),
                        new BlockPos(14, 83, 77),
                        new BlockPos(12, 85, 51),
                        new BlockPos(6, 89, 43),
                        new BlockPos(8, 85, 48),
                        new BlockPos(8, 86, 57)
                ).outlines(
                        new AddressedData<>(new BlockPos(14, 82, 77), Color.cyan),
                        new AddressedData<>(new BlockPos(12, 84, 51), Color.cyan),
                        new AddressedData<>(new BlockPos(6, 88, 43), Color.cyan),
                        new AddressedData<>(new BlockPos(25, 69, 54), Color.red),
                        new AddressedData<>(new BlockPos(15, 83, 79), Color.green),
                        new AddressedData<>(new BlockPos(25, 70, 57), Color.green),
                        new AddressedData<>(new BlockPos(4, 90, 39), Color.green),
                        new AddressedData<>(new BlockPos(8, 85, 59), Color.green)
                ).blocks(
                        new BBox(22, 70, 55, 25, 70, 53, Blocks.air.getDefaultState()),
                        new BBox(25, 70, 53, 24, 69, 53, Blocks.air.getDefaultState()),
                        new BBox(6, 90, 40, 2, 90, 43, Blocks.air.getDefaultState())
                );
    }
}
