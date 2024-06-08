package me.qigan.abse.fr;

import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

public class FunnyAddons extends Module {

    private static List<AddressedData<AddressedData<Vector3d, Integer>, Integer>> render = new ArrayList<>();

    @SubscribeEvent
    void death(LivingDeathEvent e) {
        if (!isEnabled() || !Index.MAIN_CFG.getBoolVal("real_fun_rng")) return;
        Vector3d vec = new Vector3d();
        vec.x = e.entity.posX;
        vec.y = e.entity.posY;
        vec.z = e.entity.posZ;
        render.add(new AddressedData<>(new AddressedData<>(vec, (int) ((double) new Random().nextInt()%
                (double) Index.MAIN_CFG.getIntVal("real_fun_rng_dev"))), 60));
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || e.phase == TickEvent.Phase.END) return;
        try {
            for (AddressedData<AddressedData<Vector3d, Integer>, Integer> ele : render) {
                if (ele.getObject() > 0) ele.setObject(ele.getObject()-1);
                else render.remove(ele);
            }
        } catch (ConcurrentModificationException ignored) {}
    }

    @SubscribeEvent
    void renderWorld(RenderWorldLastEvent e) {
        if (!isEnabled() || !Index.MAIN_CFG.getBoolVal("real_fun_rng") || Minecraft.getMinecraft().theWorld == null) return;
        List<AddressedData<AddressedData<Vector3d, Integer>, Integer>> timed = new ArrayList<>(render);
        for (AddressedData<AddressedData<Vector3d, Integer>, Integer> ele : timed) {
            Esp.renderTextInWorld(ele.getNamespace().getObject().toString(),
                    ele.getNamespace().getNamespace().x, ele.getNamespace().getNamespace().y, ele.getNamespace().getNamespace().z,
                    Color.cyan.getRGB(), e.partialTicks);
        }
    }

    @Override
    public String id() {
        return "real_fun";
    }

    @Override
    public String fname() {
        return "Funny addons";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("real_fun_rng", "Pseudo rng tracker", ValType.BOOLEAN, "false"));
        list.add(new SetsData<>("real_fun_rng_dev", "Pseudo rng devider", ValType.NUMBER, "1000000"));
        return list;
    }

    @Override
    public String description() {
        return "Funny things";
    }
}
