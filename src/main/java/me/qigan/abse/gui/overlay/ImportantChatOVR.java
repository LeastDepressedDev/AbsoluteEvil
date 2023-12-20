package me.qigan.abse.gui.overlay;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.EnabledByDefault;
import me.qigan.abse.crp.Module;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@EnabledByDefault
public class ImportantChatOVR extends Module {

    public static final double ALPHA_FADE_CONST = 0.34;

    public static List<String> list = new ArrayList<>();
    public static final int CONST_LINE_SIZE = 20;

    private static double pulseA = 0;
    private static int actualA = 255;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    void render(RenderGameOverlayEvent.Text e) {
        if (!isEnabled()) return;
        if (pulseA > 0) pulseA--;
        if (actualA > Index.MAIN_CFG.getIntVal("imp_chat_a") && pulseA == 0) actualA-=ALPHA_FADE_CONST;
        Point loc = Index.POS_CFG.calc("imp_chat");
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 1);
        Esp.drawAllignedTextList(list, loc.x, loc.y, false, e.resolution, S2Dtype.CORNERED,
                new Color(255, 255, 255, (int) actualA));
        GlStateManager.popMatrix();
    }

    public static void clear() {
        list.clear();
    }

    public static void add(String str) {
        if (list.size()+1 > CONST_LINE_SIZE) {
            list.remove(0);
        }
        list.add(str);

        //TODO: Replace it with custom register sound when it finished
        Minecraft.getMinecraft().thePlayer.playSound("note.pling", 2f, 1f);

        pulse();
    }

    public static void remove(int line) {
        list.remove(line);
    }

    public static void pulse() {
        pulseA = Index.MAIN_CFG.getIntVal("imp_chat_pulse");
        actualA = 255;
    }

    @Override
    public String id() {
        return "imp_chat";
    }

    @Override
    public String fname() {
        return "Important chat";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("imp_chat_a", "Opacity[0-255]", ValType.NUMBER, "255"));
        list.add(new SetsData<>("imp_chat_pulse", "Pulse time[Render tick]", ValType.NUMBER, "200"));
        list.add(new SetsData<>("imp_chat_clear", "Clear", ValType.BUTTON, (Runnable) ImportantChatOVR::clear));
        list.add(new SetsData<>("imp_chat_ars", "Auto reset", ValType.BOOLEAN, "true"));
        return list;
    }

    @Override
    public String description() {
        return "Important chat. Like, rly important";
    }
}