package me.qigan.abse.mapping;

import me.qigan.abse.config.AddressedData;
import me.qigan.abse.mapping.routing.Route;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

import java.util.Collection;

public abstract class RoomTemplate {

    private final int id;
    private final String name;
    private final Room.Shape shape;

    public RoomTemplate(Room.Shape shape, int id, String name) {
        this.shape = shape;
        this.id = id;
        this.name = name;
    }

    public abstract Collection<AddressedData<BlockPos, Block>> hooks();

    public Route route() {return new Route();}

    public final int getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final Room.Shape getShape() {
        return shape;
    }
}
