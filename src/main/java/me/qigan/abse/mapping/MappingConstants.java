package me.qigan.abse.mapping;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.*;

public class MappingConstants {

    public static final Set<Block> NOT_COLLIDABLE = new HashSet<>();

    public static void setup() {
        NOT_COLLIDABLE.add(Blocks.air);
        NOT_COLLIDABLE.add(Blocks.torch);
        NOT_COLLIDABLE.add(Blocks.ladder);
        NOT_COLLIDABLE.add(Blocks.lever);
        NOT_COLLIDABLE.add(Blocks.stone_button);
        NOT_COLLIDABLE.add(Blocks.wooden_button);
        NOT_COLLIDABLE.add(Blocks.cake);
        NOT_COLLIDABLE.add(Blocks.carpet);
        NOT_COLLIDABLE.add(Blocks.bed);
        NOT_COLLIDABLE.add(Blocks.stone_slab);
        NOT_COLLIDABLE.add(Blocks.stone_slab2);
        NOT_COLLIDABLE.add(Blocks.wooden_slab);
        NOT_COLLIDABLE.add(Blocks.vine);
        NOT_COLLIDABLE.add(Blocks.tallgrass);
    }
}
