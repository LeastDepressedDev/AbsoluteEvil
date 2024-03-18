package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.qol.GhostBlocks;
import me.qigan.abse.sync.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

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

    public static class DynamicRouteElement implements Runnable {

        public final Block before;
        public final Block after;
        public final BlockPos pos;

        public DynamicRouteElement(BlockPos pos, Block before, Block after) {
            this.before = before;
            this.after = after;
            this.pos = pos;
        }

        @Override
        public void run() {

        }
    }

    public static void setup() {
        register(new DynamicRouteElement(new BlockPos(91, 132, 45), Blocks.redstone_block, Blocks.emerald_block) {
            @Override
            public void run() {
                new BBox(90, 131, 44, 91, 132, 46, Blocks.air).run();
                new BBox(88, 131, 46, 91, 132, 46, Blocks.air).run();
            }
        });
    }

    public static void register(DynamicRouteElement e) {
        dynamics.put(e.pos, e);
    }

    public static List<BBox> bounds = new ArrayList<BBox>(Arrays.asList(
            new BBox(85, 219, 61, 92, 213, 61, Blocks.air),
            new BBox(91, 165, 41, 95, 167, 40, Blocks.air),
            new BBox(54, 64, 80, 54, 63, 78, Blocks.air),
            new BBox(57, 108, 123, 56, 111, 118, Blocks.air),
            new BBox(88, 165, 41, 95, 166, 41, Blocks.oak_fence),
            new BBox(75, 221, 38, 76, 221, 38, Blocks.ender_chest),
            new BBox(75, 220, 38, 76, 220, 38, Blocks.air),
            new BBox(51, 114, 112, 51, 114, 112, Blocks.ender_chest),
            new BBox(100, 167, 47, 100, 165, 46, Blocks.air),
            new BBox(100, 169, 46, 100, 169, 46, Blocks.ender_chest),
            new BBox(52, 114, 111, 51, 114, 111, Blocks.ender_chest),
            new BBox(52, 113, 111, 51, 113, 111, Blocks.air),
            new BBox(72, 106,142, 63, 106, 123, Blocks.rail),
            new BBox(57, 106, 132, 52, 106, 136, Blocks.rail),
            new BBox(20, 130, 135, 15, 128, 137, Blocks.air),
            new BBox(20, 130, 135, 18, 132, 135, Blocks.oak_fence)
            ));

    public static Map<BlockPos, DynamicRouteElement> dynamics = new HashMap<>();

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
                for (Map.Entry<BlockPos, DynamicRouteElement> etr : dynamics.entrySet()) {
                    GhostBlocks.placeBlock(etr.getKey(), etr.getValue().before);
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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    void click(PlayerInteractEvent e) {
        if (!Index.MAIN_CFG.getBoolVal("m7r_dg")) return;
        if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (e.pos == null) return;
            BlockPos bp = Utils.unify(e.pos);
            DynamicRouteElement ele = dynamics.get(bp);
            if (ele != null && Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == ele.before) {
                GhostBlocks.placeBlock(bp, ele.after);
                ele.run();
                e.setCanceled(true);
            }
        }
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
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("m7r_dg", "Use dynamic routes", ValType.BOOLEAN, "true"));
        return list;
    }

    @Override
    public String description() {
        return "auto f7-m7 route";
    }
}
