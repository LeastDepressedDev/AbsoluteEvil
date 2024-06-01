package me.qigan.abse.fr.qol;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoChoco extends Module {

    public static long openTime = 0;

    public static int tick = 0;

    @SubscribeEvent
    void runtime(GuiScreenEvent.InitGuiEvent e) {
        if (!isEnabled()) return;
        if (e.gui instanceof GuiChest) {
            GuiChest chest = (GuiChest) e.gui;
            ContainerChest c1 = (ContainerChest) chest.inventorySlots;
            if (c1.getLowerChestInventory().getName().startsWith("Chocolate Factory")) {
                openTime = System.currentTimeMillis();
            }
        }
    }

    @SubscribeEvent
    void runtime(GuiScreenEvent.DrawScreenEvent e) {
        if (!isEnabled()) return;
        if (e.gui instanceof GuiChest) {
            GuiChest chest = (GuiChest) e.gui;
            ContainerChest c1 = (ContainerChest) chest.inventorySlots;
            if (c1.getLowerChestInventory().getName().startsWith("Chocolate Factory") && System.currentTimeMillis()-openTime > 2000) {
               guiTick(chest, c1);
            }
        }
    }

    private static void guiTick(GuiChest chest, ContainerChest c) {

    }

    public static void click(GuiChest gui, ContainerChest chest, int slot) {
        gui.mc.playerController.windowClick(chest.windowId, slot, 0, 0, gui.mc.thePlayer);
        tick = Index.MAIN_CFG.getIntVal("auto_choco_tdel");
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (tick > 0) tick--;
    }

    @Override
    public String id() {
        return "auto_choco";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("auto_choco_tdel", "Click delay[tick]", ValType.NUMBER, "2"));
        return list;
    }

    @Override
    public String description() {
        return "True chocolate factory automation";
    }
}
