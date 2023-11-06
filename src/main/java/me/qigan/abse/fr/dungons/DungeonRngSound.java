package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.crp.Module;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DungeonRngSound extends Module {

    private boolean queue = false;

    @SubscribeEvent
    public void onGui(GuiScreenEvent.InitGuiEvent event) {
        if (!this.isEnabled()) return;
        if (!Sync.inDungeon) return;
        if (event.gui instanceof GuiChest && !queue) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(50);
                        IInventory chestInventory = ((ContainerChest) ((GuiChest) event.gui).inventorySlots)
                                .getLowerChestInventory();
                        for (int i = 0; i < chestInventory.getSizeInventory(); i++) {
                            ItemStack in = chestInventory.getStackInSlot(i);
                            if (in.hasDisplayName()) {
                                String str = in.getDisplayName();
                                if (str.contains("Shiny Necron's Handle")) {
                                    Minecraft.getMinecraft().thePlayer.playSound("abse:aaaaaa", 1f, 1f);
                                    Minecraft.getMinecraft().thePlayer.playSound("abse:kurlyk", 1f, 1f);
                                } else if (str.contains("Necron's Handle")) {
                                    Minecraft.getMinecraft().thePlayer.playSound("abse:cum_zone", 1f, 1f);
                                } else if (str.contains("Implosion") || str.contains("Shadow Warp") || str.contains("Wither Shield")) {
                                    Minecraft.getMinecraft().thePlayer.playSound("abse:stonks", 1f, 1f);
                                } else if (str.contains("Master Star")) {
                                    Minecraft.getMinecraft().thePlayer.playSound("abse:cashreg", 1f, 1f);
                                } else if (str.contains("Dark claymore")) {
                                    Minecraft.getMinecraft().thePlayer.playSound("abse:fucked", 1f, 1f);
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            queue = true;
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if (!isEnabled()) return;
        String str = Utils.cleanSB(e.message.getFormattedText());
        if (str.contains("unlocked")) {
            str = str.substring(str.lastIndexOf("unlocked"));
            if (str.contains("Necron's Handle") ||
                    str.contains("Implosion") ||
                    str.contains("Shadow Warp") ||
                    str.contains("Wither Shield")) {
                Minecraft.getMinecraft().thePlayer.playSound("abse:pipe", 1f, 1f);
            }
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        queue = false;
    }

    @Override
    public String id() {
        return "dungwsound";
    }

    @Override
    public String fname() {
        return "Dungeon sounds";
    }

    @Override
    public String description() {
        return "Sounds when you are getting rng. Or someone else...";
    }
}
