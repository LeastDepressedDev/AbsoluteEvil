package me.qigan.abse.fr.other;

import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.gui.overlay.ImportantChatOVR;

import java.util.ArrayList;
import java.util.List;

public class Experimental extends Module {

    public static int ij = 0;

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
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("exp_button_1", "Test", ValType.BUTTON, (Runnable) () -> {
            ImportantChatOVR.add("Test " + ij);
            ij++;
        }));
        return list;
    }

    @Override
    public String description() {
        return "Being used for testing some crazy stuff";
    }
}
