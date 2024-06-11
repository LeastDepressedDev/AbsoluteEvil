package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class WidgetButton extends WidgetUpdatable{

    public static Color colorMain = new Color(36, 97, 129);
    public double textScale = 1;

    private String text;
    private final Runnable fun;

    public WidgetButton(int x, int y, int width, int height, Runnable runnable) {
        super(x, y);
        this.fun = runnable;
        box(width, height);
    }

    public WidgetButton text(String text) {
        this.text = text;
        return this;
    }

    public WidgetButton textScale(double scale) {
        this.textScale = scale;
        return this;
    }

    public Runnable getFun() {
        return fun;
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Color colIn = new Color(colorMain.getRed()-20, colorMain.getGreen()-20, colorMain.getBlue()-20);
        if (Utils.pointInMovedDim(new Point(mouseX, mouseY), new Point(cordX, cordY), new Dimension(boxX, boxY))) {
            Gui.drawRect(cordX-1, cordY-1, cordX+boxX+1, cordY+boxY+1, Color.white.getRGB());
        }
        Gui.drawRect(cordX, cordY, cordX+boxX, cordY+boxY, colorMain.getRGB());
        Gui.drawRect(cordX+2, cordY+2, cordX+boxX-2, cordY+boxY-2, colIn.getRGB());

        GlStateManager.pushMatrix();
        GlStateManager.translate(cordX+ (double) boxX / 2, cordY+ (double) boxY / 2, 0d);
        GlStateManager.scale(textScale, textScale, 0d);
        Esp.drawOverlayString(NewMainMenu.fntj, text, - NewMainMenu.fntj.getStringWidth(text) / 2, -3, 0xFFFFFF, S2Dtype.DEFAULT);
        GlStateManager.popMatrix();
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (Utils.pointInMovedDim(new Point(mouseX, mouseY), new Point(cordX, cordY), new Dimension(boxX, boxY))) fun.run();
    }
}
