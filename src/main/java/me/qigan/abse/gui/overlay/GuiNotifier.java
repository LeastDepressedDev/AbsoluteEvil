package me.qigan.abse.gui.overlay;

import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GuiNotifier {

    public static String displ;
    public static int tick;
    public static boolean cornered;

    public static void call(String str, int ticks, boolean drawCorner) {
        tick = ticks;
        displ = str;
        cornered = drawCorner;
    }

    @SubscribeEvent
    void tickl(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) return;
        if (tick > 0) tick--;
    }

    @SubscribeEvent
    void render(RenderGameOverlayEvent.Text e) {
        if (tick > 0) {
            Esp.renderNotification(displ, cornered, 0xFFFFFF);
        }
    }
}
