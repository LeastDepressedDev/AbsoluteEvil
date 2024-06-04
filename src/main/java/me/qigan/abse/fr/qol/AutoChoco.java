package me.qigan.abse.fr.qol;

import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.config.ValType;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.exc.TickTasks;
import me.qigan.abse.sync.Utils;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AutoChoco extends Module {

    public static long openTime = 0;
    public static int tick = 0;

    public static int lastPrio = -1;

    public static int[][] rabbits = new int[7][];

    @SubscribeEvent
    void runtime(GuiScreenEvent.InitGuiEvent e) {
        if (!isEnabled()) return;
        if (e.gui instanceof GuiChest) {
            GuiChest chest = (GuiChest) e.gui;
            ContainerChest c1 = (ContainerChest) chest.inventorySlots;
            if (c1.getLowerChestInventory().getName().equalsIgnoreCase("Chocolate Factory")) {
                openTime = System.currentTimeMillis();
            }
        }
    }

    @SubscribeEvent
    void runtime(GuiScreenEvent.DrawScreenEvent e) {
        if (!isEnabled()) return;
        if (e.gui instanceof GuiChest) {
            GuiChest chest = (GuiChest) e.gui;
            ContainerChest c1 = (ContainerChest) chest.inventorySlots;
            if (c1.getLowerChestInventory().getName().equalsIgnoreCase("Chocolate Factory") && System.currentTimeMillis()-openTime > 2000) {
                try {
                    List<String> render = new ArrayList<>();
                    render.add("\u00A76Prio: \u00A7a" + lastPrio);
                    render.add("\u00A76Delay ticks: \u00A7c" + tick);
                    for (int i = 0; i < rabbits.length; i++) {
                        render.add("\u00A76" + i + ":  " + rabbits[i][0] + "||" + rabbits[i][1] + "||" +
                                ((double) rabbits[i][0] / (double) rabbits[i][1]));
                    }
                    Esp.drawAllignedTextList(render, 10, 10, false, new ScaledResolution(Minecraft.getMinecraft()), S2Dtype.CORNERED);
                } catch (NullPointerException ignored) {}
                guiTick(chest, c1);
            }
        }
    }

    private static void guiTick(GuiChest chest, ContainerChest c) {
        int amount = Integer.parseInt(Utils.cleanSB(c.getInventory().get(13).getDisplayName().split(" ")[0].replaceAll(",", "")));
        //28-34
        Random rand = new Random();
        int optimalSlot = -1;
        double optimalValue = 0;
        for (int i = 28; i <= 34; i++) {
            ItemStack stack = c.getInventory().get(i);
            NBTTagCompound displ = Utils.getDisplay(stack);
            if (displ == null) continue;
            int[] rab = parseRabit(displ);
            rabbits[i-28] = rab;
            double mfs = (double) rab[0] / (double) rab[1];
            if (mfs > optimalValue) {
                optimalValue = mfs;
                optimalSlot = i;
            }
        }

        if (tick == 0 && Index.MAIN_CFG.getBoolVal("auto_choco_cm")) {
            for (int i = 0; i < 53; i++) {
                ItemStack stack = c.getInventory().get(i);
                if (stack == null) continue;
                if (Utils.cleanSB(stack.getDisplayName()).startsWith("CLICK ME")) {
                    tick = 200;
                    final int I = i;
                    TickTasks.call(() -> chest.mc.playerController.windowClick(c.windowId, I, 0, 0, chest.mc.thePlayer),
                            30+(rand.nextInt()%50));
                }
            }
        }
        //39

        lastPrio = optimalSlot;
        if (optimalSlot == -1) return;
        if (amount - rabbits[optimalSlot-28][1] > 0 && Index.MAIN_CFG.getBoolVal("auto_choco_rab")) click(chest, c, optimalSlot);
    }



    private static int[] parseRabit(NBTTagCompound displ) {
        NBTTagList list = displ.getTagList("Lore", 8);
        int[] asw = {0, -1};
        String defLine = "";
        for (int i = 2; i <= 5; i++) {
            defLine += list.getStringTagAt(i)+"\n";
        }

        for (String part : defLine.split(" ")) {
            part = Utils.cleanSB(part);
            try {
                asw[0] = Integer.parseInt(part.substring(1));
                break;
            } catch (Exception ignore) {}
        }


        for (int i = 0; i < list.tagCount(); i++) {
            String line = Utils.cleanSB(list.getStringTagAt(i));
            if (line.startsWith("PROMOTE") || line.startsWith("HIRE")) {
                String[] ls = Utils.cleanSB(list.getStringTagAt(i+1)).substring(1).split(" ");
                if (ls.length < 4) continue;
                //System.out.println(ls[0] + "; " + ls[1] + "; " + ls[2] + "; " + ls[3]);
                asw[0] -= Integer.parseInt(ls[1].substring(1).replaceAll(",", ""));
                asw[0] *= -1;
            }
            else if (line.startsWith("Cost")) {
                asw[1] = Integer.parseInt(Utils.cleanSB(list.getStringTagAt(i+1)).split(" ")[0].replaceAll(",", ""));
                break;
            }
        }
        return asw;
    }

    public static void click(GuiChest gui, ContainerChest chest, int slot) {
        if (tick > 0) return;
        tick = Index.MAIN_CFG.getIntVal("auto_choco_tdel");
        gui.mc.playerController.windowClick(chest.windowId, slot, 0, 0, gui.mc.thePlayer);
    }

    @SubscribeEvent
    void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) return;
        if (tick > 0) tick--;
    }

    @Override
    public String id() {
        return "auto_choco";
    }

    @Override
    public List<SetsData<?>> sets() {
        List<SetsData<?>> list = new ArrayList<>();
        list.add(new SetsData<>("auto_choco_tdel", "Click delay[tick]", ValType.NUMBER, "10"));
        list.add(new SetsData<>("auto_choco_cm", "Auto CLICK ME", ValType.BOOLEAN, "true"));
        list.add(new SetsData<>("auto_choco_rab", "Auto upgrade rabbits", ValType.BOOLEAN, "true"));
        return list;
    }

    @Override
    public String description() {
        return "True chocolate factory automation";
    }

    @Override
    public String fname() {
        return "Auto chocolate farm";
    }
}
