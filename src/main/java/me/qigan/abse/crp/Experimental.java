package me.qigan.abse.crp;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.mapping.MappingController;
import me.qigan.abse.pathing.Path;
import me.qigan.abse.sync.Sync;

import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.util.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

@AutoDisable
@DangerousModule
public class Experimental extends Module implements EDLogic {

    @Override
    public String id() {
        return "exptl";
    }

    @Override
    public Specification category() {
        return Specification.SPECIAL;
    }

    @Override
    public String fname() {
        char[] str = "Experimental".toCharArray();
        String nstr = "";
        for (int i = 0; i < str.length; i++) {
            nstr += (i % 2 == 0) ? ("\u00A7e" + str[i]) : ("\u00A77" + str[i]);
        }
        return nstr;
    }

    @SubscribeEvent
    void tick(RenderWorldLastEvent e) {
        if (!isEnabled()) return;

        Esp.autoFilledBox3D(new BlockPos(5, 5, 5), new Color(255, 0, 255, 155), 2f, false);

        if (Minecraft.getMinecraft().objectMouseOver.getBlockPos() != null)
            Esp.autoBox3D(Minecraft.getMinecraft().objectMouseOver.getBlockPos(), Color.cyan, 2f, true);
        if (Minecraft.getMinecraft().objectMouseOver.entityHit != null)
            Esp.autoBox3D(Minecraft.getMinecraft().objectMouseOver.entityHit, Color.cyan, 2f, true);
    }

    @SubscribeEvent
    void rend(RenderGameOverlayEvent.Text e) {
        if (!isEnabled()) return;

        try {
            Esp.drawCenteredString(Index.MOVEMENT_CONTROLLER.getPath().getPosPath().size() + " : " + Index.MOVEMENT_CONTROLLER.isPaused(), 400, 200, 0xFFFFFF, S2Dtype.DEFAULT);
        } catch (Exception ex) {

        }
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("exptl_but1", "Routing", ValType.BUTTON, (Runnable) () -> {
            if (isEnabled()) Index.MOVEMENT_CONTROLLER.go(new Path(Sync.playerPosAsBlockPos(), new BlockPos(10, 9, 7)).build());
        }));
        list.add(new SetsData<>("exptl_but2", "Mapping", ValType.BUTTON, (Runnable) () -> {
            if (isEnabled()) {
                MappingController.debug.clear();
            }
        }));
        return list;
    }

    @Override
    public String description() {
        return "Being used for testing some crazy stuff";
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        Index.MOVEMENT_CONTROLLER.stop();
    }
}
