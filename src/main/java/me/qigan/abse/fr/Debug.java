package me.qigan.abse.fr;

import me.qigan.abse.Index;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Debug extends Module {

    public static boolean GENERAL = false;

    public static final List<String> DISABLE_STATE = new ArrayList<String>(Arrays.asList(
            "bc", "harp", "m7prios", "devices", "sbot"
    ));

    public static final Color debugCol = new Color(255, 100, 0, 255);

    @SubscribeEvent(priority = EventPriority.HIGH)
    void onUse(PlayerInteractEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() == null) return;
        System.out.println(Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()));
    }

    @SubscribeEvent
    void renderWorld(RenderWorldLastEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().theWorld == null) return;
        for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            Esp.autoBox3D(ent, debugCol, true);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().theWorld == null) return;
        for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (Index.MAIN_CFG.getBoolVal("debug_render_names")) Esp.renderTextInWorld(ent.getName(), ent.posX, ent.posY+1, ent.posZ, 16776960, e.partialTicks);
            if (Index.MAIN_CFG.getBoolVal("debug_render_id")) Esp.renderTextInWorld(Integer.toString(ent.getEntityId()), ent.posX, ent.posY+0.5, ent.posZ, 16776960, e.partialTicks);
        }
    }

    @Override
    public String id() {
        return "debug";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("debug_render_names", "Render names", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("debug_render_id", "Render id", ValType.BOOLEAN, "true"));
        return list;
    }

    @Override
    public String description() {
        return "A lot of useful shit";
    }
}
