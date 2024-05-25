package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.EnabledByDefault;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.LagTracker;
import me.qigan.abse.fr.dungons.m7p3.DeviceIssue;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@EnabledByDefault
public class M7Visuals extends Module {

    private static double local_dist = 0;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    void overlay(RenderGameOverlayEvent.Text e) {
        if (!isEnabled() || !Index.MAIN_CFG.getBoolVal("m7visz_ovr")) return;
        if (!Sync.inDungeon) return;
        Point pos = Index.POS_CFG.calc("m7visz");
        List<String> lines = new ArrayList<>();
        lines.add("\u00A7bDistance: " +
                (local_dist < 17 ? "\u00A7c" : (local_dist < 25 ? "\u00A7e" : "\u00A7a"))
                + (local_dist == 25555 ? "\u00A7cNone" : local_dist + "m"));
        lines.add("\u00A7dSimon says: \u00A7e" + DeviceIssue.stepIter+ " " + (Index.MAIN_CFG.getBoolVal("devices") ? " \u00A77(\u00A7a" + DeviceIssue.clickedSS
                        + "\u00A72/\u00A7a" + DeviceIssue.SS_CLICK_LIM + "\u00A77)" : "\u00A7cEnable DeviceIssue"));
        lines.add("\u00A76Rate: \u00A7a" + LagTracker.AVERAGE + "(" + LagTracker.ticks_since + ")");
        lines.add("\u00A76Last tick: \u00A7a" + LagTracker.diff());
        Esp.drawAllignedTextList(lines, pos.x, pos.y, false, e.resolution, S2Dtype.CORNERED);
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        if (!Sync.inDungeon) return;
        double lc = 25555;
        for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (ent instanceof EntityWither && !ent.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
                if (Index.MAIN_CFG.getBoolVal("m7visz_wb")) Esp.autoBox3D(ent.posX, ent.posY+3.5, ent.posZ, 2, 3, Color.cyan, 4f, true);
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
        list.add(new SetsData<>("m7visz_ovr", "Overlay", ValType.BOOLEAN, "true"));
        return list;
    }

    @Override
    public String description() {
        return "Cool visual feats for m7";
    }
}
