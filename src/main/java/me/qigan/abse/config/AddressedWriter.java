package me.qigan.abse.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//WIP
public class AddressedWriter {

    private final File file;

    public static final char LINER = '@';

    public AddressedWriter(String pathname) {
        File fl = new File(pathname);
        if (!fl.exists()) {
            try {
                fl.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.file=fl;
    }

    public AddressedWriter(File file) {
        this.file = file;
    }

    public void write(AddressedData<String, String> data) {
        List<AddressedData<String, String>> nwk = this.get();
        nwk.add(data);
        this.reset(nwk);
    }

    public void reset(List<AddressedData<String, String>> data) {
        try {
            PrintStream out = new PrintStream(file);
            if (file.canWrite()) {
                for (AddressedData<String, String> line : data) {
                    out.println(line.getNamespace()+LINER+line.getObject());
                }
            } else {
                this.file.setWritable(true);
                reset(data);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void set(String namespace, String value) {
    	AddressedData<String, String> val = new AddressedData<String, String>(namespace, value);
    	try {
    		if (this.contains(namespace)) {
	    		List<AddressedData<String, String>> data = this.get();
	    		int i = 0;
	    		for (AddressedData<String, String> pval: data) {
	    			if (val.getNamespace().equalsIgnoreCase(pval.getNamespace())) {
	    				data.set(i, val);
	    				break;
	    			}
	    			i++;
	    		}
	    		reset(data);
    		} else {
    			this.write(val);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public boolean contains(String namespace) {
    	for (AddressedData<String, String> val: this.get()) {
    		if (val.getNamespace().equalsIgnoreCase(namespace)) return true;
    	}
    	return false;
    }

    public boolean remove(String namespace) {
        List<AddressedData<String, String>> bef = this.get();
        List<AddressedData<String, String>> result = new ArrayList<AddressedData<String, String>>();
        for (AddressedData<String, String> data: bef) {
            if (!data.getNamespace().equals(namespace)) {
                result.add(data);
            }
        }
        this.reset(result);
        return false;
    }

    public List<AddressedData<String, String>> get() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            List<AddressedData<String, String>> data = new ArrayList<AddressedData<String, String>>();
            for (Object b : in.lines().toArray()) {
            	String a = (String) b;
                String[] strl = a.split("@");
                data.add(new AddressedData<String, String>(strl[0], strl.length > 1 ? strl[1] : ""));
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getFile() {
        return file;
    }
}