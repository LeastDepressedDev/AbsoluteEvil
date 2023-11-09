package me.qigan.abse.fr.other;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.cbh.CombatHelperAim;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class SaveBot extends Module {

    public static int DEL_Q = 30;

    public static boolean SAVING = false;

    void executeBucketSave() {
        new Thread(() -> {
            try {
                SAVING = true;
                CombatHelperAim.OVERRIDE = true;
                CombatHelperAim.prim = new CombatHelperAim.Target(null, 0, 90).unlockTheta();
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), true);
                Thread.sleep(DEL_Q);
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), false);
                CombatHelperAim.prim = null;
                CombatHelperAim.OVERRIDE = false;
                SAVING = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    boolean bucketCheck() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.water_bucket) return true;
        }
        return false;
    }

    double verticalCheck() {
        for (int y = (int) Minecraft.getMinecraft().thePlayer.posY; y >= 0; y--) {
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(Minecraft.getMinecraft().thePlayer.posX, y, Minecraft.getMinecraft().thePlayer.posZ)).getBlock();
            if (!(block instanceof BlockAir || block instanceof BlockFluidBase)) {
                return Minecraft.getMinecraft().thePlayer.posY - y;
            }
        }
        return -1;
    }

    void scan() {
        double sx = Minecraft.getMinecraft().thePlayer.posX;
        double sy = Minecraft.getMinecraft().thePlayer.posY;
        double sz = Minecraft.getMinecraft().thePlayer.posZ;

        for (int x = (int) sx-3; x < (int)sx+3; x++) {
            for (int y = (int) sy-3; y < (int)sy+3; y++) {
                for (int z = (int) sz-3; z < (int)sz+3; z++) {

                }
            }
        }
    }

    @SubscribeEvent
    void onSee(RenderWorldLastEvent e) {
        if (!isEnabled() || SAVING) return;
        double dy = Minecraft.getMinecraft().thePlayer.posY-Minecraft.getMinecraft().thePlayer.prevPosY;

        if (dy > 0.4) {
            double vt = verticalCheck();
            if (vt != -1) {
                if (Index.MAIN_CFG.getBoolVal("sbot_bucket") && bucketCheck()) {
                    if (vt < 3) executeBucketSave();
                }
            }
        }
    }

    @Override
    public String id() {
        return "sbot";
    }

    @Override
    public String fname() {
        return "Save bot";
    }

    @Override
    public List<SetsData<String>> sets() {
        List<SetsData<String>> list = new ArrayList<>();
        list.add(new SetsData<>("sbot_bucket", "Water bucket", ValType.BOOLEAN, "true"));
        return list;
    }

    @Override
    public String description() {
        return "Automatically do the clutch and saves thing";
    }
}
