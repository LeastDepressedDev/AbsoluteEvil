package me.qigan.abse.gui.inst;

import me.qigan.abse.Index;
import me.qigan.abse.config.Loc2d;
import me.qigan.abse.gui.QGuiScreen;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import me.qigan.abse.vp.VisualApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class PositionsGui extends QGuiScreen {

    public static final List<String> INSTRUCTION = new ArrayList<>(Arrays.asList(
            "How to use: ",
            "Hover your mouse over colored area(if this area is uncomfortable",
            "for you, you can change it in config/abse.pos by changing 3rd and 4th value)",
            "[Values is numbers or text splited with `;`]",
            "Scroll-Up: move up",
            "Scroll-Down: move down",
            "Shift-Scroll-Up: move right",
            "Shift-Scroll-Down: move left",
            "Right-Click: change style(increasing by 1[resets on max])"
    ));

    public final Map<String, Color> colorMap = new HashMap<>();

    public PositionsGui(QGuiScreen screen) {
        super(screen);
    }

    public static boolean opened = false;

    private int mx = -1;
    private int my = -1;

    private static Map.Entry<String, Loc2d> find(int x, int y, ScaledResolution res) {
        for (Map.Entry<String, Loc2d> lond : Index.POS_CFG.poses.entrySet()) {
            Loc2d ref = lond.getValue();
            Point cord = ref.get();
            Dimension hbxy = ref.aligner.hitSelectorSize();

            int rectX = (cord.x < res.getScaledWidth()/2) ? cord.x : cord.x - hbxy.width;
            int rectY = (cord.y < res.getScaledHeight()/2) ? cord.y : cord.y - hbxy.height;
            if (x >= rectX && x <= rectX + hbxy.width && y >= rectY && y <= rectY + hbxy.height) return lond;
        }
        return null;
    }

    @Override
    public void initGui() {
        opened = true;

        for (Map.Entry<String, Loc2d> lond : Index.POS_CFG.poses.entrySet()) {
            colorMap.put(lond.getKey(), new Color(255, 255, 255, 50));
        }

        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        opened = false;
        Index.POS_CFG.update();
        super.onGuiClosed();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mx = mouseX;
        my = mouseY;

        super.drawDefaultBackground();

        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        double height = res.getScaledHeight();
        double width = res.getScaledWidth();

        drawString(Minecraft.getMinecraft().fontRendererObj, "\u00A7l\u00A7aLocation Picker", (int) width/2-25, 10, 0xFFFFFF);

        for (Map.Entry<String, Loc2d> lond : Index.POS_CFG.poses.entrySet()) {
            Loc2d ref = lond.getValue();
            Point cord = ref.get();
            Dimension hbxy = ref.aligner.hitSelectorSize();

            int rectX = (cord.x < width/2) ? cord.x : cord.x - hbxy.width;
            int rectY = (cord.y < height/2) ? cord.y : cord.y - hbxy.height;

            //Creative solution XD
            Esp.drawModalRectWithCustomSizedTexture(rectX, rectY, hbxy.width, hbxy.height,
                    0, 0, 2, 2, new ResourceLocation("abse", "filler.png"), colorMap.get(lond.getKey()));

            Esp.drawCenteredString(lond.getKey(), rectX + (hbxy.width/2), rectY + (hbxy.height/2) - 5, 0x00FF00, S2Dtype.CORNERED);
            int style = ref.aligner.style();
            Esp.drawCenteredString("style: " + style, rectX + (hbxy.width/2), rectY + (hbxy.height/2) + 2, style == ref.aligner.maxStyle() ? 0xFF0000 : 0x00FF00, S2Dtype.CORNERED);
            if (
                    mouseX >= rectX && mouseX <= rectX + hbxy.width
                            &&
                    mouseY >= rectY && mouseY <= rectY + hbxy.height
            ) {
                String toRender = "x: " + cord.x + ", y: " + cord.y + ", type: " + ref.aligner.type();
                int ln = fontRendererObj.getStringWidth(toRender);
                drawString(fontRendererObj,
                        toRender,
                        (int) Math.min(mouseX, width-ln), (int) Math.max(mouseY-10, 0), 0x00AAFF);
            }
        }

        Esp.drawAllignedTextList(INSTRUCTION, 0, 0, false, res);

        if (Index.MAIN_CFG.getBoolVal("debug")) {
            String toRender = "\u00A7a" + mouseX + " " + mouseY + "\u00A75 | \u00A7c" + Mouse.getX() + " " + Mouse.getY();
            int ln = fontRendererObj.getStringWidth(toRender);
            drawString(fontRendererObj,
                    toRender,
                    (int) Math.min(mouseX, width-ln), (int) Math.max(mouseY+10, 0), 0xFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        if (mouseButton == 1) {
            Map.Entry<String, Loc2d> loc = find(mx, my, res);
            if (loc != null) {
                Loc2d ref = loc.getValue();
                ref.aligner.incStyle();
                Index.POS_CFG.poses.put(loc.getKey(), ref);
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        int dwheel = Mouse.getEventDWheel();
        if (dwheel != 0) {
            if (dwheel > 0) {
                Map.Entry<String, Loc2d> loc = find(mx, my, res);
                if (loc != null) {
                    Loc2d ref = loc.getValue();
                    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                        ref.ux++;
                    } else {
                        ref.uy--;
                    }
                    Index.POS_CFG.poses.put(loc.getKey(), ref);
                }
            } else {
                Map.Entry<String, Loc2d> loc = find(mx, my, res);
                if (loc != null) {
                    Loc2d ref = loc.getValue();
                    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                        ref.ux--;
                    } else {
                        ref.uy++;
                    }
                    Index.POS_CFG.poses.put(loc.getKey(), ref);
                }
            }
        }
    }
}
