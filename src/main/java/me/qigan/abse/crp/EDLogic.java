package me.qigan.abse.crp;

import me.qigan.abse.Holder;

import java.util.HashMap;
import java.util.Map;

public interface EDLogic {

    public static Map<String, Runnable> eLogic = new HashMap<>();
    public static Map<String, Runnable> dLogic = new HashMap<>();

    public static void tryEnableLogic(String id) {
        Module mod = Holder.quickFind(id);
        if (mod instanceof EDLogic) ((EDLogic) mod).onEnable();
        else if (EDLogic.eLogic.containsKey(id)) EDLogic.eLogic.get(id).run();
    }

    public static void tryDisableLogic(String id) {
        Module mod = Holder.quickFind(id);
        if (mod instanceof EDLogic) ((EDLogic) mod).onDisable();
        else if (EDLogic.dLogic.containsKey(id)) EDLogic.dLogic.get(id).run();
    }

    void onEnable();
    void onDisable();
}
