package me.qigan.abse.gui.inst.elem;

public class WidgetUpdatable extends WidgetElement {
    public double scaleFactorW = 1;
    public double scaleFactorH = 1;

    public WidgetUpdatable scale(double factorW, double factorH) {
        this.scaleFactorW = factorW;
        this.scaleFactorH = factorH;
        return this;
    }

    public WidgetUpdatable scale(double factor) {
        return scale(factor, factor);
    }

    public WidgetUpdatable(int x, int y) {
        super(x, y);
    }

    public boolean draw(int mouseX, int mouseY, float partialTicks) {return true;}

    public void onClick(int mouseX, int mouseY, int mouseButton) {}

    public void keyTyped(char typedChar, int keyCode) {}
}
