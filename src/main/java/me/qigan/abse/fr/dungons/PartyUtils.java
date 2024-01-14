package me.qigan.abse.fr.dungons;

import javafx.util.Pair;
import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.dungons.exc.CustomPLBuildFailedException;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartyUtils extends Module {
    private static int scanTick = 0;

    private static final Map<String, Integer> party = new HashMap<>();
    public static final Map<String, Character> classReg = new HashMap<>();

    @SubscribeEvent
    void onChat(ClientChatReceivedEvent e) {
        if (!isEnabled()) return;
        String msg = Utils.cleanSB(e.message.getFormattedText());
        if (msg.equalsIgnoreCase("Starting in 1 second.")) {
            for (String ln : Utils.getScoreboard()) {
                String str = ln.substring(0, 3);
                switch (str) {
                    case "[A]":
                        classReg.put(ln.substring(4).replace(" ", ""), 'A');
                        break;
                    case "[B]":
                        classReg.put(ln.substring(4).replace(" ", ""), 'B');
                        break;
                    case "[M]":
                        classReg.put(ln.substring(4).replace(" ", ""), 'M');
                        break;
                    case "[H]":
                        classReg.put(ln.substring(4).replace(" ", ""), 'H');
                        break;
                    case "[T]":
                        classReg.put(ln.substring(4).replace(" ", ""), 'T');
                        break;
                }
            }
        }
        else if (msg.startsWith("Party Finder > ") && msg.contains("joined")) {
            String pname = msg.substring(15);
            int i = 0;
            while (pname.charAt(i) != ' ') i++;
            String lastStr = pname.substring(i+28);
            pname = pname.substring(0, i);

            classReg.put(pname, classOf(lastStr));

            if (pname.equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.getName())) return;
            IChatComponent message = new ChatComponentText("\u00A7aActions for " + pname + ": ");
            message.appendSibling(new ChatComponentText("\u00A72[View]").setChatStyle(
                    new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pv " + pname))
                            .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("\u00A7r\u00A7bLemme see your 3 secrets per run")))));
            message.appendSibling(new ChatComponentText(" "));
            message.appendSibling(new ChatComponentText("\u00A7c[Kick]").setChatStyle(
                    new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p kick " + pname))
                            .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("\u00A7r\u00A7cGet out of here!")))));

            new Thread(() -> {
                Minecraft.getMinecraft().thePlayer.addChatMessage(message);
            }).start();
        } else if (msg.contains("Party Members")) {
            scanTick = Index.MAIN_CFG.getIntVal("patils_st")*2;
        }

        //if (ln.length() > 2) is a check of name reality (3+ symbols)
        if (scanTick > 0) {
            int c = 0;
            for (char chr : e.message.getFormattedText().toCharArray()) {
                if (chr == '\u25CF') c++;
            }
            if (msg.startsWith("Party Leader: ")) {
                msg = msg.substring(14);
                if (c == 1) party.put(format(msg).replace(" ", ""), 3);
                else for (String ln : format(msg).split(" ")) if (ln.length() > 2) party.put(ln, 3);
            } else if (msg.startsWith("Party Moderators: ")) {
                msg = msg.substring(18);
                if (c == 1) party.put(format(msg).replace(" ", ""), 2);
                else for (String ln : format(msg).split(" ")) if (ln.length() > 2) party.put(ln, 2);
            } else if (msg.startsWith("Party Members: ")) {
                msg = msg.substring(15);
                if (c == 1) party.put(format(msg).replace(" ", ""), 1);
                else for (String ln : format(msg).split(" ")) if (ln.length() > 2) party.put(ln, 1);
            }
        }
    }

    private char classOf(String str) {
        if (str.startsWith("Archer")) return 'A';
        else if (str.startsWith("Berserk")) return 'B';
        else if (str.startsWith("Mage")) return 'M';
        else if (str.startsWith("Healer")) return 'H';
        else if (str.startsWith("Tank")) return 'T';
        else return 'U';
    }

    private String format(String in) {
        return in.replace("[VIP]", "").replace("[VIP+]", "")
                .replace("[MVP]", "").replace("[MVP+]", "").replace("[MVP++]", "")
                .replace("[ADMIN]", "").replace("[HELPER]", "").replace("[YOUTUBE]", "");
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (!isEnabled()) return;
        if (scanTick-1 == 0) {
            try {
                int yourPrio = party.get(Minecraft.getMinecraft().thePlayer.getName());
                List<IChatComponent> seq = new ArrayList<>();
                for (Map.Entry<String, Integer> mem : party.entrySet()) {
                    if (mem.getValue() == 3)
                        seq.add(0, new ChatComponentText("\u00A72" + mem.getKey() + "'s party: "));
                    ChatComponentText text = new ChatComponentText("\u00A7a[" + classReg.getOrDefault(mem.getKey(), 'U') + "] " + mem.getKey() + " ");
                    text.appendSibling(new ChatComponentText("\u00A72[View]").setChatStyle(
                            new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pv " + mem.getKey()))
                                    .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("\u00A7r\u00A7bLemme see your 3 secrets per run")))));
                    text.appendSibling(new ChatComponentText(" "));
                    if (yourPrio > mem.getValue()) {
                        text.appendSibling(new ChatComponentText("\u00A7c[Kick]").setChatStyle(
                                new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p kick " + mem.getKey()))
                                        .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("\u00A7r\u00A7cGet out of here!")))));
                    }
                    if (yourPrio == 3) {
                        text.appendSibling(new ChatComponentText(" "));
                        text.appendSibling(new ChatComponentText("\u00A73[Promote]").setChatStyle(
                                new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p promote " + mem.getKey()))
                                        .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("\u00A7r\u00A73Now you are the law!")))));
                        text.appendSibling(new ChatComponentText(" "));
                        text.appendSibling(new ChatComponentText("\u00A7d[Transfer]").setChatStyle(
                                new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p transfer " + mem.getKey()))
                                        .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("\u00A7r\u00A7d\u00A7oIs it time to lead or is it time to die?\n" + "Time to raise hell or walk on by?")))));
                    }
                    seq.add(text);
                }

                for (IChatComponent comp : seq) Minecraft.getMinecraft().thePlayer.addChatMessage(comp);
            } catch (RuntimeException ex) {
                if (Index.isSafe()) {
                    for (Map.Entry<String, Integer> mem : party.entrySet())
                        System.out.println(mem.getKey() + " - " + mem.getValue());
                    System.out.println("idk lol");
                    for (Map.Entry<String, Character> mem : classReg.entrySet())
                        System.out.println(mem.getKey() + " - " + mem.getValue());
                    new CustomPLBuildFailedException(ex).printStackTrace();
                }
                else throw new CustomPLBuildFailedException();
            }
            party.clear();
        }
        if (scanTick > 0) scanTick--;
    }

    @Override
    public String id() {
        return "patils";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("patils_st", "Scan tick", ValType.NUMBER, "20"));
        return list;
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
