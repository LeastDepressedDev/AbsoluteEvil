package me.qigan.abse.gui.inst;

import me.qigan.abse.Index;
import me.qigan.abse.crp.Module;
import me.qigan.abse.gui.QGuiScreen;
import me.qigan.abse.gui.inst.elem.*;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewMainMenu extends QGuiScreen {

    public static FontRenderer fntj = Minecraft.getMinecraft().fontRendererObj;

    public static Color BG_COL_1 = new Color(32, 106, 125);
    public static Color LINES_COL = new Color(41, 73, 84);
    public static Color LL_COL = new Color(12, 54, 55);
    public static Color SEMI_BG_COL_1 = new Color(2, 34, 35);
    public static Color BG_COL_2 = new Color(4, 17, 20);

    public List<WidgetElement> elements = new ArrayList<>();

    public static double scaleFactorW = 1;
    public static double scaleFactorH = 1;

    private Point clickDef = null;
    private Point matrixSavePrev = null;
    private boolean translationTrigger = false;

    private static Dimension PREV_DIM;

    public static Point MATRIX_BEGIN = null;
    public static Dimension MATRIX_SIZES = new Dimension(600, 400);

    public static boolean queue = false;

    public static Module.Specification selectedCategory = Module.Specification.COMBAT;
    public static int viewMode = 0;

    public NewMainMenu(QGuiScreen screen) {
        super(screen);
    }

    public NewMainMenu(GuiScreen guiScreen) {
        super(guiScreen);
    }

    @Override
    public void initGui() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        scaleFactorW = (double) res.getScaledWidth() / 960;
        scaleFactorH = (double) res.getScaledHeight() / 600;
        scale(res);

        elements.add(new WidgetText("ABSE", 5, 6).scale(6, 6.5));
        elements.add(new WidgetText("Absolute Evil v" + Index.VERSION, 1,
                (int) (MATRIX_SIZES.height-((float)MATRIX_SIZES.height/50))-8).recolor(Color.darkGray));

        elements.add(new WidgetButton((int) (5*MATRIX_SIZES.width/6f)+2, (int) (MATRIX_SIZES.height-((float)MATRIX_SIZES.height/50))-20,
                (int) (MATRIX_SIZES.width/6d-11), 20, Index::absoluteFix).textScale(1.2).text("Absolute fix"));
        elements.add(new WidgetButton((int) (MATRIX_SIZES.width-((float) MATRIX_SIZES.width/70))-50, 10, 40, 40, () -> Minecraft.getMinecraft().displayGuiScreen(new PositionsGui(this)))
                .textScale(4d).text("\u233A"));
        elements.add(new WidgetCategoryRenderer());
        elements.add(new WidgetUpperBar());

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

    private void drawGuiShape() {
        int l_bound_w = (int) (MATRIX_SIZES.width-((float) MATRIX_SIZES.width/70));
        int l_bound_h = (int) (MATRIX_SIZES.height-((float)MATRIX_SIZES.height/50));
        //Main shapes
        Gui.drawRect(0, 0, l_bound_w, l_bound_h, BG_COL_2.getRGB());
        Gui.drawRect(0, 0, (int) (MATRIX_SIZES.width/4f), l_bound_h, SEMI_BG_COL_1.getRGB());
        Gui.drawRect(0, 0, (int) (MATRIX_SIZES.width/4f), (MATRIX_SIZES.height/7), LL_COL.getRGB());

        //Lines
        Gui.drawRect((int) (MATRIX_SIZES.width/4f)-2, 0, (int) (MATRIX_SIZES.width/4f), l_bound_h, LINES_COL.getRGB());
        Gui.drawRect(0, (MATRIX_SIZES.height/7), l_bound_w, (MATRIX_SIZES.height/7)+2, LINES_COL.getRGB());
        //    Config line!!!
        Gui.drawRect((int) (5*MATRIX_SIZES.width/6f), (MATRIX_SIZES.height/7), (int) (5*MATRIX_SIZES.width/6f)+2, l_bound_h, LINES_COL.getRGB());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(MATRIX_BEGIN.x, MATRIX_BEGIN.y, 0f);
        GlStateManager.scale(scaleFactorW, scaleFactorH, 0d);
        //Corner
        Gui.drawRect(0, 0, MATRIX_SIZES.width, MATRIX_SIZES.height, BG_COL_1.getRGB());
            GlStateManager.pushMatrix();
        GlStateManager.translate(((float) MATRIX_SIZES.width/140), ((float) MATRIX_SIZES.height/70), 0f);

        drawGuiShape();

        Point innerCords = Utils.scaleDim(new Point(mouseX-MATRIX_BEGIN.x, mouseY-MATRIX_BEGIN.y), 1/scaleFactorW, 1/scaleFactorH);
        innerCords.x-=3;
        innerCords.y-=6;
        for (WidgetElement elem : elements) {
            if (elem instanceof WidgetUpdatable) {
                ((WidgetUpdatable) elem).draw(innerCords.x, innerCords.y, partialTicks);
            }
        }
            GlStateManager.popMatrix();


        GlStateManager.popMatrix();


        Esp.drawOverlayString(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() + "||" + new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(),
                10, 10, 0xFFFFFF, S2Dtype.CORNERED);
        Esp.drawOverlayString(innerCords.x + "||" + innerCords.y,
                10, 25, 0xFFFFFF, S2Dtype.CORNERED);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        clickDef = new Point(mouseX, mouseY);
        Point innerCords = Utils.scaleDim(new Point(mouseX-MATRIX_BEGIN.x, mouseY-MATRIX_BEGIN.y), 1/scaleFactorW, 1/scaleFactorH);
        innerCords.x-=3;
        innerCords.y-=6;
        matrixSavePrev = new Point(MATRIX_BEGIN);
        translationTrigger = Utils.pointInMovedDim(clickDef, MATRIX_BEGIN, Utils.scaleDim(MATRIX_SIZES, scaleFactorW, scaleFactorH))
                &&
                !Utils.pointInMovedDim(clickDef,
                new Point((int) ((MATRIX_BEGIN.x+(float) MATRIX_SIZES.width/40)), (int) ((MATRIX_BEGIN.y+(float) MATRIX_SIZES.height/40))),
                Utils.scaleDim(new Dimension((int) ((MATRIX_SIZES.width-2*((float) MATRIX_SIZES.width/40*scaleFactorW))),
                        (int) ((MATRIX_SIZES.height-2*((float) MATRIX_SIZES.height/40*scaleFactorH)))), scaleFactorW, scaleFactorH)
                );
        for (WidgetElement elem : elements) {
            if (elem instanceof WidgetUpdatable) {
                ((WidgetUpdatable) elem).onClick(innerCords.x, innerCords.y, mouseButton);
            }
        }
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

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (WidgetElement elem : elements) {
            if (elem instanceof WidgetUpdatable) {
                ((WidgetUpdatable) elem).keyTyped(typedChar, keyCode);
            }
        }
        super.keyTyped(typedChar, keyCode);
    }
}
