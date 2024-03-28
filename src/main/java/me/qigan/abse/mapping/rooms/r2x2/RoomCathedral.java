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

public class RoomCathedral extends RoomTemplate {
    public RoomCathedral() {
        super(Room.Shape.r2X2, 7, "Cathedral");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(new AddressedData<>(new BlockPos(-25, 91, 31), Blocks.web),
                new AddressedData<>(new BlockPos(12, 84, 14), Blocks.cobblestone_wall),
                new AddressedData<>(new BlockPos(8, 59, 48), Blocks.cobblestone_wall));
    }

    @Override
    public Route route() {
        return super.route().path(
                new BlockPos(0, 66, 30),
                new BlockPos(-3, 66, 41),
                new BlockPos(-3, 59, 41),
                new BlockPos(-7, 59, 40),
                new BlockPos(-12, 59, 44),
                new BlockPos(-12, 59, 50),
                new BlockPos(-19, 59, 51),
                new BlockPos(-24, 81, 46),
                new BlockPos(-27, 88, 38),
                new BlockPos(-23, 93, 34),
                new BlockPos(-18, 93, 33),
                new BlockPos(-27, 81, 32),
                new BlockPos(-27, 83, 23),
                new BlockPos(-17, 86, 20),
                new BlockPos(-13, 83, 20),
                new BlockPos(-13, 90, 19),
                new BlockPos(-13, 90, 16),
                new BlockPos(6, 84, 2),
                new BlockPos(10, 69, 7),
                new BlockPos(5, 64, 9),
                new BlockPos(12, 59, 9),
                new BlockPos(22, 59, 9),
                new BlockPos(23, 59, 17),
                new BlockPos(22, 59, 9),
                new BlockPos(12, 59, 9),
                new BlockPos(5, 64, 9),
                new BlockPos(14, 69, 3),
                new BlockPos(19, 82, 17),
                new BlockPos(19, 81, 29),
                new BlockPos(24, 81, 30),
                new BlockPos(19, 81, 31),
                new BlockPos(18, 81, 32),
                new BlockPos(19, 81, 45),
                new BlockPos(26, 85, 59),
                new BlockPos(23, 83, 56),
                new BlockPos(11, 60, 53),
                new BlockPos(7, 59, 49)
        ).outlines(
                new AddressedData<>(new BlockPos(-3, 66, 41), Color.cyan),
                new AddressedData<>(new BlockPos(-24, 80, 46), Color.cyan),
                new AddressedData<>(new BlockPos(-17, 85, 20), Color.cyan),
                new AddressedData<>(new BlockPos(-13, 89, 19), Color.cyan),
                new AddressedData<>(new BlockPos(6, 83, 2), Color.cyan),
                new AddressedData<>(new BlockPos(19, 81, 17), Color.cyan),
                new AddressedData<>(new BlockPos(-6, 59, 38), Color.green),
                new AddressedData<>(new BlockPos(-18, 92, 33), Color.green),
                new AddressedData<>(new BlockPos(-13, 89, 19), Color.green),
                new AddressedData<>(new BlockPos(23, 60, 19), Color.green),
                new AddressedData<>(new BlockPos(27, 82, 28), Color.green),
                new AddressedData<>(new BlockPos(26, 84, 59), Color.green),
                new AddressedData<>(new BlockPos(7, 58, 49), Color.green)
        ).blocks(
                new BBox(-3, 65, 41, -3, 64, 41, Blocks.air.getDefaultState()),
                new BBox(-16, 87, 20, -15, 86, 20, Blocks.air.getDefaultState()),
                new BBox(6, 83, 2, 6, 83, 2, Blocks.air.getDefaultState())
        );
    }
}
