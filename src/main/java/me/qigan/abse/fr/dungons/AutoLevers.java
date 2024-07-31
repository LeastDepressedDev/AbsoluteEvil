package me.qigan.abse.fr.dungons;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AutoLevers extends Module {

    public static final List<BlockPos> CONSTS = Arrays.asList(
            new BlockPos(94, 124, 113), new BlockPos(106, 124, 113),
            new BlockPos(23, 132, 138), new BlockPos(27, 124, 127),
            new BlockPos(2, 122, 55), new BlockPos(14, 122, 55),
            new BlockPos(84, 121, 34), new BlockPos(86, 128, 46),

            new BlockPos(62, 133, 142), new BlockPos(58, 136, 142),
            new BlockPos(62, 136, 142), new BlockPos(60, 134, 142),
            new BlockPos(58, 133, 142), new BlockPos(60, 135, 142)
    );

    public static Set<BlockPos> tracks = new HashSet<>(CONSTS);
    private static int del = 0;

    @SubscribeEvent
    void load(WorldEvent.Load e) {
        if (!isEnabled()) return;
        tracks = new HashSet<>(CONSTS);
    }

    @SubscribeEvent
    void interact(PlayerInteractEvent e) {
        if (!isEnabled() || !Sync.inDungeon) return;
        if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || e.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            tracks.remove(new BlockPos(e.pos.getX(), e.pos.getY(), e.pos.getZ()));
        }
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || e.phase == TickEvent.Phase.END
                || Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null || !Sync.inDungeon) return;
        if (del > 0) del--;
        if (del != 0) return;
        try {
            BlockPos pos = Minecraft.getMinecraft().thePlayer
                    .rayTrace(Index.MAIN_CFG.getDoubleVal("aulev_dist"), 1f).getBlockPos();
            if (tracks.contains(pos) && !Minecraft.getMinecraft().thePlayer.isSneaking()) {
                ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), Index.MAIN_CFG.getIntVal("aulev_hold"));
                del = Index.MAIN_CFG.getIntVal("aulev_t");
            }
        } catch (Exception exd) {}
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled() || Minecraft.getMinecraft().theWorld == null || !Sync.inDungeon) return;
        for (BlockPos pos: CONSTS) {
            Esp.autoBox3D(pos, tracks.contains(pos) ? Color.red : Color.green, 3f, false);
        }
    }

    @Override
    public String id() {
        return "aulev";
    }

    @Override
    public Specification category() {
        return Specification.DUNGEONS;
    }

    @Override
    public String fname() {
        return "Auto levers on F7/M7";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("aulev_t", "Tick delay", ValType.NUMBER, "4"));
        list.add(new SetsData<>("aulev_hold", "Hold time[ticks]", ValType.NUMBER, "1"));
        list.add(new SetsData<>("aulev_dist", "Active distance", ValType.DOUBLE_NUMBER, "4.2"));
        return list;
    }

    @Override
    public String description() {
        return "This shit was annoying so now it's automatic";
    }
}
