package me.qigan.abse.config;

import me.qigan.abse.Index;
import net.minecraftforge.fml.common.Loader;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ConfigManager {
    public final String innerPath;

    public ConfigManager(String path) {
        this.innerPath = Loader.instance().getConfigDir() + "/" + path;
    }

    public File[] getConfigFiles() {
        File file = new File(innerPath);
        if (file.exists() && file.isDirectory()) {
            return file.listFiles((dir, name) -> name.endsWith(".cfg"));
        } else return null;
    }

    public void reloadMain() {
        Index.MAIN_CFG = new MuConfig();
    }

    public void fromFileToFile(File from, File to) {
        try {
            PrintStream out = new PrintStream(to);
            if (to.canWrite()) {
                String lines = new Scanner(from).useDelimiter("\\Z").next();
                out.println(lines);
            } else {
                to.setWritable(true);
                fromFileToFile(from, to);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFrom(String name) {
        File file = new File(innerPath + "/" + name + ".cfg");
        fromFileToFile(file, new File(Loader.instance().getConfigDir() + "/abse.cfg"));
        reloadMain();
    }

    public void saveTo(String name) {
        File file = new File(innerPath + "/" + name + ".cfg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        fromFileToFile(Index.MAIN_CFG.writer.getFile(), file);
    }
}
