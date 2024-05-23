package me.qigan.abse.mapping.mod;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.qol.GhostBlocks;
import me.qigan.abse.mapping.routing.BBox;
import me.qigan.abse.sync.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
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
                new BBox(90, 131, 44, 91, 132, 46, Blocks.air.getDefaultState()).run();
                new BBox(88, 131, 46, 91, 132, 46, Blocks.air.getDefaultState()).run();
            }
        });
    }

    public static void register(DynamicRouteElement e) {
        dynamics.put(e.pos, e);
    }

    public static List<BBox> bounds = new ArrayList<BBox>(Arrays.asList(
            new BBox(85, 219, 61, 92, 213, 61, Blocks.air.getDefaultState()),
            new BBox(91, 165, 41, 95, 167, 40, Blocks.air.getDefaultState()),
            new BBox(54, 64, 80, 54, 63, 78, Blocks.air.getDefaultState()),
            new BBox(57, 108, 123, 56, 111, 118, Blocks.air.getDefaultState()),
            new BBox(88, 165, 41, 95, 166, 41, Blocks.oak_fence.getDefaultState()),
            new BBox(75, 221, 38, 76, 221, 38, Blocks.ender_chest.getDefaultState()),
            new BBox(75, 220, 38, 76, 220, 38, Blocks.air.getDefaultState()),
            new BBox(51, 114, 112, 51, 114, 112, Blocks.ender_chest.getDefaultState()),
            new BBox(100, 167, 47, 100, 165, 46, Blocks.air.getDefaultState()),
            new BBox(100, 169, 46, 100, 169, 46, Blocks.ender_chest.getDefaultState()),
            new BBox(52, 114, 111, 51, 114, 111, Blocks.ender_chest.getDefaultState()),
            new BBox(52, 113, 111, 51, 113, 111, Blocks.air.getDefaultState()),
            new BBox(72, 106,142, 63, 106, 123, Blocks.rail.getDefaultState()),
            new BBox(53, 106, 137, 55, 106, 139, Blocks.rail.getDefaultState()),
//            new BBox(20, 130, 135, 15, 128, 137, Blocks.air.getDefaultState()),
//            new BBox(20, 130, 135, 18, 132, 135, Blocks.oak_fence.getDefaultState()),
            new BBox(17, 131, 136, 18, 128, 135, Blocks.air.getDefaultState()),
            new BBox(18, 131, 135, 18, 131, 136,
                    Blocks.wooden_slab.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP)),
            new BBox(19, 132, 135, 20, 129, 135, Blocks.oak_fence.getDefaultState()),
            new BBox(52, 132, 140, 39, 136, 140, Blocks.stained_glass.getDefaultState()),
            new BBox(32, 132, 137, 57, 136, 137, Blocks.stained_glass.getDefaultState()),
            new BBox(52, 106, 137, 52, 129, 139, Blocks.stained_glass.getDefaultState()),
            new BBox(85, 118, 35, 83, 106, 35, Blocks.stained_glass.getDefaultState()),
            new BBox(58, 134, 142, 58, 134, 142, Blocks.lapis_block.getDefaultState()),
            new BBox(58, 123, 122,57, 125, 118, Blocks.air.getDefaultState())
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
                    GhostBlocks.placeBlock(etr.getKey(), etr.getValue().before.getDefaultState());
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
                GhostBlocks.placeBlock(bp, ele.after.getDefaultState());
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
