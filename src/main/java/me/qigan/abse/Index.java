package me.qigan.abse;

import me.qigan.abse.config.MuConfig;
import me.qigan.abse.config.PositionConfig;
import me.qigan.abse.crp.MainWrapper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Index.MODID, version = Index.VERSION)
public class Index
{
    public static MuConfig MAIN_CFG;
    public static PositionConfig POS_CFG;

    public static final String MODID = "abse";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MainWrapper.initialise(event);
    }
}
