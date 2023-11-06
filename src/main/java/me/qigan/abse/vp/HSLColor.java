package me.qigan.abse.vp;

import java.awt.*;

public class HSLColor {
    public int h;
    public int s;
    public int l;
    public float a;


    public HSLColor(int hue, int sat, int light, float alpha) {
        this.h = hue;
        this.s = sat;
        this.l = light;
        this.a = alpha;
    }

    public Color toRgb() {
        Color cd = VisualApi.hslToRgb((float)h/100f, (float)s/100f, (float)l/100f);
        return new Color(cd.getRed(), cd.getGreen(), cd.getBlue(), (int) (this.a*255f));
    }
}
