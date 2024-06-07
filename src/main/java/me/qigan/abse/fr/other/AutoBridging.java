package me.qigan.abse.fr.other;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.MainWrapper;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.sync.Sync;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoBridging extends Module {

    @SubscribeEvent
    void rend(RenderWorldLastEvent e) {
        if (!isEnabled() || !MainWrapper.Keybinds.autoBridging.isKeyDown()) return;
        MovingObjectPosition pos = Minecraft.getMinecraft().objectMouseOver;
        if (Minecraft.getMinecraft().theWorld.getBlockState(Sync.playerPosAsBlockPos().add(0, -1, 0)).getBlock() == Blocks.air &&
                pos.getBlockPos() != null && Minecraft.getMinecraft().theWorld.getBlockState(pos.getBlockPos()) != Blocks.air) {
            if (Minecraft.getMinecraft().theWorld.getBlockState(Sync.playerPosAsBlockPos().add(0, -2, 0))
                    .getBlock() != Blocks.air) {
                ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), 1);
            } else if (pos.sideHit != EnumFacing.DOWN && pos.sideHit != EnumFacing.UP) {
                ClickSimTick.click(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), 1);
            }

        }
        //Esp.autoBox3D(pos.hitVec.xCoord, pos.hitVec.yCoord+0.25, pos.hitVec.zCoord, 0.5, 0.5, Color.cyan, false);
    }

    @Override
    public String id() {
        return "abrig";
    }

    @Override
    public String fname() {
        return "Auto bridging[WIP]";
    }

    @Override
    public String description() {
        return "Automatically do fast bridging.";
    }
}
