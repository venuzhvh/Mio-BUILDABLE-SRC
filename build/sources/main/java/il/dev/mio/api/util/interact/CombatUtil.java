//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util.interact;

import java.util.Arrays;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.util.math.RayTraceResult;
import java.util.ArrayList;
import il.dev.mio.Mio;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import il.dev.mio.api.util.world.HoleUtil;
import net.minecraft.init.Blocks;
import java.util.Iterator;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.inventory.ClickType;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import java.util.List;

public class CombatUtil
{
    public static final List<Block> blackList;
    public static final List<Block> shulkerList;
    private static Minecraft mc;
    public static final Vec3d[] cityOffsets;
    private static final List<Integer> invalidSlots;
    
    public static int findCrapple() {
        if (CombatUtil.mc.player == null) {
            return -1;
        }
        for (int x = 0; x < CombatUtil.mc.player.inventoryContainer.getInventory().size(); ++x) {
            if (!CombatUtil.invalidSlots.contains(x)) {
                final ItemStack stack = (ItemStack)CombatUtil.mc.player.inventoryContainer.getInventory().get(x);
                if (!stack.isEmpty()) {
                    if (stack.getItem().equals(Items.GOLDEN_APPLE) && stack.getItemDamage() != 1) {
                        return x;
                    }
                }
            }
        }
        return -1;
    }
    
    public static int findItemSlotDamage1(final Item i) {
        if (CombatUtil.mc.player == null) {
            return -1;
        }
        for (int x = 0; x < CombatUtil.mc.player.inventoryContainer.getInventory().size(); ++x) {
            if (!CombatUtil.invalidSlots.contains(x)) {
                final ItemStack stack = (ItemStack)CombatUtil.mc.player.inventoryContainer.getInventory().get(x);
                if (!stack.isEmpty()) {
                    if (stack.getItem().equals(i) && stack.getItemDamage() == 1) {
                        return x;
                    }
                }
            }
        }
        return -1;
    }
    
    public static EntityPlayer getTarget(final float range) {
        EntityPlayer currentTarget = null;
        for (int size = CombatUtil.mc.world.playerEntities.size(), i = 0; i < size; ++i) {
            final EntityPlayer player = CombatUtil.mc.world.playerEntities.get(i);
            if (!EntityUtil.isntValid((Entity)player, range)) {
                if (currentTarget == null) {
                    currentTarget = player;
                }
                else if (CombatUtil.mc.player.getDistanceSq((Entity)player) < CombatUtil.mc.player.getDistanceSq((Entity)currentTarget)) {
                    currentTarget = player;
                }
            }
        }
        return currentTarget;
    }
    
    public static int findItemSlot(final Item i) {
        if (CombatUtil.mc.player == null) {
            return -1;
        }
        for (int x = 0; x < CombatUtil.mc.player.inventoryContainer.getInventory().size(); ++x) {
            if (!CombatUtil.invalidSlots.contains(x)) {
                final ItemStack stack = (ItemStack)CombatUtil.mc.player.inventoryContainer.getInventory().get(x);
                if (!stack.isEmpty()) {
                    if (stack.getItem().equals(i)) {
                        return x;
                    }
                }
            }
        }
        return -1;
    }
    
    public static boolean isHoldingCrystal(final boolean onlyMainHand) {
        if (onlyMainHand) {
            return CombatUtil.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL;
        }
        return CombatUtil.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || CombatUtil.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
    }
    
    public static boolean requiredDangerSwitch(final double dangerRange) {
        final int dangerousCrystals = (int)CombatUtil.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).filter(entity -> CombatUtil.mc.player.getDistance(entity) <= dangerRange).filter(entity -> calculateDamage(entity.posX, entity.posY, entity.posZ, (Entity)CombatUtil.mc.player) >= CombatUtil.mc.player.getHealth() + CombatUtil.mc.player.getAbsorptionAmount()).count();
        return dangerousCrystals > 0;
    }
    
    public static boolean passesOffhandCheck(final double requiredHealth, final Item item, final boolean isCrapple) {
        final double totalPlayerHealth = CombatUtil.mc.player.getHealth() + CombatUtil.mc.player.getAbsorptionAmount();
        if (!isCrapple) {
            if (findItemSlot(item) == -1) {
                return false;
            }
        }
        else if (findCrapple() == -1) {
            return false;
        }
        return totalPlayerHealth >= requiredHealth;
    }
    
    public static void switchOffhandStrict(final int targetSlot, final int step) {
        switch (step) {
            case 0: {
                CombatUtil.mc.playerController.windowClick(CombatUtil.mc.player.inventoryContainer.windowId, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)CombatUtil.mc.player);
                break;
            }
            case 1: {
                CombatUtil.mc.playerController.windowClick(CombatUtil.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)CombatUtil.mc.player);
                break;
            }
            case 2: {
                CombatUtil.mc.playerController.windowClick(CombatUtil.mc.player.inventoryContainer.windowId, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)CombatUtil.mc.player);
                CombatUtil.mc.playerController.updateController();
                break;
            }
        }
    }
    
    public static void switchOffhandTotemNotStrict() {
        final int targetSlot = findItemSlot(Items.TOTEM_OF_UNDYING);
        if (targetSlot != -1) {
            CombatUtil.mc.playerController.windowClick(CombatUtil.mc.player.inventoryContainer.windowId, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)CombatUtil.mc.player);
            CombatUtil.mc.playerController.windowClick(CombatUtil.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)CombatUtil.mc.player);
            CombatUtil.mc.playerController.windowClick(CombatUtil.mc.player.inventoryContainer.windowId, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)CombatUtil.mc.player);
            CombatUtil.mc.playerController.updateController();
        }
    }
    
    public static void switchOffhandNonStrict(final int targetSlot) {
        CombatUtil.mc.playerController.windowClick(CombatUtil.mc.player.inventoryContainer.windowId, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)CombatUtil.mc.player);
        CombatUtil.mc.playerController.windowClick(CombatUtil.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)CombatUtil.mc.player);
        CombatUtil.mc.playerController.windowClick(CombatUtil.mc.player.inventoryContainer.windowId, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)CombatUtil.mc.player);
        CombatUtil.mc.playerController.updateController();
    }
    
    public static boolean canSeeBlock(final BlockPos pos) {
        return CombatUtil.mc.world.rayTraceBlocks(new Vec3d(CombatUtil.mc.player.posX, CombatUtil.mc.player.posY + CombatUtil.mc.player.getEyeHeight(), CombatUtil.mc.player.posZ), new Vec3d((double)pos.getX(), (double)(pos.getY() + 1.0f), (double)pos.getZ()), false, true, false) == null;
    }
    
    public static boolean placeBlock(final BlockPos blockPos, final boolean offhand, final boolean rotate, final boolean packetRotate, final boolean doSwitch, final boolean silentSwitch, final int toSwitch) {
        if (!checkCanPlace(blockPos)) {
            return false;
        }
        final EnumFacing placeSide = getPlaceSide(blockPos);
        final BlockPos adjacentBlock = blockPos.offset(placeSide);
        final EnumFacing opposingSide = placeSide.getOpposite();
        if (!CombatUtil.mc.world.getBlockState(adjacentBlock).getBlock().canCollideCheck(CombatUtil.mc.world.getBlockState(adjacentBlock), false)) {
            return false;
        }
        if (doSwitch) {
            if (silentSwitch) {
                CombatUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(toSwitch));
            }
            else if (CombatUtil.mc.player.inventory.currentItem != toSwitch) {
                CombatUtil.mc.player.inventory.currentItem = toSwitch;
            }
        }
        boolean isSneak = false;
        if (CombatUtil.blackList.contains(CombatUtil.mc.world.getBlockState(adjacentBlock).getBlock()) || CombatUtil.shulkerList.contains(CombatUtil.mc.world.getBlockState(adjacentBlock).getBlock())) {
            CombatUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CombatUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            isSneak = true;
        }
        final Vec3d hitVector = getHitVector(adjacentBlock, opposingSide);
        if (rotate) {
            final float[] angle = getLegitRotations(hitVector);
            CombatUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], CombatUtil.mc.player.onGround));
        }
        final EnumHand actionHand = offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        CombatUtil.mc.playerController.processRightClickBlock(CombatUtil.mc.player, CombatUtil.mc.world, adjacentBlock, opposingSide, hitVector, actionHand);
        CombatUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(actionHand));
        if (isSneak) {
            CombatUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CombatUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        return true;
    }
    
    private static Vec3d getHitVector(final BlockPos pos, final EnumFacing opposingSide) {
        return new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5).add(new Vec3d(opposingSide.getDirectionVec()).scale(0.5));
    }
    
    public static Vec3d getHitAddition(final double x, final double y, final double z, final BlockPos pos, final EnumFacing opposingSide) {
        return new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5).add(new Vec3d(opposingSide.getDirectionVec()).scale(0.5));
    }
    
    public static void betterRotate(final BlockPos blockPos, final EnumFacing opposite, final boolean packetRotate) {
        float offsetZ;
        float offsetX;
        float offsetY = offsetX = (offsetZ = 0.0f);
        switch (getPlaceSide(blockPos)) {
            case UP: {
                offsetZ = (offsetX = 0.5f);
                offsetY = 0.0f;
                break;
            }
            case DOWN: {
                offsetZ = (offsetX = 0.5f);
                offsetY = -0.5f;
                break;
            }
            case NORTH: {
                offsetX = 0.5f;
                offsetY = -0.5f;
                offsetZ = -0.5f;
                break;
            }
            case EAST: {
                offsetX = 0.5f;
                offsetY = -0.5f;
                offsetZ = 0.5f;
                break;
            }
            case SOUTH: {
                offsetX = 0.5f;
                offsetY = -0.5f;
                offsetZ = 0.5f;
                break;
            }
            case WEST: {
                offsetX = -0.5f;
                offsetY = -0.5f;
                offsetZ = 0.5f;
                break;
            }
        }
        final float[] angle = getLegitRotations(getHitAddition(offsetX, offsetY, offsetZ, blockPos, opposite));
        CombatUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], CombatUtil.mc.player.onGround));
    }
    
    private static EnumFacing getPlaceSide(final BlockPos blockPos) {
        EnumFacing placeableSide = null;
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos adjacent = blockPos.offset(side);
            if (CombatUtil.mc.world.getBlockState(adjacent).getBlock().canCollideCheck(CombatUtil.mc.world.getBlockState(adjacent), false) && !CombatUtil.mc.world.getBlockState(adjacent).getMaterial().isReplaceable()) {
                placeableSide = side;
            }
        }
        return placeableSide;
    }
    
    public static boolean checkCanPlace(final BlockPos pos) {
        if (!(CombatUtil.mc.world.getBlockState(pos).getBlock() instanceof BlockAir) && !(CombatUtil.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid)) {
            return false;
        }
        for (final Entity entity : CombatUtil.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityArrow)) {
                return false;
            }
        }
        return getPlaceSide(pos) != null;
    }
    
    public static boolean isInCity(final EntityPlayer player, final double range, final double placeRange, final boolean checkFace, final boolean topBlock, final boolean checkPlace, final boolean checkRange) {
        final BlockPos pos = new BlockPos(player.getPositionVector());
        for (final EnumFacing face : EnumFacing.values()) {
            if (face != EnumFacing.UP) {
                if (face != EnumFacing.DOWN) {
                    final BlockPos pos2 = pos.offset(face);
                    final BlockPos pos3 = pos2.offset(face);
                    if ((CombatUtil.mc.world.getBlockState(pos2).getBlock() == Blocks.AIR && ((CombatUtil.mc.world.getBlockState(pos3).getBlock() == Blocks.AIR && isHard(CombatUtil.mc.world.getBlockState(pos3.up()).getBlock())) || !checkFace) && !checkRange) || (CombatUtil.mc.player.getDistanceSq(pos3) <= placeRange * placeRange && CombatUtil.mc.player.getDistanceSq((Entity)player) <= range * range && isHard(CombatUtil.mc.world.getBlockState(pos.up(3)).getBlock())) || !topBlock) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean isHard(final Block block) {
        return block == Blocks.OBSIDIAN || block == Blocks.BEDROCK;
    }
    
    public static boolean canLegPlace(final EntityPlayer player, final double range) {
        int safety = 0;
        int blocksInRange = 0;
        for (final Vec3d vec : HoleUtil.cityOffsets) {
            final BlockPos pos = getFlooredPosition((Entity)player).add(vec.x, vec.y, vec.z);
            if (CombatUtil.mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN || CombatUtil.mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK) {
                ++safety;
            }
            if (CombatUtil.mc.player.getDistanceSq(pos) >= range * range) {
                ++blocksInRange;
            }
        }
        return safety == 4 && blocksInRange >= 1;
    }
    
    public static int getSafetyFactor(final BlockPos pos) {
        return 0;
    }
    
    public static boolean canPlaceCrystal(final BlockPos pos, final double range, final double wallsRange, final boolean raytraceCheck) {
        final BlockPos up = pos.up();
        final BlockPos up2 = up.up();
        final AxisAlignedBB bb = new AxisAlignedBB(up).expand(0.0, 1.0, 0.0);
        return ((CombatUtil.mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN || CombatUtil.mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK) && CombatUtil.mc.world.getBlockState(up).getBlock() == Blocks.AIR && CombatUtil.mc.world.getBlockState(up2).getBlock() == Blocks.AIR && CombatUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, bb).isEmpty() && CombatUtil.mc.player.getDistanceSq(pos) <= range * range && !raytraceCheck) || rayTraceRangeCheck(pos, wallsRange, 0.0);
    }
    
    public static int getVulnerability(final EntityPlayer player, final double range, final double placeRange, final double wallsRange, final double maxSelfDamage, final double maxFriendDamage, final double minDamage, final double friendRange, final double facePlaceHP, final int minArmor, final boolean cityCheck, final boolean rayTrace, final boolean lowArmorCheck, final boolean antiSuicide, final boolean antiFriendPop) {
        if (isInCity(player, range, placeRange, true, true, true, false) && cityCheck) {
            return 5;
        }
        if (getClosestValidPos(player, maxSelfDamage, maxFriendDamage, minDamage, placeRange, wallsRange, friendRange, rayTrace, antiSuicide, antiFriendPop, true) != null) {
            return 4;
        }
        if (player.getHealth() + player.getAbsorptionAmount() <= facePlaceHP) {
            return 3;
        }
        if (isArmorLow(player, minArmor, true) && lowArmorCheck) {
            return 2;
        }
        return 0;
    }
    
    public static Map<BlockPos, Double> mapBlockDamage(final EntityPlayer player, final double maxSelfDamage, final double maxFriendDamage, final double minDamage, final double placeRange, final double wallsRange, final double friendRange, final boolean rayTrace, final boolean antiSuicide, final boolean antiFriendPop) {
        final Map<BlockPos, Double> damageMap = new HashMap<BlockPos, Double>();
        for (final BlockPos pos : getSphere(new BlockPos((Vec3i)getFlooredPosition((Entity)CombatUtil.mc.player)), (float)placeRange, (int)placeRange, false, true, 0)) {
            if (!canPlaceCrystal(pos, placeRange, wallsRange, rayTrace)) {
                continue;
            }
            if (!checkFriends(pos, maxFriendDamage, friendRange, antiFriendPop)) {
                continue;
            }
            if (!checkSelf(pos, maxSelfDamage, antiSuicide)) {
                continue;
            }
            if (rayTrace && !rayTraceRangeCheck(pos, wallsRange, 0.0)) {
                continue;
            }
            final double damage = calculateDamage(pos, (Entity)player);
            if (damage < minDamage) {
                continue;
            }
            damageMap.put(pos, damage);
        }
        return damageMap;
    }
    
    public static boolean checkFriends(final BlockPos pos, final double maxFriendDamage, final double friendRange, final boolean antiFriendPop) {
        for (final EntityPlayer player : CombatUtil.mc.world.playerEntities) {
            if (CombatUtil.mc.player.getDistanceSq((Entity)player) > friendRange * friendRange) {
                continue;
            }
            if (calculateDamage(pos, (Entity)player) > maxFriendDamage) {
                return false;
            }
            if (calculateDamage(pos, (Entity)player) > player.getHealth() + player.getAbsorptionAmount() && antiFriendPop) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean checkFriends(final EntityEnderCrystal crystal, final double maxFriendDamage, final double friendRange, final boolean antiFriendPop) {
        for (final EntityPlayer player : CombatUtil.mc.world.playerEntities) {
            if (CombatUtil.mc.player.getDistanceSq((Entity)player) > friendRange * friendRange) {
                continue;
            }
            if (calculateDamage((Entity)crystal, (Entity)player) > maxFriendDamage) {
                return false;
            }
            if (calculateDamage((Entity)crystal, (Entity)player) > player.getHealth() + player.getAbsorptionAmount() && antiFriendPop) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean checkSelf(final BlockPos pos, final double maxSelfDamage, final boolean antiSuicide) {
        final boolean willPopSelf = calculateDamage(pos, (Entity)CombatUtil.mc.player) > CombatUtil.mc.player.getHealth() + CombatUtil.mc.player.getAbsorptionAmount();
        final boolean willDamageSelf = calculateDamage(pos, (Entity)CombatUtil.mc.player) > maxSelfDamage;
        return (!antiSuicide || !willPopSelf) && !willDamageSelf;
    }
    
    public static boolean checkSelf(final EntityEnderCrystal crystal, final double maxSelfDamage, final boolean antiSuicide) {
        final boolean willPopSelf = calculateDamage((Entity)crystal, (Entity)CombatUtil.mc.player) > CombatUtil.mc.player.getHealth() + CombatUtil.mc.player.getAbsorptionAmount();
        final boolean willDamageSelf = calculateDamage((Entity)crystal, (Entity)CombatUtil.mc.player) > maxSelfDamage;
        return (!antiSuicide || !willPopSelf) && !willDamageSelf;
    }
    
    public static boolean isPosValid(final EntityPlayer player, final BlockPos pos, final double maxSelfDamage, final double maxFriendDamage, final double minDamage, final double placeRange, final double wallsRange, final double friendRange, final boolean rayTrace, final boolean antiSuicide, final boolean antiFriendPop) {
        if (pos == null) {
            return false;
        }
        if (!isHard(CombatUtil.mc.world.getBlockState(pos).getBlock())) {
            return false;
        }
        if (!canPlaceCrystal(pos, placeRange, wallsRange, rayTrace)) {
            return false;
        }
        if (!checkFriends(pos, maxFriendDamage, friendRange, antiFriendPop)) {
            return false;
        }
        if (!checkSelf(pos, maxSelfDamage, antiSuicide)) {
            return false;
        }
        final double damage = calculateDamage(pos, (Entity)player);
        return damage >= minDamage && (!rayTrace || rayTraceRangeCheck(pos, wallsRange, 0.0));
    }
    
    public static BlockPos getClosestValidPos(final EntityPlayer player, final double maxSelfDamage, final double maxFriendDamage, final double minDamage, final double placeRange, final double wallsRange, final double friendRange, final boolean rayTrace, final boolean antiSuicide, final boolean antiFriendPop, final boolean multiplace) {
        double highestDamage = -1.0;
        BlockPos finalPos = null;
        if (player == null) {
            return null;
        }
        final List<BlockPos> placeLocations = getSphere(new BlockPos((Vec3i)getFlooredPosition((Entity)CombatUtil.mc.player)), (float)placeRange, (int)placeRange, false, true, 0);
        placeLocations.sort(Comparator.comparing(blockPos -> CombatUtil.mc.player.getDistanceSq(blockPos)));
        for (final BlockPos pos : placeLocations) {
            if (!canPlaceCrystal(pos, placeRange, wallsRange, rayTrace)) {
                continue;
            }
            if (rayTrace && !rayTraceRangeCheck(pos, wallsRange, 0.0)) {
                continue;
            }
            final double damage = calculateDamage(pos, (Entity)player);
            if (damage < minDamage) {
                continue;
            }
            if (!checkFriends(pos, maxFriendDamage, friendRange, antiFriendPop)) {
                continue;
            }
            if (!checkSelf(pos, maxSelfDamage, antiSuicide)) {
                continue;
            }
            if (damage > 15.0) {
                return pos;
            }
            if (damage <= highestDamage) {
                continue;
            }
            highestDamage = damage;
            finalPos = pos;
        }
        return finalPos;
    }
    
    public static BlockPos getClosestValidPosMultiThread(final EntityPlayer player, final double maxSelfDamage, final double maxFriendDamage, final double minDamage, final double placeRange, final double wallsRange, final double friendRange, final boolean rayTrace, final boolean antiSuicide, final boolean antiFriendPop) {
        final List<ValidPosThread> threads = new CopyOnWriteArrayList<ValidPosThread>();
        BlockPos finalPos = null;
        for (final BlockPos pos : getSphere(new BlockPos(player.getPositionVector()), 13.0f, 13, false, true, 0)) {
            final ValidPosThread thread2 = new ValidPosThread(player, pos, maxSelfDamage, maxFriendDamage, minDamage, placeRange, wallsRange, friendRange, rayTrace, antiSuicide, antiFriendPop);
            threads.add(thread2);
            thread2.start();
        }
        boolean areAllInvalid = false;
        do {
            for (final ValidPosThread thread2 : threads) {
                if (thread2.isInterrupted() && thread2.isValid) {
                    finalPos = thread2.pos;
                }
            }
            areAllInvalid = threads.stream().noneMatch(thread -> thread.isValid && thread.isInterrupted());
        } while (finalPos == null && !areAllInvalid);
        Mio.LOGGER.info((finalPos == null) ? "pos was null" : finalPos.toString());
        return finalPos;
    }
    
    public static List<BlockPos> getSphere(final BlockPos pos, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = pos.getX();
        final int cy = pos.getY();
        final int cz = pos.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    public static boolean isArmorLow(final EntityPlayer player, final int durability, final boolean checkDurability) {
        for (final ItemStack piece : player.inventory.armorInventory) {
            if (piece == null) {
                return true;
            }
            if (checkDurability && getItemDamage(piece) < durability) {
                return true;
            }
        }
        return false;
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static boolean rayTraceRangeCheck(final Entity target, final double range) {
        final boolean isVisible = CombatUtil.mc.player.canEntityBeSeen(target);
        return !isVisible || CombatUtil.mc.player.getDistanceSq(target) <= range * range;
    }
    
    public static boolean rayTraceRangeCheck(final BlockPos pos, final double range, final double height) {
        final RayTraceResult result = CombatUtil.mc.world.rayTraceBlocks(new Vec3d(CombatUtil.mc.player.posX, CombatUtil.mc.player.posY + CombatUtil.mc.player.getEyeHeight(), CombatUtil.mc.player.posZ), new Vec3d((double)pos.getX(), pos.getY() + height, (double)pos.getZ()), false, true, false);
        return result == null || CombatUtil.mc.player.getDistanceSq(pos) <= range * range;
    }
    
    public static BlockPos getFlooredPosition(final Entity entity) {
        return new BlockPos(Math.floor(entity.posX), Math.floor(entity.posY), Math.floor(entity.posZ));
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = 0.0;
        try {
            blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        }
        catch (Exception ex) {}
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)Minecraft.getMinecraft().world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = 0;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            }
            catch (Exception ex) {}
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = Minecraft.getMinecraft().world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float calculateDamage(final Entity crystal, final Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }
    
    public static float calculateDamage(final BlockPos pos, final Entity entity) {
        return calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, entity);
    }
    
    public static Vec3d interpolateEntity(final Entity entity) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * CombatUtil.mc.getRenderPartialTicks(), entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * CombatUtil.mc.getRenderPartialTicks(), entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * CombatUtil.mc.getRenderPartialTicks());
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = new Vec3d(CombatUtil.mc.player.posX, CombatUtil.mc.player.posY + CombatUtil.mc.player.getEyeHeight(), CombatUtil.mc.player.posZ);
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { CombatUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - CombatUtil.mc.player.rotationYaw), CombatUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - CombatUtil.mc.player.rotationPitch) };
    }
    
    static {
        blackList = Arrays.asList((Block)Blocks.TALLGRASS, Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR);
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        CombatUtil.mc = Minecraft.getMinecraft();
        cityOffsets = new Vec3d[] { new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(2.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 2.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -2.0) };
        invalidSlots = Arrays.asList(0, 5, 6, 7, 8);
    }
    
    public static class ValidPosThread extends Thread
    {
        BlockPos pos;
        EntityPlayer player;
        double maxSelfDamage;
        double maxFriendDamage;
        double minDamage;
        double placeRange;
        double wallsRange;
        double friendRange;
        boolean rayTrace;
        boolean antiSuicide;
        boolean antiFriendPop;
        public float damage;
        public boolean isValid;
        public CombatPosInfo info;
        
        public ValidPosThread(final EntityPlayer player, final BlockPos pos, final double maxSelfDamage, final double maxFriendDamage, final double minDamage, final double placeRange, final double wallsRange, final double friendRange, final boolean rayTrace, final boolean antiSuicide, final boolean antiFriendPop) {
            super("Break");
            this.pos = pos;
            this.maxSelfDamage = maxSelfDamage;
            this.maxFriendDamage = maxFriendDamage;
            this.minDamage = minDamage;
            this.placeRange = placeRange;
            this.wallsRange = wallsRange;
            this.friendRange = friendRange;
            this.rayTrace = rayTrace;
            this.antiSuicide = antiSuicide;
            this.antiFriendPop = antiFriendPop;
            this.player = player;
        }
        
        @Override
        public void run() {
            if (CombatUtil.mc.player.getDistanceSq(this.pos) <= this.placeRange * this.placeRange && CombatUtil.canPlaceCrystal(this.pos, this.placeRange, this.wallsRange, this.rayTrace) && CombatUtil.checkFriends(this.pos, this.maxFriendDamage, this.friendRange, this.antiFriendPop) && CombatUtil.checkSelf(this.pos, this.maxSelfDamage, this.antiSuicide)) {
                this.damage = CombatUtil.calculateDamage(this.pos, (Entity)this.player);
                if (this.damage >= this.minDamage && (!this.rayTrace || CombatUtil.rayTraceRangeCheck(this.pos, this.wallsRange, 0.0))) {
                    this.isValid = true;
                    this.info = new CombatPosInfo(this.player, this.pos, this.damage);
                    Mio.LOGGER.info("Pos was valid.");
                    return;
                }
            }
            this.isValid = false;
            this.info = new CombatPosInfo(this.player, this.pos, -1.0f);
            Mio.LOGGER.info("Pos was invalid.");
        }
    }
    
    public static class CombatPosInfo
    {
        public EntityPlayer player;
        public BlockPos pos;
        public float damage;
        
        public CombatPosInfo(final EntityPlayer player, final BlockPos pos, final float damage) {
            this.pos = pos;
            this.damage = damage;
            this.player = player;
        }
    }
}
