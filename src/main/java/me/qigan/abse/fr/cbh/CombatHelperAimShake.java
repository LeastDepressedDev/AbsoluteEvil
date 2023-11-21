package me.qigan.abse.fr.cbh;

import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;

import java.util.ArrayList;
import java.util.List;

public class CombatHelperAimShake extends Module {

    @Override
    public String fname() {
        return "Shake";
    }

    @Override
    public String id() {
        return "cbh_shake";
    }

    @Override
    public List<SetsData<String>> sets() {
        List<SetsData<String>> list = new ArrayList<>();
        list.add(new SetsData<>("cbh_shake_amount", "Amount", ValType.DOUBLE_NUMBER, "1.5"));
        return list;
    }

    @Override
    public String description() {
        return "Makes it feels like your hand is shaking";
    }
}
