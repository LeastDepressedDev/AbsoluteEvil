package me.qigan.abse.fr.dungons;

import me.qigan.abse.crp.Module;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.vecmath.Vector3f;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//TODO: Fix this shit
public class BloodCamp extends Module {

    public static class BloodMob {

        public int clTime = 30;
        public boolean ctrlT = false;

        public final int entId;
        public final EntityArmorStand zomb;
        public final String worldName;
        public final Vector3f spawnPos;
        public Vector3f nextTick = null;
        public Color col = new Color(255, 0, 0, 255);
        public Vector3f vec = new Vector3f();

        public BloodMob(EntityArmorStand ent) {
            this.zomb = ent;
            this.entId = ent.getEntityId();
            this.worldName = ent.getEntityWorld().getWorldInfo().getWorldName();
            this.spawnPos = new Vector3f((float) ent.posX, (float) ent.posY, (float) ent.posZ);
        }

        public void update() {
            if (nextTick != null) {
                if (zomb.getInventory()[4].getItem() != Items.skull) {
                    mobs.remove(this);
                    return;
                }

                vec.x = (nextTick.getX()-spawnPos.getX())*1000;
                vec.y = (nextTick.getY()-spawnPos.getY())*1000;
                vec.z = (nextTick.getZ()-spawnPos.getZ())*1000;

                if (zomb.getPosition().distanceSq(vec.x, vec.y, vec.z) < 1) {
                    col = new Color(0, 0, 255, 255);
                    clTime--;
                }
                if (clTime <= 0) {
                    mobs.remove(this);
                }
            }
        }

        public void render() {
            try {
                if (nextTick != null) {
                    Esp.autoBox3D(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 1, 1, new Color(0, 255, 0, 255), false);
                    Esp.autoBox3D(vec.x, vec.y, vec.z, 1, 1, col, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<BloodMob> mobs = new ArrayList<BloodMob>();

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().theWorld == null) return;
        for (BloodMob mob : mobs) {
            if (!mob.ctrlT) {
                mob.ctrlT = true;
                continue;
            }
            if (mob.nextTick == null) {
                if (mob.zomb.posX != mob.spawnPos.x || mob.zomb.posY != mob.spawnPos.y || mob.zomb.posZ != mob.spawnPos.z) {
                    mob.nextTick = new Vector3f((float) mob.zomb.posX, (float) mob.zomb.posY, (float) mob.zomb.posZ);
                }
            }
            mob.update();
        }
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().theWorld == null) return;
        for (BloodMob mob : mobs) {
            mob.render();
        }
    }

    @SubscribeEvent
    void spawn(EntityJoinWorldEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().theWorld != null) {
            try {
                if (e.entity instanceof EntityArmorStand) {
                    mobs.add(new BloodMob((EntityArmorStand) e.entity));
                }
            }catch (Exception ev) {
                ev.printStackTrace();
            }
        }
    }

    @Override
    public String id() {
        return "bc";
    }

    @Override
    public String description() {
        return "Blood camp helper";
    }
}
