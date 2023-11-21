package me.qigan.abse.gui.inst;

import me.qigan.abse.Holder;
import me.qigan.abse.Index;
import me.qigan.abse.config.SetsData;
import me.qigan.abse.crp.Module;
import me.qigan.abse.fr.Debug;
import me.qigan.abse.gui.GuiDoubleNumberField;
import me.qigan.abse.gui.GuiNumberField;
import me.qigan.abse.gui.QGuiScreen;
import me.qigan.abse.gui.TooltipBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainGui extends QGuiScreen {

    private static Map<Integer, String> sch = new HashMap<Integer, String>();
    private static Map<Integer, String> tsc = new HashMap<Integer, String>();

    public static List<Integer> moduleIDs = new ArrayList<>();

    public static final int PAGE_SIZE = 16;

    private GuiButton pageUp;
    private GuiButton pageDown;
    private GuiButton colorPicker;

    public final int page;

    public static final int sizeH = 10;
    public static final int sizeW = 130;

    public static boolean queue = false;

    public static boolean opened = false;

    public MainGui(int page, QGuiScreen cls) {
        super(cls);
        this.page = page;
    }

    @Override
    public void initGui() {
        int Id = 1;
        opened = true;
        sch.clear();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int height = sr.getScaledHeight();
        int width = sr.getScaledWidth();

        pageDown = new GuiButton(Id, width/6*2, 20, 40, 20, "<");
        if (page <= 0) {
            pageDown.enabled = false;
        }
        buttonList.add(pageDown);
        Id++;
        pageUp = new GuiButton(Id, width/6*4, 20, 40, 20, ">");
        if ((page+1)*PAGE_SIZE > Holder.MRL.size()) {
            pageUp.enabled = false;
        }
        buttonList.add(pageUp);
        Id++;
        colorPicker = new GuiButton(Id, width-100, height-20, 100, 20, "Position picker");
        buttonList.add(colorPicker);
        Id++;

        int i = 0;
        for (int u = PAGE_SIZE * page; u < Holder.MRL.size() && u < PAGE_SIZE*(page+1); u++) {
            Module mod = Holder.MRL.get(u);
            ItemStack stack = new ItemStack(Items.arrow);
            stack.setStackDisplayName("\u00A7f" + mod.description());
//            NBTTagList tagList = new NBTTagList();
//            tagList.appendTag(new NBTTagString("Looool"));
//            NBTTagCompound comp =  stack.serializeNBT();
            tooltipBoxList.add(new TooltipBox(40, 60 + i*2*sizeH, sizeW, sizeH, stack));
            GuiLabel label = new GuiLabel(fontRendererObj, Id, 40, 60 + i*2*sizeH, sizeW, sizeH, 0xFFFFFF);
            label.func_175202_a(mod.fname() + ":");
            labelList.add(label);
            Id++;
            GuiButton button = new GuiButton(Id, 60+sizeW, 60 + i*2*sizeH, sizeW, sizeH, "\u00A7l" + (mod.isEnabled() ? "\u00A7a ON" : "\u00A7c OFF"));
            if (Debug.DISABLE_STATE.contains(mod.id())) {
                button.enabled = false;
                ItemStack stack1 = new ItemStack(Items.arrow);
                stack1.setStackDisplayName("\u00A7f\u00A7cTemporary disabled!");
                tooltipBoxList.add(new TooltipBox(60+sizeW, 60 + i*2*sizeH, sizeW, sizeH, stack1));
            }
            sch.put(button.id, mod.id());
            moduleIDs.add(button.id);
            buttonList.add(button);
            Id++;

            int r = 2;
            int gr = 0;
            for (SetsData<String> ddr : mod.sets()) {
                String call = ddr.guiName + ":";
                int size = Minecraft.getMinecraft().fontRendererObj.getStringWidth(call);
                int kprf = ((100+size+r*sizeW) + (sizeW/2)) * gr;
                if ((100+size+r*sizeW) + (sizeW/2) - kprf > width) {i++; gr++; }
                kprf = (100+r*sizeW - 150) * gr;
                switch (ddr.dataType) {
                    case BOOLEAN:
                    {
                        GuiLabel label1 = new GuiLabel(fontRendererObj, Id, 80+r*sizeW - kprf, 60 + i*2*sizeH, sizeW/2, sizeH, 0xFFFFFF);
                        label1.func_175202_a(call);
                        labelList.add(label1);
                        Id++;
                        GuiButton button1 = new GuiButton(Id, 100+size+r*sizeW - kprf, 60 + i*2*sizeH, sizeW/2, sizeH,
                                "\u00A7l" + (Index.MAIN_CFG.getBoolVal(ddr.setId) ? "\u00A7a ON" : "\u00A7c OFF"));
                        if (Debug.DISABLE_STATE.contains(ddr.setId)) {
                            button1.enabled = false;
                            ItemStack stack1 = new ItemStack(Items.arrow);
                            stack1.setStackDisplayName("\u00A7f\u00A7cTemporary disabled!");
                            tooltipBoxList.add(new TooltipBox(100+size+r*sizeW - kprf, 60 + i*2*sizeH, sizeW/2, sizeH, stack1));
                        }
                        sch.put(button1.id, ddr.setId);
                        buttonList.add(button1);
                        Id++;

                    }
                    break;
                    case STRING:
                    {
                        GuiLabel label1 = new GuiLabel(fontRendererObj, Id, 80+r*sizeW - kprf, 60 + i*2*sizeH, sizeW/2, sizeH, 0xFFFFFF);
                        label1.func_175202_a(call);
                        labelList.add(label1);
                        Id++;
                        GuiTextField textField = new GuiTextField(Id, Minecraft.getMinecraft().fontRendererObj, 100+size+r*sizeW - kprf, 60 + i*2*sizeH, sizeW/2, sizeH);
                        textField.setText(Index.MAIN_CFG.getStrVal(ddr.setId));
                        textField.setMaxStringLength(256);
                        tsc.put(textField.getId(), ddr.setId);
                        textFieldList.add(textField);
                        Id++;
                    }
                    break;
                    case NUMBER:
                    {
                        GuiLabel label1 = new GuiLabel(fontRendererObj, Id, 80+r*sizeW - kprf, 60 + i*2*sizeH, sizeW/2, sizeH, 0xFFFFFF);
                        label1.func_175202_a(call);
                        labelList.add(label1);
                        Id++;
                        GuiNumberField textField = new GuiNumberField(Id, Minecraft.getMinecraft().fontRendererObj, 100+size+r*sizeW - kprf, 60 + i*2*sizeH, sizeW/2, sizeH);
                        textField.setText(Index.MAIN_CFG.getStrVal(ddr.setId));
                        textField.setMaxStringLength(256);
                        tsc.put(textField.getId(), ddr.setId);
                        textFieldList.add(textField);
                        Id++;
                    } case DOUBLE_NUMBER:
                    {
                        GuiLabel label1 = new GuiLabel(fontRendererObj, Id, 80+r*sizeW - kprf, 60 + i*2*sizeH, sizeW/2, sizeH, 0xFFFFFF);
                        label1.func_175202_a(call);
                        labelList.add(label1);
                        Id++;
                        GuiDoubleNumberField textField = new GuiDoubleNumberField(Id, Minecraft.getMinecraft().fontRendererObj, 100+size+r*sizeW - kprf, 60 + i*2*sizeH, sizeW/2, sizeH);
                        textField.setText(Index.MAIN_CFG.getStrVal(ddr.setId));
                        textField.setMaxStringLength(256);
                        tsc.put(textField.getId(), ddr.setId);
                        textFieldList.add(textField);
                        Id++;
                    }
                    break;
                }
                r+=2;
            }

            i++;
        }


        super.initGui();
    }

    private void reopen(final int page) {
        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                Minecraft.getMinecraft().displayGuiScreen(new MainGui(page, null));
            }
        });
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (Index.MAIN_CFG.getBoolVal("debug")) System.out.println(typedChar + " - " + keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        super.drawDefaultBackground();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        double height = sr.getScaledHeight();
        double width = sr.getScaledWidth();

        drawString(Minecraft.getMinecraft().fontRendererObj, "\u00A7l\u00A7aAbsolute Evil", (int) width/2-25, 10, 0xFFFFFF);

//        Esp.drawModalRectWithCustomSizedTexture(-100, -100, (float) width + 100, (float) height + 100, 0, 0, 640, 404,
//                new ResourceLocation("abse", "test.png"), new MColor(1, 1, 1, 1f));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        for (GuiTextField textField : textFieldList) {
            Index.MAIN_CFG.set(tsc.get(textField.getId()), textField.getText());
        }

        opened = false;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                if (page > 0) {
                    reopen(page-1);
                }
                break;
            case 2:
                if ((page+1)*PAGE_SIZE <= Holder.MRL.size()) {
                    reopen(page+1);
                }
                break;
            case 3:
                Minecraft.getMinecraft().displayGuiScreen(new PositionsGui(this));
                break;
            default:
                button.displayString = (button.displayString.contains("ON") ? "\u00A7l\u00A7c OFF" : "\u00A7l\u00A7a ON");
                Index.MAIN_CFG.toggle(sch.get(button.id), moduleIDs.contains(button.id));
                break;
        }
    }
}
