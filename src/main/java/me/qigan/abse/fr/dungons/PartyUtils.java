package me.qigan.abse.fr.dungons;

import me.qigan.abse.crp.Module;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PartyUtils extends Module {

    @SubscribeEvent
    void onChat(ClientChatReceivedEvent e) {
        if (!isEnabled()) return;
        String msg = Utils.cleanSB(e.message.getFormattedText());
        if (msg.startsWith("Party Finder > ") && msg.contains("joined")) {
            String pname = msg.substring(15);
            int i = 0;
            while (pname.charAt(i) != ' ') i++;
            pname = pname.substring(0, i);

            IChatComponent message = new ChatComponentText("\u00A7aActions: ");
            message.appendSibling(new ChatComponentText("\u00A72[View]").setChatStyle(
                    new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pv " + pname))));
            message.appendSibling(new ChatComponentText(" "));
            message.appendSibling(new ChatComponentText("\u00A7c[Kick]").setChatStyle(
                    new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p kick " + pname))));

            new Thread(() -> {
                Minecraft.getMinecraft().thePlayer.addChatMessage(message);
            }).start();
        }
    }

    @Override
    public String id() {
        return "patils";
    }

    @Override
    public String fname() {
        return "Party utils";
    }

    @Override
    public String description() {
        return "Easy party management";
    }
}
