package me.qigan.abse.sync;

import net.minecraft.client.Minecraft;

public class ClientUtils {
    public static boolean itemReselect(int slot) {
        assert(slot > 8);
        if (Minecraft.getMinecraft().thePlayer.inventory.currentItem == slot) return false;
        Minecraft.getMinecraft().thePlayer.inventory.changeCurrentItem(slot);
        return true;
    }
}
