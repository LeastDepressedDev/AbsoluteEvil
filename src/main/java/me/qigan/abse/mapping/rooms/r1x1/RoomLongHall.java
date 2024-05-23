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

public class RoomLongHall extends RoomTemplate {
    public RoomLongHall() {
        super(Room.Shape.r1X1, 17, "Long Hall");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(28, 71, 5), Blocks.bookshelf),
                new AddressedData<>(new BlockPos(11, 68, 24), Blocks.dirt),
                new AddressedData<>(new BlockPos(11, 83, 4), Blocks.stone_brick_stairs),
                new AddressedData<>(new BlockPos(20, 86, 6), Blocks.stained_hardened_clay)
        );
    }

    @Override
    public Route route() {
        return new Route()
                .outlines(
                        new AddressedData<>(new BlockPos(7, 68, 16), Color.cyan),
                        new AddressedData<>(new BlockPos(10, 87, 21), Color.cyan),
                        new AddressedData<>(new BlockPos(6, 82, 28), Color.cyan),
                        new AddressedData<>(new BlockPos(16, 68, 10), Color.cyan),
                        new AddressedData<>(new BlockPos(7, 62, 18), Color.green),
                        new AddressedData<>(new BlockPos(5, 81, 28), Color.green),
                        new AddressedData<>(new BlockPos(15, 82, 28), Color.green)
                ).path(
                        new BlockPos(14, 69, 18),
                        new BlockPos(9, 69, 21),
                        new BlockPos(5, 69, 19),
                        new BlockPos(4, 69, 11),
                        new BlockPos(4, 69, 4),
                        new BlockPos(5, 59, 7),
                        new BlockPos(6, 61, 17),
                        new BlockPos(7, 69, 16),
                        new BlockPos(17, 69, 18),
                        new BlockPos(10, 88, 21),
                        new BlockPos(8, 87, 21),
                        new BlockPos(5, 81, 20),
                        new BlockPos(4, 81, 27),
                        new BlockPos(6, 83, 28),
                        new BlockPos(12, 83, 28),
                        new BlockPos(15, 81, 27),
                        new BlockPos(16, 69, 10)
                ).blocks(
                        new BBox(9, 88, 21, 7, 89, 21, Blocks.air.getDefaultState()),
                        new BBox(7, 83, 28, 13, 84, 28, Blocks.air.getDefaultState())
                );
    }
}
