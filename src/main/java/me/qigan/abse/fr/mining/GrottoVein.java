package me.qigan.abse.fr.mining;

import me.qigan.abse.sync.Utils;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class GrottoVein {

    public List<BlockPos> crystals = new ArrayList<>();


    public GrottoVein(BlockPos pos) {
        crystals.add(pos);
    }

    public boolean add(BlockPos pos) {
        for (BlockPos under : crystals) {
            if (under.distanceSq(pos) < 16) {
                crystals.add(pos);
                return true;
            }
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
