package me.qigan.abse.fr.cbh;

import me.qigan.abse.Holder;
import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.MainWrapper;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.Debug;
import me.qigan.abse.fr.exc.SmoothAimControl;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CombatHelperAim extends Module {
    private static int skip = 0;
    private static int atkTick = 0;

    public static boolean BREAK_TOGGLE = false;

    public static Target prim;

    public static class Target {
        public final Entity ref;
        public final double theta;
        public final double zeta;

        public boolean lockTheta = true;
        public boolean lockZeta = true;

        public Target(Entity ref, double theta, double zeta) {
            this.ref = ref;
            this.theta = theta;
            this.zeta = zeta;
        }

        public Target unlockTheta() {
            this.lockTheta = false;
            return this;
        }

        public Target unlockZeta() {
            this.lockZeta = false;
            return this;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    void overLay(RenderGameOverlayEvent.Pre e) {
        if (!isEnabled() || Index.MAIN_CFG.getBoolVal("cbh_hide_target")) return;
        if (e.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS && prim != null) {
            float fYaw = Minecraft.getMinecraft().thePlayer.rotationYawHead;
            float fPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;

            double dw = e.resolution.getScaledWidth()/180d;
            double dh = e.resolution.getScaledHeight()/90d;

            int x = (int) ((e.resolution.getScaledWidth()/2f)+(dw*((prim.theta-fYaw))));
            int y = (int) ((e.resolution.getScaledHeight()/2f)+(dh*(prim.zeta-fPitch)));

            GL11.glPushMatrix();
            GlStateManager.enableBlend();

            GL11.glTranslatef(x, y, 0);
            Esp.drawModalRectWithCustomSizedTexture(-4, -4, 8, 8, 0, 0, 8, 8,
                    new ResourceLocation("abse", "target.png"), new Color(0xFFFFFF));
            GL11.glPopMatrix();
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.icons);
        }
    }

    @SubscribeEvent
    void debugText(RenderGameOverlayEvent.Text e) {
        if (!isEnabled() || !Debug.GENERAL) return;
        EntityPlayerSP mplayer = Minecraft.getMinecraft().thePlayer;
        List<String> tr = new ArrayList<>();
        tr.add("\u00a7erealY: " + mplayer.rotationYawHead);
        tr.add("\u00a7erealP: " + mplayer.rotationPitch);
        tr.add("\u00a7eTarget: " + ((prim == null || prim.ref == null) ? "None" : prim.ref.getName()));
        if (prim != null) {
            tr.add("\u00a7etargetTheta: " + prim.theta);
            tr.add("\u00a7etargetZeta: " + prim.zeta);
        }
        Esp.drawAllignedTextList(tr, e.resolution.getScaledWidth()/2+120, e.resolution.getScaledHeight()/2+10, false, e.resolution, S2Dtype.SHADOW);
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (Index.MAIN_CFG.getBoolVal("cbh_aim_tbkm") &&
                MainWrapper.Keybinds.aimBreak.isPressed()) BREAK_TOGGLE=!BREAK_TOGGLE;
        if (Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() &&
                !(Index.MAIN_CFG.getBoolVal("cbh_aim_tbkm") ? BREAK_TOGGLE : MainWrapper.Keybinds.aimBreak.isKeyDown())) atkTick = Index.MAIN_CFG.getIntVal("cbh_atk");
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return;
        if (SmoothAimControl.OVERRIDE || !isEnabled()) return;
        if (skip == 0) {
            if (atkTick == 0) {
                prim = null;
                return;
            }

            boolean advcState = Holder.quickFind("cbh_aim_adv").isEnabled();
            boolean randState = Holder.quickFind("cbh_aim_rand").isEnabled();
            boolean selState = Holder.quickFind("cbh_aim_sel").isEnabled();
            int ln = -1;
            if (selState && Minecraft.getMinecraft().thePlayer.getEquipmentInSlot(4) != null) {
                ln = Utils.getItemColor(Minecraft.getMinecraft().thePlayer.getEquipmentInSlot(4));
            }

            float fYaw = Minecraft.getMinecraft().thePlayer.rotationYawHead;
            float fPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
            skip = Index.MAIN_CFG.getIntVal("cbh_tickskip");
            Target primary = null;

            double distLim = Index.MAIN_CFG.getDoubleVal("cbh_dist");
            double s = Index.MAIN_CFG.getDoubleVal("cbh_speed");

                for (Entity ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    if (ent.getName() == Minecraft.getMinecraft().thePlayer.getName()) continue;
                    if (ent instanceof EntityPlayer || Debug.GENERAL) {
                        if (selState && !Debug.GENERAL) {
                            EntityPlayer player = (EntityPlayer) ent;
                            if (Index.MAIN_CFG.getBoolVal("cbh_aim_sel_team") && player.getEquipmentInSlot(4) != null) {
                                if (player.getEquipmentInSlot(4).getItem() == Items.leather_helmet) {
                                    int prec = Utils.getItemColor(player.getEquipmentInSlot(4));
                                    if (prec == ln) continue;
                                }
                            }
                        }
                        double f = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(ent);

                        if (!ent.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) && f < distLim && (primary == null || (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(ent) && f < primary.ref.getDistanceToEntity(Minecraft.getMinecraft().thePlayer)))) {
                            final float[] rotations = Utils.getRotationsTo(Minecraft.getMinecraft().thePlayer, ent);
                            primary = new Target(ent, rotations[0], rotations[1]);
                        }
                    }
                }

                prim = primary;

            if (prim != null) {
                double d = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(prim.ref);
                if (d > Index.MAIN_CFG.getDoubleVal("cbh_aim_distbp")) {
                    if (randState) s+=CombatHelperAimRandomize.createRandomDouble();
                    double v = (prim.theta - fYaw) * (s / (10 * d));
                    double u = (prim.zeta - fPitch) * (s / (10 * d));
                    if (advcState) {
                        if (prim.lockTheta) Minecraft.getMinecraft().thePlayer.rotationYaw += (Math.abs(prim.theta - fYaw) < Index.MAIN_CFG.getDoubleVal("cbh_aim_px")) ? 0 : v;
                        if (prim.lockZeta) Minecraft.getMinecraft().thePlayer.rotationPitch += (Math.abs(prim.zeta - fPitch) < Index.MAIN_CFG.getDoubleVal("cbh_aim_py")) ? 0 : u;
                    } else {
                        if (prim.lockTheta) Minecraft.getMinecraft().thePlayer.rotationYaw += v;
                        if (prim.lockZeta) Minecraft.getMinecraft().thePlayer.rotationPitch += u;
                    }
                }
            }

            atkTick--;
        } else {
            skip--;
        }
    }

    @Override
    public String id() {
        return "cbh_aim";
    }

    @Override
    public String fname() {
        return "Combat helper[aim]";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("cbh_speed", "Speed Modifier", ValType.DOUBLE_NUMBER, "4"));
        list.add(new SetsData<>("cbh_dist", "Distance", ValType.DOUBLE_NUMBER, "5"));
        list.add(new SetsData<>("cbh_tickskip", "Tick skip[don't change if you are not sure]", ValType.NUMBER, "1"));
        list.add(new SetsData<>("cbh_atk", "Attack tick mod", ValType.NUMBER, "20"));
        list.add(new SetsData<>("cbh_hide_target", "Hide aim cursor", ValType.BOOLEAN, "false"));
        return list;
    }

    @Override
    public String description() {
        return "Complicated thing as a massive problem";
    }
}
