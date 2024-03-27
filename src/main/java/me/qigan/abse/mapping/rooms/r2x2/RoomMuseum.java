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

public class RoomMuseum extends RoomTemplate {
    public RoomMuseum() {
        super(Room.Shape.r2X2, 11, "Museum");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(new AddressedData<>(new BlockPos(6, 69, 48), Blocks.leaves),
                new AddressedData<>(new BlockPos(-31, 74, 15), Blocks.cauldron),
                new AddressedData<>(new BlockPos(1, 72, 4), Blocks.wool),
                new AddressedData<>(new BlockPos(28, 72, 37), Blocks.stone_slab));
    }

    @Override
    public Route route() {
        return super.route().path(
                new BlockPos(-17, 69, 31),
                new BlockPos(-25, 69, 31),
                new BlockPos(-20, 82, 38),
                new BlockPos(-22, 93, 43),
                new BlockPos(-21, 82, 44),
                new BlockPos(-20, 82, 10),
                new BlockPos(-8, 82, 9),
                new BlockPos(9, 82, 7),
                new BlockPos(23, 82, 7),
                new BlockPos(21, 82, 22),
                new BlockPos(22, 88, 28),
                new BlockPos(22, 88, 33),
                new BlockPos(22, 82, 37),
                new BlockPos(19, 82, 39),
                new BlockPos(17, 82, 35),
                new BlockPos(17, 69, 35),
                new BlockPos(20, 69, 33),
                new BlockPos(22, 69, 39),
                new BlockPos(22, 66, 39),
                new BlockPos(22, 66, 41),
                new BlockPos(23, 61, 45)
        ).outlines(
                new AddressedData<>(new BlockPos(-20, 81, 38), Color.cyan),
                new AddressedData<>(new BlockPos(-22, 92, 43), Color.cyan),
                new AddressedData<>(new BlockPos(17, 82, 35), Color.cyan),
                new AddressedData<>(new BlockPos(22, 87, 28), Color.cyan),
                new AddressedData<>(new BlockPos(-30, 70, 31), Color.green),
                new AddressedData<>(new BlockPos(-23, 93, 42), Color.green),
                new AddressedData<>(new BlockPos(21, 84, 26), Color.red),
                new AddressedData<>(new BlockPos(19, 83, 34), Color.green),
                new AddressedData<>(new BlockPos(21, 70, 31), Color.green),
                new AddressedData<>(new BlockPos(24, 62, 48), Color.green)
        ).blocks(
                new BBox(-27, 70, 30, -28, 70, 32, Blocks.air),
                new BBox(22, 89, 29, 22, 88, 33, Blocks.air),
                new BBox(17, 81, 35, 17, 81, 35, Blocks.air),
                new BBox(22, 68, 38, 23, 68, 38, Blocks.stained_glass),
                new BBox(23, 68, 39, 23, 68, 39, Blocks.stained_glass),
                new BBox(22, 68, 39, 22, 65, 39, Blocks.air),
                new BBox(22, 66, 39, 22, 65, 41, Blocks.air)
        );
    }
}
