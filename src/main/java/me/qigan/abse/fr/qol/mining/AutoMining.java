package me.qigan.abse.fr.qol.mining;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.DangerousModule;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.fr.exc.SmoothAimControl;
import me.qigan.abse.fr.exc.TickTasks;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

@DangerousModule
public class AutoMining extends Module {

    public static final double DIST = 4.5d;
    public static final Random rand = new Random();

    public static List<BlockPos> blockRoute = new ArrayList<>();

    public static boolean active = false;
    public static BlockPos mining = null;
    public static float[] offsets = new float[]{0, 0, 0};

    public static int aotvSlot = -1;

    public static STAGE stage = STAGE.IDLE;
    public static int progress = 0;


    public static int forceDelay = 0;

    public enum STAGE {
        IDLE,
        MINING,
        MOVING
    }

    @SubscribeEvent
    void worldChange(WorldEvent.Load e) {
        clearRoute();
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled() || e.phase == TickEvent.Phase.END) return;
        if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;
        if (forceDelay > 0) {
            forceDelay--;
            return;
        }
        aotvSlot = findSlot("ASPECT_OF_THE_VOID");
        if (aotvSlot == -1 && !Index.MAIN_CFG.getBoolVal("auto_mining_debug")) return;
        BlockPos playerPos = Sync.playerPosAsBlockPos();
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (active) {
            reselect();
            if (Utils.compare(playerPos, blockRoute.get(progress).add(0, 1, 0))) {
                if (stage != STAGE.MINING) reselect();
                stage = STAGE.MINING;
            } else {
                stage = STAGE.MOVING;
            }
        } else {
            stage = STAGE.IDLE;
            mining = null;
        }

        switch (stage) {
            case IDLE: return;
            case MINING: {
                if (mining != null) {
                    if (Index.MAIN_CFG.getBoolVal("auto_mining_manual")) return;
                    simTowardRotation(mining, 1d);

                    BlockPos trace = player.rayTrace(DIST, 1f).getBlockPos();
                    if (Utils.compare(trace, mining)) {
                        ClickSimTick.updatableClick(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), 2);
                    }
                } else {
                    if (progress+1 < blockRoute.size()) progress++;
                    else progress = 0;
                }
            }
            break;
            case MOVING: {
                if (Index.MAIN_CFG.getBoolVal("auto_mining_manual")) return;
                BlockPos target = blockRoute.get(progress);
                simTowardRotation(target, 1.3d);

                BlockPos trace = player.rayTrace(56, 1f).getBlockPos();
                if (Utils.compare(trace, target)) {
                    int slot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = aotvSlot;
                    TickTasks.call(() -> {
                        ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), 2);
                        TickTasks.call(() -> Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot, 4);
                    }, 10);

                    ClickSimTick.clickWCheck(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), 13);

                    forceDelay = 18;
                }
            }
            break;
        }
    }

    private static int findSlot(String str) {
        for (int i = 0; i < 9; i++) {
            if (Utils.getSbData(Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i))
                    .getString("id").equalsIgnoreCase(str)) return i;
        }
        return -1;
    }

    public static void simTowardRotation(BlockPos target, double speedMod) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(target).getBlock();
        double dx = target.getX() + 0.5d - player.posX + (block == Blocks.stained_glass_pane ? 0 : offsets[0]);
        double dy = target.getY() - player.posY - 1d + offsets[1];
        double dz = target.getZ() + 0.5d - player.posZ + (block == Blocks.stained_glass_pane ? 0 : offsets[2]);
        Float[] angles = Utils.getRotationsTo(dx, dy, dz, new float[]{player.rotationYaw, player.rotationPitch});
        SmoothAimControl.set(angles, 2, 20, Index.MAIN_CFG.getDoubleVal("auto_mining_aim")*speedMod);
    }

    public static void rollOffset() {
        float spread = Index.MAIN_CFG.getIntVal("auto_mining_sprd");
        offsets[0] = rand.nextInt()%spread/1000f;
        offsets[1] = rand.nextInt()%spread/1000f;
        offsets[2] = rand.nextInt()%spread/1000f;
    }


    //Double check cuz yeah!

    @SubscribeEvent
    void blockDestroyPacket(PacketEvent.ReceiveEvent e) {
        if (!isEnabled()) return;
        if (e.packet instanceof S22PacketMultiBlockChange) {
            S22PacketMultiBlockChange packet = (S22PacketMultiBlockChange) e.packet;
            for (S22PacketMultiBlockChange.BlockUpdateData data : packet.getChangedBlocks()) {
                if (Utils.compare(data.getPos(), mining)) {
                    forceDelay = Index.MAIN_CFG.getIntVal("auto_mining_force");
                }
            }
        }
    }

    @SubscribeEvent
    void blockDestroyEvent(BlockEvent.BreakEvent e) {
        if (!isEnabled()) return;
        if (Utils.compare(e.pos, mining)) {
            forceDelay = Index.MAIN_CFG.getIntVal("auto_mining_force");
            rollOffset();
            reselect();
        }
    }



    @SubscribeEvent
    void onGuiOpen(GuiScreenEvent.InitGuiEvent e) {
        if (!isEnabled()) return;
        active = false;
        forceDelay = 0;
    }

    @SubscribeEvent
    void onScreenRender(RenderGameOverlayEvent.Text e) {
        if (!isEnabled()) return;
        List<String> toRender = new ArrayList<>(Arrays.asList(
                "\u00A76Status: " + (active ? "\u00A7aACTIVE" : "\u00A7cSTOPPED"),
                "\u00A76Phase: \u00a7d" + stage.name(),
                "\u00A76Mining pos: " + (mining == null ? "\u00A7cUNSET" : "\u00A7aSet")
        ));
        if (aotvSlot == -1 && !Index.MAIN_CFG.getBoolVal("auto_mining_debug")) toRender.add(0, "\u00A7c!!!MISSING REQUIREMENT!!!");
        Esp.drawAllignedTextList(toRender, e.resolution.getScaledWidth()/2+20, e.resolution.getScaledHeight()/2-50, false, e.resolution, S2Dtype.SHADOW);
    }

    public static BlockPos crystalScan(BlockPos playerPos, BlockPos lastMinedPos) {
        BlockPos sel = null;
        for (int x = -4; x < 4; x++) {
            for (int y = -4; y < 4; y++) {
                for (int z = -4; z < 4; z++) {
                    BlockPos scanPos = playerPos.add(x, y, z);
                    double sq = scanPos.distanceSq(playerPos);
                    if (sq > DIST*DIST || sq < 1.3d*1.3d) continue;
                    if (scanPos.getX() == playerPos.getX() && scanPos.getZ() == playerPos.getZ() && scanPos.getY() <= playerPos.getY()) continue;
                    Block curBlock = Minecraft.getMinecraft().theWorld.getBlockState(scanPos).getBlock();
                    if (curBlock != Blocks.stained_glass && curBlock != Blocks.stained_glass_pane) continue;
                    if (sel == null) {
                        sel = scanPos;
                    } else {
                        if (lastMinedPos == null) {
                            if (sel.distanceSq(playerPos) > scanPos.distanceSq(playerPos)) sel = scanPos;
                        } else {
                            if (sel.distanceSq(playerPos) + sel.distanceSq(lastMinedPos) >
                                    scanPos.distanceSq(playerPos) + scanPos.distanceSq(lastMinedPos)) sel = scanPos;
                        }
                    }
                }
            }
        }
        return sel;
    }

    @SubscribeEvent
    void renderWorld(RenderWorldLastEvent e) {
        if (!isEnabled()) return;
        if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;
        BlockPos prePos = null;
        for (int i = 0; i < blockRoute.size(); i++) {
            BlockPos curPos = blockRoute.get(i);
            if (prePos != null) Esp.drawTracer(prePos.getX()+0.5d, prePos.getY()+0.5d, prePos.getZ()+0.5d,
                    curPos.getX()+0.5d, curPos.getY()+0.5d, curPos.getZ()+0.5d,
                    Color.magenta, 8f, true);
            Esp.autoBox3D(curPos, i == progress ? Color.green : Color.yellow, 2f, true);
            prePos = curPos;
            if (i == blockRoute.size()-1) {
                BlockPos sp = blockRoute.get(0);
                Esp.drawTracer(prePos.getX()+0.5d, prePos.getY()+0.5d, prePos.getZ()+0.5d,
                        sp.getX()+0.5d, sp.getY()+0.5d, sp.getZ()+0.5d,
                        Color.magenta, 8f, true);
            }
        }
        if (mining != null) Esp.autoBox3D(mining, Color.magenta, 4f, false);
    }

    @SubscribeEvent
    void interactEvent(PlayerInteractEvent e) {
        if (!isEnabled() || blockRoute.isEmpty()) return;
        if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (Utils.compare(blockRoute.get(progress), e.pos)) {
                active = true;
            }
        }
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
//        list.add(new SetsData<>("test_route", "Test route", ValType.BUTTON, (Runnable) () -> {
//            blockRoute = new ArrayList<>(Arrays.asList(
//                    new BlockPos(5, 5, 5),
//                    new BlockPos(5, 7, 10),
//                    new BlockPos(3, 3, 9)
//            ));
//        }));
        list.add(new SetsData<>("auto_mining_comment0", "Manual mode is disabling automation but keeps visuals enabled", ValType.COMMENT, null));
        list.add(new SetsData<>("auto_mining_manual", "Manual mode", ValType.BOOLEAN, "false"));
        list.add(new SetsData<>("auto_mining_debug", "\u00A7cDebug mode", ValType.BOOLEAN, "false"));
        list.add(new SetsData<>("auto_mining_comment1", "Routes are located in config/abse/mining", ValType.COMMENT, null));
        list.add(new SetsData<>("auto_mining_load", "Load route", ValType.BUTTON, (Runnable) AutoMining::loadRoute));
        list.add(new SetsData<>("auto_mining_load_path", "Route file name", ValType.STRING, ""));
        list.add(new SetsData<>("auto_mining_clear", "Clear route", ValType.BUTTON, (Runnable) AutoMining::clearRoute));
        list.add(new SetsData<>("auto_mining_reset", "Reset route", ValType.BUTTON, (Runnable) AutoMining::routeReset));
        list.add(new SetsData<>("auto_mining_comment2", "Active settings: ", ValType.COMMENT, null));
        list.add(new SetsData<>("auto_mining_force", "Force delay ticks", ValType.NUMBER, "1"));
        list.add(new SetsData<>("auto_mining_aim", "Aim speed", ValType.DOUBLE_NUMBER, "11"));
        list.add(new SetsData<>("auto_mining_sprd", "Spread[cord/1000]", ValType.NUMBER, "300"));
        return list;
    }

    @Override
    public String id() {
        return "auto_mining";
    }

    @Override
    public String fname() {
        return "Auto mining";
    }

    @Override
    public Specification category() {
        return Specification.SB_QOL;
    }

    @Override
    public String description() {
        return "Solution for your poor nw. Instruction:\n1. Get aotv with etherwarp.\n2. To start mining left click on green box\n3. To stop mining open any gui(inventroy and etc)\n4. Enjoy free money go brrrr.";
    }

    public static void reselect() {
        BlockPos pseudoMining = crystalScan(Sync.playerPosAsBlockPos().add(0, 1, 0), mining);
        if (!Utils.compare(pseudoMining, mining)) rollOffset();
        mining = pseudoMining;
    }

    public static void loadRoute() {
        File file = new File(Loader.instance().getConfigDir() + "/abse/mining/" + Index.MAIN_CFG.getStrVal("auto_mining_load_path"));
        if (file.isFile() && file.exists()) {
            routeReset();
            try {
                List<BlockPos> path = new ArrayList<>();
                JSONObject obj = new JSONObject(new Scanner(file).useDelimiter("\\Z").next());
                JSONArray posses = obj.getJSONArray("pos");
                if (posses.length() != obj.getInt("size")) {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("\u00A7cInvalid route configuration!"));
                    return;
                }
                for (int i = 0; i < posses.length(); i++) {
                    JSONObject pos = posses.getJSONObject(i);
                    path.add(new BlockPos(pos.getInt("x"), pos.getInt("y"), pos.getInt("z")));
                }
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("\u00A7aLoaded " + obj.getString("name") + " by " + obj.getString("author") + ". Length: " + obj.getInt("size") + "."));
                blockRoute = path;
            } catch (FileNotFoundException e) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("\u00A7cUnpredicted thing went wrong!"));
                throw new RuntimeException(e);
            }
        } else {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("\u00A7cRoute file not found!"));
        }
    }

    public static void init() {
        File file = new File(Loader.instance().getConfigDir() + "/abse/mining");
        if (!file.exists()) file.mkdirs();
    }

    public static void routeReset() {
        active = false;
        progress = 0;
    }

    public static void clearRoute() {
        routeReset();
        blockRoute.clear();
    }
}
