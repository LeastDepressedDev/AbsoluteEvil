package me.qigan.abse.vp;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

public class VisualApi {

    public static Color randomRGB() {
        Random rnd = new Random();
        return new Color(Math.abs(rnd.nextInt()%256), Math.abs(rnd.nextInt()%256), Math.abs(rnd.nextInt()%256), Math.abs(rnd.nextInt()%256));
    }

    public static Color hslToRgb(float h, float s, float l){
        float r, g, b;

        if (s == 0f) {
            r = g = b = l; // achromatic
        } else {
            float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
            float p = 2 * l - q;
            r = hueToRgb(p, q, h + 1f/3f);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1f/3f);
        }
        return new Color(to255(r), to255(g), to255(b), 255);
    }

    public static int to255(float v) { return (int)Math.min(255,256*v); }

    public static float hueToRgb(float p, float q, float t) {
        if (t < 0f)
            t += 1f;
        if (t > 1f)
            t -= 1f;
        if (t < 1f/6f)
            return p + (q - p) * 6f * t;
        if (t < 1f/2f)
            return q;
        if (t < 2f/3f)
            return p + (q - p) * (2f/3f - t) * 6f;
        return p;
    }


    public static void prepareGLL() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
    }

    public static void endGLL() {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void setupLine(float width, Color color) {
        GL11.glLineWidth(width);
        GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), (float) color.getAlpha()/255f);
    }
}
