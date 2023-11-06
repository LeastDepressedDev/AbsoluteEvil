package me.qigan.abse.config.alg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class AlignMidRelative extends AlignType{

    public AlignMidRelative(int hx, int hy, int maxStyle) {
        super(hx, hy, maxStyle);
    }

    @Override
    public Point gav(int ux, int uy) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        return new Point(scale.getScaledWidth()/2+ux, scale.getScaledHeight()/2+uy);
    }

    @Override
    public Type type() {
        return Type.MID_RELATIVE;
    }
}
