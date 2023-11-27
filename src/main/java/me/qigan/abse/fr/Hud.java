package me.qigan.abse.fr;

import me.qigan.abse.Holder;
import me.qigan.abse.Index;
import me.qigan.abse.crp.Module;
import me.qigan.abse.crp.MainWrapper;
import me.qigan.abse.fr.other.AutoBridging;
import me.qigan.abse.vp.Esp;
import me.qigan.abse.vp.S2Dtype;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Hud extends Module{
    @Override
    public String id() {
        return "hud";
    }

    @Override
    public String fname() {
        return "Hud";
    }

    @Override
    public String description() {
        return "Hud thingies";
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void rov(RenderGameOverlayEvent.Text e) {
        if (!isEnabled()) return;
        List<String> lines = new ArrayList<>();
        for (Module mdl : Holder.MRL) {
            if (mdl.isEnabled()) {
                lines.add("\u00A7a" + mdl.fname());
            }
        }

        if (MainWrapper.Keybinds.unlimitedRange.isKeyDown()) lines.add("\u00A7cRender distance++");
        if (Index.MAIN_CFG.getBoolVal("abrig_tog")) {
            if (AutoBridging.toggle) lines.add("\u00A7cAuto bridging[toggle]");
        } else {
            if (MainWrapper.Keybinds.unlimitedRange.isKeyDown()) lines.add("\u00A7cAuto bridging[hold]");
        }
        if (MainWrapper.Keybinds.aimBreak.isKeyDown()) lines.add("\u00A7cAim break!");

        Point point = Index.POS_CFG.calc("module_list");
        Esp.drawAllignedTextList(lines, point.x, point.y, true, e.resolution, S2Dtype.SHADOW);
    }
}
