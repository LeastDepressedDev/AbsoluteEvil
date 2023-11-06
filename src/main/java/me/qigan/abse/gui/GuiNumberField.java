package me.qigan.abse.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
//item.tile.ice
public class GuiNumberField extends GuiTextField {
    public GuiNumberField(int componentId, FontRenderer fontrendererObj, int x, int y, int Width, int Height) {
        super(componentId, fontrendererObj, x, y, Width, Height);
    }

    @Override
    public boolean textboxKeyTyped(char ch, int tpr) {
        //if (tpr == ) 1 2 3 4 5 6 7 8 9 0 backspace
        if (tpr > 0 && tpr < 12 || tpr == 14 || (this.getText().length() == 0 && tpr == 12)) return super.textboxKeyTyped(ch, tpr);
        else return false;
    }
}
