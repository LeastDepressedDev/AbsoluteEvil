package me.qigan.abse.fr.qol;

import me.qigan.abse.crp.Module;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//TODO: Finish this
public class AutoHarp extends Module {

    @SubscribeEvent
    public void onGui(GuiScreenEvent.KeyboardInputEvent event) {
//        if (!this.isEnabled()) return;
//        if (event.gui instanceof GuiChest) {
//            GuiChest chest = (GuiChest) event.gui;
//            ContainerChest c1 = (ContainerChest) chest.inventorySlots;
//            for (int i = 0; i < c1.inventoryItemStacks.size(); i++) {
//                if (c1.inventoryItemStacks.get(i).getItem() == Item.getItemFromBlock(Blocks.quartz_block)) {
//                    event.gui.mc.playerController.windowClick(c1.windowId, i, 0, 0, event.gui.mc.thePlayer);
//                }
//            }
//        }
    }


    @Override
    public String id() {
        return "harp";
    }

    @Override
    public String description() {
        return "Auto harp";
    }
}
