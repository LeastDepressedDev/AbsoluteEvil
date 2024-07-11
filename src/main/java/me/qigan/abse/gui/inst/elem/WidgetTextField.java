package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;

import java.awt.*;

public class WidgetTextField extends WidgetUpdatable{

    private boolean selected = false;
    public double textScale = 1;

    public String innerText = "";
    public String ph = "";
    public String filterLine = "";
    public int limit = 256;

    public WidgetTextField(int x, int y, int w, int h) {
        super(x, y);
        box(w, h);
    }

    public WidgetTextField setText(String line) {
        this.innerText = line;
        return this;
    }

    public WidgetTextField textScale(double scale) {
        this.textScale = scale;
        return this;
    }

    public WidgetTextField placeholder(String ph) {
        this.ph = ph;
        return this;
    }

    public WidgetTextField filter(String filterLine) {
        this.filterLine = filterLine;
        return this;
    }

    public WidgetTextField lim(int lim) {
        this.limit = lim;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public void deselect() {
        selected = false;
    }

    @Override
    public boolean draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(cordX, cordY, 0d);
        Color col = new Color(NewMainMenu.LINES_COL.getRed()+50, NewMainMenu.LINES_COL.getGreen()+50, NewMainMenu.LINES_COL.getBlue()+50);
        Gui.drawRect(0, 0, boxX, boxY, selected ? col.getRGB() : NewMainMenu.LINES_COL.getRGB());
        Gui.drawRect(1, 1, boxX-1, boxY-1, NewMainMenu.BG_COL_2.getRGB());
        GlStateManager.pushMatrix();
        GlStateManager.translate(3, (double) boxY / 2, 0d);
        GlStateManager.scale(textScale, textScale, 0d);
        Esp.drawOverlayString(NewMainMenu.fntj, innerText.isEmpty() ? ph : innerText, 0, -3, innerText.isEmpty() ? Color.darkGray : Color.gray, S2Dtype.DEFAULT);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        return true;
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        selected = Utils.pointInMovedDim(new Point(mouseX-3, mouseY-6), new Point(cordX, cordY), new Dimension(boxX, boxY));
        super.onClick(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (!isSelected()) return;
        if (keyCode == 14) {
            if (!innerText.isEmpty()) innerText = innerText.substring(0, innerText.length()-1);
        } else if (GuiScreen.isKeyComboCtrlC(keyCode)) {
            GuiScreen.setClipboardString(innerText);
        } else if (GuiScreen.isKeyComboCtrlV(keyCode)) {
            innerText += GuiScreen.getClipboardString();
        } else if (GuiScreen.isKeyComboCtrlX(keyCode)) {
            GuiScreen.setClipboardString(innerText);
            innerText = "";
        } else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
            if (filterLine.length() == 0 || filterLine.contains(Character.toString(typedChar))) innerText += typedChar;
        }
        NewMainMenu.updateRenderedModules();
        super.keyTyped(typedChar, keyCode);
    }
}
