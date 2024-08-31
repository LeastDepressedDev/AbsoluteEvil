package me.qigan.abse.events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CoreEventProfiler {

    public static Set<Integer> hurtEventCollect = new HashSet<>();

    @SubscribeEvent
    void entityUpdate(LivingEvent.LivingUpdateEvent e) {
        if (e.entityLiving.hurtTime != 0) {
            if (!hurtEventCollect.contains(e.entityLiving.getEntityId())) {
                hurtEventCollect.add(e.entityLiving.getEntityId());
                MinecraftForge.EVENT_BUS.post(new LivingHurtEvent(e.entityLiving));
            }
        } else {
            hurtEventCollect.remove(e.entityLiving.getEntityId());
        }
    }
}
