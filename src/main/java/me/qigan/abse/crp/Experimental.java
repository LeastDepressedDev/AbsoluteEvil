package me.qigan.abse.crp;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.fr.exc.SmoothAimControl;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AutoDisable
@DangerousModule
public class Experimental extends Module {

    public static int iter = 0;
    public static List<BlockPos> seq = new ArrayList<>(
            Arrays.asList(new BlockPos(0, 6, 1), new BlockPos(0, 8, 4), new BlockPos(0, 7, 2)
            ));

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null || e.phase == TickEvent.Phase.END) return;
        if (MainWrapper.Keybinds.ssKey.isKeyDown()) {
            BlockPos pos = seq.get(iter);
            double dx = pos.getX() + 0.1d - Minecraft.getMinecraft().thePlayer.posX;
            double dy = pos.getY() - Minecraft.getMinecraft().thePlayer.posY - 1.1d;
            double dz = pos.getZ() + 0.5d - Minecraft.getMinecraft().thePlayer.posZ;
            float[] vecs = Utils.getRotationsTo(dx, dy, dz, new float[]{
                    Minecraft.getMinecraft().thePlayer.rotationYaw,
                    Minecraft.getMinecraft().thePlayer.rotationPitch
            });
            SmoothAimControl.set(vecs, 2, Index.MAIN_CFG.getDoubleVal("exp_2"), Index.MAIN_CFG.getDoubleVal("exp_1"));
            BlockPos bp = Minecraft.getMinecraft().thePlayer.rayTrace(4.2d, 1f).getBlockPos();
            if (Utils.compare(seq.get(iter), bp)) {
                iter++;
            }
            if (iter > 2) iter = 0;
        }
    }

    @SubscribeEvent
    void rend(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        for (BlockPos pos : seq) {
            Esp.autoBox3D(pos, Color.red, 2f, true);
        }
    }

    @Override
    public String id() {
        return "exptl";
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

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("exp_1", "Speed", ValType.DOUBLE_NUMBER, "3.5"));
        list.add(new SetsData<>("exp_2", "Coef", ValType.DOUBLE_NUMBER, "20"));
        return list;
    }

    @Override
    public String description() {
        return "Being used for testing some crazy stuff";
    }
}
