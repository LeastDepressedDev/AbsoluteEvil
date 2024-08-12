package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.List;

public class WidgetOpenSelector extends WidgetUpdatable{
    //-1 means that nothing is selected rn
    public int sel = -1;
    private int heightParam = 18;
    private int page = 0;
    private int amt = 15;
    public final List<String> opts;

    public WidgetOpenSelector(int x, int y, int w, int h, List<String> options) {
        super(x, y);
        box(w, h);
        this.opts = options;
    }

    public WidgetOpenSelector setPageSize(int n) {
        this.amt = n;
        return this;
    }

    public WidgetOpenSelector setOptionHeightParam(int h) {
        this.heightParam = h;
        return this;
    }

    public WidgetOpenSelector setInitialSelected(int v) {
        this.sel = v;
        return this;
    }

    private int calcPageCount() {
        return opts.size()/amt+1;
    }

    @Override
    public boolean draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(cordX, cordY, 0d);
        Color col = new Color(NewMainMenu.LINES_COL.getRed()+50, NewMainMenu.LINES_COL.getGreen()+50, NewMainMenu.LINES_COL.getBlue()+50);
        Gui.drawRect(0, 0, boxX, boxY, col.getRGB());
        Gui.drawRect(1, 1, boxX-1, boxY-1, NewMainMenu.BG_COL_2.getRGB());

        for (int i = 0; i < amt && i < opts.size(); i++) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0d, i*heightParam, 0d);
            if (Utils.pointInMovedDim(new Point(mouseX, mouseY), new Point(cordX, cordY+i*heightParam), new Dimension(boxX, 18)))
                Gui.drawRect(1, 1, boxX-2, 18, NewMainMenu.SEMI_BG_COL_1.getRGB());
            if (i+amt*page == sel) Gui.drawRect(1, 1, boxX-2, 18, NewMainMenu.LL_COL.getRGB());
            String line = opts.get(i+amt*page);
            if (line.length() > 25) {
                line = line.substring(0, 25) + "...";
            }
            Esp.drawOverlayString(NewMainMenu.fntj, line, 2, heightParam/2-3, 0xFFFFFF, S2Dtype.DEFAULT);
            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
        return super.draw(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        for (int i = 0; i < amt && i < opts.size(); i++) {
            if (Utils.pointInMovedDim(new Point(mouseX, mouseY), new Point(cordX, cordY+i*heightParam), new Dimension(boxX, 18))) {
                sel = i+amt*page;
            }
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return amt;
    }
}
