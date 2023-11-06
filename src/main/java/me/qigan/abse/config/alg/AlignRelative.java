package me.qigan.abse.config.alg;

import java.awt.*;

public class AlignRelative extends AlignType{

    public final int relativeX;
    public final int relativeY;

    public AlignRelative(int hx, int hy, int maxStyle, int relativeX, int relativeY) {
        super(hx, hy, maxStyle);
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    @Override
    public Point gav(int ux, int uy) {
        return new Point(relativeX+ux, relativeY+uy);
    }

    @Override
    public Type type() {
        return Type.RELATIVE;
    }
}
