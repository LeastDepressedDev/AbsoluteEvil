package me.qigan.abse.fr.qol;

import me.qigan.abse.crp.Module;
import me.qigan.abse.packets.PacketEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PickaxePlus extends Module {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @SideOnly(Side.CLIENT)
    void blockBreak(PacketEvent.SendEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()) {
            if (e.packet instanceof C07PacketPlayerDigging) {
                C07PacketPlayerDigging ev = (C07PacketPlayerDigging) e.packet;
                //if (ev.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                    GhostBlocks.placeBlock(ev.getPosition(), Blocks.air);
                //}
            }
        }
    }

    @Override
    public String id() {
        return "pickp";
    }

    @Override
    public String fname() {
        return "Pickaxe++";
    }

    @Override
    public String description() {
        return "While holding use button adding ghost blocks when mining.";
    }
}
