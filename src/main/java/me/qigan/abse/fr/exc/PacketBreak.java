package me.qigan.abse.fr.exc;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.AutoDisable;
import me.qigan.abse.crp.DangerousModule;
import me.qigan.abse.crp.EDLogic;
import me.qigan.abse.crp.Module;
import me.qigan.abse.gui.overlay.GuiNotifier;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.sync.Sync;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.network.Packet;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@DangerousModule
@AutoDisable
public class PacketBreak extends Module implements EDLogic {

    private static boolean ready = true;

    @SubscribeEvent
    void load(WorldEvent.Load e) {
        ready = true;
    }

    @SubscribeEvent
    void onSpawn(EntityJoinWorldEvent e) {
        if (!Index.MAIN_CFG.getBoolVal("packet_bk_m7")) return;
        if (ready && Sync.inDungeon && e.entity instanceof EntityWither) {
            if (Minecraft.getMinecraft().thePlayer.getDistance(54, 65, 76) > 16 || e.entity.getDistance(54, 65, 76) > 16) return;
            stun();
            ready = false;
        }
    }

    @Override
    public String id() {
        return "packet_bk";
    }

    @Override
    public String fname() {
        return "Packet breaker";
    }

    @Override
    public String description() {
        return "Freezes your game";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("packet_bk_time", "Time[Seconds]", ValType.DOUBLE_NUMBER, "10"));
        list.add(new SetsData<>("packet_bk_m7", "Auto freeze on m7", ValType.BOOLEAN, "false"));
        return list;
    }

    public static void stun() {
        GuiNotifier.call("\u00A7cSTUNNED", 5, true, 0xFFFFFF);
        Index.MAIN_CFG.set("packet_bk", "false");
        long start = System.nanoTime();
        double sec_time = Index.MAIN_CFG.getDoubleVal("packet_bk_time");
        while ((double) (System.nanoTime() - start) / 1000000000d < sec_time) {}
        Minecraft.getMinecraft().displayGuiScreen(null);
    }

    @Override
    public void onEnable() {
        stun();
    }

    @Override
    public void onDisable() {

    }
}
