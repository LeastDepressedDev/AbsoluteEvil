package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class WidgetUpperBar extends WidgetUpdatable {

    public WidgetTextField searchBar = new WidgetTextField(90, 28, 207, 18).placeholder("Enter your prompt here.").allowAutoUpdate();

    public WidgetUpperBar() {
        super(0, 0);
    }

    @Override
    public boolean draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((int) (NewMainMenu.MATRIX_SIZES.width/4f)+10, 7, 0d);

        GlStateManager.pushMatrix();
        GlStateManager.scale(1.5d, 1.5d, 0d);
        Esp.drawOverlayString(NewMainMenu.fntj, "View mode:", 0, 4, 0xFFFFFF, S2Dtype.DEFAULT);
        GlStateManager.popMatrix();

        Color col1 = new Color(NewMainMenu.BG_COL_2.getRed()+15, NewMainMenu.BG_COL_2.getGreen()+15, NewMainMenu.BG_COL_2.getBlue()+15);
        Color col2 = new Color(NewMainMenu.BG_COL_2.getRed()+35, NewMainMenu.BG_COL_2.getGreen()+35, NewMainMenu.BG_COL_2.getBlue()+35);
        Gui.drawRect(90, 2, 190, 22, NewMainMenu.viewMode == 0 ? col2.getRGB() : col1.getRGB());
        Esp.drawCenteredString("Categorized", 140, 8, 0xFFFFFF, S2Dtype.SHADOW);

        Gui.drawRect(197, 2, 297, 22, NewMainMenu.viewMode == 1 ? col2.getRGB() : col1.getRGB());
        Esp.drawCenteredString("All", 247, 8, 0xFFFFFF, S2Dtype.SHADOW);

        Esp.drawOverlayString(NewMainMenu.fntj, "Search:", 0, 32, 0xFFFFFF, S2Dtype.DEFAULT);
        searchBar.draw(mouseX, mouseY, partialTicks);

        GlStateManager.popMatrix();
        return true;
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (Utils.pointInMovedDim(new Point(mouseX, mouseY),
                new Point((int) (NewMainMenu.MATRIX_SIZES.width/4f)+100, 9),
                new Dimension(100, 20))) {
            NewMainMenu.viewMode = 0;
            NewMainMenu.updateRenderedModules();
        }
        if (Utils.pointInMovedDim(new Point(mouseX, mouseY),
                new Point((int) (NewMainMenu.MATRIX_SIZES.width/4f)+207, 9),
                new Dimension(100, 20))) {
            NewMainMenu.viewMode = 1;
            NewMainMenu.updateRenderedModules();
        }
        searchBar.onClick(mouseX-(int) (NewMainMenu.MATRIX_SIZES.width/4f)-10-3, mouseY-6, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        searchBar.keyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }
}
