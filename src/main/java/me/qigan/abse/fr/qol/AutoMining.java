package me.qigan.abse.fr.qol;

import me.qigan.abse.crp.DangerousModule;
import me.qigan.abse.crp.Module;

@DangerousModule
public class AutoMining extends Module {
    @Override
    public String id() {
        return "auto_m";
    }

    @Override
    public String fname() {
        return "Auto mining";
    }

    @Override
    public Specification category() {
        return Specification.SB_QOL;
    }

    @Override
    public String description() {
        return "Solution for your profile";
    }
}
