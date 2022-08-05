//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util.plugs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import il.dev.mio.api.util.interact.BlockUtil;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Comparator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import java.util.Random;
import il.dev.mio.api.util.IMinecraftUtil;

public class MathUtilll implements IMinecraftUtil
{
    private static final Random random;
    
    public static int getRandom(final int min, final int max) {
        return min + MathUtilll.random.nextInt(max - min + 1);
    }
    
    public static double getRandom(final double min, final double max) {
        return MathHelper.clamp(min + MathUtilll.random.nextDouble() * max, min, max);
    }
    
    public static float getRandom(final float min, final float max) {
        return MathHelper.clamp(min + MathUtilll.random.nextFloat() * max, min, max);
    }
    
    public static int clamp(final int num, final int min, final int max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static float clamp(final float num, final float min, final float max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static double clamp(final double num, final double min, final double max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static float sin(final float value) {
        return MathHelper.sin(value);
    }
    
    public static float cos(final float value) {
        return MathHelper.cos(value);
    }
    
    public static float wrapDegrees(final float value) {
        return MathHelper.wrapDegrees(value);
    }
    
    public static double wrapDegrees(final double value) {
        return MathHelper.wrapDegrees(value);
    }
    
    public static Vec3d roundVec(final Vec3d vec3d, final int places) {
        return new Vec3d(round(vec3d.x, places), round(vec3d.y, places), round(vec3d.z, places));
    }
    
    public static double angleBetweenVecs(final Vec3d vec3d, final Vec3d other) {
        double angle = Math.atan2(vec3d.x - other.x, vec3d.z - other.z);
        angle = -(angle / 3.141592653589793) * 360.0 / 2.0 + 180.0;
        return angle;
    }
    
    public static double lengthSQ(final Vec3d vec3d) {
        return square(vec3d.x) + square(vec3d.y) + square(vec3d.z);
    }
    
    public static double length(final Vec3d vec3d) {
        return Math.sqrt(lengthSQ(vec3d));
    }
    
    public static double dot(final Vec3d vec3d, final Vec3d other) {
        return vec3d.x * other.x + vec3d.y * other.y + vec3d.z * other.z;
    }
    
    public static double square(final double input) {
        return input * input;
    }
    
    public static double square(final float input) {
        return input * input;
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.doubleValue();
    }
    
    public static float wrap(final float valI) {
        float val = valI % 360.0f;
        if (val >= 180.0f) {
            val -= 360.0f;
        }
        if (val < -180.0f) {
            val += 360.0f;
        }
        return val;
    }
    
    public static Vec3d direction(final float yaw) {
        return new Vec3d(Math.cos(degToRad(yaw + 90.0f)), 0.0, Math.sin(degToRad(yaw + 90.0f)));
    }
    
    public static float round(final float value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.floatValue();
    }
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map, final boolean descending) {
        final LinkedList<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        if (descending) {
            list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        }
        else {
            list.sort(Map.Entry.comparingByValue());
        }
        final LinkedHashMap result = new LinkedHashMap();
        for (final Map.Entry entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return (Map<K, V>)result;
    }
    
    public static String getTimeOfDay() {
        final Calendar c = Calendar.getInstance();
        final int timeOfDay = c.get(11);
        if (timeOfDay < 12) {
            return "Good Morning ";
        }
        if (timeOfDay < 16) {
            return "Good Afternoon ";
        }
        if (timeOfDay < 21) {
            return "Good Evening ";
        }
        return "Good Night ";
    }
    
    public static double radToDeg(final double rad) {
        return rad * 57.295780181884766;
    }
    
    public static double degToRad(final double deg) {
        return deg * 0.01745329238474369;
    }
    
    public static double getIncremental(final double val, final double inc) {
        final double one = 1.0 / inc;
        return Math.round(val * one) / one;
    }
    
    public static double[] directionSpeed(final double speed) {
        float forward = MathUtilll.mc.player.movementInput.moveForward;
        float side = MathUtilll.mc.player.movementInput.moveStrafe;
        float yaw = MathUtilll.mc.player.prevRotationYaw + (MathUtilll.mc.player.rotationYaw - MathUtilll.mc.player.prevRotationYaw) * MathUtilll.mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static List<Vec3d> getBlockBlocks(final Entity entity) {
        final ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        final AxisAlignedBB bb = entity.getEntityBoundingBox();
        final double y = entity.posY;
        final double minX = round(bb.minX, 0);
        final double minZ = round(bb.minZ, 0);
        final double maxX = round(bb.maxX, 0);
        final double maxZ = round(bb.maxZ, 0);
        if (minX != maxX) {
            final Vec3d vec3d1 = new Vec3d(minX, y, minZ);
            final Vec3d vec3d2 = new Vec3d(maxX, y, minZ);
            final BlockPos pos1 = new BlockPos(vec3d1);
            final BlockPos pos2 = new BlockPos(vec3d2);
            if (BlockUtil.isBlockUnSolid(pos1) && BlockUtil.isBlockUnSolid(pos2)) {
                vec3ds.add(vec3d1);
                vec3ds.add(vec3d2);
            }
            if (minZ != maxZ) {
                final Vec3d vec3d3 = new Vec3d(minX, y, maxZ);
                final Vec3d vec3d4 = new Vec3d(maxX, y, maxZ);
                final BlockPos pos3 = new BlockPos(vec3d1);
                final BlockPos pos4 = new BlockPos(vec3d2);
                if (BlockUtil.isBlockUnSolid(pos3) && BlockUtil.isBlockUnSolid(pos4)) {
                    vec3ds.add(vec3d3);
                    vec3ds.add(vec3d4);
                    return vec3ds;
                }
            }
            if (vec3ds.isEmpty()) {
                vec3ds.add(entity.getPositionVector());
            }
            return vec3ds;
        }
        if (minZ != maxZ) {
            final Vec3d vec3d1 = new Vec3d(minX, y, minZ);
            final Vec3d vec3d2 = new Vec3d(minX, y, maxZ);
            final BlockPos pos1 = new BlockPos(vec3d1);
            final BlockPos pos2 = new BlockPos(vec3d2);
            if (BlockUtil.isBlockUnSolid(pos1) && BlockUtil.isBlockUnSolid(pos2)) {
                vec3ds.add(vec3d1);
                vec3ds.add(vec3d2);
            }
            if (vec3ds.isEmpty()) {
                vec3ds.add(entity.getPositionVector());
            }
            return vec3ds;
        }
        vec3ds.add(entity.getPositionVector());
        return vec3ds;
    }
    
    public static boolean areVec3dsAligned(final Vec3d vec3d1, final Vec3d vec3d2) {
        return areVec3dsAlignedRetarded(vec3d1, vec3d2);
    }
    
    public static boolean areVec3dsAlignedRetarded(final Vec3d vec3d1, final Vec3d vec3d2) {
        final BlockPos pos1 = new BlockPos(vec3d1);
        final BlockPos pos2 = new BlockPos(vec3d2.x, vec3d1.y, vec3d2.z);
        return pos1.equals((Object)pos2);
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static float[] calcAngleNoY(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difZ = to.z - from.z;
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0) };
    }
    
    public static Vec3d calculateLine(final Vec3d x1, final Vec3d x2, final double distance) {
        final double length = Math.sqrt(multiply(x2.x - x1.x) + multiply(x2.y - x1.y) + multiply(x2.z - x1.z));
        final double unitSlopeX = (x2.x - x1.x) / length;
        final double unitSlopeY = (x2.y - x1.y) / length;
        final double unitSlopeZ = (x2.z - x1.z) / length;
        final double x3 = x1.x + unitSlopeX * distance;
        final double y = x1.y + unitSlopeY * distance;
        final double z = x1.z + unitSlopeZ * distance;
        return new Vec3d(x3, y, z);
    }
    
    public static double multiply(final double one) {
        return one * one;
    }
    
    public static Vec3d extrapolatePlayerPosition(final EntityPlayer player, final int ticks) {
        final Vec3d lastPos = new Vec3d(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ);
        final Vec3d currentPos = new Vec3d(player.posX, player.posY, player.posZ);
        final double distance = multiply(player.motionX) + multiply(player.motionY) + multiply(player.motionZ);
        final Vec3d tempVec = calculateLine(lastPos, currentPos, distance * ticks);
        return new Vec3d(tempVec.x, player.posY, tempVec.z);
    }
    
    static {
        random = new Random();
    }
}
