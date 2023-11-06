package me.qigan.abse.fr.other;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.MainWrapper;
import me.qigan.abse.crp.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoBridging extends Module {

    public static boolean toggle = false;

    public static boolean pressed = false;

    @SubscribeEvent
    void tick(RenderWorldLastEvent e) {
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return;
        if (!isEnabled()) return;
        boolean tgState = Index.MAIN_CFG.getBoolVal("abrig_tog");
        if (tgState && MainWrapper.Keybinds.autoBridging.isPressed()) toggle=!toggle;
        if ((MainWrapper.Keybinds.autoBridging.isKeyDown() && !tgState) || (toggle && tgState)) {
            BlockPos blockBelowPlayer = new BlockPos(Minecraft.getMinecraft().thePlayer.posX,
                    Minecraft.getMinecraft().thePlayer.posY - 1,
                    Minecraft.getMinecraft().thePlayer.posZ);
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockBelowPlayer).getBlock();
            MovingObjectPosition prePos = Minecraft.getMinecraft().thePlayer.rayTrace(Index.MAIN_CFG.getDouble("abrig_tracel"), e.partialTicks);
            if (prePos == null) return;
            BlockPos trace = prePos.getBlockPos();
            if (trace == null) return;
            if (!pressed && Minecraft.getMinecraft().theWorld.getBlockState(trace).getBlock() instanceof BlockAir) {
                new Thread(() -> {
                    try {
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), true);
                        pressed = true;
                        Thread.sleep(Index.MAIN_CFG.getIntVal("abrig_hold"));
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), false);
                        pressed = false;
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
           }
        }
    }

    @Override
    public String id() {
        return "abrig";
    }

    @Override
    public String fname() {
        return "Auto bridging[WIP]";
    }

    @Override
    public List<SetsData<String>> sets() {
        List<SetsData<String>> list = new ArrayList<>();
        list.add(new SetsData<>("abrig_tog", "Toggle mode", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("abrig_tracel", "Trace length", ValType.DOUBLE_NUMBER, "1.7"));
        list.add(new SetsData<>("abrig_hold", "Hold time(ms)", ValType.NUMBER, "30"));
        return list;
    }

    @Override
    public String description() {
        return "Automatically do fast bridging.";
    }
}
