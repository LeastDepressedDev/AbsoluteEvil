package me.qigan.abse.mapping;

import me.qigan.abse.config.AddressedData;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {

    public enum Shape {
        r1X1,
        r1X2,
        r1X3,
        r1X4,
        r2X2,
        rL
    }

    public enum Rotation {
        SOUTH(0f),
        WEST(-90f),
        NORTH(-180f),
        EAST(-270f);

        public final float angle;

        Rotation(float angle) {
            this.angle = angle;
        }
    }

    public enum Type {
        SPAWN,
        REGULAR,
        BOSS,
        BLOOD,
        PUZZLE,
        TRAP,
        FAIRY
    }

    public final int iter;
    private List<AddressedData<int[], Integer>> elements;
    private int height = 0;

    public int[] center = null;

    private Shape shape = Shape.r1X1;
    private Rotation rotation = Rotation.EAST;
    private Type type = Type.REGULAR;

    private int id = -1;

    public Room(int iter) {
        this.iter = iter;
        if (this.iter == 1) this.type = Type.SPAWN;
    }

    public Room define(int[][] map) {
        Map<int[], Integer> tgr = new HashMap<>();
        WorldClient world = Minecraft.getMinecraft().theWorld;
        elements = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (map[i][j] == iter) {
                    int s = 0;
                    if (i - 1 >= 0 && map[i-1][j] == iter) s++;
                    if (i + 1 < 6 && map[i+1][j] == iter) s++;
                    if (j - 1 >= 0 && map[i][j-1] == iter) s++;
                    if (j + 1 < 6 && map[i][j+1] == iter) s++;
                    AddressedData<int[], Integer> e = new AddressedData<>(new int[]{i, j}, s);
                    elements.add(e);
                    tgr.put(e.getNamespace(), e.getObject());
                }
            }
        }

        if (elements.size() == 0) return this;
        this.height = Mapping.rayDown(Mapping.cellToReal(elements.get(0).getNamespace()), world);

        switch (tgr.size()) {
            default:
            case 1:
                this.shape = Shape.r1X1;
                break;
            case 2:
                this.shape = Shape.r1X2;
                break;
            case 3:
                this.shape = r3(tgr);
                break;
            case 4:
                this.shape = r4(tgr);
                break;
        }

        switch (shape) {
            case r1X1: {
                center = this.elements.get(0).getNamespace();
                int[] coord = Mapping.cellToReal(this.elements.get(0).getNamespace());
                if (world.getBlockState(new BlockPos(coord[0], height, coord[1]))
                        .getBlock() == Blocks.lapis_block &&
                        world.getBlockState(new BlockPos(coord[0] + MappingConstants.ROOM_SIZE, height, coord[1] + MappingConstants.ROOM_SIZE))
                                .getBlock() == Blocks.lapis_block &&
                        world.getBlockState(new BlockPos(coord[0], height, coord[1] + MappingConstants.ROOM_SIZE))
                                .getBlock() == Blocks.lapis_block &&
                        world.getBlockState(new BlockPos(coord[0] + MappingConstants.ROOM_SIZE, height, coord[1]))
                                .getBlock() == Blocks.lapis_block) {
                    this.type = Type.FAIRY;
                    this.rotation = Rotation.EAST;
                    break;
                }

                if (world.getBlockState(new BlockPos(coord[0] + MappingConstants.ROOM_SIZE, height, coord[1] + 3))
                        .getBlock() == Blocks.stained_hardened_clay) rotation = Rotation.SOUTH;
                else if (world.getBlockState(new BlockPos(coord[0] + MappingConstants.ROOM_SIZE - 3, height, coord[1] + MappingConstants.ROOM_SIZE))
                        .getBlock() == Blocks.stained_hardened_clay) rotation = Rotation.WEST;
                else if (world.getBlockState(new BlockPos(coord[0], height, coord[1] + MappingConstants.ROOM_SIZE - 3))
                        .getBlock() == Blocks.stained_hardened_clay) rotation = Rotation.NORTH;
                else if (world.getBlockState(new BlockPos(coord[0] + 3, height, coord[1]))
                        .getBlock() == Blocks.stained_hardened_clay) rotation = Rotation.EAST;

                defineRoomType();
            }
                break;
            case r1X2:
            case r1X3:
            case r1X4:
            case r2X2:
            case rL:
            {
                for (AddressedData<int[], Integer> data : this.elements) {
                    int[] coord = Mapping.cellToReal(data.getNamespace());
                    //coord[1] + 3 cuz some rooms can have stupid alignment
                    if (world.getBlockState(new BlockPos(coord[0] + MappingConstants.ROOM_SIZE, height, coord[1] + 3))
                            .getBlock() == Blocks.stained_hardened_clay) rotation = Rotation.SOUTH;
                    else if (world.getBlockState(new BlockPos(coord[0] + MappingConstants.ROOM_SIZE - 3, height, coord[1] + MappingConstants.ROOM_SIZE))
                            .getBlock() == Blocks.stained_hardened_clay) rotation = Rotation.WEST;
                    else if (world.getBlockState(new BlockPos(coord[0], height, coord[1] + MappingConstants.ROOM_SIZE - 3))
                            .getBlock() == Blocks.stained_hardened_clay) rotation = Rotation.NORTH;
                    else if (world.getBlockState(new BlockPos(coord[0] + 3, height, coord[1]))
                            .getBlock() == Blocks.stained_hardened_clay) rotation = Rotation.EAST;

                    this.center = data.getNamespace();
                    break;
                }
            }
                break;
        }

        defineRoomId();

        return this;
    }

    private void defineRoomType() {
        if (shape != Shape.r1X1) return;
        BlockPos pos = new BlockPos(21, height, 0);
        for (int i = 0; i < 5; i++) {
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(this.transformInnerCoordinate(pos.add(0, 0, i))).getBlock();
            if (block == Blocks.redstone_block) this.type = Type.BLOOD;
            else if (block == Blocks.emerald_ore) this.type = Type.PUZZLE;
            else if (block == Blocks.netherrack) this.type = Type.BOSS;
            else if (block == Blocks.tnt) this.type = Type.TRAP;
        }
    }

    private static Shape r3(final Map<int[], Integer> tgr) {
        for (Map.Entry<int[], Integer> etr : tgr.entrySet()) {
            if (etr.getValue() == 2) {
                if (check(tgr, etr.getKey())) return Shape.rL;
            }
        }
        return Shape.r1X3;
    }

    private static Shape r4(final Map<int[], Integer> tgr) {
        for (Map.Entry<int[], Integer> etr : tgr.entrySet()) {
            if (etr.getValue() != 2) {
                return Shape.r1X4;
            }
        }
        return Shape.r2X2;
    }

    public BlockPos transformInnerCoordinate(BlockPos pos) {
        int[] coord = Mapping.transp(pos.getZ() - 15, pos.getX() - 15, this.rotation.angle);
        int[] cellC = Mapping.cellToReal(this.center);
        return new BlockPos(coord[0] + cellC[0] + 15, pos.getY(), coord[1] + cellC[1] + 15);
    }

    private static boolean check(Map<int[], Integer> tgr, final int[] crd) {
        int[] c1 = crd.clone(), c2 = crd.clone();
        c1[0]+=1;
        c2[1]+=1;
        if (tgr.get(c1) != null && tgr.get(c2) != null) return true;
        c1 = crd.clone(); c2 = crd.clone();
        c1[0]-=1;
        c2[1]+=1;
        if (tgr.get(c1) != null && tgr.get(c2) != null) return true;
        c1 = crd.clone(); c2 = crd.clone();
        c1[0]+=1;
        c2[1]-=1;
        if (tgr.get(c1) != null && tgr.get(c2) != null) return true;
        c1 = crd.clone(); c2 = crd.clone();
        c1[0]-=1;
        c2[1]-=1;
        return tgr.get(c1) != null && tgr.get(c2) != null;
    }

    public void defineRoomId() {
        this.id = Rooms.match(this);
    }

    public void placeRoute() {
        if (id == -1) return;

    }

    public Shape getShape() {
        return shape;
    }

    public int getHeight() {
        return height;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public Type getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
