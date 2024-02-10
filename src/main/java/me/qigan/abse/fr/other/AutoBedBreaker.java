package me.qigan.abse.fr.other;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.DangerousModule;
import me.qigan.abse.crp.MainWrapper;
import me.qigan.abse.crp.Module;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

@DangerousModule
public class AutoBedBreaker extends Module {

    @SubscribeEvent
    void renderWorld(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.START) return;
        if (!isEnabled() || !MainWrapper.Keybinds.blockBreaker.isKeyDown()) return;
        BlockPos coord = getBP();
        if (coord == null) return;
        Minecraft.getMinecraft().thePlayer.swingItem();
        Minecraft.getMinecraft().playerController.onPlayerDamageBlock(coord,
                EnumFacing.getFacingFromVector(Minecraft.getMinecraft().thePlayer.cameraYaw,
                        Minecraft.getMinecraft().thePlayer.cameraPitch, 0f));
    }

    private BlockPos getBP() {
        BlockPos bpBase = new BlockPos(Minecraft.getMinecraft().thePlayer.posX,
                Minecraft.getMinecraft().thePlayer.posY,
                Minecraft.getMinecraft().thePlayer.posZ);
        double paraDist = Index.MAIN_CFG.getDoubleVal("abbbr_dist");
        for (double x = bpBase.getX()-paraDist; x <= bpBase.getX()+paraDist; x++) {
            for (double y = bpBase.getY()-paraDist; y <= bpBase.getY()+paraDist; y++) {
                for (double z = bpBase.getZ()-paraDist; z <= bpBase.getZ()+paraDist; z++) {
                    BlockPos semiBp = new BlockPos(x, y, z);
                    IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(semiBp);
                    if (blockState == null || blockState.getBlock() == Blocks.air) continue;
                    if (blockState.getBlock() == Blocks.bed && semiBp.distanceSq(semiBp) <= paraDist * paraDist) {
                        return semiBp;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String id() {
        return "abbbr";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("abbbr_dist", "Distance", ValType.DOUBLE_NUMBER, "3"));
        return list;
    }

    @Override
    public String fname() {
        return "Auto be d breaker";
    }

    @Override
    public String renderName() {
        return (MainWrapper.Keybinds.blockBreaker.isKeyDown() ? "\u00A76" : "\u00A7a") + super.renderName();
    }

    @Override
    public String description() {
        return "Breaks beds for you";
    }
}
