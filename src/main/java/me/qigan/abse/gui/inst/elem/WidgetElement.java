package me.qigan.abse.gui.inst.elem;

public class WidgetElement {
    public int cordX;
    public int cordY;

    public int boxX;
    public int boxY;

    public WidgetElement(int x, int y) {
        this.cordX = x;
        this.cordY = y;
    }

    public WidgetElement box(int x, int y) {
        this.boxX = x;
        this.boxY = y;
        return this;
    }
}
