package me.qigan.abse.fr.exc;

import me.qigan.abse.gui.overlay.GuiNotifier;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Alert {

    public static int globalTick = 0;
    public static String alertText = "Def";

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) return;
        if (globalTick == 0) return;

        GuiNotifier.call(alertText, 30, true, 0xFFFFFF);
        Minecraft.getMinecraft().thePlayer.playSound("note.pling", 2f, 1f);
        Minecraft.getMinecraft().thePlayer.playSound("note.pling", 2f, 1f);
        globalTick--;
    }

    public static void call(String text, int ticks) {
        alertText = text;
        globalTick = ticks;
    }
}
