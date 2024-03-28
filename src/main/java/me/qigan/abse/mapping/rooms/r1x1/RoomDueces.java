package me.qigan.abse.mapping.rooms.r1x1;

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

public class RoomDueces extends RoomTemplate {
    public RoomDueces() {
        super(Room.Shape.r1X1, 4, "Dueces");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(2, 73, 26), Blocks.bedrock),
                new AddressedData<>(new BlockPos(21, 78, 2), Blocks.torch),
                new AddressedData<>(new BlockPos(25, 70, 27), Blocks.leaves),
                new AddressedData<>(new BlockPos(2, 73, 6), Blocks.stone_slab)
        );
    }

    @Override
    public Route route() {
        return new Route()
                .outlines(
                        new AddressedData<>(new BlockPos(15, 73, 1), Color.cyan),
                        new AddressedData<>(new BlockPos(17, 71, 9), Color.cyan),
                        new AddressedData<>(new BlockPos(15, 78, 2), Color.green),
                        new AddressedData<>(new BlockPos(23, 71, 9), Color.green)
                )
                .blocks(
                        new BBox(18, 73, 9, 20, 72, 9, Blocks.air.getDefaultState())
                );
    }
}
