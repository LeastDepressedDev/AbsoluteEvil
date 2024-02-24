package me.qigan.abse.config;

import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.Map;

public class RestrictedCfg {
    private Map<String, String> sets = new HashMap<String, String>();

    public final AddressedWriter writer;

    public RestrictedCfg() {
        this.writer = new AddressedWriter(Loader.instance().getConfigDir() + "/restricted_servers.cfg");


    }
}
