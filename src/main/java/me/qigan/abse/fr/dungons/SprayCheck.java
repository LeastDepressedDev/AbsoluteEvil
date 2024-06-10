package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.gui.overlay.GuiNotifier;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SprayCheck extends Module {

    public static final int SPRAY_TICKS_CONST = 100;

    public static int tick = 0;
    private static boolean ready = true;
    //public static int tickLim;

    public static Set<AddressedData<Entity, Integer>> attracted = new HashSet<>();

    @SubscribeEvent
    void load(WorldEvent.Unload e) {
        attracted.clear();
    }

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
        if (!isEnabled() || e.phase == TickEvent.Phase.END) return;
        if (Minecraft.getMinecraft().theWorld == null) return;
        if (tick > 0) {
            tick--;
            if (ready) {
                for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    //if (ent instanceof EntityItem) {
                    if (ent.getName().equalsIgnoreCase("item.tile.ice") && tick > 0) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7b Sprayed!"));
                        GuiNotifier.call("\u00A7bSprayed", 60, false, 0xFFFFFF);
                        Minecraft.getMinecraft().thePlayer.playSound("random.orb", 2f, 1f);
                        for (Entity xEnt : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                            if (xEnt instanceof EntityPlayer || xEnt instanceof EntityArmorStand || xEnt instanceof EntityItem
                            || xEnt instanceof EntityItemFrame || xEnt instanceof EntityEnderCrystal || xEnt instanceof EntityEnderPearl
                            || xEnt instanceof EntityEnderEye || xEnt instanceof EntityExpBottle || xEnt instanceof EntityArrow
                            || xEnt instanceof EntityXPOrb) continue;
                            if (ent.getDistanceToEntity(xEnt) < 5 || (xEnt instanceof EntityDragon && ent.getDistanceToEntity(xEnt) < 15)) {
                                attracted.add(new AddressedData<>(xEnt, SPRAY_TICKS_CONST));
                            }
                        }
                        ready = false;
                        break;
                    }
                    //}
                }
            }
        } else {
            ready = true;
        }

        //Goofy code
        try {
            for (AddressedData<Entity, Integer> mp : attracted) {
                if (mp.getObject() <= 0) attracted.remove(mp);
                mp.setObject(mp.getObject()-1);
            }
        } catch (ConcurrentModificationException ex) {}
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    void rend(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null || !Index.MAIN_CFG.getBoolVal("sprayed_mobs_rend")) return;
        //Goofy code X2
        try {
            for (AddressedData<Entity, Integer> ent : attracted) {
                if (!ent.getNamespace().isEntityAlive()) {
                    attracted.remove(ent);
                    continue;
                }
                Esp.renderTextInWorld("Sprayed", ent.getNamespace().posX, ent.getNamespace().posY + 1.7, ent.getNamespace().posZ, Color.cyan.getRGB(),
                        Index.MAIN_CFG.getDoubleVal("sprayed_mobs_rend_size"), e.partialTicks);
            }
        } catch (ConcurrentModificationException ex) {}
    }

    @Override
    public String id() {
        return "spchk";
    }

    @Override
    public Specification category() {
        return Specification.SB_QOL;
    }

    @Override
    public String fname() {
        return "Spray check";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("scan_time", "Scan time(ticks)", ValType.NUMBER, "25"));
        list.add(new SetsData<>("spray_msg", "Spray message", ValType.STRING, "Sprayed!"));
        list.add(new SetsData<>("sprayed_mobs_rend", "Show sprayed mobs", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("sprayed_mobs_rend_size", "Text size", ValType.DOUBLE_NUMBER, "1.6"));
        return list;
    }

    @Override
    public String description() {
        return "Displaying message when mob is sprayed.";
    }
}
