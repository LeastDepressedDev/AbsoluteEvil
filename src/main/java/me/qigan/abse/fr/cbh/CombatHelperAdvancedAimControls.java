package me.qigan.abse.fr.cbh;

import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class CombatHelperAdvancedAimControls extends Module {
    @Override
    public String id() {
        return "cbh_aim_adv";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("cbh_aim_px", "Yaw limiter", ValType.DOUBLE_NUMBER, "0.5"));
        list.add(new SetsData<>("cbh_aim_py", "Pitch limiter", ValType.DOUBLE_NUMBER, "1.4"));
        list.add(new SetsData<>("cbh_aim_distbp", "Distance break point", ValType.DOUBLE_NUMBER, "1"));
        list.add(new SetsData<>("cbh_aim_tbkm", "Toggle break key", ValType.BOOLEAN, "false"));
        return list;
    }

    @Override
    public String fname() {
        return "Advanced aim controls";
    }

    @Override
    public String description() {
        return "Advanced parameters for aim";
    }
}
