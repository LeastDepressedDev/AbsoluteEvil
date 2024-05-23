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

public class RoomRails extends RoomTemplate {
    public RoomRails() {
        super(Room.Shape.r2X2, 18, "Rails");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(25, 68, 58), Blocks.stonebrick),
                new AddressedData<>(new BlockPos(-8, 50, 54), Blocks.iron_ore),
                new AddressedData<>(new BlockPos(-13, 56, 18), Blocks.spruce_fence),
                new AddressedData<>(new BlockPos(15, 60, 11), Blocks.vine)
        );
    }

    @Override
    public Route route() {
        return new Route()
                .outlines(
                        new AddressedData<>(new BlockPos(-1, 71, 42), Color.cyan),
                        new AddressedData<>(new BlockPos(-1, 71, 41), Color.cyan),
                        new AddressedData<>(new BlockPos(-1, 72, 31), Color.cyan),
                        new AddressedData<>(new BlockPos(1, 70, 28), Color.cyan)
                ).blocks(
                        new BBox(-1, 74, 30, 2, 72, 30, Blocks.air.getDefaultState()),
                        new BBox(2, 73, 31, 1, 72, 29, Blocks.air.getDefaultState()),
                        new BBox(1, 72, 32, 1, 71, 29, Blocks.air.getDefaultState()),
                        new BBox(1, 70, 28, 1, 67, 28, Blocks.air.getDefaultState())
                );
    }
}
