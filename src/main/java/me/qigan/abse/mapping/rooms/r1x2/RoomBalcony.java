package me.qigan.abse.mapping.rooms.r1x2;

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

public class RoomBalcony extends RoomTemplate {
    public RoomBalcony() {
        super(Room.Shape.r1X2, 21, "Balcony");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(10, 72, 14), Blocks.stone),
                new AddressedData<>(new BlockPos(29, 49, 32), Blocks.torch),
                new AddressedData<>(new BlockPos(10, 43, 43), Blocks.iron_bars),
                new AddressedData<>(new BlockPos(4, 60, 11), Blocks.stone)
        );
    }

    @Override
    public Route route() {
        return new Route()
                .path(
                        new BlockPos(15, 73, 20),
                        new BlockPos(15, 73, 23),
                        new BlockPos(15, 69, 26),
                        new BlockPos(18, 73, 26),
                        new BlockPos(17, 74, 30),
                        new BlockPos(15, 51, 36),
                        new BlockPos(15, 51, 32),
                        new BlockPos(15, 55, 28),
                        new BlockPos(15, 55, 26),
                        new BlockPos(13, 55, 16),
                        new BlockPos(9, 58, 4),
                        new BlockPos(13, 55, 16),
                        new BlockPos(20, 55, 22),
                        new BlockPos(24, 56, 18),
                        new BlockPos(24, 51, 18),
                        new BlockPos(24, 51, 14),
                        new BlockPos(24, 42, 14),
                        new BlockPos(12, 42, 13),
                        new BlockPos(4, 42, 19)
                ).outlines(
                        new AddressedData<>(new BlockPos(15, 50, 36), Color.cyan),
                        new AddressedData<>(new BlockPos(24, 55, 18), Color.cyan),
                        new AddressedData<>(new BlockPos(15, 69, 27), Color.green),
                        new AddressedData<>(new BlockPos(3, 55, 3), Color.green),
                        new AddressedData<>(new BlockPos(24, 50, 13), Color.green),
                        new AddressedData<>(new BlockPos(2, 42, 20), Color.green)
                ).blocks(
                        new BBox(24, 54, 18, 24, 51, 18, Blocks.air.getDefaultState()),
                        new BBox(24, 51, 18, 24, 52, 16, Blocks.air.getDefaultState())
                ).comments(
                        new AddressedData<>(new BlockPos(15, 73.5, 26), "\u00A7cTnt here")
                );
    }
}
