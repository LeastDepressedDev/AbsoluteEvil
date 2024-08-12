package me.qigan.abse.gui.inst;

import me.qigan.abse.Index;
import me.qigan.abse.gui.QGuiScreen;
import me.qigan.abse.gui.inst.elem.WidgetButton;
import me.qigan.abse.gui.inst.elem.WidgetTextField;
import me.qigan.abse.gui.inst.elem.WidgetUpdatable;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginMenu extends QGuiScreen {

    public static final int CONST_TF_WIDTH = 500;
    public static final int CONST_TF_HEIGHT = 40;

    public static WidgetTextField uname;
    public static WidgetTextField token;
    public static WidgetTextField pid;

    public static double scaleFactorW = 1;
    public static double scaleFactorH = 1;

    public static List<WidgetUpdatable> elems = new ArrayList<>();

    public LoginMenu(QGuiScreen screen) {
        super(screen);
    }

    @Override
    public void initGui() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        scaleFactorW = (double) res.getScaledWidth() / 960;
        scaleFactorH = (double) res.getScaledHeight() / 600;

        int Id = 0;
        String in = "Logginer";
        GuiLabel header = new GuiLabel(fontRendererObj, Id, res.getScaledWidth()/2-(fontRendererObj.getStringWidth(in)/2), res.getScaledHeight()/7*3, fontRendererObj.getStringWidth(in), 20, 0xFFFFFF);
        labelList.add(header);

        uname = new WidgetTextField(res.getScaledWidth()/2-CONST_TF_WIDTH/2, 7*res.getScaledHeight()/25, CONST_TF_WIDTH, CONST_TF_HEIGHT)
                .textScale(1.8f).placeholder("Username");
        token = new WidgetTextField(res.getScaledWidth()/2-CONST_TF_WIDTH/2, 11*res.getScaledHeight()/25, CONST_TF_WIDTH, CONST_TF_HEIGHT)
                .textScale(1.8f).placeholder("Token[req for license]");
        pid = new WidgetTextField(res.getScaledWidth()/2-CONST_TF_WIDTH/2, 15*res.getScaledHeight()/25, CONST_TF_WIDTH, CONST_TF_HEIGHT)
                .textScale(1.8f).placeholder("Player id[preferable to have]");

        elems.add(uname);
        elems.add(token);
        elems.add(pid);
        elems.add(new WidgetButton(res.getScaledWidth()/2-50, 18*res.getScaledHeight()/25, 100, 40,
                () -> Index.relog(uname.innerText, token.innerText, pid.innerText)
        ).textScale(1.3f).text("Login"));

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

        Gui.drawRect(0, 0, res.getScaledWidth(), res.getScaledHeight(), NewMainMenu.BG_COL_2.getRGB());

        for (WidgetUpdatable elem : elems) {
            elem.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (WidgetUpdatable elem : elems) {
            elem.onClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (WidgetUpdatable elem : elems) {
            elem.keyTyped(typedChar, keyCode);
        }
    }

    //    @Override
//    public void drawBackground(int tint) {
//        GlStateManager.disableLighting();
//        GlStateManager.disableFog();
//        Tessellator tessellator = Tessellator.getInstance();
//        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
//        this.mc.getTextureManager().bindTexture(new ResourceLocation());
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        float f = 32.0F;
//        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
//        worldrenderer.pos(0.0D, (double)this.height, 0.0D).tex(0.0D, (double)((float)this.height / 32.0F + (float)tint)).color(64, 64, 64, 255).endVertex();
//        worldrenderer.pos((double)this.width, (double)this.height, 0.0D).tex((double)((float)this.width / 32.0F), (double)((float)this.height / 32.0F + (float)tint)).color(64, 64, 64, 255).endVertex();
//        worldrenderer.pos((double)this.width, 0.0D, 0.0D).tex((double)((float)this.width / 32.0F), (double)tint).color(64, 64, 64, 255).endVertex();
//        worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double)tint).color(64, 64, 64, 255).endVertex();
//        tessellator.draw();
//    }
}
