package me.qigan.abse.crp;

import java.util.ArrayList;
import java.util.List;

import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;

public abstract class Module {
	
	public abstract String id();
	public String fname() {
		return this.id();
	}
	public abstract String description();
	public boolean isEnabled() {
		return Index.MAIN_CFG.getBoolVal(id());
	}
	public List<SetsData<String>> sets() {
		return new ArrayList<>();
	}

	public void onEnable() {};
	public void onDisable() {};
}
