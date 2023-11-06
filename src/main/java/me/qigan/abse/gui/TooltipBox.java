package me.qigan.abse.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;

public class TooltipBox extends Gui {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    public ItemStack stack;


    public TooltipBox(int x, int y, int width, int height, ItemStack stack) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.stack = stack;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean shouldRender(int mouseX, int mouseY) {
        return (mouseX > x && mouseX < x+width) && (mouseY > y && mouseY < y+height);
    }
}
