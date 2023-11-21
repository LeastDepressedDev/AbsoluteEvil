package me.qigan.abse.sync;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import me.qigan.abse.Index;
import me.qigan.abse.config.AddressedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Utils {

    public static double createRandomDouble(double up, double down) {
        Random rand = new Random();
        return rand.nextBoolean() ? rand.nextInt()%up : down;
    }

    public static double createRandomDouble(double up, double down, long seed) {
        Random rand = new Random(seed);
        return rand.nextBoolean() ? rand.nextInt()%up : down;
    }

    public static float[] getRotationsNeeded(Entity entity1, Entity entity2) {
        if (entity1 == null || entity2 == null) {
            return null;
        }

        final double diffX = entity2.posX - entity1.posX;
        final double diffZ = entity2.posZ - entity1.posZ;
        double diffY;

        if (entity2 instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase) entity2;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (entity1.posY + entity1.getEyeHeight());
        } else {
            diffY = (entity2.getEntityBoundingBox().minY + entity2.getEntityBoundingBox().maxY) / 2.0D - (entity1.posY + entity1.getEyeHeight());
        }

        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] { entity1.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - entity1.rotationYaw), entity1.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - entity1.rotationPitch) };
    }

    public static <K, V> List<AddressedData<K, V>> mapToAddressedDataList(Map<K, V> map) {
        List<AddressedData<K, V>> list = new ArrayList<>();
        for (Map.Entry<K, V> kv : map.entrySet()) {
            list.add(new AddressedData<>(kv.getKey(), kv.getValue()));
        }
        return list;
    }

    /**
     * Return `-1` if item don't have a color
     */
    public static int getItemColor(ItemStack stack) {
        //.serializeNBT().getCompoundTag("tag").getCompoundTag("display").getInteger("color")
        if (stack == null) return -1;
        NBTTagCompound tag = stack.serializeNBT();
        if (tag.hasKey("tag")) {
            tag = tag.getCompoundTag("tag");
            if (tag.hasKey("display")) {
                tag = tag.getCompoundTag("display");
                if (tag.hasKey("color")) {
                    return tag.getInteger("color");
                }
            }
        }
        return -1;
    }

    public static String cleanSB(String scoreboard) {
        char[] nvString = StringUtils.stripControlCodes(scoreboard).toCharArray();
        StringBuilder cleaned = new StringBuilder();

        for (char c : nvString) {
            if ((int) c > 20 && (int) c < 127) {
                cleaned.append(c);
            }
        }

        return cleaned.toString();
    }

    public static String keepDigit(String str, List<Character> except) {
        String nstr = "";
        for (char ch : str.toCharArray()) {
            switch (ch) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '.':
                nstr += ch;
                default:
                    if (except != null) {
                        if (except.contains(ch)) nstr += ch;
                    }
            }
        }
        return nstr;
    }

    public static NBTTagCompound getSbData(ItemStack stack) {
        try {
            return stack.serializeNBT().getCompoundTag("tag").getCompoundTag("ExtraAttributes");
        } catch (Exception e) {
            return new NBTTagCompound();
        }
    }

    public static List<String> getDataFromTab() {
        List<String> result = new ArrayList<String>();
        for (NetworkPlayerInfo ev: Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
            String strm = Minecraft.getMinecraft().ingameGUI.getTabList().getPlayerName(ev).toLowerCase();
            result.add(strm);
        }
        return result;
    }

    public static List<String> getScoreboard() {
        List<String> lines = new ArrayList<String>();
        if (Minecraft.getMinecraft().theWorld == null) return lines;
        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        if (scoreboard == null) return lines;

        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective == null) return lines;

        Collection<Score> scores = scoreboard.getSortedScores(objective);
        List<Score> list = new ArrayList<Score>();
        for (Score scr : scores) {
            if (scr != null && scr.getPlayerName() != null && !scr.getPlayerName().startsWith("#")) list.add(scr);
        }

        if (list.size() > 15) {
            scores = Lists.newArrayList(Iterables.skip(list, scores.size() - 15));
        } else {
            scores = list;
        }

        for (Score score : scores) {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
            lines.add(cleanSB(ScorePlayerTeam.formatPlayerName(team, score.getPlayerName())));
        }

        return lines;
    }
}
