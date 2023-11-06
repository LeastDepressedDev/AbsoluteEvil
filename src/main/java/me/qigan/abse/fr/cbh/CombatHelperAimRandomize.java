package me.qigan.abse.fr.cbh;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatHelperAimRandomize extends Module {

    public static double createRandomDouble() {
        Random rand = new Random();
        return rand.nextBoolean() ? rand.nextInt()%Index.MAIN_CFG.getDouble("cbh_aim_ups") :
                rand.nextInt()%Index.MAIN_CFG.getDouble("cbh_aim_downs");
    }

    @Override
    public String id() {
        return "cbh_aim_rand";
    }

    @Override
    public List<SetsData<String>> sets() {
        List<SetsData<String>> list = new ArrayList<>();
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
