package me.qigan.abse.fr.cbh;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.events.LivingHurtEvent;
import me.qigan.abse.events.PacketEvent;
import me.qigan.abse.fr.exc.ClickSimTick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.*;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

public class CombatHelperSR extends Module {
    //private static int timer = 0;

    @SubscribeEvent
    void combo(LivingHurtEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().thePlayer == null) return;
        double horDist = Math.sqrt(Math.pow(Minecraft.getMinecraft().thePlayer.posX-e.entity.posX, 2) +
                Math.pow(Minecraft.getMinecraft().thePlayer.posZ-e.entity.posZ, 2));
        if (horDist < Index.MAIN_CFG.getDoubleVal("cbh_resd") && e.entity.getUniqueID() != Minecraft.getMinecraft().thePlayer.getUniqueID()) {
            if (Minecraft.getMinecraft().thePlayer.isSprinting()) {
                if (Index.MAIN_CFG.getBoolVal("cbh_sword") && Minecraft.getMinecraft().thePlayer.getHeldItem() != null &&
                        Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                    ClickSimTick.clickWCheck(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), Index.MAIN_CFG.getIntVal("cbh_sword_time"));
                } else {
                    new Thread(() -> {
                        try {
                            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
                            Thread.sleep(Math.abs(new Random().nextInt() % 100) + 50);
                            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), true);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }).start();
                }
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
        list.add(new SetsData<>("cbh_sword", "Use sword if possible", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("cbh_sword_time", "Block time", ValType.NUMBER, "3"));
        return list;
    }

    @Override
    public String description() {
        return "Helps you to be on top";
    }
}
