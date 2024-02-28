package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.EnabledByDefault;
import me.qigan.abse.crp.Module;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@EnabledByDefault
public class M7Visuals extends Module {

    public static final int SS_MAX_PART = 15;


    private static double local_dist = 0;
    private static int ss_part = 0;

    @SubscribeEvent
    void onLoad(WorldEvent.Load e) {
        ss_part = 0;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @SideOnly(Side.CLIENT)
    void onPacket(PacketEvent.ReceiveEvent e) {
        if (e.packet instanceof S23PacketBlockChange) {
            S23PacketBlockChange packet = (S23PacketBlockChange) e.packet;
            if (packet.getBlockState().getBlock() == Blocks.sea_lantern)
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7aLantern!" + packet.getBlockPosition()));
            if (packet.getBlockState().getBlock() == Blocks.sea_lantern &&
                    Utils.posInDim(packet.getBlockPosition(), DeviceIssue.BLOCK_SPAWN_SS_CONST)) {
                ss_part++;
            }
        }
    }

    @SubscribeEvent
    void block() {

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    void overlay(RenderGameOverlayEvent.Text e) {
        if (!isEnabled() || !Index.MAIN_CFG.getBoolVal("m7visz_ovr")) return;
        if (!Sync.inDungeon) return;
        Point pos = Index.POS_CFG.calc("m7visz");
        List<String> lines = new ArrayList<>();
        lines.add("\u00A7bDistance: " +
                (local_dist < 17 ? "\u00A7c" : (local_dist < 25 ? "\u00A7e" : "\u00A7a"))
                + (local_dist == 25555 ? "\u00A7cNone" : local_dist + "m"));
        lines.add("\u00A7dSimon says: \u00A77(\u00A7a" + ss_part + "\u00A72/\u00A7a" + SS_MAX_PART + "\u00A77)");
        Esp.drawAllignedTextList(lines, pos.x, pos.y, false, e.resolution, S2Dtype.CORNERED);
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        if (!Sync.inDungeon) return;
        double lc = 25555;
        for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (ent instanceof EntityWither && !ent.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
                if (Index.MAIN_CFG.getBoolVal("m7visz_wb")) Esp.autoBox3D(ent.posX, ent.posY+2.2, ent.posZ, 2, 3, Color.cyan, 4f, true);
                double dist = ent.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
                if (dist < lc) lc = dist;
            }
        }
        local_dist = lc;
    }

    @Override
    public String id() {
        return "m7visz";
    }

    @Override
    public String fname() {
        return "M7 visuals";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("m7visz_wb", "Esp box withers", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("m7visz_ovr", "Esp box withers", ValType.BOOLEAN, "true"));
        return list;
    }

    @Override
    public String description() {
        return "Cool visual feats for m7";
    }
}
