package me.qigan.abse.config.alg;

import me.qigan.abse.config.alg.spec.AlignBWA;

import java.awt.*;

public abstract class AlignType {
    public static enum Type {
        ABSOLUTE("abs"),
        RELATIVE("rel"),
        MID_RELATIVE("mid_rel"),
        RELATIVE_PERCENT("rel_pc"),
        BEDWARS_ADDONS("bwa")
        ;

        public final String c;

        Type(String symb) {
            this.c = symb;
        }

        public static AlignType match(String cl, String[] segs) {
            switch (cl) {
                case "rel":
                    return new AlignRelative(Integer.parseInt(segs[2]), Integer.parseInt(segs[3]), Integer.parseInt(segs[5]), Integer.parseInt(segs[7]), Integer.parseInt(segs[8])).setStyle(Integer.parseInt(segs[6]));
                case "mid_rel":
                    return new AlignMidRelative(Integer.parseInt(segs[2]), Integer.parseInt(segs[3]), Integer.parseInt(segs[5])).setStyle(Integer.parseInt(segs[6]));
                case "rel_pc":
                    return new AlignRelativePercent(Integer.parseInt(segs[2]), Integer.parseInt(segs[3]), Integer.parseInt(segs[5])).setStyle(Integer.parseInt(segs[6]));
                case "bwa":
                    return new AlignBWA();
                case "abs":
                default:
                    return new AlignAbsolute(Integer.parseInt(segs[2]), Integer.parseInt(segs[3]), Integer.parseInt(segs[5])).setStyle(Integer.parseInt(segs[6]));
            }
        }
    }

    public AlignType(int hx, int hy, int maxStyle) {
        this.maxStyle = maxStyle;
        this.hbx = hx;
        this.hby = hy;
    }

    protected int style = 0;
    protected int maxStyle;

    protected final int hbx;
    protected final int hby;

    abstract public Point gav(int ux, int uy);
    abstract public Type type();
    public boolean incStyle() {
        if (style+1>maxStyle()) {
            style = 0;
            return true;
        } else {
            style++;
            return false;
        }
    }
    public AlignType setStyle(int style) {
        this.style = style;
        return this;
    }
    public final int style() {
        return style;
    }
    public final int maxStyle() {
        return maxStyle;
    }
    public Dimension hitSelectorSize() {
        return new Dimension(this.hbx, this.hby);
    }
}
