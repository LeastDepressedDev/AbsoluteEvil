package me.qigan.abse.fr.mining;

import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class GrottoVein {

    public final List<BlockPos> crystals;


    public GrottoVein(BlockPos pos) {
        this.crystals = new ArrayList<>();
        crystals.add(pos);
    }

    public boolean add(BlockPos pos) {
        if (pos.distanceSq(getVeinPosition()) < 64) {
            crystals.add(pos);
            return true;
        }
        return false;
    }

    public BlockPos getVeinPosition() {
        int x = 0, y = 0, z = 0;
        final int sz = crystals.size();
        for (BlockPos pos : crystals) {
            x += pos.getX();
            y += pos.getY();
            z += pos.getZ();
        }
        return new BlockPos(x/sz, y/sz, z/sz);
    }
}
