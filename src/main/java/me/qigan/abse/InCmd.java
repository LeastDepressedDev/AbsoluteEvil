package me.qigan.abse;

import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.Debug;
import me.qigan.abse.fr.exc.PacketBreak;
import me.qigan.abse.fr.qol.GhostBlocks;
import me.qigan.abse.fr.qol.GhostUtils;
import me.qigan.abse.fr.dungons.M7Route;
import me.qigan.abse.fr.mapping.Mapping;
import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.gui.overlay.GuiNotifier;
import me.qigan.abse.gui.inst.MainGui;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class InCmd extends CommandBase{
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("list")) {
				for (Module mdl: Holder.MRL) {
					String color;
					if (mdl.isEnabled()) {
						color = "\u00A7a";
					} else {
						color = "\u00A7c";
					}
					sender.addChatMessage(new ChatComponentText(color + mdl.id() + " - " + mdl.description()));
				}
			} else if (args[0].equalsIgnoreCase("help")) {
				sender.addChatMessage(new ChatComponentText("\u00A7aAbsolute Evil help: "));
				sender.addChatMessage(new ChatComponentText("\u00A7a/abse <module> - manually toggling modules."));
				sender.addChatMessage(new ChatComponentText("\u00A7a/abse help - command for help(xD)."));
				sender.addChatMessage(new ChatComponentText("\u00A7a/abse list - view list of modules."));
				sender.addChatMessage(new ChatComponentText("\u00A7a/abse m7 - set m7 route manually."));
				sender.addChatMessage(new ChatComponentText("\u00A7a/abse test - test shit lol."));
				sender.addChatMessage(new ChatComponentText("\u00A7a/abse cfg - command for manually changing cfg."));
				sender.addChatMessage(new ChatComponentText("\u00A7a/abse gb - ghost block management."));
				sender.addChatMessage(new ChatComponentText("\u00A7a/abse ghosts - ghost utils management."));
			} else if (args[0].equalsIgnoreCase("ghosts")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("reset")) {
						GhostUtils.reset();
						sender.addChatMessage(new ChatComponentText("\u00A7aReset session status."));
					} else {
						sender.addChatMessage(new ChatComponentText("\u00A7cWrong argument!"));
					}
				} else {
					sender.addChatMessage(new ChatComponentText("\u00A7a/abse ghosts reset - reset session ghost status."));
				}
			} else if (args[0].equalsIgnoreCase("m7")) {
				for (M7Route.BBox b : M7Route.bounds) {
					b.run();
				}
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7a[ABSE] Healer route set!"));
			} else if (args[0].equalsIgnoreCase("freeze")) {
				PacketBreak.stun();
			} else if (args[0].equalsIgnoreCase("crash")) {
				Minecraft.getMinecraft().crashed(null);
			} else if (args[0].equalsIgnoreCase("dbg")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("items")) {
						System.out.println(Utils.getSbData(Minecraft.getMinecraft().thePlayer.getHeldItem()).toString());
					} else if (args[1].equalsIgnoreCase("sound")) {
						if (args.length > 2) {
							Minecraft.getMinecraft().thePlayer.playSound(args[2], 1f, 1f);
						} else {
							sender.addChatMessage(new ChatComponentText("\u00A7cPlease select sound!"));
						}
					} else if (args[1].equalsIgnoreCase("al")) {
						Mapping.DEBUG=!Mapping.DEBUG;
					} else if (args[1].equalsIgnoreCase("general") || args[1].equalsIgnoreCase("gen")) {
						Debug.GENERAL=!Debug.GENERAL;
					}
				} else {
					sender.addChatMessage(new ChatComponentText("\u00A7a/abse dbg <items/sound/al>"));
				}
			} else if (args[0].equalsIgnoreCase("test")) {
				GuiNotifier.call("Test", 100, true, 0xFFFFFF);
			} else if (args[0].equalsIgnoreCase("cfg")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("set")) {
						switch (args.length) {
							case 2:
								sender.addChatMessage(new ChatComponentText("\u00A7c <cfg_name> and <value> fields are missing!"));
							case 3:
								sender.addChatMessage(new ChatComponentText("\u00A7c <value> field is missing!"));
							default:
							{
								String str = "";
								for (int i = 3; i < args.length; i++) {
									str += args[i] + " ";
								}
								Index.MAIN_CFG.set(args[2], str);
							}
						}
					} else if (args[1].equalsIgnoreCase("get")) {
						if (args.length > 2) {
							sender.addChatMessage(new ChatComponentText(args[2] + " - " + Index.MAIN_CFG.getStrVal(args[2])));
						} else {
							sender.addChatMessage(new ChatComponentText("\u00A7c <cfg_name> field is missing!"));
						}
					} else {
						sender.addChatMessage(new ChatComponentText("\u00A7c Wrong action"));
					}
				} else {
					sender.addChatMessage(new ChatComponentText("\u00A7a AbsoluteEvil cfg help: "));
					sender.addChatMessage(new ChatComponentText("\u00A7a'/abse cfg set <cfg_name> <value>' to set it."));
					sender.addChatMessage(new ChatComponentText("\u00A7a'/abse cfg get <cfg_name>' to get it."));
				}
			} else if (args[0].equalsIgnoreCase("gb")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("reset")) {
						GhostBlocks.grestore();
						sender.addChatMessage(new ChatComponentText("\u00A7a Ghost blcoks reset!"));
					} else if (args[1].equalsIgnoreCase("add")) {
						sender.addChatMessage(new ChatComponentText("\u00A76 [COMING SOON]"));
					} else {
						sender.addChatMessage(new ChatComponentText("\u00A7c Wrong action!"));
					}
				} else {
					sender.addChatMessage(new ChatComponentText("\u00A7c Ghost blocks shit"));
				}
			} else if (args[0].equalsIgnoreCase("new")) {
				NewMainMenu.queue = true;
			} else {
				for (Module mdl: Holder.MRL) {
					if (args[0].equalsIgnoreCase(mdl.id())) {
						sender.addChatMessage(new ChatComponentText(mdl.isEnabled() ?
								"\u00A7cDisabled " + mdl.id() :
								"\u00A7aEnabled " + mdl.id()));
						Index.MAIN_CFG.toggle(mdl.id());
					}
				}
			}
		} else {
			sender.addChatMessage(new ChatComponentText("\u00A7aAbsoluteEvil " + Index.VERSION + " by qigan"));
			MainGui.queue = true;
		}
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/abse <args>";
	}
	
	@Override
	public String getCommandName() {
		return "abse";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
}
