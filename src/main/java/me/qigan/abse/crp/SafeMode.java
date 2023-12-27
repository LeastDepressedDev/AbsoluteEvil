package me.qigan.abse.crp;

@EnabledByDefault
public class SafeMode extends Module{
    @Override
    public String id() {
        return "safe_mod";
    }

    @Override
    public String description() {
        return "If enabled - preserve you from the most of possible crashes(caused by mode). Lower performance, can cause incorrect work of module";
    }
}
