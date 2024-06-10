package me.qigan.abse.fr.qol;

import me.qigan.abse.config.AddressedData;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoBlaze extends Module {

    public static enum STATE {
        SPIRIT,
        CRYSTAL,
        ASHEN,
        AURIC
    }

    private static int del = 10;

    private static AddressedData<Integer, Item>[] getSlotsAndStates() {
        AddressedData<Integer, Item>[] data = new AddressedData[]{null, null};
        for (int i = 0; i < 9; i++) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (stack == null) continue;
            if (stack.getDisplayName().contains("Dagger")) {
                if (stack.getItem() == Items.iron_sword || stack.getItem() == Items.diamond_sword) data[0] = new AddressedData<>(i, stack.getItem());
                else if (stack.getItem() == Items.stone_sword || stack.getItem() == Items.golden_sword) data[1] = new AddressedData<>(i, stack.getItem());
            }
        }
        return data;
    }

    private static STATE match() {
        double dist = 5;
        STATE state = null;
        for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            String lnM = Utils.cleanSB(ent.getName());
            double d = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(ent);
            if (lnM.startsWith("ASHEN") && d < dist) state = STATE.ASHEN;
            else if (lnM.startsWith("SPIRIT") && d < dist) state = STATE.SPIRIT;
            else if (lnM.startsWith("AURIC") && d < dist) state = STATE.AURIC;
            else if (lnM.startsWith("CRYSTAL") && d < dist) state = STATE.CRYSTAL;
        }
        return state;
    }

    private void click() {
        new Thread(() -> {
            try {
                Thread.sleep(30);
                ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @SubscribeEvent
    void attack(AttackEntityEvent e) {
        if (!isEnabled() || del > 0) return;
        if (e.entity.getUniqueID() == Minecraft.getMinecraft().thePlayer.getUniqueID()) {
            AddressedData<Integer, Item>[] dats = getSlotsAndStates();
            STATE state = match();
            if (dats[0] == null || dats[1] == null || state == null) return;
            switch (state) {
                case ASHEN:
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = dats[1].getNamespace();
                    if (dats[1].getObject() == Items.golden_sword) click();
                    del = 10;
                    break;
                case AURIC:
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = dats[1].getNamespace();
                    if (dats[1].getObject() == Items.stone_sword) click();
                    del = 10;
                    break;
                case SPIRIT:
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = dats[0].getNamespace();
                    if (dats[0].getObject() == Items.diamond_sword) click();
                    del = 10;
                    break;
                case CRYSTAL:
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = dats[0].getNamespace();
                    if (dats[0].getObject() == Items.iron_sword) click();
                    del = 10;
                    break;
            }
        }
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || e.phase == TickEvent.Phase.END) return;
        if (del > 0) del--;
    }

    @Override
    public String id() {
        return "autoblaze";
    }

    @Override
    public Specification category() {
        return Specification.SB_QOL;
    }

    @Override
    public String fname() {
        return "Auto blaze";
    }

    @Override
    public String description() {
        return "Do that kinky sword swapps";
    }
}
