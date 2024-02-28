package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.GhostBlocks;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DeviceIssue extends Module {

    public static final BlockPos[] BOUNDING_SS_CONST = {new BlockPos(107, 120, 92), new BlockPos(109, 123, 95)};
    public static final BlockPos[] BLOCK_SPAWN_SS_CONST = {new BlockPos(111, 120, 92), new BlockPos(111, 123, 95)};
    public static final BlockPos CENTER_BUTTON_SS_CONST = new BlockPos(110, 121, 91);

    public static boolean ready = true;
    public static boolean started = false;
    public static boolean done = false;


    private static int time = 0;
    private static int iter = 0;

    @SubscribeEvent
    void load(WorldEvent.Load e) {
        ready = true;
        started = false;
        done = false;
        time = 0;
        iter = 0;
    }

    @SubscribeEvent
    void onSpawn(EntityJoinWorldEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null) return;
        if (ready && e.entity.getDistanceSqToCenter(CENTER_BUTTON_SS_CONST) <= 7) {
            ready = false;
            if (Minecraft.getMinecraft().thePlayer.isSneaking()) started = true;
            else done = true;
        }
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null || e.phase == TickEvent.Phase.END) return;
        if (!Utils.posInDim(Sync.playerPosAsBlockPos(), BOUNDING_SS_CONST)) return;
        if (started) {
            int lim = Index.MAIN_CFG.getIntVal("ss_count");
            if (iter < lim) {
                if (time == 0) {
                    time = Index.MAIN_CFG.getIntVal("ss_del");
                    iter++;
                    //CLICK!
                    new Thread(() -> {
                        try {
                            int hold = Index.MAIN_CFG.getIntVal("ss_hold");
                            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), true);
                            Thread.sleep(hold);
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7aClick!"));
                            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), false);
                        } catch (InterruptedException ex) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A74Something bad happen when skipping ss."));
                            throw new RuntimeException(ex);
                        }
                    }).start();
                } else {
                    time--;
                }
            } else if (iter == lim) {
                done = true;
            }
        }
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return;
        if (Utils.posInDim(Sync.playerPosAsBlockPos(), BOUNDING_SS_CONST)) {
            if (done) {
                Esp.renderTextInWorld(started ? "\u00A76Done" : "\u00A7cCancelled",
                        CENTER_BUTTON_SS_CONST.getX(), CENTER_BUTTON_SS_CONST.getY() + 1, CENTER_BUTTON_SS_CONST.getZ(),
                        0xFFFFFF, e.partialTicks);
            } else {
                Esp.renderTextInWorld("\u00A7aAuto skip enabled",
                        CENTER_BUTTON_SS_CONST.getX(), CENTER_BUTTON_SS_CONST.getY() + 1, CENTER_BUTTON_SS_CONST.getZ(),
                        0xFFFFFF, e.partialTicks);
                Esp.renderTextInWorld(Minecraft.getMinecraft().thePlayer.isSneaking() ? "\u00A7eSkipping" : "\u00A74Not skipping",
                        CENTER_BUTTON_SS_CONST.getX(), CENTER_BUTTON_SS_CONST.getY() + 0.7, CENTER_BUTTON_SS_CONST.getZ(),
                        0xFFFFFF, e.partialTicks);
            }
        }
    }

    @Override
    public String id() {
        return "devices";
    }

    @Override
    public String fname() {
        return "Device issue";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("devices_auto_ss", "Auto skip SS", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("ss_count", "Clicks amount", ValType.NUMBER, "3"));
        list.add(new SetsData<>("ss_del", "Delay ticks", ValType.NUMBER, "3"));
        list.add(new SetsData<>("ss_hold", "Hold time[milliseconds]", ValType.NUMBER, "5"));
        return list;
    }

    @Override
    public String description() {
        return "Device utils for f7-m7";
    }
}
