package me.qigan.abse.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class LivingHurtEvent extends Event {
    public final EntityLivingBase entity;

    public LivingHurtEvent(EntityLivingBase living) {
        this.entity = living;
    }
}
