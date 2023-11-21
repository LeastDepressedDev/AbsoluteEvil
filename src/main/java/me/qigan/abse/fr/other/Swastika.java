package me.qigan.abse.fr.other;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.HSLColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Swastika extends Module {

    public static float rotation = 0;
    public static HSLColor col = new HSLColor(0, 100, 50, 1f);

    @SubscribeEvent
    void render(RenderGameOverlayEvent.Pre e) {
        if (!isEnabled()) return;
        if (e.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            e.setCanceled(true);
            int sz = Index.MAIN_CFG.getIntVal("swastair_size");
            int x = e.resolution.getScaledWidth()/2;
            int y = e.resolution.getScaledHeight()/2;
            float sizecoef = sz/16f;
            GL11.glPushMatrix();
            GlStateManager.enableBlend();

            GL11.glTranslatef(x, y, 0);
            GL11.glRotatef(rotation, 0f, 0f, rotation);
            GL11.glScalef(sizecoef, sizecoef, 0);

            Esp.drawModalRectWithCustomSizedTexture(-(sz/(2f*sizecoef)), -(sz/(2f*sizecoef)), 16, 16, 0, 0, 16, 16,
                    new ResourceLocation("abse", "cursor.png"), col.toRgb());
            GL11.glPopMatrix();
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.icons);
        }
    }

    @SubscribeEvent
    void animate (TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) {
            rotation+=Index.MAIN_CFG.getDouble("swastair_speed");
            col.h+=Index.MAIN_CFG.getDouble("swastair_chroma");
        }
        if (col.h >= 100) col.h = 0;
        if (rotation >= 90) rotation = 0;
    }



    @Override
    public String id() {
        return "swastair";
    }

    @Override
    public String fname() {
        return "Custom crosshair";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("swastair_size", "Size", ValType.NUMBER, "16"));
        list.add(new SetsData<>("swastair_speed", "Speed", ValType.DOUBLE_NUMBER, "4"));
        list.add(new SetsData<>("swastair_chroma", "Chroma speed", ValType.DOUBLE_NUMBER, "1"));
        return list;
    }

    @Override
    public String description() {
        return "Custom cool crosshair";
    }
}
