package me.qigan.abse.pathing;

import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.fr.exc.SmoothAimControl;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.vecmath.Point3d;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovementController {
    private Path path = null;
    private boolean paused = true;
    public boolean sprint = true;

    public MovementController() {

    }

    @SubscribeEvent
    void gui(GuiScreenEvent.InitGuiEvent e) {
        stop();
    }

    @SubscribeEvent
    void onWorldRender(RenderWorldLastEvent e) {
        if (path != null && !paused) {
            BlockPos cur = getClosest();
            BlockPos next = getNext(cur);
            if (Utils.compare(cur, path.to)) path = null;
            if (next == null) return;
            EntityPlayer staticPlayer = Minecraft.getMinecraft().thePlayer;
            if (next.getY()-cur.getY() > 0)
                ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), 2);

            double dx = next.getX() + 0.5d - staticPlayer.posX;
            double dz = next.getZ() + 0.5 - staticPlayer.posZ;

            double dist = staticPlayer.getDistance(next.getX() + 0.5d, next.getY() + 0.5d, next.getZ() + 0.d);
            if (dist > 3)
                path = new Path(Sync.playerPosAsBlockPos(), path.to).build();

            Float[] rotations = Utils.getRotationsTo(dx, 0, dz, new float[]{staticPlayer.rotationYaw, staticPlayer.rotationPitch});
            rotations[1] = null;
            rotations[0] = dist > 0.75d ? rotations[0] : null;
            SmoothAimControl.set(rotations, 1, 20, 6*dist);
            ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), 2);
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), sprint);

            Esp.autoBox3D(path.from, Color.green, 2f, true);
            Esp.autoBox3D(path.to, Color.red, 2f, true);
            List<Point3d> points = new ArrayList<>();
            for (BlockPos pos : path.getPosPath()) {
                points.add(new Point3d(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5));
            }
            Esp.drawAsSingleLine(points, Color.cyan, 4f, false);
        }
    }

    private BlockPos getClosest() {
        double dist = 255;
        BlockPos pos = null;
        for (BlockPos s : path.getPosPath()) {
            double subDist = Minecraft.getMinecraft().thePlayer.getDistance(s.getX(), s.getY(), s.getZ());
            if (subDist < dist) {
                pos = s;
                dist = subDist;
            }
        }
        return pos;
    }

    private BlockPos getNext(BlockPos pos) {
        for (int i = 0; i < path.getPosPath().size(); i++) {
            if (path.getPosPath().get(i) == pos) {
                if (i + 1 < path.getPosPath().size()) return path.getPosPath().get(i+1);
            }
        }
        return null;
    }

    public void go(Path path) {
        this.path = path;
        start();
    }

    public void start() {
        this.paused = false;
    }

    public void stop() {
        this.paused = true;
    }

    public void pause() {
        this.paused=!this.paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public Path getPath() {
        return path;
    }
}
