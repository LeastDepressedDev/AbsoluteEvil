package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.sync.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class BonzoCorrector extends Module {

    @SubscribeEvent
    void interact(PlayerInteractEvent e) {
        if (!isEnabled()) return;
        if (e.pos != null) {
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(e.pos).getBlock();
            if (block == Blocks.lever || block == Blocks.stone_button || block == Blocks.wooden_button) return;
        }
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null && Minecraft.getMinecraft().thePlayer.isSprinting()) {
            String id = Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()).getString("id");
            if (id.equalsIgnoreCase("BONZO_STAFF") || id.equalsIgnoreCase("STARRED_BONZO_STAFF")) {
                new Thread(() -> {
                    try {
                        Thread.sleep(Index.MAIN_CFG.getIntVal("bonzo_cor_time"));
                        ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), 4);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            }
        }
    }

    @Override
    public String id() {
        return "bonzo_cor";
    }

    @Override
    public Specification category() {
        return Specification.DUNGEONS;
    }

    @Override
    public String fname() {
        return "Bonzo corrector";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("bonzo_cor_time", "Delay[milliseconds]", ValType.NUMBER, "200"));
        return list;
    }

    @Override
    public String description() {
        return "Ideally corrects bonzo jump";
    }
}
