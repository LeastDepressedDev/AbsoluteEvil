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

public class RoomBridges extends RoomTemplate {
    public RoomBridges() {
        super(Room.Shape.r1X2, 3, "Bridges");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(2, 59, 54), Blocks.lever),
                new AddressedData<>(new BlockPos(28, 65, 25), Blocks.cauldron),
                new AddressedData<>(new BlockPos(14, 53, 53), Blocks.light_weighted_pressure_plate),
                new AddressedData<>(new BlockPos(27, 81, 4), Blocks.tallgrass)
        );
    }

    @Override
    public Route route() {
        return new Route()
                .path(
                        new BlockPos(17, 64, 30),
                        new BlockPos(12, 64, 39),
                        new BlockPos(6, 64, 44),
                        new BlockPos(5, 58, 52),
                        new BlockPos(10, 58, 51),
                        new BlockPos(14, 58, 51),
                        new BlockPos(15, 52, 51),
                        new BlockPos(13, 52, 42),
                        new BlockPos(10, 52, 41),
                        new BlockPos(13, 52, 39),
                        new BlockPos(16, 52, 20),
                        new BlockPos(26, 52, 18),
                        new BlockPos(26, 52, 12),
                        new BlockPos(20, 55, 10),
                        new BlockPos(15, 59, 15),
                        new BlockPos(17, 64, 20),
                        new BlockPos(21, 82, 26),
                        new BlockPos(15, 82, 20),
                        new BlockPos(15, 82, 5),
                        new BlockPos(14, 81, 13),
                        new BlockPos(7, 81, 19),
                        new BlockPos(7, 81, 55),
                        new BlockPos(12, 81, 55),
                        new BlockPos(7, 81, 55),
                        new BlockPos(7, 81, 48),
                        new BlockPos(21, 81, 47),
                        new BlockPos(23, 93, 48),
                        new BlockPos(27, 93, 50)
                )
                .outlines(
                        new AddressedData<>(new BlockPos(2, 59, 54), Color.red),
                        new AddressedData<>(new BlockPos(10, 58, 51), Color.cyan),
                        new AddressedData<>(new BlockPos(21, 81, 26), Color.cyan),
                        new AddressedData<>(new BlockPos(15, 81, 20), Color.cyan),
                        new AddressedData<>(new BlockPos(23, 92, 48), Color.cyan),
                        new AddressedData<>(new BlockPos(13, 52, 53), Color.green),
                        new AddressedData<>(new BlockPos(17, 53, 53), Color.green),
                        new AddressedData<>(new BlockPos(9, 52, 41), Color.green),
                        new AddressedData<>(new BlockPos(15, 82, 3), Color.green),
                        new AddressedData<>(new BlockPos(27, 93, 49), Color.green)
                )
                .blocks(
                        new BBox(11, 59, 51, 15, 58, 51, Blocks.air),
                        new BBox(15, 57, 51, 15, 57, 51, Blocks.air)
                );
    }
}
