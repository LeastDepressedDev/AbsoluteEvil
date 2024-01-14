package me.qigan.abse.gui.inst;

import me.qigan.abse.gui.QGuiScreen;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.io.IOException;

public class NewMainMenu extends QGuiScreen {

    private Point clickDef = null;
    private Point matrixSavePrev = null;
    private boolean translationTrigger = false;

    private static Dimension PREV_DIM;

    public static Point MATRIX_BEGIN = null;
    public static Dimension MATRIX_SIZES = new Dimension(200, 90);

    public static boolean queue = false;

    public NewMainMenu(QGuiScreen screen) {
        super(screen);
    }

    public NewMainMenu(GuiScreen guiScreen) {
        super(guiScreen);
    }

    @Override
    public void initGui() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        scale(res);

        super.initGui();
    }

    /**
     * Function to change gui position relative to current Window size.
     * Works by m̶a̶g̶i̶c̶math
     */
    private void scale(ScaledResolution res) {
        if (MATRIX_BEGIN == null || res.getScaledWidth() != PREV_DIM.width || res.getScaledHeight() != PREV_DIM.height) {
            MATRIX_BEGIN = new Point((int) ((double) res.getScaledWidth()/2-((double) MATRIX_SIZES.width/2)),
                    (int) ((double) res.getScaledHeight()/2-((double) MATRIX_SIZES.height/2)));
            PREV_DIM = new Dimension(res.getScaledWidth(), res.getScaledHeight());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(MATRIX_BEGIN.x+(float) MATRIX_SIZES.width/2, MATRIX_BEGIN.y+(float) MATRIX_SIZES.height/2, 0f);
        Esp.drawModalRectWithCustomSizedTexture(-(float) MATRIX_SIZES.width/2, -(float) MATRIX_SIZES.height/2, 200, 90,
                0, 0, 16, 16, new ResourceLocation("abse", "filler.png"), new Color(255, 255, 255));

        GlStateManager.popMatrix();
//        Esp.drawModalRectWithCustomSizedTexture((float) MATRIX_BEGIN.x, (float) MATRIX_BEGIN.y, 200, 90,
//                0, 0, 200, 90, new ResourceLocation("abse", "filler.png"), new Color(255, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        clickDef = new Point(mouseX, mouseY);
        matrixSavePrev = new Point(MATRIX_BEGIN);
        translationTrigger = Utils.pointInMovedDim(clickDef, MATRIX_BEGIN, MATRIX_SIZES);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        translationTrigger = false;
        clickDef = null;
        matrixSavePrev = null;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (translationTrigger) MATRIX_BEGIN = Utils.diff(matrixSavePrev, new Point(mouseX - clickDef.x, mouseY - clickDef.y));
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
}
