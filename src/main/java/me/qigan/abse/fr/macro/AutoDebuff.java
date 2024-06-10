package me.qigan.abse.fr.macro;

import me.qigan.abse.Index;
import me.qigan.abse.crp.MainWrapper;
import me.qigan.abse.crp.Module;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Macro
public class AutoDebuff extends Module {

    private boolean use = false;
    private int SPRAY_SLOT = -1, SW_SLOT = -1;

    private static int findSlot(String str) {
        for (int i = 0; i < 9; i++) {
            if (Utils.getSbData(Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i))
                    .getString("id").equalsIgnoreCase(str)) return i;
        }
        return -1;
    }



    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled()) return;
        if (MainWrapper.Keybinds.debuffKey.isPressed()) {
            SPRAY_SLOT = findSlot("ICE_SPRAY_WAND");
            SW_SLOT = findSlot("SOUL_WHIP");
            if (SPRAY_SLOT == -1 || SW_SLOT == -1) return;
            new Thread(() -> {
                try {
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = SPRAY_SLOT;
                    Thread.sleep(80);
                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), true);
                    use = true;
                    Thread.sleep(150);
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = SW_SLOT;
                    Thread.sleep(70);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
        }
        if (!MainWrapper.Keybinds.debuffKey.isKeyDown() && use) {
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), false);
            SPRAY_SLOT = -1;
            SW_SLOT = -1;
            use = false;
        }
    }

    @Override
    public String id() {
        return "atm7db";
    }

    @Override
    public Specification category() {
        return Specification.DUNGEONS;
    }

    @Override
    public String fname() {
        return "Auto debuff [M7]";
    }

    @Override
    public String description() {
        return "Automatically debuff dragons on key";
    }
}
