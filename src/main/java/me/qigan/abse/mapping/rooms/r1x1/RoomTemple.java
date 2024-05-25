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

public class RoomTemple extends RoomTemplate {
    public RoomTemple() {
        super(Room.Shape.r1X1, 19, "Temple");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(22, 63, 19), Blocks.dirt),
                new AddressedData<>(new BlockPos(6, 63, 8), Blocks.dirt),
                new AddressedData<>(new BlockPos(3, 78, 13), Blocks.leaves),
                new AddressedData<>(new BlockPos(15, 91, 16), Blocks.stone)
        );
    }

    @Override
    public Route route() {
        return new Route()
                .outlines(
                        new AddressedData<>(new BlockPos(13, 64, 9), Color.cyan)
                ).blocks(
                        new BBox(13, 64, 9, 13, 60, 9, Blocks.air.getDefaultState()),
                        new BBox(2, 65, 6, 5, 63, 6, Blocks.stained_glass.getDefaultState()),
                        new BBox(5, 65, 5, 5, 63, 5, Blocks.stained_glass.getDefaultState()),
                        new BBox(6, 65, 5, 6, 64, 2, Blocks.stained_glass.getDefaultState()),
                        new BBox(11, 63, 17, 20, 61, 10, Blocks.stained_glass.getDefaultState())
                );
    }
}
