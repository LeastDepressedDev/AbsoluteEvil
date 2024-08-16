package me.qigan.abse.fr.mining;

import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class GrottoObj {
    public final BlockPos srcPos;

    public final List<GrottoVein> veins;

    public int crystalsAmt = 0;
    public int mfAmount = 0;

    public GrottoObj(BlockPos pos) {
        this.srcPos = pos;
        this.veins = new ArrayList<>();
        this.add(pos);
    }

    public void add(BlockPos pos) {
        this.crystalsAmt++;
        if (pos.getY() < 68) mfAmount++;
        for (GrottoVein vein : veins) {
            if (vein.add(pos)) return;
        }
        veins.add(new GrottoVein(pos));
    }

    public BlockPos grottoCentralPos() {
        int x = 0, y = 0, z = 0;
        final int sz = veins.size();
        for (GrottoVein vein : veins) {
            BlockPos mid = vein.getVeinPosition();
            x += mid.getX();
            y += mid.getY();
            z += mid.getZ();
        }
        return new BlockPos(x/sz, y/sz, z/sz);
    }

    public int mfPercent() {
        return (int) Math.floor((float) mfAmount / (float) crystalsAmt * 100f);
    }
}
