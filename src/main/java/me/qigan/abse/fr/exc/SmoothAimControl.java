package me.qigan.abse.fr.exc;

import me.qigan.abse.Index;
import me.qigan.abse.fr.cbh.CombatHelperAimRandomize;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SmoothAimControl {
    public static boolean OVERRIDE = false;

    public static float[] aimPoint;
    private static int aimTime = 0;

    private static double speed = 3.5d;

    private static double devideCF = 5;

    @SubscribeEvent
    void move(TickEvent.ClientTickEvent e) {
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return;
        if (e.phase == TickEvent.Phase.END) {
            if (aimTime > 0) {
                if (aimPoint.length > 1) {
                    double s = speed + CombatHelperAimRandomize.createRandomDouble();
                    Minecraft.getMinecraft().thePlayer.rotationYaw += (aimPoint[0] - Minecraft.getMinecraft().thePlayer.rotationYawHead) * (s / devideCF);
                    Minecraft.getMinecraft().thePlayer.rotationPitch += (aimPoint[1] - Minecraft.getMinecraft().thePlayer.rotationPitch) * (s / devideCF);
                }
                aimTime--;
            }
        }
    }

    public static void set(final float[] angles, final int time, final double cf, final double ovrSpeed) {
        aimPoint = angles;
        aimTime = time;
        devideCF = cf;
        speed = ovrSpeed;
    }

    public static void set(final float[] angles, final int time) {
        set(angles, time, 20, 3.5d);
    }
}
