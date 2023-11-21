package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.gui.GuiNotifier;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class SprayCheck extends Module {

    public static int tick = 0;
    private static boolean ready = true;
    //public static int tickLim;

    @SubscribeEvent(priority = EventPriority.HIGH)
    void onUse(PlayerInteractEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() == null) return;
        if (Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()).getString("id").equalsIgnoreCase("ICE_SPRAY_WAND")) {
            if (ready) {
                tick = Index.MAIN_CFG.getIntVal("scan_time");
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().theWorld == null) return;
        if (tick > 0) {
            tick--;
            if (ready) {
                for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    //if (ent instanceof EntityItem) {
                    if (ent.getName().equalsIgnoreCase("item.tile.ice") && tick > 0) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7b Sprayed!"));
                        GuiNotifier.call("\u00A7bSprayed", 60, false);
                        Minecraft.getMinecraft().thePlayer.playSound("random.orb", 2f, 1f);
                        ready = false;
                        break;
                    }
                    //}
                }
            }
        } else {
            ready = true;
        }
    }

    @Override
    public String id() {
        return "spchk";
    }

    @Override
    public String fname() {
        return "Spray check";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<String>("scan_time", "Scan time(ticks)", ValType.NUMBER, "40"));
        list.add(new SetsData<String>("spray_msg", "Spray message", ValType.STRING, "Sprayed!"));
        return list;
    }

    @Override
    public String description() {
        return "Displaying message when mob is sprayed.";
    }
}
