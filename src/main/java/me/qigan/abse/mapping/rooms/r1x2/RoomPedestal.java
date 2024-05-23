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

public class RoomPedestal extends RoomTemplate {
    public RoomPedestal() {
        super(Room.Shape.r1X2, 16, "Pedestal");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(5, 92, 7), Blocks.bedrock),
                new AddressedData<>(new BlockPos(29, 80, 36), Blocks.cobblestone_wall),
                new AddressedData<>(new BlockPos(20, 79, 57), Blocks.stone),
                new AddressedData<>(new BlockPos(8, 63, 31), Blocks.stained_hardened_clay)
        );
    }

    @Override
    public Route route() {
        return new Route()
                .path(
                        new BlockPos(18, 80, 25),
                        new BlockPos(12, 94, 6),
                        new BlockPos(20, 69, 27),
                        new BlockPos(15, 70, 30),
                        new BlockPos(8, 72, 31),
                        new BlockPos(7, 72, 31),
                        new BlockPos(7, 63, 31),
                        new BlockPos(5, 69, 33),
                        new BlockPos(5, 69, 40),
                        new BlockPos(10, 69, 47),
                        new BlockPos(10, 69, 56),
                        new BlockPos(10, 61, 56),
                        new BlockPos(10, 61, 53),
                        new BlockPos(10, 69, 54),
                        new BlockPos(12, 69, 40)
                ).outlines(
                        new AddressedData<>(new BlockPos(12, 93, 6), Color.cyan),
                        new AddressedData<>(new BlockPos(20, 68, 27), Color.cyan),
                        new AddressedData<>(new BlockPos(8, 71, 31), Color.cyan),
                        new AddressedData<>(new BlockPos(5, 68, 33), Color.cyan),
                        new AddressedData<>(new BlockPos(10, 68, 54), Color.cyan),
                        new AddressedData<>(new BlockPos(9, 94, 7), Color.green),
                        new AddressedData<>(new BlockPos(15, 70, 31), Color.green),
                        new AddressedData<>(new BlockPos(7, 60, 32), Color.green),
                        new AddressedData<>(new BlockPos(12, 60, 51), Color.green),
                        new AddressedData<>(new BlockPos(8, 61, 53), Color.green)
                ).blocks(
                    new BBox(7, 73, 31, 7, 67, 31, Blocks.air.getDefaultState())
                );
    }
}
