package me.qigan.abse.fr.exc;

import me.qigan.abse.gui.overlay.GuiNotifier;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Alert {

    private static int globalTick = 0;
    private static String alertText = "Def";
    private static int vol = 2;

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) return;
        if (globalTick == 0) return;

        GuiNotifier.call(alertText, 30, true, 0xFFFFFF);
        for (int i = 0; i < vol; i++) {
            Minecraft.getMinecraft().thePlayer.playSound("note.pling", 2f, 1f);
        }
        globalTick--;
    }

    public static void call(String text, int ticks, int volume) {
        alertText = text;
        globalTick = ticks;
        vol = volume;
    }

    public static void forceStop() {
        globalTick = 0;
    }
}
