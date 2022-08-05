// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util.world;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockPosWithFacing
{
    public BlockPos blockPos;
    public EnumFacing enumFacing;
    
    public BlockPosWithFacing(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
    }
}
