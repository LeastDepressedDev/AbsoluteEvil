package me.qigan.abse.config;

import java.util.HashMap;
import java.util.Map;

import me.qigan.abse.Holder;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.Debug;
import net.minecraftforge.fml.common.Loader;

public class MuConfig {
	private Map<String, String> sets = new HashMap<String, String>();
	
	public final AddressedWriter writer;

	public MuConfig() {
		
		//init writer class
		this.writer = new AddressedWriter(Loader.instance().getConfigDir() + "/abse.cfg");

		//clr_mem
		sets.clear();
		//values
		
		//writer sets deafult
		for (Module mdl: Holder.MRL) {
			if(!writer.contains(mdl.id()) || Debug.DISABLE_STATE.contains(mdl.id())) {
				this.writer.set(mdl.id(), "false");
			}
			for (SetsData<String> dat : mdl.sets()) {
				if(!writer.contains(dat.setId)) {
					this.writer.set(dat.setId, dat.defVal);
				}
			}
		}
		
		//cfg defining
		for(AddressedData<String, String> w: writer.get()) {
			sets.put(w.getNamespace(), w.getObject());
		}
	}
	
	/*public List<AddressedData<String, Boolean>> getAll() {
		List<AddressedData<String, Boolean>> result = new ArrayList<AddressedData<String, Boolean>>();
		for(Entry<String, Boolean> val: sets.entrySet()) {
			result.add(new AddressedData<String, Boolean>(val.getKey(), val.getValue()));
		}
		return result;
	}*/
	
	public Map<String, String> getAll() {
		return sets;
	}
	
	public void set(String namespace, String value) {
		this.sets.put(namespace, value);
		this.writer.set(namespace, value);
	}
	
	public String getStrVal(String namespace) {
		return sets.get(namespace);
	}

	public int getIntVal(String namespace) {
		return Integer.parseInt(getStrVal(namespace));
	}

	public double getDouble(String namespace) {
		return Double.parseDouble(getStrVal(namespace));
	}

	public boolean has(String namespace) {
		return sets.containsKey(namespace);
	}
	
	public boolean getBoolVal(String namespace) {
		return sets.get(namespace).equalsIgnoreCase("true");
	}
	
	public void toggle(String namespace, boolean modular) {
		if (sets.get(namespace).equalsIgnoreCase("true")) {
			this.set(namespace, "false");
			if (modular) Holder.quickFind(namespace).onDisable();
		} else {
			this.set(namespace, "true");
			if (modular) Holder.quickFind(namespace).onEnable();
		}
	}
}
