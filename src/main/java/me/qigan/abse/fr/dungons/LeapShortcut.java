package me.qigan.abse.fr.dungons;

import me.qigan.abse.crp.MainWrapper;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LeapShortcut extends Module {

    private static int find() {
        for (int i = 0; i < 9; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i) == null) continue;
            NBTTagCompound tag = Utils.getSbData(Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i));
            if (tag != null && (tag.getString("id").equalsIgnoreCase("SPIRIT_LEAP") ||
                    tag.getString("id").equalsIgnoreCase("INFINITE_SPIRIT_LEAP"))) return i;
        }
        return -1;
    }

    @SubscribeEvent
    void rendTick(RenderWorldLastEvent e) {
        if (!isEnabled() || !Sync.inDungeon) return;
        if (MainWrapper.Keybinds.leapShortcut.isPressed()) {
            new Thread(() -> {
                try {
                    int slot = find();
                    if (slot == -1) return;
                    int pre = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot;
                    Thread.sleep(70);
                    ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), 1);
                    Thread.sleep(40);
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = pre;
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
        }
    }

    @Override
    public String id() {
        return "leapSC";
    }

    @Override
    public String fname() {
        return "Leap shortcut";
    }

    @Override
    public String description() {
        return "Leaps with one button on keyboard!";
    }
}
