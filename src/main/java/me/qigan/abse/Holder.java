package me.qigan.abse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.qigan.abse.crp.Experimental;
import me.qigan.abse.crp.Module;
import me.qigan.abse.crp.SafeMode;
import me.qigan.abse.fr.*;
import me.qigan.abse.fr.exc.PacketBreak;
import me.qigan.abse.fr.kuudra.AutoRefillPearls;
import me.qigan.abse.fr.kuudra.SuplyPearl;
import me.qigan.abse.fr.other.AutoBridging;
import me.qigan.abse.fr.cbh.*;
import me.qigan.abse.fr.dungons.*;
import me.qigan.abse.fr.other.*;
import me.qigan.abse.fr.qol.*;
import me.qigan.abse.gui.overlay.ImportantChatOVR;
import net.minecraftforge.common.MinecraftForge;

public class Holder {
	public static Module HOLDER;
	public static List<Module> MRL = new ArrayList<Module>();

	public static Map<String, Integer> linker = new HashMap<>();
	
	public static void link() {
		register(new Experimental());
		register(new SafeMode());
		register(new FunnyAddons());
		register(new LagTracker());
		register(new M7Route());
		register(new Hud());
		register(new AutoHarp());
		register(new NFBA());
		register(new PacketBreak());
		register(new GyroAddons());
		register(new BloodCamp());
		register(new StarredMobs());
		register(new Debug());
		register(new PartyUtils());
		register(new MelodyShit());
		register(new GhostBlocks());
		register(new PickaxePlus());
		register(new SprayCheck());
		register(new M7Visuals());
		register(new M7Dragons());
		register(new SFUtils());
		register(new GhostUtils());
		register(new AutoDebuff());
		register(new TemporaryGb());
		register(new LeapShortcut());
		register(new AutoBlaze());
		register(new InvTracker());
		register(new DeviceIssue());
		register(new AutoLevers());
		register(new AutoLeapDungeon());
		register(new SoundOverride());
		register(new DungeonRngSound());
		register(new SuplyPearl());
		register(new AutoRefillPearls());
		register(new Swastika());
		register(new BWEsp());
		register(new BowAimEsp());
		register(new BWTeamTracker());
		register(new CombatHelperAim());
		register(new CombatHelperAdvancedAimControls());
		register(new CombatHelperAimRandomize());
		register(new CombatHelperAimSelector());
		register(new CombatHelperAimShake());
		register(new CombatHelperSR());
		register(new FireballDetector());
		register(new AutoBridging());
		register(new BowPracticeMod());
		register(new ImportantChatOVR());
		register(new InguiDisplay());
		register(new AutoBedBreaker());
	}
	
	public static void register(Module mod) {
		try {
			mod.onRegister();
		} catch (Exception ex) {
			System.out.println(mod.id() + " - " + mod.fname());
			System.out.println("register script is incorrect: SKIPPING");
			return;
		}
		linker.put(mod.id(), MRL.size());
		MRL.add(mod);
		MinecraftForge.EVENT_BUS.register(mod);
	}

	public static Module quickFind(String id) {
		Integer lid = linker.getOrDefault(id, null);
		return lid == null ? null : MRL.get(lid);
	}
}
