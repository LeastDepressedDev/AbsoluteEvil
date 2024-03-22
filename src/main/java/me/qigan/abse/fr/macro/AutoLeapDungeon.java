package me.qigan.abse.fr.macro;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.ClickSimTick;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Macro
public class AutoLeapDungeon extends Module {

    private int findTNT() {
        for (int i = 0; i < 9; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i) == null) continue;
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem() == Item.getItemFromBlock(Blocks.tnt)) return i;
        }
        return -1;
    }

    private int findPicake() {
        for (int i = 0; i < 9; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i) == null) continue;
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem() == Items.diamond_pickaxe
            || Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem() == Items.golden_pickaxe
            || Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem() == Items.iron_pickaxe
            || Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem() == Items.stone_pickaxe
            || Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem() == Items.wooden_pickaxe) return i;
        }
        return -1;
    }

    @SubscribeEvent
    void interact(PlayerInteractEvent e) {
        if (!isEnabled()) return;
        if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (Minecraft.getMinecraft().thePlayer.getHeldItem() == null) return;
            if (Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() == Items.ender_pearl) {
                new Thread(() -> {
                    try {
                        int tnt = findTNT();
                        int pearl = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
                        if (pearl == -1 || tnt == -1) return;
                        Minecraft.getMinecraft().thePlayer.inventory.currentItem = tnt;
                        ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), 1);
                        Thread.sleep(250);
                        Minecraft.getMinecraft().thePlayer.inventory.currentItem = pearl;
                        Thread.sleep(100);
                        ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), 1);
                        Thread.sleep(100);
                        if (Index.MAIN_CFG.getBoolVal("aldwp_ap")) {
                            int p = findPicake();
                            if (p == -1) return;
                            Minecraft.getMinecraft().thePlayer.inventory.currentItem = p;
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            }
        }
    }

    @Override
    public String id() {
        return "aldwp";
    }

    @Override
    public String fname() {
        return "Dungeon pearl stonk";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("aldwp_ap", "Auto swap on near pickaxe", ValType.BOOLEAN, "false"));
        return list;
    }

    @Override
    public String description() {
        return "Automatically swaps on superboom and do goofy stuff.";
    }
}
