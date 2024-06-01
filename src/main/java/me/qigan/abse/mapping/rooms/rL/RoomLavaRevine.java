package me.qigan.abse.mapping.rooms.rL;

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

public class RoomLavaRevine extends RoomTemplate {
    public RoomLavaRevine() {
        super(Room.Shape.rL, 24, "Lava Revine");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(-1, 73, 37), Blocks.stone),
                new AddressedData<>(new BlockPos(23, 68, 46), Blocks.stone),
                new AddressedData<>(new BlockPos(13, 58, 48), Blocks.spruce_fence),
                new AddressedData<>(new BlockPos(7, 49, 40), Blocks.spruce_fence)
        );
    }

    @Override
    public Route route() {
        return new Route().outlines(
                new AddressedData<>(new BlockPos(-20, 87, 42), Color.magenta)
        ).blocks(
                new BBox(24, 68, 36, 26, 68, 35, Blocks.air.getDefaultState()),
                new BBox(9, 84, 52, 8, 85, 56, Blocks.air.getDefaultState()),
                new BBox(-20, 89, 42, -12, 88, 42, Blocks.air.getDefaultState()),
                new BBox(14, 66, 40, 15, 66, 39, Blocks.emerald_block.getDefaultState()),
                new BBox(5, 50, 44, 3, 50, 42, Blocks.lapis_block.getDefaultState())
        );
    }
}
