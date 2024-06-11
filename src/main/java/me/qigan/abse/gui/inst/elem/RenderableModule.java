package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.crp.Module;
import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class RenderableModule extends WidgetUpdatable {

    public static int VERTICAL_GAP = 5;
    public static int INNER_GAP = 3;

    public final Module module;
    public boolean optionOpened = false;

    public RenderableModule(Module module) {
        super(0, 0);
        box(335, 16);
        this.module = module;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        Gui.drawRect(0, 0, boxX, boxY, NewMainMenu.SEMI_BG_COL_1.getRGB());
        Esp.drawOverlayString(NewMainMenu.fntj, module.fname(), cordX+2, cordY+4, 0xFFFFFF, S2Dtype.SHADOW);

        GlStateManager.popMatrix();
    }

    public boolean isOptionOpened() {
        return optionOpened;
    }

    public int calcSize() {
        return boxY+VERTICAL_GAP+(optionOpened ? INNER_GAP*module.sets().size() : 0);
    }
}
