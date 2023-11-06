package me.qigan.abse.config.alg.spec;

import me.qigan.abse.config.alg.AlignRelativePercent;

import java.awt.*;

public class AlignBWA extends AlignRelativePercent {
    public AlignBWA() {
        super(150, 20, 1);
    }

    @Override
    public Type type() {
        return Type.BEDWARS_ADDONS;
    }

    @Override
    public Dimension hitSelectorSize() {
        if (this.style() == 0) {
            return super.hitSelectorSize();
        } else {
            return new Dimension(hby, hbx);
        }
    }
}
