package me.qigan.abse.fr.cbh;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatHelperSR extends Module {
    //private static int timer = 0;

    @SubscribeEvent
    void combo(LivingHurtEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().thePlayer == null) return;
        if (e.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) < Index.MAIN_CFG.getDoubleVal("cbh_resd") && e.entity.getUniqueID() != Minecraft.getMinecraft().thePlayer.getUniqueID()) {
            if (Minecraft.getMinecraft().thePlayer.isSprinting()) {
                new Thread(() -> {
                    try {
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
                        Thread.sleep(Math.abs(new Random().nextInt()%100)+50);
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), true);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            }
        }
    }

    @Override
    public String id() {
        return "cbh_wtap";
    }

    @Override
    public Specification category() {
        return Specification.COMBAT;
    }

    @Override
    public String fname() {
        return "Combat helper[sprint]";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("cbh_resd", "Resprint distance[double(1-3)]", ValType.DOUBLE_NUMBER, "3"));
        return list;
    }

    @Override
    public String description() {
        return "Helps you to be on top";
    }
}
