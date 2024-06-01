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

public class RoomCrypt extends RoomTemplate {
    public RoomCrypt() {
        super(Room.Shape.r1X2, 23, "Crypt");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(6, 70, 2), Blocks.leaves),
                new AddressedData<>(new BlockPos(28, 71, 3), Blocks.cobblestone_wall),
                new AddressedData<>(new BlockPos(29, 70, 48), Blocks.stone),
                new AddressedData<>(new BlockPos(8, 59, 31), Blocks.gold_block)
        );
    }

    @Override
    public Route route() {
        return new Route().path(
                new BlockPos(15, 70, 39),
                new BlockPos(15, 67, 35),
                new BlockPos(15, 59, 30),
                new BlockPos(15, 59, 16),
                new BlockPos(15, 59, 30),
                new BlockPos(10, 73, 39),
                new BlockPos(6, 73, 39),
                new BlockPos(6, 73, 37),
                new BlockPos(8, 91, 35),
                new BlockPos(13, 91, 35),
                new BlockPos(15, 91, 41),
                new BlockPos(15, 88, 45),
                new BlockPos(15, 91, 41),
                new BlockPos(13, 91, 35),
                new BlockPos(8, 91, 35),
                new BlockPos(8, 81, 32),
                new BlockPos(5, 83, 26),
                new BlockPos(4, 84, 22),
                new BlockPos(7, 85, 18),
                new BlockPos(10, 85, 16),
                new BlockPos(12, 85, 12),
                new BlockPos(14, 81, 4),
                new BlockPos(22, 88, 13),
                new BlockPos(26, 88, 19),
                new BlockPos(20, 88, 21),
                new BlockPos(20, 82, 21),
                new BlockPos(20, 82, 23),
                new BlockPos(20, 81, 26),
                new BlockPos(20, 81, 31),
                new BlockPos(26, 81, 35),
                new BlockPos(28, 81, 38),
                new BlockPos(28, 82, 42),
                new BlockPos(24, 85, 52),
                new BlockPos(20, 86, 56),
                new BlockPos(13, 86, 55),
                new BlockPos(12, 86, 52),
                new BlockPos(4, 86, 51),
                new BlockPos(6, 86, 46),
                new BlockPos(6, 69, 46)
        ).outlines(
                new AddressedData<>(new BlockPos(20, 88, 21), Color.cyan),
                new AddressedData<>(new BlockPos(6, 86, 46), Color.cyan),
                new AddressedData<>(new BlockPos(10, 72, 39), Color.cyan),
                new AddressedData<>(new BlockPos(15, 60, 13), Color.green),
                new AddressedData<>(new BlockPos(15, 87, 46), Color.green),
                new AddressedData<>(new BlockPos(14, 81, 2), Color.green),
                new AddressedData<>(new BlockPos(28, 89, 19), Color.green),
                new AddressedData<>(new BlockPos(4, 86, 44), Color.green)
        ).blocks(
                new BBox(20, 87, 21, 20, 82, 21, Blocks.air.getDefaultState()),
                new BBox(20, 82, 22, 20, 83, 23, Blocks.air.getDefaultState()),
                new BBox(6, 85, 46, 6, 80, 46, Blocks.air.getDefaultState()),
                new BBox(9, 74, 39, 7, 73, 39, Blocks.air.getDefaultState()),
                new BBox(8, 74, 40, 7, 73, 40, Blocks.stained_glass.getDefaultState())
        );
    }
}
