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
    public boolean sprint = false;


    public int progress = 0;

    public MovementController() {

    }

    @SubscribeEvent
    void gui(GuiScreenEvent.InitGuiEvent e) {
        stop();
    }

    @SubscribeEvent
    void onWorldRender(RenderWorldLastEvent e) {
        int step = 1;
        try {
            if (path != null && !paused) {
                BlockPos cur = Sync.playerPosAsBlockPos();
                BlockPos next = path.getPosPath().get(progress);
                if (Utils.compare(cur, path.to)) {
                    stop();
                    return;
                }
                if (progress == path.getPosPath().size()) return;
                if (Minecraft.getMinecraft().thePlayer.getDistanceSq(next) < 2) next(step);
                EntityPlayer staticPlayer = Minecraft.getMinecraft().thePlayer;
                if (next.getY() - cur.getY() > 0)
                    ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), 2);
                double distHor = Math.sqrt(Math.pow(staticPlayer.posX-next.getX(), 2) + Math.pow(staticPlayer.posZ-next.getZ(), 2));

                double dx = next.getX() + 0.5d - staticPlayer.posX;
                double dz = next.getZ() + 0.5d - staticPlayer.posZ;

                if (distHor > step+2)
                    path = new Path(Sync.playerPosAsBlockPos(), path.to).build();

                Float[] rotations = Utils.getRotationsTo(dx, 0, dz, new float[]{staticPlayer.rotationYaw, staticPlayer.rotationPitch});
                rotations[1] = null;
                SmoothAimControl.set(rotations, 1, 20, Math.min(11, 18));
                //Minecraft.getMinecraft().thePlayer.rotationYaw = rotations[0];
                if (Math.abs(rotations[0] - Minecraft.getMinecraft().thePlayer.rotationYawHead) < 30d)
                    ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), 2);
                if (sprint) ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), 2);
                Esp.autoBox3D(path.from, Color.green, 2f, true);
                Esp.autoBox3D(path.to, Color.red, 2f, true);
                List<Point3d> points = new ArrayList<>();
                for (BlockPos pos : path.getPosPath()) {
                    points.add(new Point3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5));
                }
                Esp.drawAsSingleLine(points, Color.cyan, 4f, false);
            }
        } catch (Exception ignored) {ignored.printStackTrace();}
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

    private void next(int step) {
        BlockPos cur = path.getPosPath().get(progress);
        for (int i = progress; i < progress+step && i < path.getPosPath().size()-1; i++) {
            BlockPos pos = path.getPosPath().get(progress);
            if (cur.getY() < pos.getY()) {
                progress = i;
                return;
            }
        }
        progress = (Math.min(progress+step, path.getPosPath().size()-1));
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
        this.progress = 0;
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
