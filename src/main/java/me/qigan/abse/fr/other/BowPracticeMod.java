package me.qigan.abse.fr.other;

import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.Debug;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.vecmath.Point3d;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowPracticeMod extends Module {

    public static Map<EntityArrow, AddressedData<List<Point3d>, Boolean>> tracking = new HashMap<>();

    @SubscribeEvent
    void onProjectileJoin(EntityJoinWorldEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return;
        if (e.entity instanceof EntityArrow) {
            tracking.put((EntityArrow) e.entity, new AddressedData<>(new ArrayList<>(), true));
        }
    }

    @SubscribeEvent
    void onUpdate(TickEvent.ClientTickEvent e) {
        if (!isEnabled()) return;
        try {
            for (Map.Entry<EntityArrow, AddressedData<List<Point3d>, Boolean>> data : tracking.entrySet()) {
                if (!data.getValue().getObject()) continue;

                double dx = data.getKey().posX - data.getKey().prevPosX;
                double dy = data.getKey().posY - data.getKey().prevPosY;
                double dz = data.getKey().posZ - data.getKey().prevPosZ;

                if (dx == 0 && dy == 0 && dz == 0 && data.getKey().ticksExisted > 2) {
                    AddressedData<List<Point3d>, Boolean> dat = data.getValue();
                    dat.setObject(false);
                    data.setValue(dat);
                }
                data.getValue().getNamespace().add(new Point3d(data.getKey().posX,
                        data.getKey().posY,
                        data.getKey().posZ));
            }
        } catch (Exception ex) {}
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled()) return;
        try {
            for (Map.Entry<EntityArrow, AddressedData<List<Point3d>, Boolean>> data : tracking.entrySet()) {
                Esp.drawAsSingleLine(data.getValue().getNamespace(),
                        new Color(255, 0, 0, Index.MAIN_CFG.getIntVal("bowpm_alpha")),
                        Index.MAIN_CFG.getIntVal("bowpm_lsize"), Index.MAIN_CFG.getBoolVal("bowpm_esp"),
                        Index.MAIN_CFG.getBoolVal("bowpm_trig") ? 2 : 1);
                if (Debug.GENERAL) {
                    for (Point3d pt : data.getValue().getNamespace()) {
                        Esp.autoBox3D(pt.x, pt.y, pt.z, 1, 1, new Color(255, 0, 0, 255), true);
                    }
                }
            }
        } catch (Exception ex) {}
    }

    @Override
    public String id() {
        return "bowpm";
    }

    @Override
    public List<SetsData<String>> sets() {
        List<SetsData<String>> list = new ArrayList<>();
        list.add(new SetsData<>("bowpm_alpha", "Alpha[0-255]", ValType.NUMBER, "200"));
        list.add(new SetsData<>("bowpm_lsize", "Line size", ValType.NUMBER, "2"));
        list.add(new SetsData<>("bowpm_esp", "Esp mode", ValType.BOOLEAN, "false"));
        list.add(new SetsData<>("bowpm_trig", "Double tracer mode", ValType.BOOLEAN, "false"));
        return list;
    }

    @Override
    public String description() {
        return "Helps you with a bow practice";
    }

    @Override
    public String fname() {
        return "Bow practice mod";
    }

    @Override
    public void onDisable() {
        tracking.clear();
    }
}
