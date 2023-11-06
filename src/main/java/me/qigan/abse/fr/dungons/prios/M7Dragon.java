package me.qigan.abse.fr.dungons.prios;

import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;

public enum M7Dragon {
    RED(new BlockPos(27, 14, 59).up(5), "\u00A7cRED", 'r'),
    GREEN(new BlockPos(27, 14, 94).up(5), "\u00A7aGREEN", 'g'),
    PURPLE(new BlockPos(56, 14, 125).up(5), "\u00A7dPURPLE", 'p'),
    BLUE(new BlockPos(84, 14, 94).up(5), "\u00A7bBLUE", 'b'),
    ORANGE(new BlockPos(85, 14, 56).up(5), "\u00A76ORANGE", 'o')

    ;
    public final BlockPos particle;
    public final String name;
    public final char liter;

    M7Dragon(BlockPos particle, String name, char l) {
        this.particle = particle;
        this.name = name;
        this.liter = l;
    }

    public static M7Dragon match(S2APacketParticles p) {
        for (M7Dragon drag : M7Dragon.values()) {
            if (drag.particle.getX() == p.getXCoordinate() && drag.particle.getZ() == p.getZCoordinate()) {
                return drag;
            }
        }
        return null;
    }
}
