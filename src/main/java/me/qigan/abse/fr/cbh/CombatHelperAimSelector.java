package me.qigan.abse.fr.cbh;

import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;

import java.util.ArrayList;
import java.util.List;

public class CombatHelperAimSelector extends Module {

    @Override
    public String id() {
        return "cbh_aim_sel";
    }

    @Override
    public String fname() {
        return "Aim selector module";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("cbh_aim_sel_team", "Trigger team", ValType.BOOLEAN, "true"));
        //list.add(new SetsData<>("cbh_aim_downs", "Down scale", ValType.BOOLEAN, "2"));
        return list;
    }

    @Override
    public String description() {
        return "Selects what enemies can be targeted";
    }
}
