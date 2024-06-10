package me.qigan.abse.fr.qol;

import me.qigan.abse.sync.Utils;
import me.qigan.abse.crp.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GyroAddons extends Module {

    @SubscribeEvent(priority = EventPriority.HIGH)
    void onUse(PlayerInteractEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() == null) return;
        if (Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()).getString("id").equalsIgnoreCase("GYROKINETIC_WAND")) {
            e.setCanceled(true);
        }
    }

    @Override
    public String id() {
        return "gyad";
    }

    @Override
    public Specification category() {
        return Specification.SB_QOL;
    }

    @Override
    public String fname() {
        return "Cancel allign";
    }

    @Override
    public String description() {
        return "GyroAddons(prevent align)";
    }
}
