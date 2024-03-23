package me.qigan.abse.mapping.rooms.r1x1;

import me.qigan.abse.config.AddressedData;
import me.qigan.abse.mapping.Room;
import me.qigan.abse.mapping.RoomTemplate;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.util.Arrays;
import java.util.Collection;

public class RoomScaffolding extends RoomTemplate {
    public RoomScaffolding() {
        super(Room.Shape.r1X1, 1, "Scaffolding");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(3, 70, 8), Blocks.stone_brick_stairs),
                new AddressedData<>(new BlockPos(24, 74, 22), Blocks.stonebrick),
                new AddressedData<>(new BlockPos(18, 69, 3), Blocks.anvil),
                new AddressedData<>(new BlockPos(8, 75, 3), Blocks.cobblestone_wall)
        );
    }
}
