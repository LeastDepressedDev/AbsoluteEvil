package me.qigan.abse.mapping;

import me.qigan.abse.vp.Esp;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.actors.threadpool.Arrays;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Mapping {

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
    }
}
