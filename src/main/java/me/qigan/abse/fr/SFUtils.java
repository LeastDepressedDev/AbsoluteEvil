package me.qigan.abse.fr;

import me.qigan.abse.Index;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SFUtils extends Module {

    private static boolean swap;

    public static int tick = 0;
    public static int stick = 0;

    public static final Color col = new Color(96, 255, 0, 255);

    @SubscribeEvent(priority = EventPriority.HIGH)
    void onUse(PlayerInteractEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() == null ||
                Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()) == null) return;
        if (Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()).getString("id").equalsIgnoreCase("SHADOW_FURY")) {
            if (tick == 0) {
                tick = 295;
            }
        } else if (Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()).getString("id").equalsIgnoreCase("STARRED_SHADOW_FURY")) {
            if (stick == 0) {
                stick = 295;
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().theWorld == null) return;
        swap=!swap;
        if (swap) {
            if (tick > 0) {
                tick--;
            }
            if (stick > 0) {
                stick--;
            }
        }
    }

    @SubscribeEvent
    void cancel(ClientChatReceivedEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() == null) return;
        if (e.message.getFormattedText().contains("There are no enemies nearby!")) {
            if (Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()).getString("id").equalsIgnoreCase("SHADOW_FURY")) {
                tick = 0;
            } else if (Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()).getString("id").equalsIgnoreCase("STARRED_SHADOW_FURY")) {
                stick = 0;
            }
        }
    }

    @SubscribeEvent
    void renderOverlay(RenderGameOverlayEvent.Text e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().theWorld == null) return;
        int x = e.resolution.getScaledWidth()/5;
        int y = e.resolution.getScaledHeight()/3*2 + 15;
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("\u00A76Shadow fury: " + ((tick == 0) ? "\u00A7aREADY!" : Double.toString(((double) tick) / 20)), x, y, 0xFFFFFF);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("\u00A76\u272F Shadow fury: " + ((stick == 0) ? "\u00A7aREADY!" : Double.toString(((double) stick) / 20)), x, y + 10, 0xFFFFFF);
    }

    @SubscribeEvent
    void renderClosest(RenderWorldLastEvent e) {
        if (!isEnabled()) return;
        if (!Index.MAIN_CFG.getBoolVal("sf_trace_mob")) return;
        if (Minecraft.getMinecraft().theWorld == null) return;
        Entity dEnt = null;
        float dist = 12;
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        for (Entity ent: Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (ent instanceof EntityLiving && ent.getUniqueID() != player.getUniqueID()) {
                float nd;
                if (ent instanceof EntityDragon) nd = ent.getDistanceToEntity(player) - 3;
                else nd = ent.getDistanceToEntity(player);
                if (nd < dist) {
                    dEnt = ent;
                    dist = nd;
                }
            }
        }
        if (dEnt != null && (Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem())
                .getString("id").equalsIgnoreCase("SHADOW_FURY") ||
                        Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem())
                                .getString("id").equalsIgnoreCase("STARRED_SHADOW_FURY"))) {
            Esp.autoBox3D(dEnt, col, 3f, false);
            Esp.drawTracer(player.posX, player.posY, player.posZ, dEnt.posX, dEnt.posY, dEnt.posZ, col, 2f);
        }
    }


    @Override
    public String id() {
        return "sftils";
    }

    @Override
    public String fname() {
        return "Shadow fury utils";
    }

    @Override
    public List<SetsData<String>> sets() {
        List<SetsData<String>> list = new ArrayList<SetsData<String>>();
        list.add(new SetsData<String>("sf_trace_mob", "Trace closest mob", ValType.BOOLEAN, "true"));
        return list;
    }

    @Override
    public String description() {
        return "Shows you the closest mob and";
    }
}
