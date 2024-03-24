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
        return Arrays.asList(new AddressedData<>(new BlockPos(29, 73, 109), Blocks.cobblestone_wall),
                new AddressedData<>(new BlockPos(2, 89, 82), Blocks.carpet),
                new AddressedData<>(new BlockPos(3, 81, 49), Blocks.leaves),
                new AddressedData<>(new BlockPos(27, 81, 15), Blocks.leaves));
    }

    @Override
    public Route route() {
        return super.route().path(
                new BlockPos(15, 69, 111),
                new BlockPos(15, 81, 119),
                new BlockPos(23, 81, 114),
                new BlockPos(23, 81, 98),
                new BlockPos(26, 78, 82),
                new BlockPos(24, 80, 82),
                new BlockPos(18, 60, 78),
                new BlockPos(24, 60, 71),
                new BlockPos(20, 61, 55),
                new BlockPos(25, 65, 35),
                new BlockPos(25, 69, 24),
                new BlockPos(22, 69, 12),
                new BlockPos(20, 79, 16),
                new BlockPos(16, 79, 23),
                new BlockPos(19, 81, 28),
                new BlockPos(13, 83, 31),
                new BlockPos(9, 83, 34),
                new BlockPos(13, 83, 31),
                new BlockPos(19, 81, 28),
                new BlockPos(16, 79, 23),
                new BlockPos(16, 79, 15),
                new BlockPos(12, 69, 6),
                new BlockPos(9, 54, 7),
                new BlockPos(17, 44, 6),
                new BlockPos(7, 44, 5),
                new BlockPos(3, 44, 7),
                new BlockPos(7, 44, 5),
                new BlockPos(20, 44, 18),
                new BlockPos(24, 44, 28),
                new BlockPos(17, 44, 40),
                new BlockPos(5, 44, 42),
                new BlockPos(3, 44, 46),
                new BlockPos(3, 44, 51),
                new BlockPos(3, 44, 46),
                new BlockPos(5, 44, 42),
                new BlockPos(17, 44, 40),
                new BlockPos(18, 44, 52),
                new BlockPos(21, 44, 67),
                new BlockPos(19, 44, 75),
                new BlockPos(15, 45, 82),
                new BlockPos(5, 45, 87)
        ).outlines(
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
                new BBox(7, 82, 40, 9, 85, 38, Blocks.air)
        );
    }
}
