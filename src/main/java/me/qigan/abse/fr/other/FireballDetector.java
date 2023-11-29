package me.qigan.abse.fr.other;

import me.qigan.abse.Index;
import me.qigan.abse.crp.Module;
import me.qigan.abse.gui.overlay.GuiNotifier;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FireballDetector extends Module {

    public static int tickCd = 0;
    public static boolean glRend = false;

    public static List<EntityFireball> scan = new ArrayList<>();

    @SubscribeEvent
    void spawn(EntityJoinWorldEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().thePlayer == null) return;
        if (e.entity instanceof EntityFireball) {
            if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e.entity) > 5) {
                scan.add((EntityFireball) e.entity);
            }
        }
    }

    @SubscribeEvent
    void detect(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null)
            return;
        for (EntityFireball ent : scan) {
            if (!ent.isEntityAlive()) {
                scan.remove(ent);
                return;
            }
            double moveX = ent.posX - ent.prevPosX;
            double moveY = ent.posY - ent.prevPosY;
            double moveZ = ent.posZ - ent.prevPosZ;

            for (int i = 0; i <= 100; i++) {
                if (Minecraft.getMinecraft().thePlayer.getDistance(ent.posX + moveX * i, ent.posY + moveY * i, ent.posZ + moveZ * i) < 7) {
                    if (tickCd == 0) {
                        GuiNotifier.call("\u00A7cFireball incoming", 30, true);
                        Minecraft.getMinecraft().thePlayer.playSound("note.pling", 2f, 1f);
                        tickCd = 7;
                    } else {
                        tickCd--;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled()) return;
        boolean rendStr = false;
        for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (ent instanceof EntityFireball) {
                double moveX = ent.posX - ent.prevPosX;
                double moveY = ent.posY - ent.prevPosY;
                double moveZ = ent.posZ - ent.prevPosZ;

                Esp.drawTracer(ent.posX, ent.posY, ent.posZ, ent.posX + moveX * 100, ent.posY + moveY * 100, ent.posZ + moveZ * 100, new Color(0xFF0000), 1f);
            } else if (ent instanceof EntityPlayer) {
                if (ent.getUniqueID() == Minecraft.getMinecraft().thePlayer.getUniqueID()) continue;
                if (((EntityPlayer) ent).getHeldItem() != null && ((EntityPlayer) ent).getHeldItem().getItem() == Items.fire_charge)
                    rendStr = true;
            }
        }
        glRend = rendStr;
    }

    @SubscribeEvent
    void renderOVR(RenderGameOverlayEvent.Text e) {
        if(!isEnabled() || !glRend) return;
        Point loc = Index.POS_CFG.calc("fbd_display");
        Esp.drawOverlayString("Fireball warning!", loc.x, loc.y, 0xFF0000, S2Dtype.CORNERED);
    }

    @Override
    public String id() {
        return "fanonc";
    }

    @Override
    public String fname() {
        return "Fireball detector";
    }

    @Override
    public String description() {
        return "Now you know when fireball is going for you";
    }
}
