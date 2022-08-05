//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util.plugs;

import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.Iterator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import il.dev.mio.api.util.IMinecraftUtil;

public class TrapUtil implements IMinecraftUtil
{
    private static boolean unshift;
    
    public static List<BlockPos> getSphere(final float radius, final boolean ignoreAir) {
        final ArrayList<BlockPos> sphere = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(TrapUtil.mc.player.getPositionVector());
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        final int radiuss = (int)radius;
        for (int x = posX - radiuss; x <= posX + radius; ++x) {
            for (int z = posZ - radiuss; z <= posZ + radius; ++z) {
                for (int y = posY - radiuss; y < posY + radius; ++y) {
                    if ((posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y) < radius * radius) {
                        final BlockPos position = new BlockPos(x, y, z);
                        if (!ignoreAir || TrapUtil.mc.world.getBlockState(position).getBlock() != Blocks.AIR) {
                            sphere.add(position);
                        }
                    }
                }
            }
        }
        return sphere;
    }
    
    public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand, final boolean swing) {
        if (pos == null || hand == null) {
            return;
        }
        final EnumFacing facing = EnumFacing.UP;
        TrapUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
        if (swing) {
            TrapUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        }
        TrapUtil.mc.playerController.updateController();
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean check) {
        return canPlaceCrystal(blockPos, check, true);
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean check, final boolean entity) {
        if (TrapUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && TrapUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        final BlockPos boost = blockPos.add(0, 1, 0);
        return TrapUtil.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && TrapUtil.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock() == Blocks.AIR && (!entity || TrapUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB((double)boost.getX(), (double)boost.getY(), (double)boost.getZ(), (double)(boost.getX() + 1), (double)(boost.getY() + (check ? 2 : 1)), (double)(boost.getZ() + 1)), e -> !(e instanceof EntityEnderCrystal)).size() == 0);
    }
    
    public static boolean placeBlock(final BlockPos pos) {
        final Block block = TrapUtil.mc.world.getBlockState(pos).getBlock();
        final EnumFacing direction = calcSide(pos);
        if (direction == null) {
            return false;
        }
        final boolean activated = block.onBlockActivated((World)TrapUtil.mc.world, pos, TrapUtil.mc.world.getBlockState(pos), (EntityPlayer)TrapUtil.mc.player, EnumHand.MAIN_HAND, direction, 0.0f, 0.0f, 0.0f);
        if (activated) {
            TrapUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)TrapUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        TrapUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos.offset(direction), direction.getOpposite(), EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        TrapUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        if (activated || TrapUtil.unshift) {
            TrapUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)TrapUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            TrapUtil.unshift = false;
        }
        TrapUtil.mc.playerController.updateController();
        return true;
    }
    
    public static EnumFacing calcSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final IBlockState offsetState = TrapUtil.mc.world.getBlockState(pos.offset(side));
            final boolean activated = offsetState.getBlock().onBlockActivated((World)TrapUtil.mc.world, pos, offsetState, (EntityPlayer)TrapUtil.mc.player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
            if (activated) {
                TrapUtil.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)TrapUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                TrapUtil.unshift = true;
            }
            if (offsetState.getBlock().canCollideCheck(offsetState, false) && !offsetState.getMaterial().isReplaceable()) {
                return side;
            }
        }
        return null;
    }
    
    public static List<BlockPos> getUnsafePositions(final Vec3d vector, final int level) {
        return getLevels(level).stream().map(vec -> new BlockPos(vector).add(vec.x, vec.y, vec.z)).filter(bp -> TrapUtil.mc.world.getBlockState(bp).getMaterial().isReplaceable()).collect(Collectors.toList());
    }
    
    public static List<Vec3d> getLevels(final int y) {
        return Arrays.asList(new Vec3d(-1.0, (double)y, 0.0), new Vec3d(1.0, (double)y, 0.0), new Vec3d(0.0, (double)y, 1.0), new Vec3d(0.0, (double)y, -1.0));
    }
    
    public static int isPlaceable(final BlockPos bp) {
        if (TrapUtil.mc.world == null || bp == null) {
            return 1;
        }
        if (!TrapUtil.mc.world.getBlockState(bp).getMaterial().isReplaceable()) {
            return 1;
        }
        for (Entity e : TrapUtil.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(bp))) {
            if (!(e instanceof EntityXPOrb)) {
                if (e instanceof EntityItem) {
                    continue;
                }
                if (e instanceof EntityPlayer) {
                    return 1;
                }
                return -1;
            }
        }
        return 0;
    }
    
    public static BlockResistance getBlockResistance(final BlockPos block) {
        if (TrapUtil.mc.world.isAirBlock(block)) {
            return BlockResistance.Blank;
        }
        if (TrapUtil.mc.world.getBlockState(block).getBlock().getBlockHardness(TrapUtil.mc.world.getBlockState(block), (World)TrapUtil.mc.world, block) != -1.0f && !TrapUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) && !TrapUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) && !TrapUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) && !TrapUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)) {
            return BlockResistance.Breakable;
        }
        if (TrapUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) || TrapUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) || TrapUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) || TrapUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)) {
            return BlockResistance.Resistant;
        }
        if (TrapUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.BEDROCK)) {
            return BlockResistance.Unbreakable;
        }
        return null;
    }
    
    static {
        TrapUtil.unshift = false;
    }
    
    enum BlockResistance
    {
        Unbreakable, 
        Blank, 
        Breakable, 
        Resistant;
    }
}
