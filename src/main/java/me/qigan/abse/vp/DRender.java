package me.qigan.abse.vp;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public interface DRender {
	void render(RenderWorldLastEvent e);
	void rov(RenderGameOverlayEvent e);
}
