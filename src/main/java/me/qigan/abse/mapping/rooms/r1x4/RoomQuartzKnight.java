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

public class RoomQuartzKnight extends RoomTemplate {
    public RoomQuartzKnight() {
        super(Room.Shape.r1X4, 22, "Quartz Knight");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(4, 69, 54), Blocks.dirt),
                new AddressedData<>(new BlockPos(30, 71, 68), Blocks.stonebrick),
                new AddressedData<>(new BlockPos(20, 86, 59), Blocks.iron_bars),
                new AddressedData<>(new BlockPos(29, 85, 52), Blocks.cobblestone)
        );
    }

    @Override
    public Route route() {
        return new Route().blocks(
                new BBox(13, 89, 32, 13, 89, 32, Blocks.lapis_block.getDefaultState()),
                new BBox(7, 90, 19, 5, 90, 20, Blocks.air.getDefaultState()),
                new BBox(25, 83, 65, 25, 76, 65, Blocks.air.getDefaultState()),
                new BBox(22, 86, 103, 21, 86, 106, Blocks.iron_block.getDefaultState()),
                new BBox(26, 88, 108, 25, 88, 110, Blocks.air.getDefaultState()),
                new BBox(25, 87, 108, 25, 88, 108, Blocks.oak_fence.getDefaultState())
        ).outlines(
                new AddressedData<>(new BlockPos(13, 89, 32), Color.cyan)
        );
    }
}
