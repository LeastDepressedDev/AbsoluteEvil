package me.qigan.abse.fr.other;

import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.TickTasks;
import me.qigan.abse.gui.overlay.GuiNotifier;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.vp.Esp;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GrottoFinder extends Module {

    public static List<BlockPos> grottos = new ArrayList<>();

    public static Set<AddressedData<Integer, Integer>> vChunks = new HashSet<>();

    public static boolean active = false;

    public static final int DISTANCE_PARA_CONST = 10000;

    public static Queue<Thread> queue = new LinkedList<>();
    public static AddressedData<Integer, Integer> lastScannedChunk = null;

    @SubscribeEvent
    void onChunkLoad(PacketEvent.ReceiveEvent e) {
        if (!isEnabled()) return;
        if (e.packet instanceof S21PacketChunkData) {
            S21PacketChunkData packet = (S21PacketChunkData) e.packet;
            AddressedData<Integer, Integer> dat = new AddressedData<>(packet.getChunkX(), packet.getChunkZ());
            if (!vChunks.contains(dat)) {
                vChunks.add(dat);
                queue.add(new Thread(() -> scan(packet.getChunkX(), packet.getChunkZ())));
            }
        }
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!active && !queue.isEmpty()) {
            active = true;
            queue.poll().start();
        }
    }

    public static void scan(final int cx, final int cz) {
        lastScannedChunk = new AddressedData<>(cx, cz);
        int mx = cx * 16;
        int mz = cz * 16;
        for (int x = mx; x < mx + 16; x++) {
            for (int z = mz; z < mz + 16; z++) {
                for (int y = 31; y < 170; y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    IBlockState state = Minecraft.getMinecraft().theWorld.getBlockState(pos);
                    if (state.getBlock() != Blocks.stained_glass) continue;
                    if (state.getValue(BlockStainedGlass.COLOR) == EnumDyeColor.MAGENTA) {
                        boolean preq = false;
                        for (BlockPos grot : grottos) {
                            if (pos.distanceSq(grot.getX(), grot.getY(), grot.getZ()) < DISTANCE_PARA_CONST) {
                                preq = true;
                                break;
                            }
                        }
                        if (preq) continue;
                        System.out.println("You found!");
                        found(pos);
                        active = false;
                        return;
                    }
                }
            }
        }

        active = false;
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!isEnabled()) return;

        for (int i = 0; i < grottos.size(); i++) {
            BlockPos pos = grottos.get(i);
            if (Index.MAIN_CFG.getBoolVal("grotto_finder_wp")) {
                Esp.autoBox3D(pos, Color.magenta, 2f, true);
                //Esp.autoBeaconBeam(pos.getX(), pos.getY(), pos.getZ(), Color.magenta.getRGB(), 100, e.partialTicks);
                Esp.renderTextInWorld("Grotto #" + i, pos, Color.magenta.getRGB(), 3f, e.partialTicks);
            }
            if (Index.MAIN_CFG.getBoolVal("grotto_finder_tracer")) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                Esp.drawTracer(player.posX, player.posY, player.posY, pos.getX()+0.5d, pos.getY()+0.5d, pos.getZ()+0.5d,
                        Color.magenta, 2f, true);
            }
        }

    }

    public static void fix() {
        vChunks.clear();
        queue.clear();
        grottos.clear();
        active = false;
    }

    public static void found(BlockPos pos) {
        grottos.add(pos);

        String plLine = "[";
        int amt = 0;
        for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (ent instanceof EntityPlayer) {
                if (ent.getDistanceSqToCenter(pos) < 3600) {
                    plLine+=ent.getName()+",";
                    amt++;
                }
            }
        }
        String finalLine = "\u00A76There is \u00A7" + (amt == 0 ? "a" : "c") + amt + "\u00A76 players near. \u00A7c";
        if (amt > 0) {
            finalLine += plLine.substring(0, plLine.length() - 1) + "]";
        }
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
                new ChatComponentText("\u00A7aFound potential grotto at " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + ". " + finalLine)
        );
        GuiNotifier.call("Found grotto", 20, true, Color.magenta.getRGB());
    }

    @SubscribeEvent
    void reset(WorldEvent.Load e) {
        vChunks.clear();
        queue.clear();
        grottos.clear();
        active = false;
    }

    @Override
    public String id() {
        return "grotto_finder";
    }

    @Override
    public String fname() {
        return "Grotto finder";
    }

    @Override
    public Specification category() {
        return Specification.SB_QOL;
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("grotto_finder_wp", "Show waypoints", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("grotto_finder_tracer", "Show tracer", ValType.BOOLEAN, "false"));
        list.add(new SetsData<>("grotto_finder_fix", "Fix", ValType.BUTTON, (Runnable) GrottoFinder::fix));
        list.add(new SetsData<>("grotto_finder_stat", "Status", ValType.BUTTON, (Runnable) () -> {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
                    new ChatComponentText("Status: scanning: " + active + ", last: " + (lastScannedChunk == null ? "None" :
                            lastScannedChunk.getNamespace() + " " + lastScannedChunk.getObject()))
            );
        }));
        return list;
    }

    @Override
    public String description() {
        return "Finds fairy grotto.";
    }
}
