package me.qigan.abse.fr.dungons;

import me.qigan.abse.crp.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeviceIssue extends Module {

    @SubscribeEvent
    void join(EntityJoinWorldEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        if (e.entity instanceof EntityItemFrame) {
            EntityItemFrame frame = (EntityItemFrame) e.entity;
            if (frame.getDisplayedItem().getItem() == Items.arrow) {

            }
        }
    }

    @Override
    public String id() {
        return "devices";
    }

    @Override
    public String fname() {
        return "Device issue";
    }

    @Override
    public String description() {
        return "Device utils for f7-m7";
    }
}
