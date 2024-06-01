package me.qigan.abse.mapping.rooms.r2x2;

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

public class RoomAtlas extends RoomTemplate {
    public RoomAtlas() {
        super(Room.Shape.r2X2, 25, "Atlas");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(-16, 68, 17), Blocks.stone),
                new AddressedData<>(new BlockPos(12, 83, 28), Blocks.end_stone),
                new AddressedData<>(new BlockPos(13, 77, 56), Blocks.hardened_clay),
                new AddressedData<>(new BlockPos(-15, 66, 35), Blocks.leaves)
        );
    }

    @Override
    public Route route() {
        return new Route().path(
                new BlockPos(-1, 66, 40),
                new BlockPos(-1, 66, 48),
                new BlockPos(1, 76, 54),
                new BlockPos(-4, 89, 58),
                new BlockPos(-6, 78, 55),
                new BlockPos(-13, 78, 53),
                new BlockPos(-2, 81, 14),
                new BlockPos(15, 74, 38),
                new BlockPos(24, 78, 38),
                new BlockPos(24, 79, 41),
                new BlockPos(20, 69, 47),
                new BlockPos(20, 70, 50),
                new BlockPos(20, 66, 51),
                new BlockPos(21, 58, 50),
                new BlockPos(17, 57, 50),
                new BlockPos(15, 56, 46),
                new BlockPos(15, 56, 38),
                new BlockPos(15, 56, 46),
                new BlockPos(17, 57, 50),
                new BlockPos(21, 58, 50),
                new BlockPos(20, 66, 50),
                new BlockPos(20, 66, 51),
                new BlockPos(17, 70, 51),
                new BlockPos(15, 70, 49),
                new BlockPos(-19, 69, 16),
                new BlockPos(-23, 82, 17),
                new BlockPos(-24, 83, 20),
                new BlockPos(-25, 83, 25),
                new BlockPos(-24, 78, 30)
        ).outlines(
                new AddressedData<>(new BlockPos(1, 75, 54), Color.cyan),
                new AddressedData<>(new BlockPos(-4, 88, 58), Color.cyan),
                new AddressedData<>(new BlockPos(-2, 80, 14), Color.cyan),
                new AddressedData<>(new BlockPos(15, 73, 38), Color.cyan),
                new AddressedData<>(new BlockPos(-23, 81, 17), Color.cyan),
                new AddressedData<>(new BlockPos(2, 75, 54), Color.green),
                new AddressedData<>(new BlockPos(-3, 90, 60), Color.red),
                new AddressedData<>(new BlockPos(-1, 82, 11), Color.green),
                new AddressedData<>(new BlockPos(26, 78, 38), Color.green),
                new AddressedData<>(new BlockPos(15, 57, 36), Color.green),
                new AddressedData<>(new BlockPos(-22, 78, 31), Color.green)
        ).blocks(
                new BBox(2, 82, 13, -4, 82, 13, Blocks.air.getDefaultState()),
                new BBox(16, 75, 38, 16, 74, 38, Blocks.air.getDefaultState()),
                new BBox(15, 74, 39, 15, 75, 39, Blocks.stained_glass.getDefaultState()),
                new BBox(25, 77, 37, 23, 77, 38, Blocks.lapis_block.getDefaultState())
        );
    }
}
