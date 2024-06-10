package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class MelodyShit extends Module {

    private boolean queue = false;

    @SubscribeEvent
    public void onGui(GuiScreenEvent.InitGuiEvent event) {
        if (!this.isEnabled()) return;
        if (event.gui instanceof GuiChest && !queue) {
            GuiChest chest = (GuiChest) event.gui;
            ContainerChest c1 = (ContainerChest) chest.inventorySlots;
            if (c1.getLowerChestInventory().getName().startsWith("Click the button on time!")) {
                String msg = Index.MAIN_CFG.getStrVal("melody_message");
                Minecraft.getMinecraft().thePlayer.sendChatMessage(msg == null ? "Melody!" : msg);
                queue = true;
            }
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        queue = false;
    }

    @Override
    public String id() {
        return "melody";
    }

    @Override
    public Specification category() {
        return Specification.DUNGEONS;
    }

    @Override
    public String fname() {
        return "Custom melody message";
    }

    @Override
    public String description() {
        return "Melody term message(Change 'melody_message' in config for custom message)";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<String>("melody_message", "Message", ValType.STRING, "Melody"));
        return list;
    }
}
