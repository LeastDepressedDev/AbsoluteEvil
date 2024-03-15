package me.qigan.abse.crp;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.Set;

public class TagConstants {

    public static Set<Item> PICKAXES = new HashSet<>();

    public static void init() {
        initPickaxes();
    }

    private static void initPickaxes() {
        PICKAXES.add(Items.diamond_pickaxe);
        PICKAXES.add(Items.golden_pickaxe);
        PICKAXES.add(Items.wooden_pickaxe);
        PICKAXES.add(Items.stone_pickaxe);
        PICKAXES.add(Items.iron_pickaxe);
    }
}
