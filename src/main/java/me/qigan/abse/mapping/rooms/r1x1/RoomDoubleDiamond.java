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

public class RoomDoubleDiamond extends RoomTemplate {
    public RoomDoubleDiamond() {
        super(Room.Shape.r1X1, 15, "Double Diamond");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(25, 81, 3), Blocks.stained_hardened_clay),
                new AddressedData<>(new BlockPos(28, 75, 23), Blocks.diamond_block),
                new AddressedData<>(new BlockPos(3, 69, 5), Blocks.stone),
                new AddressedData<>(new BlockPos(27, 65, 20), Blocks.cobblestone_wall)
        );
    }

    @Override
    public Route route() {
        return new Route()
                .path(
                        new BlockPos(14, 69, 6),
                        new BlockPos(22, 69, 6),
                        new BlockPos(14, 69, 6),
                        new BlockPos(15, 65, 24),
                        new BlockPos(8, 65, 24),
                        new BlockPos(15, 65, 24),
                        new BlockPos(17, 81, 16),
                        new BlockPos(13, 82, 12)
                )
                .outlines(
                        new AddressedData<>(new BlockPos(17, 80, 16), Color.cyan),
                        new AddressedData<>(new BlockPos(24, 69, 6), Color.green),
                        new AddressedData<>(new BlockPos(8, 64, 24), Color.green),
                        new AddressedData<>(new BlockPos(11, 82, 11), Color.green)
                ).blocks(
                        new BBox(12, 83, 12, 10, 83, 10, Blocks.air.getDefaultState()),
                        new BBox(15, 80, 15, 19, 80, 17, Blocks.stained_glass.getDefaultState())
                );
    }
}
