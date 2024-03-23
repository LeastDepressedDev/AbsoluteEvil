package me.qigan.abse.mapping.rooms.r1x1;

import me.qigan.abse.config.AddressedData;
import me.qigan.abse.mapping.Room;
import me.qigan.abse.mapping.RoomTemplate;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.util.Arrays;
import java.util.Collection;

public class RoomRacoon extends RoomTemplate {
    public RoomRacoon() {
        super(Room.Shape.r1X1, 2, "Racoon");
    }

    @Override
    public Collection<AddressedData<BlockPos, Block>> hooks() {
        return Arrays.asList(
                new AddressedData<>(new BlockPos(2, 70, 3), Blocks.torch),
                new AddressedData<>(new BlockPos(28, 85, 24), Blocks.stonebrick),
                new AddressedData<>(new BlockPos(2, 70, 24), Blocks.lever),
                new AddressedData<>(new BlockPos(22, 72, 23), Blocks.stonebrick)
        );
    }
}
