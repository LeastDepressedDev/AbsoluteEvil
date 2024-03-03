package me.qigan.abse.fr.qol;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GhostUtils extends Module {

    public static class SessionStats {
        public static int ghostC = 0;
        public static int sorrow = 0;
        public static int volta = 0;
        public static int plasma = 0;
        public static int boots = 0;
        public static int idk = 0;
    }

    public static String toRender() {
        return "\u00A7aGhosty things: \n" +
                "\u00A7cKills: \u00A76" + SessionStats.ghostC +
                "\n\u00A79Sorrow: \u00A76" + SessionStats.sorrow +
                "\n\u00A79Plasma: \u00A76" + SessionStats.plasma +
                "\n\u00A79Volta: \u00A76" + SessionStats.volta +
                "\n\u00A79Ghostly boots: \u00A76" + SessionStats.boots +
                "\n\u00A7cIdk lol: \u00A76" + SessionStats.idk +
                "\n\u00A7bGhosts/sorrow: \u00A76" + (((double) SessionStats.ghostC) / ((double) SessionStats.sorrow + 1)) + "\n";
    }

    private static boolean isNear() {
        for (String str : Utils.getScoreboard()) {
            if (str.contains("Palace Bridge") || str.contains("Royal Mines") || str.contains("Dwarven") || str.contains("The Mist")) return true;
        }
        return false;
    }

    public static void reset() {
        SessionStats.sorrow = 0;
        SessionStats.ghostC = 0;
        SessionStats.idk = 0;
        SessionStats.volta = 0;
        SessionStats.plasma = 0;
        SessionStats.boots = 0;
    }

    @SubscribeEvent
    void renderBox(RenderWorldLastEvent e) {
        if (!isEnabled() || !Index.MAIN_CFG.getBoolVal("box_ghost") || Minecraft.getMinecraft().theWorld == null) return;
        for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (ent instanceof EntityCreeper) {
                Esp.autoBox3D(ent, new Color(255, 0, 0, 255), 3f, true);
            } else if (ent instanceof EntityPlayer && ent.getUniqueID() != Minecraft.getMinecraft().thePlayer.getUniqueID()) {
                if (isNear() && ent.posY <= 90) Esp.autoBox3D(ent, new Color(0, 255, 0, 255), 3f, true);
            }
        }
    }

    @SubscribeEvent
    void renderOverlay(RenderGameOverlayEvent.Text e) {
        if (!isEnabled() || !Index.MAIN_CFG.getBoolVal("ghost_hud")) return;
        char[] str = toRender().toCharArray();
        String nstr = "";
        int r = 0;
        Point pt = Index.POS_CFG.calc("ghost_utils");
        for (int i = 0; i < str.length; i++) {
            if (str[i] == '\n') {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(nstr, pt.x, pt.y + (r*10), 0xFFFFFF);
                nstr = "";
                r++;
            } else {
                nstr += str[i];
            }
        }
    }

    @SubscribeEvent
    void onKill(LivingDeathEvent e) {
        if (!isEnabled()) return;
        if (e.entityLiving == null || Minecraft.getMinecraft().thePlayer == null) return;
        if (e.entityLiving.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) < 7) {
            if (e.entityLiving instanceof EntityCreeper) {
                SessionStats.ghostC++;
                if (Index.MAIN_CFG.getBoolVal("custom_ghost_sound")) Minecraft.getMinecraft().thePlayer.playSound("abse:skeet_hit", 1f, 1f);
            }
        }
    }

    @SubscribeEvent
    void onMsg(ClientChatReceivedEvent e) {
        if (!isEnabled()) return;
        String str = Utils.cleanSB(e.message.getFormattedText());
        if (str.startsWith("RARE DROP! ")) {
            str = str.substring(11);
            if (str.startsWith("Sorrow")) {
                SessionStats.sorrow++;
            } else if (str.startsWith("Volta")) {
                SessionStats.volta++;
            } else if (str.startsWith("Plasma")) {
                SessionStats.plasma++;
            } else if (str.startsWith("Ghostly Boots")) {
                SessionStats.boots++;
            } else {
                SessionStats.idk++;
            }
        }
    }

    @Override
    public String id() {
        return "ght";
    }

    @Override
    public String fname() {
        return "Ghost Utils";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("box_ghost", "Ghost box", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("ghost_hud", "Hud", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("custom_ghost_sound", "Custom kill sound[WIP]", ValType.BOOLEAN, "false"));
        return list;
    }

    @Override
    public String description() {
        return "Ghost tracking and some other shit";
    }
}
