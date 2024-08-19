package me.qigan.abse.pathing;

import me.qigan.abse.config.AddressedData;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MovementController {
    private Path path = null;
    private boolean paused = true;

    private boolean continuous = false;
    private BlockPos prefFinish = null;

    public Queue<Path> queuePath = new LinkedList<>();


    public int progress = 0;

    public MovementController() {

    }

    @SubscribeEvent
    void gui(GuiScreenEvent.InitGuiEvent e) {
        stop();
    }

    @SubscribeEvent
    void onWorldRender(RenderWorldLastEvent e) {
        try {
            if (path != null && !paused) {
                AddressedData<BlockPos, Integer> data = path.getPosPath().get(progress);
                BlockPos cur = Sync.playerPosAsBlockPos();
                BlockPos next = data.getNamespace();
                if (Minecraft.getMinecraft().thePlayer.getDistance(path.to.getX(), path.to.getY(), path.to.getZ()) < 1d) {
                    stop();
                    return;
                }
                if (progress == path.getPosPath().size()) return;
                if (Minecraft.getMinecraft().thePlayer.getDistanceSq(next) < 2d && Sync.playerPosAsBlockPos().getY() >= next.getY()) next(data.getObject());
                EntityPlayer staticPlayer = Minecraft.getMinecraft().thePlayer;
                if (next.getY() - cur.getY() > 0 && staticPlayer.getDistance(next.getX()+0.5d, next.getY()-1, next.getZ()+0.5d) < 0.85d)
                    ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), 2);
                double distHor = Math.sqrt(Math.pow(staticPlayer.posX-next.getX(), 2) + Math.pow(staticPlayer.posZ-next.getZ(), 2));

                double dx = next.getX() + 0.5d - staticPlayer.posX;
                double dz = next.getZ() + 0.5d - staticPlayer.posZ;

                if (distHor > data.getObject()*3) {
                    path = new Path(Sync.playerPosAsBlockPos(), path.to).build();
                    progress = 0;
                    return;
                }

                Float[] rotations = Utils.getRotationsTo(dx, 0, dz, new float[]{staticPlayer.rotationYaw, staticPlayer.rotationPitch});
                rotations[1] = null;
                SmoothAimControl.set(rotations, 1, 20, Math.min(11, 18));
                //Minecraft.getMinecraft().thePlayer.rotationYaw = rotations[0];
                if (Math.abs(rotations[0] - Minecraft.getMinecraft().thePlayer.rotationYawHead) < 30d)
                    ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), 2);
                if (Math.abs(rotations[0] - Minecraft.getMinecraft().thePlayer.rotationYawHead) > 80d)
                    ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), 2);
                if (path.sprint && data.getObject() > 1) ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), 2);

                if (continuous && progress > path.getPosPath().size()-10 && queuePath.size() < 1) {
                    Path newPath = new Path(path.to, this.prefFinish).copyParam(path).build();
                    if (Utils.compare(path.to, newPath.to) || Utils.compare(newPath.from, newPath.to) || newPath.getPosPath().size() == 0) {
                        continuous = false;
                        return;
                    }
                    queuePath.add(newPath);
                }


                Esp.autoBox3D(path.from, Color.green, 2f, true);
                Esp.autoBox3D(path.to, Color.red, 2f, true);
                List<Point3d> points = new ArrayList<>();
                for (AddressedData<BlockPos, Integer> pos : path.getPosPath()) {
                    Esp.renderTextInWorld(pos.getObject()+"",pos.getNamespace(), 0xFFFFFF, 2f, e.partialTicks);
                    points.add(new Point3d(pos.getNamespace().getX() + 0.5, pos.getNamespace().getY() + 1, pos.getNamespace().getZ() + 0.5));
                }
                Esp.autoBox3D(data.getNamespace(), Color.cyan, 4f, false);
                Esp.drawAsSingleLine(points, Color.cyan, 4f, false);
            }
        } catch (Exception ignored) {ignored.printStackTrace();}
    }

    private BlockPos getClosest() {
        double dist = 255;
        BlockPos pos = null;
        for (AddressedData<BlockPos, Integer> s : path.getPosPath()) {
            double subDist = Minecraft.getMinecraft().thePlayer.getDistance(s.getNamespace().getX(), s.getNamespace().getY(), s.getNamespace().getZ());
            if (subDist < dist) {
                pos = s.getNamespace();
                dist = subDist;
            }
        }
        return pos;
    }

    private void next(int step) {
        BlockPos cur = path.getPosPath().get(progress).getNamespace();
        for (int i = progress; i < progress+step && i < path.getPosPath().size(); i++) {
            AddressedData<BlockPos, Integer> pos = path.getPosPath().get(i);
            if (cur.getY() < pos.getNamespace().getY()) {
                progress = i;
                return;
            }
            if (pos.getObject() < step) {
                progress = i;
                return;
            }
        }
        progress = (Math.min(progress+step, path.getPosPath().size()-1));
    }

    public void continuous(BlockPos absoluteFinish) {
        this.prefFinish = absoluteFinish;
        this.continuous = true;
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
        this.path = null;
        this.progress = 0;
        if (continuous) {
            go(queuePath.poll());
        }
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
