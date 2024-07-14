package me.qigan.abse.fr.cbh;

import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.crp.ovr.ExtendedController;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class AbsoluteReach extends Module {

    public static void hookReach() {
        Minecraft.getMinecraft().playerController = new ExtendedController(Minecraft.getMinecraft(), Minecraft.getMinecraft().getNetHandler());
    }

    @Override
    public String id() {
        return "abs_reach";
    }

    @Override
    public String fname() {
        return "Absolute reach";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("abs_reach_hook", "Manual hook", ValType.BUTTON, (Runnable) AbsoluteReach::hookReach));
        list.add(new SetsData<>("abs_reach_range", "Range", ValType.DOUBLE_NUMBER, "5"));
        return list;
    }

    @Override
    public Specification category() {
        return Specification.COMBAT;
    }

    @Override
    public String description() {
        return "Better than reach.";
    }
}
