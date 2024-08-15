package me.qigan.abse.crp;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.ovr.ExtendedController;
import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.fr.exc.TickTasks;
import me.qigan.abse.mapping.MappingController;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.pathing.Path;
import me.qigan.abse.sync.Sync;

import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

@AutoDisable
@DangerousModule
public class Experimental extends Module implements EDLogic {

    @Override
    public String id() {
        return "exptl";
    }

    @Override
    public Specification category() {
        return Specification.SPECIAL;
    }

    @Override
    public String fname() {
        char[] str = "Experimental".toCharArray();
        String nstr = "";
        for (int i = 0; i < str.length; i++) {
            nstr += (i % 2 == 0) ? ("\u00A7e" + str[i]) : ("\u00A77" + str[i]);
        }
        return nstr;
    }

    @SubscribeEvent
    void tick(RenderWorldLastEvent e) {
        if (!isEnabled()) return;

        Esp.autoFilledBox3D(new BlockPos(5, 5, 5), new Color(255, 0, 255, 155), 2f, false);

        if (Minecraft.getMinecraft().objectMouseOver.getBlockPos() != null)
            Esp.autoBox3D(Minecraft.getMinecraft().objectMouseOver.getBlockPos(), Color.cyan, 2f, true);
        if (Minecraft.getMinecraft().objectMouseOver.entityHit != null)
            Esp.autoBox3D(Minecraft.getMinecraft().objectMouseOver.entityHit, Color.cyan, 2f, true);
    }

    @SubscribeEvent
    void rend(RenderGameOverlayEvent.Text e) {
        if (!isEnabled()) return;

        try {
            Esp.drawCenteredString(Index.MOVEMENT_CONTROLLER.getPath().getPosPath().size() + " : " + Index.MOVEMENT_CONTROLLER.isPaused(), 400, 200, 0xFFFFFF, S2Dtype.DEFAULT);
        } catch (Exception ex) {

        }
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("exptl_but1", "Routing", ValType.BUTTON, (Runnable) () -> {
            if (isEnabled()) Index.MOVEMENT_CONTROLLER.go(new Path(Sync.playerPosAsBlockPos(), new BlockPos(10, 9, 7)).build());
        }));
        list.add(new SetsData<>("exptl_but2", "Mapping", ValType.BUTTON, (Runnable) () -> {
            if (isEnabled()) {
                MappingController.debug.clear();
            }
        }));
        return list;
    }

    @Override
    public String description() {
        return "Being used for testing some crazy stuff";
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        Index.MOVEMENT_CONTROLLER.stop();
    }
}
