package me.qigan.abse.fr.other;

import me.qigan.abse.Index;
import me.qigan.abse.crp.Module;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class InguiDisplay extends Module {

    private static final Color ENABLED_CONST = new Color(0x3BFF00);
    private static final Color DISABLED_CONST = new Color(0xC20000);

    @SubscribeEvent
    void renderWorld(RenderWorldLastEvent e) {

    }

    @SubscribeEvent
    void renderText(RenderGameOverlayEvent.Text e) {
        if (!isEnabled()) return;
        Point pos = Index.POS_CFG.calc("ingui_disp");
        Esp.drawOverlayString(Minecraft.getMinecraft().fontRendererObj, "Sprinting", pos.x, pos.y,
                Minecraft.getMinecraft().thePlayer.isSprinting() ? ENABLED_CONST : DISABLED_CONST, S2Dtype.SHADOW);
    }

    @Override
    public String id() {
        return "ingui_disp";
    }

    @Override
    public String fname() {
        return "Ingui display";
    }

    @Override
    public String description() {
        return "Displays iseful info";
    }
}
