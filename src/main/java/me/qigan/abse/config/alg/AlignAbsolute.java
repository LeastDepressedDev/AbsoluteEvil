package me.qigan.abse.config.alg;

import java.awt.*;

public class AlignAbsolute extends AlignType {
    public AlignAbsolute(int hx, int hy, int maxStyle) {
        super(hx, hy, maxStyle);
    }

    @Override
    public Point gav(int ux, int uy) {
        return new Point(ux, uy);
    }

    @Override
    public Type type() {
        return Type.ABSOLUTE;
    }
}
