package me.qigan.abse.config.alg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class AlignRelativePercent extends AlignType{
    public AlignRelativePercent(int hx, int hy, int maxStyle) {
        super(hx, hy, maxStyle);
    }

    @Override
    public Point gav(int ux, int uy) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        //Deviding by 100 and multiplying by %
        return new Point(
                (int) (((float) scale.getScaledWidth())/100f*Math.min(ux, 100)),
                (int) (((float) scale.getScaledHeight())/100f*Math.min(uy, 100))
        );
    }

    @Override
    public Type type() {
        return Type.RELATIVE_PERCENT;
    }
}
