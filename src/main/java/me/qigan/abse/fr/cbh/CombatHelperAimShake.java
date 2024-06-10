package me.qigan.abse.fr.cbh;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatHelperAimShake extends Module {

    @SubscribeEvent
    void mouseClick(InputEvent.MouseInputEvent e) {
        click(null);
    }

    @SubscribeEvent
    void click(InputEvent.KeyInputEvent e) {
        if (!isEnabled() || !Index.MAIN_CFG.getBoolVal("cbh_shake") || CombatHelperAim.prim == null || CombatHelperAim.prim.ref == null) return;
        if (Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown()) {
            Random rand = new Random();
            double xv = Utils.createRandomDouble(Index.MAIN_CFG.getDoubleVal("cbh_shake_amount"), 0);
            double yv = Utils.createRandomDouble(Index.MAIN_CFG.getDoubleVal("cbh_shake_amount"), 0);
            Minecraft.getMinecraft().thePlayer.rotationYaw += rand.nextBoolean() ? xv : -xv;
            Minecraft.getMinecraft().thePlayer.rotationPitch += rand.nextBoolean() ? yv : -yv;
        }
    }

    @Override
    public String fname() {
        return "Aim shake";
    }

    @Override
    public String id() {
        return "cbh_shake";
    }

    @Override
    public Specification category() {
        return Specification.COMBAT;
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("cbh_shake_amount", "Amount", ValType.DOUBLE_NUMBER, "1.5"));
        return list;
    }

    @Override
    public String description() {
        return "Makes it feels like your hand is shaking";
    }
}
