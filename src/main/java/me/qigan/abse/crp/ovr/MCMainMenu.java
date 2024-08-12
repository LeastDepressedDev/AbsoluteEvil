package me.qigan.abse.crp.ovr;

import me.qigan.abse.ant.LoginScreen;
import me.qigan.abse.crp.MainWrapper;
import me.qigan.abse.gui.QGuiScreen;
import me.qigan.abse.gui.inst.LoginMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.swing.*;

public class MCMainMenu {
    public static final int LEGACY_LOGIN = 565656;
    public static final int NEW_LOGIN = 771526;

    @SubscribeEvent
    void gui(GuiScreenEvent.InitGuiEvent e) {
        if (e.gui instanceof GuiMainMenu) {
            e.buttonList.add(new GuiButton(LEGACY_LOGIN, 0, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight()-20, 100, 20, "Legacy relog"));
            e.buttonList.add(new GuiButton(NEW_LOGIN, 0, 0, 100, 20, "Relog"));
        }
    }

    @SubscribeEvent
    void act(GuiScreenEvent.ActionPerformedEvent e) {
        if (e.gui instanceof GuiMainMenu) {
            if (e.button.id == LEGACY_LOGIN && (MainWrapper.ls == null || !MainWrapper.ls.isVisible())) {
                MainWrapper.ls = new LoginScreen();
                MainWrapper.ls.setVisible(true);
            } else if (e.button.id == NEW_LOGIN) {
                Minecraft.getMinecraft().addScheduledTask(() ->
                        Minecraft.getMinecraft().displayGuiScreen(new LoginMenu(null)));
            }
        }
    }
}
