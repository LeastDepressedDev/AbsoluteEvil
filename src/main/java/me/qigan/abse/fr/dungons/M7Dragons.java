package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.gui.overlay.GuiNotifier;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

public class M7Dragons extends Module {

    /* From drag prio ct module

    export const dragInfo = {

    POWER: { dragColor: "RED", renderColor: Renderer.RED, prio: [1, 3], spawned: false, easy: false },
    FLAME: { dragColor: "ORANGE", renderColor: Renderer.GOLD, prio: [2, 1], spawned: false, easy: true },
    ICE: { dragColor: "BLUE", renderColor: Renderer.BLUE, prio: [3, 4], spawned: false, easy: false },
    SOUL: { dragColor: "PURPLE", renderColor: Renderer.LIGHT_PURPLE, prio: [4, 5], spawned: false, easy: true },
    APEX: { dragColor: "GREEN", renderColor: Renderer.GREEN, prio: [5, 2], spawned: false, easy: true },
}*/

    public static enum DRAGON {
        RED(new BlockPos(23, 20, 59), "\u00A7cRed", 1,3, false, new BlockPos(26, 20, 59)),
        ORANGE(new BlockPos(89, 21, 56), "\u00A76Orange", 2, 1, true, new BlockPos(85, 21, 56)),
        BLUE(new BlockPos(88, 21, 94), "\u00A7bBlue", 3, 4, false, new BlockPos(84, 21, 94)),
        PURPLE(new BlockPos(56, 20, 129), "\u00A7dPurple", 4, 5, true, new BlockPos(56, 20, 125)),
        GREEN(new BlockPos(23, 21, 94), "\u00A7aGreen", 5, 2, true, new BlockPos(26, 21, 94))

        ;

        public final String name;
        public final BlockPos pos;
        public final int[] prio;
        public final boolean isEasy;
        public final BlockPos timerLocation;


        DRAGON(BlockPos pos, String name, int normalPrio, int easyPrio, boolean isEasy, BlockPos timerLoc) {
            this.pos = pos;
            this.name = name;
            this.isEasy = isEasy;
            this.prio = new int[]{normalPrio, easyPrio};
            this.timerLocation = timerLoc;
        }

        public static DRAGON match(BlockPos pos) {
            for (DRAGON drag : values()) {
                if (Utils.compare(pos, drag.pos)) return drag;
            }
            return null;
        }
    }

    public static Set<DRAGON> done = new HashSet<>();
    public static Map<DRAGON, Long> spawning = new HashMap<>();

    public static boolean started = false;
    public static boolean first = true;

    @SubscribeEvent
    void load(WorldEvent.Load e) {
        done.clear();
        first = true;
        started = false;
        spawning.clear();
    }

    @SubscribeEvent
    void playSound(PacketEvent.ReceiveEvent e) {
        if (!Sync.inDungeon) return;
        if (e.packet instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect s = (S29PacketSoundEffect) e.packet;
            BlockPos bp = new BlockPos((int) s.getX(), (int) s.getY(), (int) s.getZ());
            DRAGON drag = DRAGON.match(bp);
            if (drag != null) {
                if (s.getSoundName().equalsIgnoreCase("random.explode") && started) {
                    if (!done.contains(drag)) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(drag.name + " is done!"));
                        done.add(drag);
                    }
                }
            }
        }
        if (e.packet instanceof S2APacketParticles) {
            S2APacketParticles ptl = (S2APacketParticles) e.packet;
            if (ptl.getParticleType() != EnumParticleTypes.ENCHANTMENT_TABLE) return;
            BlockPos bp = new BlockPos((int) ptl.getXCoordinate(), (int) ptl.getYCoordinate(), (int) ptl.getZCoordinate());
            DRAGON drag = check(bp);
            if (drag != null) {
                if (!spawning.containsKey(drag)) {
                    started = true;
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(drag.name + " is spawning!"));
                    spawning.put(drag, System.currentTimeMillis());
                    if (spawning.size() > 1) first();
                    else if (!first) anonc(drag);
                }
            }
        }
    }


    /**
     * Force split cuz im lazy af
     */
    private void first() {
        if (!first) return;
        first = false;
        //double p = Sync.getPowerInDungeon();
        List<DRAGON> drags = new ArrayList<>();
        for (Map.Entry<DRAGON, Long> ele : spawning.entrySet()) {
            drags.add(ele.getKey());
        }
        //if (p >= Index.MAIN_CFG.getDoubleVal("m7drags_epower")) {
            int in = 1/*p >= Index.MAIN_CFG.getDoubleVal("m7drags_power") ? 0 : 1*/;
            char c = Sync.getPlayerDungeonClass();
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7aClass: " + c));
            drags.sort(Comparator.comparingInt(o -> o.prio[in]));
            System.out.println("Drags: ");
            for (DRAGON drag : drags) {
                System.out.println(drag);
            }
            switch (c) {
                case 'H':
                case 'B':
                case 'M':
                    anonc(drags.get(0));
                    break;
                case 'A':
                case 'T':
                case 'U':
                    anonc(drags.get(1));
                    break;
            }
//        } else {
//            drags.sort(Comparator.comparingInt(o -> o.prio[0]));
//            anonc(drags.get(0));
//        }
    }

    private void anonc(DRAGON dragon) {
        GuiNotifier.call(dragon.name, 40, true, 0xFFFFFF);
    }

    DRAGON check(BlockPos pos) {
        if (!(pos.getY() >= 14 && pos.getY() <= 19)) return null;
        if (pos.getX() >= 27 && pos.getX() <= 32) {
            if (pos.getZ() == 59) return DRAGON.RED;
            if (pos.getZ() == 94) return DRAGON.GREEN;
        } else if (pos.getX() >= 79 && pos.getX() <= 85) {
            if (pos.getZ() == 94) return DRAGON.BLUE;
            if (pos.getZ() == 56) return DRAGON.ORANGE;
        } else if (pos.getX() == 56 && (pos.getZ() <= 130 && pos.getZ() >= 110)) return DRAGON.PURPLE;
        return null;
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().thePlayer.posY > 25 || !Sync.inDungeon) return;
        long timeRN = System.currentTimeMillis();
        for (DRAGON drag : DRAGON.values()) {
            if (!done.contains(drag)) Esp.renderTextInWorld(drag.name, drag.pos, 0xFFFFFF, 1.6, e.partialTicks);
        }
        Map<DRAGON, Long> map = new HashMap<>(spawning);
        for (Map.Entry<DRAGON, Long> ele : map.entrySet()) {
            long dif = timeRN-ele.getValue();
            if (dif >= 5000) spawning.remove(ele.getKey());
            Esp.renderTextInWorld(Long.toString(dif), ele.getKey().timerLocation,
                    Color.green.getRGB(), 4, e.partialTicks);
        }
    }

    @Override
    public String id() {
        return "m7drags";
    }

    @Override
    public String fname() {
        return "M7 dragons";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        //list.add(new SetsData<>("m7drags_power", "Power", ValType.DOUBLE_NUMBER, "19"));
        //list.add(new SetsData<>("m7drags_epower", "Easy power", ValType.DOUBLE_NUMBER, "17"));
        return list;
    }

    @Override
    public String description() {
        return "Utils to track dragon skips and spawns";
    }
}
