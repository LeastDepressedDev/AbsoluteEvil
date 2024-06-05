package me.qigan.abse.mapping.auto;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoRouteController {
    public static List<QueuedSeq> seq = null;
    public static int iter = 0;

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) return;
        if (seq == null) {
            iter = 0;
            return;
        }
        if (seq.get(iter).finished) {
            seq.get(iter).finalise();
            iter++;
        }
        seq.get(iter).run();
    }
}
