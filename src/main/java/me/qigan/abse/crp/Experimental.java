package me.qigan.abse.crp;

import me.qigan.abse.fr.exc.SmoothAimControl;

@AutoDisable
public class Experimental extends Module implements EDLogic {

    @Override
    public String id() {
        return "exptl";
    }

    @Override
    public String fname() {
        char[] str = "Experimental".toCharArray();
        String nstr = "";
        for (int i = 0; i < str.length; i++) {
            nstr += (i % 2 == 0) ? ("\u00A7e" + str[i]) : ("\u00A77" + str[i]);
        }
        return nstr;
    }

    @Override
    public String description() {
        return "Being used for testing some crazy stuff";
    }

    @Override
    public void onEnable() {
        SmoothAimControl.OVERRIDE = true;
    }

    @Override
    public void onDisable() {
        SmoothAimControl.OVERRIDE = false;
    }
}
