package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InvTracker extends Module {

    public static long CURRENT = 0;
    public static long TIME_LIM = 0;

    public static long BONZO = 0;
    public static long SPIRIT = 0;
    public static long PHOENIX = 0;

    @SubscribeEvent
    void chat(ClientChatReceivedEvent e) {
        if (!isEnabled()) return;
        String f = Utils.cleanSB(e.message.getFormattedText());
        if (f.equalsIgnoreCase("Second Wind Activated! Your Spirit Mask saved your life!")) {
            SPIRIT = System.currentTimeMillis();
            CURRENT = System.currentTimeMillis();
            TIME_LIM = 3000;
        }
        else if (f.equalsIgnoreCase("Your Phoenix Pet saved you from certain death!")) {
            PHOENIX = System.currentTimeMillis();
            CURRENT = System.currentTimeMillis();
            TIME_LIM = (long) (Index.MAIN_CFG.getDoubleVal("invtracker_p") * 1000d);
        }
        else if (f.equalsIgnoreCase("Your  Bonzo's Mask saved your life!")
                || f.equalsIgnoreCase("Your Bonzo's Mask saved your life!")) {
            BONZO = System.currentTimeMillis();
            CURRENT = System.currentTimeMillis();
            TIME_LIM = 3000;
        }
    }

    @SubscribeEvent
    void render(RenderGameOverlayEvent.Text e) {
        if (!isEnabled()) return;
        if (Sync.inDungeon) {
            List<String> lines = new ArrayList<>();
            lines.add("\u00A79Bonzo: " + (System.currentTimeMillis() - BONZO > 180000 ? "\u00A7aREADY" :
                    "\u00A7e" + ((180000 - System.currentTimeMillis() + BONZO) / 1000d) + "s"));
            lines.add("\u00A77Spirit: " + (System.currentTimeMillis() - SPIRIT > 30000 ? "\u00A7aREADY" :
                    "\u00A7e" + ((30000 - System.currentTimeMillis() + SPIRIT) / 1000d) + "s"));
            lines.add("\u00A76Phoenix: " + (System.currentTimeMillis() - PHOENIX > 60000 ? "\u00A7aREADY" :
                    "\u00A7e" + ((60000 - System.currentTimeMillis() + PHOENIX) / 1000d) + "s"));
            Point p = Index.POS_CFG.calc("invtracker");
            Esp.drawAllignedTextList(lines, p.x, p.y, false, e.resolution, S2Dtype.SHADOW);
        }

        if (System.currentTimeMillis() - CURRENT < TIME_LIM) {
            int d = (int) ((System.currentTimeMillis() - CURRENT)/(double) TIME_LIM*255d);
            Esp.renderSubNotification(lost() + "ms", false, new Color(Utils.colorLimit(d), Utils.colorLimit(255 - d),0).getRGB());
        }
    }

    public static long lost() {
        return TIME_LIM - (System.currentTimeMillis() - CURRENT);
    }

    @Override
    public String id() {
        return "invtracker";
    }

    @Override
    public Specification category() {
        return Specification.SB_QOL;
    }

    @Override
    public String fname() {
        return "Inv tracker";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("invtracker_p", "Phoenix time[secs]", ValType.DOUBLE_NUMBER, "3"));
        return list;
    }

    @Override
    public String description() {
        return "Tracks items that gives you invulnerability";
    }
}
