package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.crp.Module;
import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class WidgetCategoryRenderer extends WidgetUpdatable {

    public static int VERTICAL_BOX_SIZE = 20;

    public WidgetCategoryRenderer() {
        super(0, 0);
    }

    private int dx = 20;

    public Module.Specification selected = null;
    public boolean deselect = true;

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0d, ((double) NewMainMenu.MATRIX_SIZES.height / 7), 0d);
        dx = 20;
        deselect = true;
        for (Module.Category category : Module.Category.values()) {
            Esp.drawOverlayString(NewMainMenu.fntj, category.name, 7, dx, NewMainMenu.BG_COL_1, S2Dtype.DEFAULT);
            Gui.drawRect(5, dx+10, (int) (NewMainMenu.MATRIX_SIZES.width/4f-7), dx+11, NewMainMenu.LINES_COL.getRGB());
            dx+=16;
            for (Module.Specification spec : Module.Specification.allByCategory(category))
                drawOption(spec, new Point(mouseX, mouseY));
        }

        if (deselect) selected = null;
        GlStateManager.popMatrix();
    }

    private void drawOption(Module.Specification spec, Point mousePoint) {
        if (Utils.pointInMovedDim(mousePoint, new Point(5, (int) (((double) NewMainMenu.MATRIX_SIZES.height / 7)+dx)),
                new Dimension((int) (NewMainMenu.MATRIX_SIZES.width/4f-12), VERTICAL_BOX_SIZE))) {
            deselect = false;
            selected = spec;
            Color col = new Color(NewMainMenu.LL_COL.getRed()+20, NewMainMenu.LL_COL.getGreen()+20, NewMainMenu.LL_COL.getBlue()+20);
            Gui.drawRect(5, dx, (int) (NewMainMenu.MATRIX_SIZES.width/4f-7), dx+VERTICAL_BOX_SIZE, col.getRGB());
        }
        if (NewMainMenu.selectedCategory == spec) {
            Gui.drawRect(5, dx, (int) (NewMainMenu.MATRIX_SIZES.width/4f-7), dx+VERTICAL_BOX_SIZE,
                    NewMainMenu.BG_COL_1.getRGB());
        }
        Esp.drawOverlayString(NewMainMenu.fntj, spec.name, 8,dx+VERTICAL_BOX_SIZE/2-3, 0xFFFFFF,
                NewMainMenu.selectedCategory == spec ? S2Dtype.SHADOW : S2Dtype.DEFAULT);
        dx+=VERTICAL_BOX_SIZE+VERTICAL_BOX_SIZE/4;
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (selected != null) NewMainMenu.selectedCategory = selected;
    }
}
