package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class WidgetSwitch extends WidgetUpdatable{

    public static int CONST_SIZE_W = 24;
    public static int CONST_SIZE_H = 12;

    public static Dimension CONST_SIZES = new Dimension(CONST_SIZE_W, CONST_SIZE_H);

    public boolean enabled = false;

    public final Runnable function;

    public WidgetSwitch(int x, int y, boolean enabled, Runnable rbl) {
        super(x, y);
        this.enabled = enabled;
        this.function = rbl;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(cordX, cordY, 0d);
        Gui.drawRect(0, 0, CONST_SIZE_W, CONST_SIZE_H, NewMainMenu.LINES_COL.getRGB());
        Gui.drawRect(2, 2, CONST_SIZE_W-2, CONST_SIZE_H-2, NewMainMenu.SEMI_BG_COL_1.getRGB());
        if (enabled) Gui.drawRect(4, 4, CONST_SIZE_W-4, CONST_SIZE_H-4, NewMainMenu.LINES_COL.getRGB());
        GlStateManager.popMatrix();
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) return;
        if (Utils.pointInMovedDim(new Point(mouseX, mouseY), new Point(cordX, cordY), CONST_SIZES)) {
            enabled=!enabled;
            function.run();
        }
    }
}
