package me.qigan.abse.pathing;

import me.qigan.abse.mapping.MappingConstants;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

import java.util.*;

public class Path{

    private final int limit;

    private final Set<BlockPos> usedGeneric = new HashSet<>();
    private final Float pitchOverride = null;

    private final Map<BlockPos, Integer> map = new HashMap<>();

    public final BlockPos from;
    public final BlockPos to;

    private final List<BlockPos> path = new ArrayList<>();
    private boolean failed = true;

    public Path(BlockPos from, BlockPos to) {
        this.from = Utils.unify(from);
        this.to = Utils.unify(to);
        this.limit = 140;
    }

    public Path(BlockPos from, BlockPos to, int lim) {
        this.from = Utils.unify(from);
        this.to = Utils.unify(to);
        this.limit = lim;
    }


    //Wave algoritm
    public Path build() {
        if (Utils.compare(from, to)) return this;
        Set<BlockPos> wave = new HashSet<>();
        wave.add(from);
        for (int i = 0; i < limit; i++) {
            wave = createWaveStep(wave, i);
            if (wave.contains(to)) {
                failed = false;
                bph(to);
                break;
            }
        }
        return this;
    }

    private Set<BlockPos> createWaveStep(Set<BlockPos> poses, int val) {
        Set<BlockPos> wave = new HashSet<>();
        for (BlockPos pos : poses) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (y == 1 && x != 0 && z != 0) continue;
                        BlockPos subPos = pos.add(x, y, z);
                        if (usedGeneric.contains(subPos) || poses.contains(subPos)) continue;
                        if (!MappingConstants.NOT_COLLIDABLE.contains(Minecraft.getMinecraft().theWorld.getBlockState(subPos.add(0, -1, 0)).getBlock())
                                && MappingConstants.NOT_COLLIDABLE.contains(Minecraft.getMinecraft().theWorld.getBlockState(subPos.add(0, 1, 0)).getBlock())
                                && MappingConstants.NOT_COLLIDABLE.contains(Minecraft.getMinecraft().theWorld.getBlockState(subPos).getBlock())) {
                            wave.add(subPos);
                            map.put(subPos, val);
                            usedGeneric.add(subPos);
                        }
                    }
                }
            }
        }
        return wave;
    }

    private void bph(BlockPos pos) {
        if (pos == from) {
            path.add(pos);
            return;
        }
        double distSQ = limit;
        BlockPos dl = null;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos subPos = pos.add(x, y, z);
                    if (map.containsKey(subPos) && map.get(subPos) < map.get(pos)) {
                        if (pos.distanceSq(subPos) < distSQ) {
                            distSQ = pos.distanceSq(subPos);
                            dl = subPos;
                        }
                    }
                }
            }
        }
        if (dl != null) {
            bph(dl);
            path.add(dl);
        }
        else fail();
    }

    private void fail() {
        failed = true;
    }

    public final boolean isFailed() {
        return failed;
    }

    public List<BlockPos> getPosPath() {
        return path;
    }
}
