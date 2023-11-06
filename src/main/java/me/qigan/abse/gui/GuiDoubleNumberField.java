package me.qigan.abse.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiDoubleNumberField extends GuiTextField {
    public GuiDoubleNumberField(int componentId, FontRenderer fontrendererObj, int x, int y, int Width, int Height) {
        super(componentId, fontrendererObj, x, y, Width, Height);
    }

    @Override
    public boolean textboxKeyTyped(char ch, int tpr) {
        if ((tpr > 0 && tpr < 12) || tpr == 14 || ch == '.' || (this.getText().length() == 0 && tpr == 12)) return super.textboxKeyTyped(ch, tpr);
        else return false;
    }
}
