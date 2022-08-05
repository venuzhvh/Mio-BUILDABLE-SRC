//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.combat;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import java.util.Iterator;
import net.minecraft.init.Blocks;
import il.dev.mio.api.util.plugs.MathUtil;
import il.dev.mio.api.util.interact.BlockUtil;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import il.dev.mio.Mio;
import net.minecraft.entity.Entity;
import il.dev.mio.api.util.interact.EntityUtil;
import il.dev.mio.api.util.world.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class BasePlace extends Module
{
    public Setting<Integer> enemyRange;
    public Setting<Double> placeReach;
    public Setting<Integer> blockDistance;
    public Setting<Boolean> rotate;
    public Setting<Boolean> packet;
    private Setting<Boolean> safety;
    private Setting<Integer> safeFactor;
    public EntityPlayer target;
    public boolean placed;
    public int oldSlot;
    public BlockPos currentPos;
    private boolean isSneaking;
    
    public BasePlace() {
        super("BasePlace", "Places obby at enemy's feet", Category.COMBAT, true, false, false);
        this.enemyRange = (Setting<Integer>)this.register(new Setting("EnemyRange", 5, 1, 8));
        this.placeReach = (Setting<Double>)this.register(new Setting("PlaceReach", 5.0, 1.0, 6.0));
        this.blockDistance = (Setting<Integer>)this.register(new Setting("BlockRange", 4, 1, 6));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", false));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", false));
        this.safety = (Setting<Boolean>)this.register(new Setting("Safety", false));
        this.safeFactor = (Setting<Integer>)this.register(new Setting("StopHealth", 4, 1, 15, v -> this.safety.getValue()));
        this.placed = false;
        this.oldSlot = -1;
        this.currentPos = null;
    }
    
    @Override
    public void onUpdate() {
        if (BasePlace.mc.player == null || BasePlace.mc.world == null) {
            return;
        }
        this.oldSlot = BasePlace.mc.player.inventory.currentItem;
        this.target = (EntityPlayer)this.getClosest();
        final int obiSlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (this.target != null) {
            if (BasePlace.mc.player.equals((Object)this.target)) {
                return;
            }
            if (this.safety.getValue() && EntityUtil.getHealth((Entity)BasePlace.mc.player) <= this.safeFactor.getValue()) {
                return;
            }
            if (Mio.friendManager.isFriend(this.target.getName())) {
                return;
            }
            final BlockPos playerPos = new BlockPos(Math.floor(this.target.posX), this.target.posY, Math.floor(this.target.posZ));
            if (!this.placed) {
                final BlockPos pos = this.getPos(this.target);
                if (!this.canPlace(pos, true, true)) {
                    return;
                }
                if (pos != null) {
                    if (obiSlot != -1) {
                        BasePlace.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(obiSlot));
                    }
                    this.isSneaking = BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet.getValue(), this.isSneaking);
                    BasePlace.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
                    this.placed = true;
                    this.currentPos = pos;
                }
            }
            if (this.currentPos != null) {
                if (this.placed) {
                    if (BasePlace.mc.player.getDistance((Entity)this.target) <= (double)this.enemyRange.getValue()) {
                        if (BasePlace.mc.player.getDistanceSq(this.currentPos) <= MathUtil.square(this.blockDistance.getValue())) {
                            if (playerPos.getY() > this.currentPos.getY()) {
                                if (!BlockUtil.isIntercepted(this.currentPos.up())) {
                                    if (BasePlace.mc.world.getBlockState(this.currentPos.up()).getBlock() == Blocks.AIR) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    this.placed = false;
                }
            }
        }
    }
    
    public BlockPos getPos(final EntityPlayer target) {
        BlockPos placePos = null;
        final BlockPos playerPos = new BlockPos(Math.floor(target.posX), target.posY, Math.floor(target.posZ));
        double dist = MathUtil.square(this.placeReach.getValue());
        for (final BlockPos pos : BlockUtil.getSphere(this.placeReach.getValue().floatValue(), true, false)) {
            if (pos.getY() >= playerPos.getY()) {
                continue;
            }
            if (pos == playerPos) {
                continue;
            }
            if (!this.canPlace(pos, true, true)) {
                continue;
            }
            if (BasePlace.mc.world.getBlockState(pos.up()).getBlock() != Blocks.AIR) {
                continue;
            }
            if (BlockUtil.isIntercepted(pos.up())) {
                continue;
            }
            if (BlockUtil.isIntercepted(pos.up())) {
                continue;
            }
            final double pDist = target.getDistanceSq(pos);
            if (pDist >= dist) {
                continue;
            }
            dist = pDist;
            placePos = pos;
        }
        return placePos;
    }
    
    public Entity getClosest() {
        Entity returnEntity = null;
        double dist = this.enemyRange.getValue();
        for (final Entity entity : BasePlace.mc.world.loadedEntityList) {
            if (entity instanceof EntityPlayer && entity != null && BasePlace.mc.player.getDistance(entity) <= dist && entity != BasePlace.mc.player) {
                final double pDist;
                if ((pDist = BasePlace.mc.player.getDistance(entity)) >= dist) {
                    continue;
                }
                dist = pDist;
                returnEntity = entity;
            }
        }
        return returnEntity;
    }
    
    public boolean canPlace(final BlockPos pos, final boolean obsidian, final boolean bedrock) {
        final Block block = BlockUtil.mc.world.getBlockState(pos).getBlock();
        return block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow || (block instanceof BlockObsidian && obsidian) || (block == Blocks.BEDROCK && bedrock);
    }
    
    @Override
    public void onDisable() {
        this.placed = false;
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
    }
}
