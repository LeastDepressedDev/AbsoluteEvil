package me.qigan.abse.gui.inst.elem;

import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class WidgetText extends WidgetUpdatable {

    private final String line;
    private int col = 0xFFFFFF;
    private S2Dtype rType = S2Dtype.DEFAULT;

    private FontRenderer fntj = Minecraft.getMinecraft().fontRendererObj;

    public WidgetText(String line, int x, int y) {
        super(x, y);
        this.line = line;
    }

    public WidgetText recolor(int col) {
        this.col = col;
        return this;
    }

    public WidgetText recolor(Color col) {
        return recolor(col.getRGB());
    }

    public WidgetText retype(S2Dtype type) {
        this.rType = type;
        return this;
    }

    public WidgetText font(FontRenderer fntj) {
        this.fntj = fntj;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(cordX, cordY, 0f);
        GlStateManager.scale(scaleFactorW, scaleFactorH, 0d);
        Esp.drawOverlayString(fntj, line, 0, 0, col, rType);
        GlStateManager.popMatrix();
    }
}
