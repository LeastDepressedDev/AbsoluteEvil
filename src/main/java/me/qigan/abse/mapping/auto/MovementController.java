package me.qigan.abse.mapping.auto;

import me.qigan.abse.fr.exc.SmoothAimControl;
import me.qigan.abse.mapping.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MovementController {
    private Path path = null;
    private boolean paused = false;

    public MovementController() {

    }

    @SubscribeEvent
    void onWorldRender(RenderWorldLastEvent e) {
        if (path != null && !paused) {
            SmoothAimControl.set(new Float[]{-1f, 1f}, 1);
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

    public boolean isPaused() {
        return paused;
    }

    public Path getPath() {
        return path;
    }
}
