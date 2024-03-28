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

public class RoomChains extends RoomTemplate {
    public RoomChains() {
        super(Room.Shape.r1X1, 12, "Chains");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(new AddressedData<>(new BlockPos(20, 90, 5), Blocks.iron_bars),
                new AddressedData<>(new BlockPos(15, 91, 8), Blocks.iron_bars),
                new AddressedData<>(new BlockPos(23, 73, 17), Blocks.log),
                new AddressedData<>(new BlockPos(7, 69, 16), Blocks.planks));
    }

    @Override
    public Route route() {
        return super.route().blocks(
            new BBox(19, 70, 24, 20, 69, 26, Blocks.air.getDefaultState())
        ).outlines(
                new AddressedData<>(new BlockPos(15, 84, 13), Color.cyan)
        );
    }
}
