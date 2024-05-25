package me.qigan.abse.fr;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.vp.IntList;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class LagTracker extends Module {

    public static double AVERAGE = 0;

    public static int ticks_since = 0;
    private static final IntList avgl = new IntList();

    public static long ll = 0;

    public static long diff() {
        return System.currentTimeMillis()-ll;
    }

    @SubscribeEvent
    void packetIn(PacketEvent.ReceiveEvent e) {
        if (e.packet instanceof S01PacketPong || e.packet instanceof S00PacketKeepAlive) ll = System.currentTimeMillis();
        if (!isEnabled()) return;
        avgl.add(ticks_since);
        if (avgl.size() > Index.MAIN_CFG.getIntVal("lag_tracker_pulls")) avgl.remove(0);
        ticks_since = 0;
    }

    @SubscribeEvent
    void clientTickEvent(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || e.phase == TickEvent.Phase.START) return;
        ticks_since++;
    }

    @SubscribeEvent
    void rend(RenderWorldLastEvent e) {
        AVERAGE = avgl.medium(ticks_since);
    }

    @Override
    public String id() {
        return "lag_tracker";
    }

    @Override
    public String fname() {
        return "\u00A7cLag tracker";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("lag_tracker_pulls", "Pulls", ValType.NUMBER, "120"));
        return list;
    }

    @Override
    public String description() {
        return "Tracks packet rate. Oh god, this is SO FUCKING USEFUL!!!";
    }
}
