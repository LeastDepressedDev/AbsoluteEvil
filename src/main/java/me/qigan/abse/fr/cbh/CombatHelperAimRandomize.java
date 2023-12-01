package me.qigan.abse.fr.cbh;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.sync.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatHelperAimRandomize extends Module {

    public static double createRandomDouble() {
        return Utils.createRandomDouble(Index.MAIN_CFG.getDoubleVal("cbh_aim_ups"), Index.MAIN_CFG.getDoubleVal("cbh_aim_downs"));
    }

    @Override
    public String id() {
        return "cbh_aim_rand";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("cbh_aim_ups", "Up scale", ValType.DOUBLE_NUMBER, "2"));
        list.add(new SetsData<>("cbh_aim_downs", "Down scale", ValType.DOUBLE_NUMBER, "2"));
        return list;
    }

    @Override
    public String fname() {
        return "Aim randomizer";
    }

    @Override
    public String description() {
        return "Randomizer settings for aim";
    }
}
