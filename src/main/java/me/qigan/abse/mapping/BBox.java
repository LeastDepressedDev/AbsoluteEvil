package me.qigan.abse.mapping;

import me.qigan.abse.fr.qol.GhostBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class BBox {
    public final int x1;
    public final int y1;
    public final int z1;
    public final int x2;
    public final int y2;
    public final int z2;

    public Block block;


    public BBox(int x1, int y1, int z1, int x2, int y2, int z2, Block bts) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.block = bts;
    }

    public void run() {
        for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
            for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++) {
                for (int l = Math.min(z1, z2); l <= Math.max(z1, z2); l++) {
                    //Minecraft.getMinecraft().thePlayer.worldObj.setBlockToAir()
                    GhostBlocks.placeBlock(new BlockPos(i, j, l), this.block);
                }
            }
        }
    }
}
