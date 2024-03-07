package me.qigan.abse.crp;

import me.qigan.abse.Holder;
import me.qigan.abse.InCmd;
import me.qigan.abse.Index;
import me.qigan.abse.ant.LoginScreen;
import me.qigan.abse.config.MuConfig;
import me.qigan.abse.config.PositionConfig;
import me.qigan.abse.crp.ovr.MCMainMenu;
import me.qigan.abse.fr.exc.ClickSimTick;
import me.qigan.abse.fr.exc.SmoothAimControl;
import me.qigan.abse.gui.inst.NewMainMenu;
import me.qigan.abse.gui.overlay.GuiNotifier;
import me.qigan.abse.gui.inst.MainGui;
import me.qigan.abse.packets.PacketHandler;
import me.qigan.abse.sync.SoundUtils;
import me.qigan.abse.sync.Sync;
import me.qigan.abse.sync.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainWrapper {

    public static class Keybinds {
        public static KeyBinding unlimitedRange;
        public static KeyBinding ghostBlocks;
        public static KeyBinding legGhostBlocks;
        public static KeyBinding ghostBlocksReset;
        public static KeyBinding tempGhostBlocks;
        public static KeyBinding ghostChest;
        public static KeyBinding autoBridging;
        public static KeyBinding aimBreak;
        public static KeyBinding blockBreaker;
        public static KeyBinding debuffKey;
        public static KeyBinding ssKey;
    }

    public static LoginScreen ls = new LoginScreen();
    public static Map<String, Runnable> linkedScripts = new HashMap<>();

    private static void keyBinds() {
        Keybinds.unlimitedRange = new KeyBinding("Unlimited render range.", Keyboard.KEY_V, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.unlimitedRange);
        Keybinds.ghostBlocks = new KeyBinding("Ghost block kaybind", Keyboard.KEY_F, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.ghostBlocks);
        Keybinds.ghostBlocksReset = new KeyBinding("Ghost block reset kaybind", Keyboard.KEY_Z, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.ghostBlocksReset);
        Keybinds.legGhostBlocks = new KeyBinding("Legacy ghost block", Keyboard.KEY_C, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.legGhostBlocks);
        Keybinds.tempGhostBlocks = new KeyBinding("Temporary ghost block", Keyboard.KEY_NONE, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.tempGhostBlocks);
        Keybinds.ghostChest = new KeyBinding("Ghost chest", Keyboard.KEY_NONE, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.ghostChest);
        Keybinds.autoBridging = new KeyBinding("Auto bridging", Keyboard.KEY_NONE, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.autoBridging);
        Keybinds.aimBreak = new KeyBinding("Aim break button", Keyboard.KEY_NONE, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.aimBreak);
        Keybinds.blockBreaker = new KeyBinding("Block breaker", Keyboard.KEY_NONE, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.blockBreaker);
        Keybinds.debuffKey = new KeyBinding("Debuff key", Keyboard.KEY_NONE, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.debuffKey);
        Keybinds.ssKey = new KeyBinding("Auto SS", Keyboard.KEY_NONE, "key.abse");
        ClientRegistry.registerKeyBinding(Keybinds.ssKey);
    }

    public static void initialise(FMLInitializationEvent e) {
        Utils.setupRoman();


        //ClientSync.active();
        MinecraftForge.EVENT_BUS.register(new MainWrapper());
        MinecraftForge.EVENT_BUS.register(new GuiNotifier());
        MinecraftForge.EVENT_BUS.register(new Sync());
        MinecraftForge.EVENT_BUS.register(new Index());
        MinecraftForge.EVENT_BUS.register(new MCMainMenu());
        MinecraftForge.EVENT_BUS.register(new SmoothAimControl());
        MinecraftForge.EVENT_BUS.register(new ClickSimTick());
        //MinecraftForge.EVENT_BUS.register(new Mapping());


        ClientCommandHandler.instance.registerCommand(new InCmd());

        File file = new File(Loader.instance().getConfigDir() + "/abse");
        if (!file.exists()) file.mkdirs();

        Holder.link();
        System.out.println("ABSE SOUND REG: " + SoundUtils.initialise() + " sounds registered.");

        Index.MAIN_CFG = new MuConfig();
        Index.POS_CFG = new PositionConfig();
        Index.POS_CFG.load().defts(true).update();

        keyBinds();

//        int x0 = 0;
//        if (QGuiScreen.register(MainGui.class, new MainGui(0, null))) x0++;
//        if (QGuiScreen.register(PositionsGui.class, new PositionsGui(MainGui.class))) x0++;
//        System.out.println("QGuiScreen: Registered " + x0 + " screens.");
    }

    @SubscribeEvent
    public void onServerConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (Minecraft.getMinecraft().getCurrentServerData() == null) return;
        event.manager.channel().pipeline().addBefore("packet_handler", "abse_packet_handler", new PacketHandler());
    }

    @SubscribeEvent
    public void tick(InputEvent.KeyInputEvent e) {
        if (MainGui.queue) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    Minecraft.getMinecraft().displayGuiScreen(new MainGui(0, null));
                }
            });
            MainGui.queue = false;
        }
        if (NewMainMenu.queue) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    Minecraft.getMinecraft().displayGuiScreen(new NewMainMenu(null));
                }
            });
            NewMainMenu.queue = false;
        }
    }
}