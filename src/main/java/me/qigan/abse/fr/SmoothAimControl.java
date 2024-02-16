package me.qigan.abse.fr;

import me.qigan.abse.Index;
import me.qigan.abse.fr.cbh.CombatHelperAimRandomize;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SmoothAimControl {
    public static boolean OVERRIDE = false;

    public static float[] aimPoint;
    private static int aimTime = 0;

    private static double speed = 3.5d;

    @SubscribeEvent
    void move(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            if (aimTime > 0) {
                if (aimPoint.length > 1) {
                    double s = speed + CombatHelperAimRandomize.createRandomDouble();
                    Minecraft.getMinecraft().thePlayer.rotationYaw += (aimPoint[0] - Minecraft.getMinecraft().thePlayer.rotationYawHead) * (s / 20);
                    Minecraft.getMinecraft().thePlayer.rotationPitch += (aimPoint[1] - Minecraft.getMinecraft().thePlayer.rotationPitch) * (s / 20);
                }
                aimTime--;
            }
        }
    }

    public static void set(final float[] angles, final int time) {
        aimPoint = angles;
        aimTime = time;
    }
}
