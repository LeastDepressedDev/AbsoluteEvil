package me.qigan.abse.sync;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SoundUtils {

    public static Map<String, File> soundReg = new HashMap<>();
    public static String URL = "abse/sounds";

    public static int initialise() {

        URL = Loader.instance().getConfigDir() + "/" + URL;

        int sc = 0;
        File file = new File(URL);
        if (!file.exists()) file.mkdirs();

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File mf : files) {
                if (!mf.getName().endsWith(".wav")) continue;
                try {
                    registerSound(mf.getName().substring(0, mf.getName().length()-4), mf);
                    sc++;
                } catch (InvalidSoundFileException e) {
                    System.out.println("Got an invalid sound in register, cancelling. Called by = " + mf.getName());
                }
            }
        }

        return sc;
    }

    public static void registerSound(final String id, final String url) throws InvalidSoundFileException {
        File file = new File(url);
        if (file.exists() && file.isFile() && file.canRead() && file.getName().endsWith(".wav")) soundReg.put(id, new File(url));
        else throw new InvalidSoundFileException();
    }
    public static void registerSound(final String id, final File file) throws InvalidSoundFileException {
        if (file.exists() && file.isFile() && file.canRead() && file.getName().endsWith(".wav")) soundReg.put(id, file);
        else throw new InvalidSoundFileException();
    }

    public static synchronized void playSound(final String id) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundReg.get(id));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }

    /**
     * Checks register for having custom sound, if not => plays default forge sound
     */
    public static synchronized void playSoundSwitch(final String id, final String forgeId) {
        if (soundReg.containsKey(id)) playSound(id);
        else Minecraft.getMinecraft().thePlayer.playSound(forgeId, 2f, 1f);
    }
}
