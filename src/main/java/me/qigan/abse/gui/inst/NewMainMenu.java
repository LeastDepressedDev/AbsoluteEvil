package me.qigan.abse.gui.inst;

import me.qigan.abse.gui.QGuiScreen;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.io.IOException;

public class NewMainMenu extends QGuiScreen {

    public static Color BG_COL_1 = new Color(32, 106, 125);
    public static Color SEMI_BG_COL_1 = new Color(2, 34, 35);
    public static Color BG_COL_2 = new Color(4, 17, 20);

    private Point clickDef = null;
    private Point matrixSavePrev = null;
    private boolean translationTrigger = false;

    private static Dimension PREV_DIM;

    public static Point MATRIX_BEGIN = null;
    public static Dimension MATRIX_SIZES = new Dimension(600, 400);

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
        GlStateManager.translate(MATRIX_BEGIN.x, MATRIX_BEGIN.y, 0f);
        Gui.drawRect(0, 0, MATRIX_SIZES.width, MATRIX_SIZES.height, BG_COL_1.getRGB());
        GlStateManager.translate(((float) MATRIX_SIZES.width/140), ((float) MATRIX_SIZES.height/70), 0f);
        Gui.drawRect(0, 0, (int) (MATRIX_SIZES.width-((float) MATRIX_SIZES.width/70)), (int) (MATRIX_SIZES.height-((float)MATRIX_SIZES.height/50)), BG_COL_2.getRGB());
        Gui.drawRect(0, 0, (int) (MATRIX_SIZES.width/4f), (int) (MATRIX_SIZES.height-((float)MATRIX_SIZES.height/50)), SEMI_BG_COL_1.getRGB());


        //        GL11.glScissor(500, 500, 700, 700);
//        GL11.glEnable(GL11.GL_SCISSOR_TEST);
//        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
//        Esp.drawModalRectWithCustomSizedTexture((float) MATRIX_BEGIN.x, (float) MATRIX_BEGIN.y, 200, 90,
//                0, 0, 200, 90, new ResourceLocation("abse", "filler.png"), new Color(255, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        clickDef = new Point(mouseX, mouseY);
        matrixSavePrev = new Point(MATRIX_BEGIN);
        translationTrigger = Utils.pointInMovedDim(clickDef, MATRIX_BEGIN, MATRIX_SIZES) &&
                !Utils.pointInMovedDim(clickDef,
                new Point((int) (MATRIX_BEGIN.x+(float) MATRIX_SIZES.width/40), (int) (MATRIX_BEGIN.y+(float) MATRIX_SIZES.height/40)),
                new Dimension((int) (MATRIX_SIZES.width-2*((float) MATRIX_SIZES.width/40)),
                        (int) (MATRIX_SIZES.height-2*((float) MATRIX_SIZES.height/40))));
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
        if (translationTrigger) MATRIX_BEGIN = Utils.diff(matrixSavePrev, new Point( clickDef.x - mouseX, clickDef.y - mouseY));
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
}
