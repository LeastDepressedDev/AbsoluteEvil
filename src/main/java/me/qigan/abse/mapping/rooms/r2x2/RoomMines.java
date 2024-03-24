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

public class RoomMines extends RoomTemplate {
    public RoomMines() {
        super(Room.Shape.r2X2, 6, "Mines");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(new AddressedData<>(new BlockPos(1, 101, 61), Blocks.stained_glass),
                new AddressedData<>(new BlockPos(-14, 94, 1), Blocks.stained_glass),
                new AddressedData<>(new BlockPos(-2, 37, 22), Blocks.stained_glass),
                new AddressedData<>(new BlockPos(14, 47, 49), Blocks.torch));
    }

    @Override
    public Route route() {
        return new Route().path(
                new BlockPos(18, 71, 30),
                new BlockPos(22, 71, 36),
                new BlockPos(23, 69, 44),
                new BlockPos(21, 79, 52),
                new BlockPos(20, 80, 51),
                new BlockPos(18, 80, 47),
                new BlockPos(18, 86, 47),
                new BlockPos(20, 87, 41),
                new BlockPos(20, 88, 35),
                new BlockPos(24, 88, 28),
                new BlockPos(26, 88, 22),
                new BlockPos(23, 88, 20),
                new BlockPos(14, 83, 19),
                new BlockPos(15, 81, 12),
                new BlockPos(12, 81, 9),
                new BlockPos(18, 84, 18),
                new BlockPos(26, 88, 22),
                new BlockPos(27, 88, 30),
                new BlockPos(23, 101, 29),
                new BlockPos(23, 100, 23),
                new BlockPos(23, 102, 15),
                new BlockPos(18, 107, 9),
                new BlockPos(15, 107, 8),
                new BlockPos(10, 110, 5),
                new BlockPos(2, 110, 7),
                new BlockPos(-2, 111, 6),
                new BlockPos(-10, 110, 6),
                new BlockPos(-12, 106, 8),
                new BlockPos(-13, 103, 11),
                new BlockPos(-11, 101, 18),
                new BlockPos(-11, 98, 30),
                new BlockPos(-18, 98, 32),
                new BlockPos(-24, 98, 31),
                new BlockPos(-18, 98, 32),
                new BlockPos(-11, 98, 30),
                new BlockPos(-20, 89, 11),
                new BlockPos(-21, 89, 8),
                new BlockPos(-26, 89, 7),
                new BlockPos(-27, 89, 3),
                new BlockPos(-29, 90, 28),
                new BlockPos(-29, 89, 31),
                new BlockPos(-29, 83, 34),
                new BlockPos(-26, 78, 32),
                new BlockPos(-26, 78, 35),
                new BlockPos(-27, 78, 46),
                new BlockPos(-28, 78, 54),
                new BlockPos(-27, 78, 56),
                new BlockPos(-22, 78, 53),
                new BlockPos(-18, 79, 54),
                new BlockPos(13, 46, 50),
                new BlockPos(17, 48, 53),
                new BlockPos(21, 48, 53),
                new BlockPos(17, 48, 53),
                new BlockPos(-4, 42, 55),
                new BlockPos(-11, 43, 56),
                new BlockPos(-15, 44, 58),
                new BlockPos(-18, 44, 60),
                new BlockPos(-15, 44, 58),
                new BlockPos(-11, 44, 55),
                new BlockPos(1, 55, 12),
                new BlockPos(-14, 55, 8),
                new BlockPos(-21, 55, 6),
                new BlockPos(-25, 54, 5)
        ).outlines(
                new AddressedData<>(new BlockPos(21, 78, 52), Color.cyan),
                new AddressedData<>(new BlockPos(23, 100, 29), Color.cyan),
                new AddressedData<>(new BlockPos(10, 110, 5), Color.cyan),
                new AddressedData<>(new BlockPos(-2, 110, 6), Color.cyan),
                new AddressedData<>(new BlockPos(-20, 88, 11), Color.cyan),
                new AddressedData<>(new BlockPos(-29, 89, 28), Color.cyan),
                new AddressedData<>(new BlockPos(-27, 78, 56), Color.cyan),
                new AddressedData<>(new BlockPos(13, 45, 50), Color.cyan),
                new AddressedData<>(new BlockPos(-4, 41, 55), Color.cyan),
                new AddressedData<>(new BlockPos(1, 54, 12), Color.cyan),
                new AddressedData<>(new BlockPos(-14, 54, 8), Color.cyan),
                new AddressedData<>(new BlockPos(21, 79, 52), Color.red),
                new AddressedData<>(new BlockPos(26, 81, 14), Color.red),
                new AddressedData<>(new BlockPos(-29, 79, 34), Color.red),
                new AddressedData<>(new BlockPos(18, 80, 41), Color.green),
                new AddressedData<>(new BlockPos(9, 82, 9), Color.green),
                new AddressedData<>(new BlockPos(13, 108, 9), Color.green),
                new AddressedData<>(new BlockPos(0, 109, 9), Color.green),
                new AddressedData<>(new BlockPos(-28, 99, 31), Color.green),
                new AddressedData<>(new BlockPos(-27, 88, 1), Color.green),
                new AddressedData<>(new BlockPos(-28, 78, 56), Color.green),
                new AddressedData<>(new BlockPos(21, 47, 53), Color.green),
                new AddressedData<>(new BlockPos(-20, 43, 60), Color.green),
                new AddressedData<>(new BlockPos(-25, 53, 5), Color.green)
        ).blocks(
                new BBox(10, 82, 10, 10, 82, 8, Blocks.air),
                new BBox(9, 111, 5, 8, 110, 5, Blocks.air),
                new BBox(-3, 112, 6, -9, 111, 6, Blocks.air),
                new BBox(-29, 91, 29, -29, 90, 30, Blocks.air),
                new BBox(-20, 79, 53, -20, 78, 53, Blocks.air)
        ).comments(
                new AddressedData<>(new BlockPos(21, 80, 52), "\u00A7aTNT here")
        );
    }
}
