package me.qigan.abse.fr.exc;

import me.qigan.abse.config.AddressedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ClickSimTick {

    public static List<AddressedData<Integer, Integer>> data = new ArrayList<>();

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) return;
        try {
            for (AddressedData<Integer, Integer> s : data) {
                if (s.getObject() > 0) {
                    s.setObject(s.getObject() - 1);
                } else {
                    data.remove(s);
                    KeyBinding.setKeyBindState(s.getNamespace(), false);
                }
            }
        } catch (ConcurrentModificationException ex) {}
    }

    public static void click(int code, int tick) {
        KeyBinding.setKeyBindState(code, true);
        data.add(new AddressedData<>(code, tick));
    }
}
