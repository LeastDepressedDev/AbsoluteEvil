package me.qigan.abse.fr.other;

import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;

import java.util.ArrayList;
import java.util.List;

public class Experimental extends Module {

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
    public List<SetsData<String>> sets() {
        List<SetsData<String>> list = new ArrayList<>();
        list.add(new SetsData<>("exptl_1", "Dist", ValType.NUMBER, "562"));
        return list;
    }

    @Override
    public String description() {
        return "Being used for testing some crazy stuff";
    }
}
