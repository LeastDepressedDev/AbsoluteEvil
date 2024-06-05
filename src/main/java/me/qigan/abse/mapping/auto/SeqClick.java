package me.qigan.abse.mapping.auto;

import me.qigan.abse.mapping.Mapping;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class SeqClick extends QueuedSeq {


    public SeqClick(boolean right, BlockPos pos) {

        if (pos != null && Mapping.currentRoom() != null) {
            pos = Mapping.currentRoom().transformInnerCoordinate(pos);
        }
        IBlockState state = Minecraft.getMinecraft().theWorld.getBlockState(pos);

    }
}
