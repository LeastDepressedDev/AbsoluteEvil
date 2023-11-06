package me.qigan.abse.fr.dungons.prios;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.gui.GuiNotifier;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.vp.Esp;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class M7Prios extends Module {

    public static Map<M7Dragon, Integer> spawning = new HashMap<>();

    private static boolean first = true;
    private static boolean mod = false;

    @SubscribeEvent
    void onPacket(PacketEvent.ReceiveEvent e) {
        //TODO: Rewrite this stupid scuffed code
        if (!isEnabled()) return;
        if (e.packet instanceof S2APacketParticles) {
            S2APacketParticles part = (S2APacketParticles) e.packet;
            if (part.getParticleType() == EnumParticleTypes.FLAME) {
                M7Dragon drag = M7Dragon.match(part);

                if (drag != null) {
                    if (!spawning.containsKey(drag)) {
                        spawning.put(drag, 100);
                        if (mod) {
                            spawnNotify(drag);
                        }
                    }
                }

                if (first) {
                    first = false;
                    new Thread(() -> {
                        try {
                            Thread.sleep(500);

                            String prio = Index.MAIN_CFG.getStrVal("m7priostr");
                            List<M7Dragon> tdg = new ArrayList<>();
                            try {
                                for (Map.Entry<M7Dragon, Integer> tdgs : spawning.entrySet()) {
                                    tdg.add(tdgs.getKey());
                                }
                            } catch (ConcurrentModificationException ex) {}
                            M7Dragon drag1 = tdg.get(0);
                            M7Dragon drag2 = tdg.get(1);

                            spawnNotify((prio.indexOf(drag1.liter) < prio.indexOf(drag2.liter)) ? drag1 : drag2);
                            mod = true;
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    void onTick(TickEvent.ClientTickEvent e) {
        if (!isEnabled()) return;
        try {
            for (Map.Entry<M7Dragon, Integer> tg : spawning.entrySet()) {
                if (tg.getValue() - 1 < 0) spawning.remove(tg.getKey());
                else spawning.put(tg.getKey(), tg.getValue() - 1);
            }
        } catch (ConcurrentModificationException ex) {}
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled()) return;
        try {
            for (Map.Entry<M7Dragon, Integer> tg : spawning.entrySet()) {
                Esp.renderTextInWorld("\u00A7" + ((tg.getValue() < 20) ? "c" : "a") + tg.getValue().toString(), tg.getKey().particle.getX(), tg.getKey().particle.getY(),
                        tg.getKey().particle.getZ(), 0xFFFFFF, e.partialTicks);
            }
        } catch (ConcurrentModificationException ex) {}
    }

    @SubscribeEvent
    void onLoad(WorldEvent.Load e) {
        spawning.clear();
        first = true;
        mod = false;
    }

    public static void spawnNotify(M7Dragon drag) {
        GuiNotifier.call(drag.name, 60, true);
    }

    @Override
    public String id() {
        return "m7prios";
    }

    @Override
    public String fname() {
        return "M7 prio";
    }

    @Override
    public List<SetsData<String>> sets() {
        List<SetsData<String>> list = new ArrayList<>();
        list.add(new SetsData<>("m7priostr", "Prio string", ValType.STRING, "robpg"));
        return list;
    }

    @Override
    public String description() {
        return "Priority helper for m7";
    }
}
