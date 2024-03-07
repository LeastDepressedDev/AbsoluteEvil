package me.qigan.abse.fr.kuudra;

import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.sync.Sync;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SuplyPearl extends Module {

    private static int getPearlSlot() {
        for (int i = 0; i < 9; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i) == null) continue;
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem() == Items.ender_pearl) return i;
        }
        return -1;
    }

    @SubscribeEvent
    void interaction(PlayerInteractEvent e) {
        if (!isEnabled() || !Sync.inKuudra) return;
        if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            if (Minecraft.getMinecraft().thePlayer.getHeldItem() == null) return;
            if (Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() == Item.getItemFromBlock(Blocks.chest)) {
                int slot = getPearlSlot();
                if (slot == -1) return;
                new Thread(() -> {
                    try {
                        Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot;
                        Thread.sleep(30);
                        ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), 1);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            }
        }
    }

    @Override
    public String id() {
        return "suply_pearl";
    }

    @Override
    public String fname() {
        return "Supply pearl";
    }

    @Override
    public String description() {
        return "throws ender pearl on supply";
    }
}
