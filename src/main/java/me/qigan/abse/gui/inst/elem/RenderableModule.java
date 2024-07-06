package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.crp.Module;
import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class RenderableModule extends WidgetUpdatable {

    public static int VERTICAL_GAP = 5;
    public static int INNER_GAP = 3;

    public final Module module;
    public boolean optionOpened = false;

    public final WidgetSwitch sch;

    public Point updatablePosition = new Point(0, 0);

    public RenderableModule(Module module) {
        super(0, 0);
        box(335, 16);
        this.module = module;
        this.sch = new WidgetSwitch(310, 2, module.isEnabled(), () -> Index.MAIN_CFG.toggle(module.id()));
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        Gui.drawRect(cordX, cordY, boxX, boxY+sizeFunction(), NewMainMenu.SEMI_BG_COL_1.getRGB());
        Esp.drawOverlayString(NewMainMenu.fntj, module.fname(), cordX+2, cordY+4, 0xFFFFFF, S2Dtype.SHADOW);
        GlStateManager.pushMatrix();
        GlStateManager.translate(cordX+293, cordY+1, 0d);
        GlStateManager.scale(1.7d, 1.7d, 0d);
        if (!module.sets().isEmpty()) Esp.drawOverlayString(NewMainMenu.fntj, "\u2699", 0, 0, 0x00FF30, S2Dtype.CORNERED);
        GlStateManager.popMatrix();

        if (optionOpened && !module.sets().isEmpty()) {
            Gui.drawRect(3, boxY+1, boxX-3, boxY+2, NewMainMenu.LINES_COL.getRGB());

            for (int i = 0; i < module.sets().size(); i++) {
                SetsData<?> data = module.sets().get(i);
                int calcYPos = cordY+boxY+(INNER_GAP+10)*i+4;
                Esp.drawOverlayString(NewMainMenu.fntj, data.guiName, cordX+2, calcYPos, 0xFFFFFF, S2Dtype.SHADOW);


            }
        }


        sch.draw(mouseX, mouseY, partialTicks);
        GlStateManager.popMatrix();
    }

    public boolean isOptionOpened() {
        return optionOpened;
    }

    private int sizeFunction() {
        return (optionOpened && !module.sets().isEmpty() ? (INNER_GAP+10)*module.sets().size()+INNER_GAP : 0);
    }

    public int calcSize() {
        return boxY+VERTICAL_GAP+sizeFunction();
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        mouseY-=updatablePosition.y;
        if (mouseButton == 1 && Utils.pointInMovedDim(new Point(mouseX-2, mouseY), new Point(0, 0), new Dimension(boxX,
                (optionOpened && !module.sets().isEmpty() ? calcSize()-VERTICAL_GAP : boxY)))) optionOpened=!optionOpened;
        this.sch.onClick(mouseX, mouseY, mouseButton);
    }
}