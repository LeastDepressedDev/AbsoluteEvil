package me.qigan.abse.mapping.mod;

import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.mapping.Mapping;
import me.qigan.abse.mapping.MappingController;
import me.qigan.abse.mapping.Room;
import me.qigan.abse.vp.Esp;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Remapping extends Module {

    public static List<AddressedData<BlockPos, Block>> repos = new ArrayList<>();

    @Override
    public String id() {
        return "remap";
    }

    @Override
    public String fname() {
        return "Remapping";
    }

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (!Index.MAIN_CFG.getBoolVal("remap_track") || Minecraft.getMinecraft().theWorld == null) return;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int[] xz = Mapping.cellToReal(i, j);
                int y = Mapping.rayDown(xz[0], xz[1], Minecraft.getMinecraft().theWorld);
                Esp.autoBox3D(new BlockPos(xz[0], y, xz[1]), Color.red, 3f, true);
            }
        }
        for (AddressedData<BlockPos, Block> bp : repos) {
            Esp.autoBox3D(bp.getNamespace(), Color.cyan, 2f, true);
            Esp.renderTextInWorld(bp.getObject().toString(), bp.getNamespace().getX()+0.5, bp.getNamespace().getY()+0.5, bp.getNamespace().getZ()+0.5,
                    Color.cyan.getRGB(), e.partialTicks);
        }
    }

    @SubscribeEvent
    void interact(PlayerInteractEvent e) {
        if (!Index.MAIN_CFG.getBoolVal("remap_track")) return;
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() == null && e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            repos.add(new AddressedData<>(e.pos, Minecraft.getMinecraft().theWorld.getBlockState(e.pos).getBlock()));
        }
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("remap_track", "Track mode", ValType.BOOLEAN, "false"));
        list.add(new SetsData<>("remap_track_clear", "Clear track", ValType.BUTTON, (Runnable) () -> repos.clear()));
        list.add(new SetsData<>("remap_track_sum", "Summarize[In console]", ValType.BUTTON, (Runnable) () -> {
            String ln = "\n";
            Room rm = Index.MAPPING_CONTROLLER.roomMap.get(Index.MAPPING_CONTROLLER.getCurrentCellIter());
            BlockPos pos = rm.transformInnerCoordinate(new BlockPos(0, 69, 0));
            for (AddressedData<BlockPos, Block> ele : repos) {
                //int[] cc = Mapping.cellToReal(rm.center[0], rm.center[1]);
                int dx = Math.abs(ele.getNamespace().getX() - pos.getX());
                int dz = Math.abs(ele.getNamespace().getZ() - pos.getZ());
                BlockPos fPos = new BlockPos(
                        (rm.getRotation() == Room.Rotation.SOUTH || rm.getRotation() == Room.Rotation.NORTH ? dx : dz),
                        ele.getNamespace().getY(),
                        (rm.getRotation() == Room.Rotation.SOUTH || rm.getRotation() == Room.Rotation.NORTH ? dz : dx));
                //MappingController.debug.add(rm.transformInnerCoordinate(fPos));
                ln += "new AddressedData<>(new BlockPos(" + fPos.getX() + ", " + fPos.getY() + ", " + fPos.getZ() + "), Blocks." + ele.getObject().getRegistryName().substring(10) + "), \n";
            }
            System.out.println(ln);
        }));
        list.add(new SetsData<>("remap_inf", "Room info", ValType.BUTTON, (Runnable) () -> Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(createRoomInfo()))));
        return list;
    }

    public static String createRoomInfo() {
        try {
            Room rm = Index.MAPPING_CONTROLLER.roomMap.get(Index.MAPPING_CONTROLLER.getCurrentCellIter());
            return rm.iter + ":   " + rm.center[0] + "-" + rm.center[1] + "\n"
                    + rm.getShape() + "||" + rm.getType() + "||" + rm.getRotation() + "||" + rm.getHeight() + "\n"
                    + "id:" + rm.getId();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String description() {
        return "Presets all the routes you need in room";
    }
}
