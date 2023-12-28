package me.qigan.abse.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class HoveringTextBox extends Gui {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    public List<String> lines;


    public HoveringTextBox(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lines = Arrays.asList(text);
    }

    public HoveringTextBox(int x, int y, int width, int height, List<String> lines) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lines = lines;
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
