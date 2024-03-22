package me.qigan.abse.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * new GuiLabel(fontRendererObj, id, x, y, width, height, color)
 */

public class QGuiScreen extends GuiScreen {

//    public static class Constants {
//        public static final ResourceLocation DEFAULT_TEXTFIELD_TEXTURE = new ResourceLocation("abse", "gui/text.png");;
//    }

    public static final long MS_WAIT_OPEN = 100;

    private final QGuiScreen prev;

    public List<HoveringTextBox> textBoxList = new ArrayList<>();
    public List<GuiTextField> textFieldList = new ArrayList<>();
    public List<TooltipBox> tooltipBoxList = new ArrayList<>();

    public QGuiScreen(QGuiScreen screen) {
        this.prev = screen;
    }

    public QGuiScreen(GuiScreen guiScreen) {
        this.prev = null;
    }

    @Override
    public void initGui() {
        super.initGui();
        for (GuiTextField field : textFieldList) {
            field.setVisible(true);
            field.setEnabled(true);
        }
    }

    @Override
    public void onGuiClosed() {
        //POV: The dumbest solution ever
        if (prev != null) {
            new Thread(() -> {
                try {
                    Thread.sleep(MS_WAIT_OPEN);
                    Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(prev));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (HoveringTextBox htb : textBoxList) {
            if (htb.shouldRender(mouseX, mouseY)) drawHoveringText(htb.lines, mouseX, mouseY, Minecraft.getMinecraft().fontRendererObj);
        }
        for (TooltipBox ttb : tooltipBoxList) {
            if (ttb.shouldRender(mouseX, mouseY)) renderToolTip(ttb.stack, mouseX, mouseY);
        }
        for (GuiTextField field: textFieldList) {
            field.drawTextBox();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        for (GuiTextField field: textFieldList) {
            if (field.isFocused()) {
                field.textboxKeyTyped(typedChar, keyCode);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for (GuiTextField field : textFieldList) {
            field.setFocused((mouseX > field.xPosition && mouseX < field.xPosition + field.width) && (mouseY > field.yPosition && mouseY < field.yPosition + field.height));
        }
    }
}
