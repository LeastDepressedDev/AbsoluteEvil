package me.qigan.abse.fr.exc;

import me.qigan.abse.config.AddressedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class ClickSimTick {

    public static Map<Integer, Integer> data = new HashMap<>();

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) return;
        try {
            for (Map.Entry<Integer, Integer> s : data.entrySet()) {
                if (s.getValue() > 0) {
                    s.setValue(s.getValue() - 1);
                } else {
                    data.remove(s.getKey());
                    KeyBinding.setKeyBindState(s.getKey(), false);
                }
            }
        } catch (ConcurrentModificationException ex) {}
    }

    public static void click(int code, int tick) {
        KeyBinding.setKeyBindState(code, true);
        data.put(code, tick);
    }
}
