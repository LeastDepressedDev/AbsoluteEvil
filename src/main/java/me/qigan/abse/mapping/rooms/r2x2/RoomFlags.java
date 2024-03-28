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

public class RoomFlags extends RoomTemplate {
    public RoomFlags() {
        super(Room.Shape.r2X2, 9, "Flags");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(new AddressedData<>(new BlockPos(-26, 73, 10), Blocks.hopper),
                new AddressedData<>(new BlockPos(15, 71, 43), Blocks.stonebrick),
                new AddressedData<>(new BlockPos(-15, 73, 57), Blocks.spruce_stairs),
                new AddressedData<>(new BlockPos(-9, 77, 20), Blocks.wool));
    }

    @Override
    public Route route() {
        return super.route().path(
                new BlockPos(-1, 70, 30),
                new BlockPos(-16, 71, 27),
                new BlockPos(-27, 66, 28),
                new BlockPos(-30, 67, 35),
                new BlockPos(-27, 66, 28),
                new BlockPos(-16, 71, 27),
                new BlockPos(-11, 71, 28),
                new BlockPos(17, 69, 47),
                new BlockPos(10, 70, 52),
                new BlockPos(7, 69, 52),
                new BlockPos(2, 73, 53),
                new BlockPos(-3, 77, 52),
                new BlockPos(-8, 87, 54),
                new BlockPos(-11, 87, 57),
                new BlockPos(-20, 87, 54),
                new BlockPos(-22, 87, 45),
                new BlockPos(-19, 87, 38),
                new BlockPos(-12, 85, 35),
                new BlockPos(21, 96, 22),
                new BlockPos(23, 96, 18),
                new BlockPos(23, 96, 13),
                new BlockPos(23, 96, 18),
                new BlockPos(21, 96, 22),
                new BlockPos(3, 97, 31),
                new BlockPos(-4, 108, 20),
                new BlockPos(-4, 107, 17),
                new BlockPos(-1, 107, 15),
                new BlockPos(-7, 107, 15),
                new BlockPos(-4, 107, 13)
        ).outlines(
                new AddressedData<>(new BlockPos(10, 69, 52), Color.cyan),
                new AddressedData<>(new BlockPos(21, 95, 22), Color.cyan),
                new AddressedData<>(new BlockPos(3, 96, 31), Color.cyan),
                new AddressedData<>(new BlockPos(-4, 107, 20), Color.cyan),
                new AddressedData<>(new BlockPos(3, 108, 14), Color.green),
                new AddressedData<>(new BlockPos(-11, 108, 14), Color.green),
                new AddressedData<>(new BlockPos(23, 97, 11), Color.green),
                new AddressedData<>(new BlockPos(-28, 67, 37), Color.green),
                new AddressedData<>(new BlockPos(7, 69, 53), Color.green),
                new AddressedData<>(new BlockPos(-5, 77, 52), Color.green),
                new AddressedData<>(new BlockPos(-11, 87, 59), Color.green)
        ).blocks(
                new BBox(9, 70, 52, 8, 71, 52, Blocks.air.getDefaultState())
        );
    }
}
