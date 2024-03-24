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

public class RoomGrassRuins extends RoomTemplate {
    public RoomGrassRuins() {
        super(Room.Shape.r1X2, 13, "Grass Ruins");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(new AddressedData<>(new BlockPos(8, 111, 38), Blocks.prismarine),
                new AddressedData<>(new BlockPos(13, 96, 14), Blocks.vine),
                new AddressedData<>(new BlockPos(27, 72, 8), Blocks.leaves),
                new AddressedData<>(new BlockPos(16, 56, 30), Blocks.grass));
    }

    @Override
    public Route route() {
        return super.route().path(
                new BlockPos(17, 65, 24),
                new BlockPos(14, 107, 15),
                new BlockPos(18, 107, 12),
                new BlockPos(14, 107, 15),
                new BlockPos(11, 90, 44),
                new BlockPos(9, 80, 42),
                new BlockPos(6, 80, 44),
                new BlockPos(5, 83, 35),
                new BlockPos(6, 80, 31),
                new BlockPos(8, 80, 25),
                new BlockPos(14, 65, 22),
                new BlockPos(15, 63, 29),
                new BlockPos(7, 59, 32),
                new BlockPos(8, 54, 38),
                new BlockPos(8, 54, 34),
                new BlockPos(8, 52, 31),
                new BlockPos(15, 55, 9),
                new BlockPos(24, 52, 30),
                new BlockPos(24, 63, 31)
        ).outlines(
                new AddressedData<>(new BlockPos(14, 106, 15), Color.cyan),
                new AddressedData<>(new BlockPos(11, 89, 44), Color.cyan),
                new AddressedData<>(new BlockPos(9, 79, 42), Color.cyan),
                new AddressedData<>(new BlockPos(15, 54, 9), Color.cyan),
                new AddressedData<>(new BlockPos(24, 51, 30), Color.cyan),
                new AddressedData<>(new BlockPos(24, 62, 31), Color.cyan),
                new AddressedData<>(new BlockPos(20, 107, 11), Color.green),
                new AddressedData<>(new BlockPos(6, 81, 48), Color.green),
                new AddressedData<>(new BlockPos(2, 82, 31), Color.red),
                new AddressedData<>(new BlockPos(6, 53, 31), Color.red),
                new AddressedData<>(new BlockPos(16, 55, 3), Color.green)
        ).blocks(
                new BBox(7, 81, 46, 5, 81, 47, Blocks.air),
                new BBox(8, 55, 37, 8, 54, 34, Blocks.air)
        );
    }
}
