package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.List;

public class WidgetHoveringTextBox extends WidgetUpdatable{

    public static final int GAP_SIZE = 13;

    private final String text;

    private long timeDiff = 0;
    private Long timeSpec = null;

    private Point realCords = null;

    public WidgetHoveringTextBox(String text, int x, int y, int w, int h) {
        super(x, y);
        box(w, h);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    /**
     * Shows after specific time.
     * Default is 0
     */
    public WidgetHoveringTextBox timed(long millis) {
        this.timeDiff = millis;
        return this;
    }

    public WidgetHoveringTextBox insertRealCords(Point point) {
        this.realCords = point;
        return this;
    }

    public WidgetHoveringTextBox insertRealCords(int x, int y) {
        return insertRealCords(new Point(x, y));
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (Utils.pointInMovedDim(new Point(mouseX, mouseY), new Point(cordX, cordY), new Dimension(boxX, boxY))) {
            if (timeSpec == null) timeSpec = System.currentTimeMillis();
            if (System.currentTimeMillis()-timeSpec<timeDiff) return;
            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            String[] lines = text.split("\n");
            int ln = 0;
            for (String str : lines) {
                int calc = NewMainMenu.fntj.getStringWidth(str);
                if (calc > ln) ln = calc;
            }
            int fullLength = ln+6;

            int subX = realCords == null ? mouseX : realCords.x;
            //TODO: Math.min doesnt work for this case. Just fix it later please.
            int cdX = subX<res.getScaledWidth()/2 ? Math.max(mouseX-fullLength/5, 0)
                    :
                    Math.min(mouseX-4*fullLength/5, res.getScaledWidth()-fullLength);
            int cdY = mouseY-3;


            Gui.drawRect(cdX, cdY-GAP_SIZE*lines.length, cdX+fullLength, cdY, NewMainMenu.BG_COL_1.getRGB());
            Gui.drawRect(cdX+2, cdY-GAP_SIZE*lines.length+2, cdX+fullLength-2, cdY-2, NewMainMenu.LL_COL.getRGB());

            for (int i = 0; i < lines.length; i++) {
                Esp.drawOverlayString(NewMainMenu.fntj, lines[i], cdX+3, cdY-GAP_SIZE*lines.length+GAP_SIZE*i+3, 0xFFFFFF, S2Dtype.SHADOW);
            }
        } else {
            timeSpec = null;
        }
        realCords = null;
    }
}
