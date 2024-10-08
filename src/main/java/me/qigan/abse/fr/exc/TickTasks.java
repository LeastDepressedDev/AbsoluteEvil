package me.qigan.abse.fr.exc;

import me.qigan.abse.config.AddressedData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Used for delayed functions which are dependent on ticks
 */
public class TickTasks {
    private static List<AddressedData<Runnable, Integer>> queue = new ArrayList<>();

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) return;
        List<AddressedData<Runnable, Integer>> toRemove = new ArrayList<>();
        try {
            for (AddressedData<Runnable, Integer> addr : queue) {
                if (addr.getObject() <= 0) {
                    addr.getNamespace().run();
                    toRemove.add(addr);
                } else addr.setObject(addr.getObject() - 1);
            }
        } catch (ConcurrentModificationException ex) {

        }
        queue.removeAll(toRemove);
    }

    public static void call(Runnable rbl, int ticks) {
        queue.add(new AddressedData<>(rbl, ticks));
    }

    public static void clearQ() {
        queue.clear();
    }
}
