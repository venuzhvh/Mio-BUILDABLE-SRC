//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util.world;

import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.util.math.Vec3i;
import net.minecraft.entity.Entity;
import il.dev.mio.api.util.interact.CombatUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import java.util.List;

public class HoleUtil
{
    public static final List<BlockPos> holeBlocks;
    private static Minecraft mc;
    public static final Vec3d[] cityOffsets;
    
    public static boolean isInHole() {
        final Vec3d playerPos = CombatUtil.interpolateEntity((Entity)HoleUtil.mc.player);
        final BlockPos blockpos = new BlockPos(playerPos.x, playerPos.y, playerPos.z);
        int size = 0;
        for (final BlockPos bPos : HoleUtil.holeBlocks) {
            if (CombatUtil.isHard(HoleUtil.mc.world.getBlockState(blockpos.add((Vec3i)bPos)).getBlock())) {
                ++size;
            }
        }
        return size == 5;
    }
    
    static {
        holeBlocks = Arrays.asList(new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1));
        HoleUtil.mc = Minecraft.getMinecraft();
        cityOffsets = new Vec3d[] { new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0) };
    }
}
