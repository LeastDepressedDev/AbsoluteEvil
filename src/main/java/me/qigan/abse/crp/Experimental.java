package me.qigan.abse.crp;

import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.EDLogic;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.GhostUtils;
import me.qigan.abse.fr.SmoothAimControl;
import me.qigan.abse.fr.cbh.CombatHelperAim;
import me.qigan.abse.fr.dungons.DeviceIssue;
import me.qigan.abse.gui.overlay.ImportantChatOVR;
import me.qigan.abse.packets.PacketEvent;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

public class Experimental extends Module implements EDLogic {

    @Override
    public String id() {
        return "exptl";
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

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("exp_button_1", "Test", ValType.BUTTON, (Runnable) () -> {
            for (int dx = 0; dx < 4; dx++) {
                for (int dy = 0; dy < 4; dy++) {
                    if (Minecraft.getMinecraft().theWorld.getBlockState(DeviceIssue.BLOCK_SPAWN_SS_CONST[0].add(0, dy, dx)).getBlock() == Blocks.sea_lantern) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7cTRUE LOL"));
                    }
                }
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
        SmoothAimControl.OVERRIDE = true;
    }

    @Override
    public void onDisable() {
        SmoothAimControl.OVERRIDE = false;
    }
}
