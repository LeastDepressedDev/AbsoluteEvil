package me.qigan.abse.crp.ovr;

import me.qigan.abse.Index;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;

public class ExtendedController extends PlayerControllerMP {
    public ExtendedController(Minecraft mcIn, NetHandlerPlayClient p_i45062_2_) {
        super(mcIn, p_i45062_2_);
    }

    @Override
    public float getBlockReachDistance() {
        return (float) Index.MAIN_CFG.getDoubleVal("abs_reach_range");
    }
}
