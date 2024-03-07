package me.qigan.abse.fr.kuudra;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoRefillPearls extends Module {

    private static int checkCount() {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (stack == null) continue;
            if (stack.getItem() == Items.ender_pearl && Utils.cleanSB(stack.getDisplayName()).contains("Ender Pearl")) {
                return stack.stackSize;
            }
        }
        return -1;
    }

    private static int tick = -1;

    @SubscribeEvent
    void change(WorldEvent.Load e) {
        if (!isEnabled()) return;
        tick = Index.MAIN_CFG.getIntVal("arefillp_t");
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END || Minecraft.getMinecraft().thePlayer == null) return;
        if (tick > -1) tick--;
        if (tick == 0) {
            int c = checkCount();
            if (c == -1 || c == 16) return;
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/gfs ender_pearl " + Integer.toString(16-c));
        }
    }

    @Override
    public String id() {
        return "arefillp";
    }

    @Override
    public String fname() {
        return "Auto refill";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("arefillp_t", "Delay[tick]", ValType.NUMBER, "100"));
        return list;
    }

    @Override
    public String description() {
        return "Automatically refills pearls from sack on world change";
    }
}
