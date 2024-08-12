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
    private boolean renderPages = false;

    public static final int PAGE_BUTTON_HEIGHT = 15;
    public WidgetButton pageDown;
    public WidgetButton pageUp;

    private void pageUp() {
        if (page+1 < calcPageCount()) {
            page++;
        }
        updateButtonState();
    }

    private void pageDown() {
        if (page-1 >= 0) {
            page--;
        }
        updateButtonState();
    }

    private void updateButtonState() {
        pageUp.enabled(page+1 < calcPageCount());
        pageDown.enabled(page-1 >= 0);
    }


    public WidgetOpenSelector(int x, int y, int w, int h, List<String> options) {
        super(x, y);
        box(w, h);
        this.opts = options;
        this.pageDown = new WidgetButton(1, boxY-PAGE_BUTTON_HEIGHT-1, boxX/2, PAGE_BUTTON_HEIGHT, this::pageDown).text("<-");
        this.pageUp = new WidgetButton(boxX/2+1, boxY-PAGE_BUTTON_HEIGHT-1, boxX/2-1, PAGE_BUTTON_HEIGHT, this::pageUp).text("->");
        updateButtonState();
    }

    public WidgetOpenSelector doRenderPages() {
        this.renderPages = true;
        return this;
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

        for (int i = 0; i < amt && i+amt*page < opts.size(); i++) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0d, i*heightParam, 0d);
            if (Utils.pointInMovedDim(new Point(mouseX, mouseY), new Point(cordX, cordY+i*heightParam), new Dimension(boxX, 18)))
                Gui.drawRect(1, 1, boxX-2, 18, NewMainMenu.SEMI_BG_COL_1.getRGB());
            if (i+amt*page == sel) Gui.drawRect(1, 1, boxX-2, 18, NewMainMenu.LL_COL.getRGB());
            String line = opts.get(i+amt*page);
            if (line.length() > 13) {
                line = line.substring(0, 13) + "...";
            }
            Esp.drawOverlayString(NewMainMenu.fntj, line, 2, heightParam/2-3, 0xFFFFFF, S2Dtype.DEFAULT);
            GlStateManager.popMatrix();
        }

        if (renderPages) Esp.drawCenteredString(NewMainMenu.fntj, "Page: " + (page+1) + "/" + calcPageCount(), boxX/2, -8, 0xFFFFFF, S2Dtype.SHADOW);

        pageDown.draw(mouseX-cordX, mouseY-cordY, partialTicks);
        pageUp.draw(mouseX-cordX, mouseY-cordY, partialTicks);

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
        pageDown.onClick(mouseX-cordX, mouseY-cordY, mouseButton);
        pageUp.onClick(mouseX-cordX, mouseY-cordY, mouseButton);
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
