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

public class RoomWaterfall extends RoomTemplate {
    public RoomWaterfall() {
        super(Room.Shape.r1X4, 8, "Waterfall");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(29, 92, 59), Blocks.stone),
                new AddressedData<>(new BlockPos(3, 67, 54), Blocks.leaves),
                new AddressedData<>(new BlockPos(22, 62, 73), Blocks.stone_brick_stairs),
                new AddressedData<>(new BlockPos(3, 89, 57), Blocks.bedrock)
        );
    }

    @Override
    public Route route() {
        return super.route().outlines(
                new AddressedData<>(new BlockPos(15, 80, 119), Color.cyan),
                new AddressedData<>(new BlockPos(15, 81, 121), Color.green),
                new AddressedData<>(new BlockPos(27, 78, 82), Color.green),
                new AddressedData<>(new BlockPos(27, 62, 69), Color.green),
                new AddressedData<>(new BlockPos(7, 84, 34), Color.green),
                new AddressedData<>(new BlockPos(9, 82, 42), Color.green),
                new AddressedData<>(new BlockPos(3, 43, 7), Color.green),
                new AddressedData<>(new BlockPos(3, 44, 53), Color.green),
                new AddressedData<>(new BlockPos(3, 46, 88), Color.green)
        ).blocks(
                new BBox(7, 82, 40, 9, 85, 38, Blocks.air.getDefaultState())
        );
    }
}
