package me.qigan.abse.fr.other;

import me.qigan.abse.crp.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundOverride extends Module {

    @SubscribeEvent
    void onSound(PlaySoundEvent e) {
        if (!isEnabled()) return;
//        if (e.name.equalsIgnoreCase("random.orb") && e.sound.getPitch() == 1.4920635f) {
//            e.manager.stopSound(e.sound);
//            Minecraft.getMinecraft().thePlayer.playSound("abse:skeet_hit", 1f, 1f);
//        }
        if (e.name.equalsIgnoreCase("mob.irongolem.throw") && e.sound.getPitch() == 1.4126984f) {
            e.manager.stopSound(e.sound);
        }
        if (e.name.equalsIgnoreCase("mob.zombie.woodbreak") && e.sound.getPitch() == 1.3333334f) {
            e.manager.stopSound(e.sound);
            Minecraft.getMinecraft().thePlayer.playSound("abse:skeet_hit", 1f, 1f);
        }
    }

    @Override
    public String id() {
        return "sovert";
    }

    @Override
    public String fname() {
        return "Sound override";
    }

    @Override
    public String description() {
        return "Change sounds to customs";
    }
}
