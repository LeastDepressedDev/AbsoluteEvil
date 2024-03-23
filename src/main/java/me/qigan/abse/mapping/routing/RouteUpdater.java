package me.qigan.abse.mapping.routing;

import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.mapping.Room;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RouteUpdater {
    public static List<AddressedData<BlockPos, Color>> path = new ArrayList<>();
    public static List<AddressedData<BlockPos, Color>> outlines = new ArrayList<>();
    public static List<AddressedData<BlockPos, String>> comments = new ArrayList<>();

    @SubscribeEvent
    void render(RenderWorldLastEvent e) {
        if (Minecraft.getMinecraft().theWorld == null) return;
        if (path.size() > 0 && Index.MAIN_CFG.getBoolVal("remap_path")) {
            BlockPos startPos = path.get(0).getNamespace();
            Esp.autoBox3D(startPos.add(0, -1, 0), Color.green, 2f, true);
            Esp.renderTextInWorld("start", startPos, Color.green.getRGB(), 1d, e.partialTicks);
            BlockPos endPos = path.get(path.size()-1).getNamespace();
            Esp.autoBox3D(endPos.add(0, -1, 0), Color.red, 2f, true);
            Esp.renderTextInWorld("end", endPos, Color.red.getRGB(), 1d, e.partialTicks);

            BlockPos pre = startPos;
            for (int i = 1; i < path.size(); i++) {
                AddressedData<BlockPos, Color> pt = path.get(i);
                Esp.drawTracer(pre.getX()+0.5d, pre.getY(), pre.getZ()+0.5d,
                        pt.getNamespace().getX()+0.5d, pt.getNamespace().getY(), pt.getNamespace().getZ()+0.5d,
                        pt.getObject(), 3f, false);
                pre = pt.getNamespace();
            }
        }
        if (Index.MAIN_CFG.getBoolVal("remap_targets")) {
            for (AddressedData<BlockPos, Color> block : outlines) {
                Esp.autoBox3D(block.getNamespace(), block.getObject(), 4f, false);
            }
        }
        if (Index.MAIN_CFG.getBoolVal("remap_comments")) {
            for (AddressedData<BlockPos, String> cmt : comments) {
                Esp.renderTextInWorld(cmt.getObject(), cmt.getNamespace(), 0xFFFFFF, 1d, e.partialTicks);
            }
        }
    }

    @SubscribeEvent
    void load(WorldEvent.Load e) {
        path = new ArrayList<>();
        comments = new ArrayList<>();
        outlines = new ArrayList<>();
    }

    public static void update(Route route, Room room) {
        path = new ArrayList<>();
        comments = new ArrayList<>();
        outlines = new ArrayList<>();

        for (AddressedData<BlockPos, Color> point : route.getPath()) path.add(new AddressedData<>(room.transformInnerCoordinate(point.getNamespace()), point.getObject()));
        for (AddressedData<BlockPos, Color> point : route.getOutlines()) outlines.add(new AddressedData<>(room.transformInnerCoordinate(point.getNamespace()), point.getObject()));
        for (AddressedData<BlockPos, String> point : route.getComments()) comments.add(new AddressedData<>(room.transformInnerCoordinate(point.getNamespace()), point.getObject()));
    }
}
