package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DragonPoint extends Module {

    public static final double AVG_DIST = 18;

    public static class DragonOvr {
        public static final double arrow_speed = 17.5D;
        private int tick = 200;

        public final EntityDragon drag;
        public final BlockPos spawn_pos;

        public double lastPosX;
        public double lastPosY;
        public double lastPosZ;

        public DragonOvr(EntityDragon ent) {
            this.drag = ent;
            this.spawn_pos = this.drag.getPosition();
        }

        public void tick() {
            if (drag.isEntityAlive()) {
                Esp.autoBox3D(drag, new Color(0, 255, 0, 255), true);

                double dist = drag.getDistance(spawn_pos.getX(), spawn_pos.getY()+1.5, spawn_pos.getZ());
                Esp.autoBox3D(drag.posX, drag.posY+2, drag.posZ, 1, 1, new Color((int) (dist/AVG_DIST*255), 0, 255 - (int)(dist/AVG_DIST*255), 255), 2f, true);
                if (Index.MAIN_CFG.getBoolVal("m7dp_traj")) {
                    double speedX = drag.posX - drag.lastTickPosX;
                    double speedY = drag.posY - drag.lastTickPosY;
                    double speedZ = drag.posZ - drag.lastTickPosZ;

                    double player_dist = Minecraft.getMinecraft().thePlayer.getDistance(drag.posX, drag.posY, drag.posZ);
                    double time = player_dist / arrow_speed + 0.5D;

                    double boxX = drag.posX + speedX * time * 8.0D;
                    double boxY = drag.posY + speedY * time * 4.0D;
                    double boxZ = drag.posZ + speedZ * time * 8.0D;

                    Esp.autoBox3D(boxX, boxY+2.5, boxZ, 5, 5, new Color(255, 0, 255, 255), true);
                    //Esp.drawModifiedRect(boxX, boxY, boxZ, drag.posX, drag.posY, drag.posZ, new MColor(0, 141, 167, 1f), 3f);
                }
            } else {
                if (spawn_pos.distanceSq(lastPosX, lastPosY, lastPosZ) > AVG_DIST*10) {
                    Esp.autoBox3D(lastPosX, lastPosY, lastPosZ, 1, 1, new Color(255, 0, 0, 255), 2f, true);
                } else {
                    Esp.autoBox3D(lastPosX, lastPosY, lastPosZ, 1, 1, new Color(0, 40, 255, 255), 2f, true);
                }
                if (tick <= 0) {
                    drags.remove(this);
                }
            }
        }
    }

    public static List<DragonOvr> drags = new ArrayList<DragonOvr>();

    @SubscribeEvent
    void rend(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        try {
            for (DragonOvr drag : drags) {
                drag.tick();
            }
        } catch (ConcurrentModificationException ev) {}
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        try {
            for (DragonOvr drag : drags) {
                if (!drag.drag.isEntityAlive()) drag.tick--;
            }
        } catch (ConcurrentModificationException ev) {}
    }

    @SubscribeEvent
    void spawn(EntityJoinWorldEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        if (e.entity instanceof EntityDragon) {
            for (DragonOvr drag : drags) {
                if (drag.drag.getEntityId() == e.entity.getEntityId()) return;
            }
            drags.add(new DragonOvr((EntityDragon) e.entity));
        }
    }

    @SubscribeEvent
    void change(WorldEvent.Load e) {
        drags.clear();
    }

    @SubscribeEvent
    void onDeath(LivingDeathEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        if (e.entity instanceof EntityDragon) {
            for (DragonOvr drag : drags) {
                if (drag.drag == (EntityDragon) e.entity) {
                    drag.lastPosX = e.entity.posX;
                    drag.lastPosY = e.entity.posY;
                    drag.lastPosZ = e.entity.posZ;
                }
            }
        }
    }

    @Override
    public String id() {
        return "m7dp";
    }

    @Override
    public String fname() {
        return "M7 drags";
    }

    @Override
    public List<SetsData<String>> sets() {
        List<SetsData<String>> list = new ArrayList<SetsData<String>>();
        list.add(new SetsData<String>("m7dp_traj", "Draw trajectory", ValType.BOOLEAN, "false"));
        return list;
    }

    @Override
    public String description() {
        return "M7 dragon center point";
    }
}
