package me.qigan.abse.config;

import me.qigan.abse.config.alg.AlignType;

import java.awt.*;

public class Loc2d {
    public int ux;
    public int uy;
    public final AlignType aligner;

    public Loc2d(int x, int y, AlignType aligner) {
        this.ux = x;
        this.uy = y;
        this.aligner = aligner;
    }

    public Point get() {
        return aligner.gav(ux, uy);
    }
}
