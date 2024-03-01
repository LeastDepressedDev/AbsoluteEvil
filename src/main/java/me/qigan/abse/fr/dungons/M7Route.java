package me.qigan.abse.fr.dungons;

import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.GhostBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class M7Route extends Module {

    private boolean readyUp = false;

    public static class BBox {
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

    public static List<BBox> bounds = new ArrayList<BBox>(Arrays.asList(
            new BBox(85, 219, 61, 92, 213, 61, Blocks.air),
            new BBox(91, 165, 41, 95, 167, 40, Blocks.air),
            new BBox(54, 64, 80, 54, 63, 78, Blocks.air),
            new BBox(57, 108, 123, 56, 111, 118, Blocks.air),
            new BBox(88, 165, 41, 95, 166, 41, Blocks.oak_fence),
            new BBox(75, 221, 38, 76, 221, 38, Blocks.ender_chest),
            new BBox(51, 114, 112, 51, 114, 112, Blocks.ender_chest),
            new BBox(100, 167, 47, 100, 165, 46, Blocks.air),
            new BBox(100, 169, 46, 100, 169, 46, Blocks.ender_chest),
            new BBox(52, 114, 111, 52, 114, 111, Blocks.ender_chest),
            new BBox(72, 106,142, 63, 106, 123, Blocks.rail)
            ));

    //TODO: FIX THIS FUCKING SHIT
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onMsg(EntityJoinWorldEvent e) {
        if (!isEnabled()) return;
        try {
            //System.out.println(e.message.getFormattedText());
            if (e.entity instanceof EntityEnderCrystal && readyUp) {
                GhostBlocks.blocks.clear();
                for (BBox b : bounds) {
                    b.run();
                }
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7a[ABSE] Healer route set!"));
                this.readyUp = false;
            }
        } catch (Exception ex) {}
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChange(EntityJoinWorldEvent e) {
        try {
        if (e.entity.getUniqueID() == Minecraft.getMinecraft().thePlayer.getUniqueID()) {
            this.readyUp = true;
        }
        } catch (Exception ex) {}
    }

    @Override
    public String id() {
        return "m7r";
    }

    @Override
    public String fname() {
        return "M7 auto route";
    }

    @Override
    public String description() {
        return "auto f7-m7 route";
    }
}
