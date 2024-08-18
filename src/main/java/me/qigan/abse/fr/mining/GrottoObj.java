package me.qigan.abse.fr.mining;

import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrottoObj {
    public final BlockPos srcPos;

    public List<GrottoVein> veins = new ArrayList<>();

    public Set<BlockPos> gen = new HashSet<>();
    public int mfAmount = 0;

    public GrottoObj(BlockPos pos) {
        this.srcPos = pos;
        this.add(pos);
    }

    public void add(BlockPos pos) {
        if (gen.contains(pos)) return;
        gen.add(pos);
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
        return (int) Math.floor((float) mfAmount / (float) gen.size() * 100f);
    }
}
