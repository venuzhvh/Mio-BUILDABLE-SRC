//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import il.dev.mio.Mio;
import net.minecraft.init.Blocks;
import il.dev.mio.api.util.interact.EntityUtil;
import il.dev.mio.api.util.render.RenderUtil;
import java.awt.Color;
import il.dev.mio.api.event.events.Render3DEvent;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.EnumHand;
import il.dev.mio.api.util.plugs.TestUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockEnderChest;
import il.dev.mio.api.util.world.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import il.dev.mio.api.event.events.PacketEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import net.minecraft.util.math.BlockPos;
import il.dev.mio.mod.modules.Module;

public class HoleFiller extends Module
{
    private static BlockPos PlayerPos;
    private final Setting<Page> page;
    private Setting<Double> range;
    private Setting<Boolean> smart;
    private Setting<Integer> smartRange;
    public Setting<Boolean> esp;
    private final Setting<RenderMode> mode;
    private Setting<Float> lineWidth;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> alpha;
    private BlockPos render;
    private Entity renderEnt;
    private EntityPlayer closestTarget;
    private int newSlot;
    double d;
    private static boolean isSpoofingAngles;
    private static float yaw;
    private static float pitch;
    private static HoleFiller INSTANCE;
    
    public HoleFiller() {
        super("HoleFiller", "Fills holes around you.", Category.COMBAT, true, false, true);
        this.page = (Setting<Page>)this.register(new Setting("Settings", Page.Global));
        this.range = (Setting<Double>)this.register(new Setting("Range", 4.5, 0.1, 4.5, v -> this.page.getValue() == Page.Global));
        this.smart = (Setting<Boolean>)this.register(new Setting("Smart", false, v -> this.page.getValue() == Page.Global));
        this.smartRange = (Setting<Integer>)this.register(new Setting("Smart Range", 4, v -> this.page.getValue() == Page.Global));
        this.esp = (Setting<Boolean>)this.register(new Setting("Render", true, v -> this.page.getValue() == Page.Global));
        this.mode = (Setting<RenderMode>)this.register(new Setting("RenderMode", RenderMode.Both, v -> this.page.getValue() == Page.Global && this.esp.getValue()));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", 1.7f, 0.1f, 5.0f, v -> this.page.getValue() == Page.Colors));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 150, 0, 255, v -> this.page.getValue() == Page.Colors));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 75, 0, 255, v -> this.page.getValue() == Page.Colors));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 150, 0, 255, v -> this.page.getValue() == Page.Colors));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", 80, 0, 255, v -> this.page.getValue() == Page.Colors));
        this.setInstance();
    }
    
    public static HoleFiller getInstance() {
        if (HoleFiller.INSTANCE == null) {
            HoleFiller.INSTANCE = new HoleFiller();
        }
        return HoleFiller.INSTANCE;
    }
    
    private void setInstance() {
        HoleFiller.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        final Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && HoleFiller.isSpoofingAngles) {
            ((CPacketPlayer)packet).yaw = HoleFiller.yaw;
            ((CPacketPlayer)packet).pitch = HoleFiller.pitch;
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onUpdate() {
        if (HoleFiller.mc.world == null) {
            return;
        }
        if (this.smart.getValue()) {
            this.findClosestTarget();
        }
        final List<BlockPos> blocks = this.findCrystalBlocks();
        BlockPos q = null;
        final int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        final int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (obbySlot == -1 && eChestSot == -1) {
            return;
        }
        final int originalSlot = HoleFiller.mc.player.inventory.currentItem;
        for (final BlockPos blockPos : blocks) {
            if (!HoleFiller.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(blockPos)).isEmpty()) {
                continue;
            }
            if (this.smart.getValue() && this.isInRange(blockPos)) {
                q = blockPos;
            }
            else {
                q = blockPos;
            }
        }
        this.render = q;
        if (q != null && HoleFiller.mc.player.onGround) {
            HoleFiller.mc.player.inventory.currentItem = ((obbySlot == -1) ? eChestSot : obbySlot);
            HoleFiller.mc.playerController.updateController();
            this.lookAtPacket(q.getX() + 0.5, q.getY() - 0.5, q.getZ() + 0.5, (EntityPlayer)HoleFiller.mc.player);
            TestUtil.placeBlock(this.render);
            if (HoleFiller.mc.player.inventory.currentItem != originalSlot) {
                HoleFiller.mc.player.inventory.currentItem = originalSlot;
                HoleFiller.mc.playerController.updateController();
            }
            HoleFiller.mc.player.swingArm(EnumHand.MAIN_HAND);
            HoleFiller.mc.player.inventory.currentItem = originalSlot;
            resetRotation();
        }
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.esp.getValue() && this.render != null) {
            final Color color = new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), 255);
            RenderUtil.drawBoxESP(this.render, color, false, color, this.lineWidth.getValue(), this.mode.getValue().equals(RenderMode.Both) || this.mode.getValue().equals(RenderMode.Outline), this.mode.getValue().equals(RenderMode.Both) || this.mode.getValue().equals(RenderMode.Solid), this.alpha.getValue(), true, 0.0, false, false, false, false, 255);
        }
    }
    
    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float)v[0], (float)v[1]);
    }
    
    private boolean IsHole(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 0, 0);
        final BlockPos boost3 = blockPos.add(0, 0, -1);
        final BlockPos boost4 = blockPos.add(1, 0, 0);
        final BlockPos boost5 = blockPos.add(-1, 0, 0);
        final BlockPos boost6 = blockPos.add(0, 0, 1);
        final BlockPos boost7 = blockPos.add(0, 2, 0);
        final BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        final BlockPos boost9 = blockPos.add(0, -1, 0);
        return HoleFiller.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && HoleFiller.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && HoleFiller.mc.world.getBlockState(boost7).getBlock() == Blocks.AIR && (HoleFiller.mc.world.getBlockState(boost3).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK) && (HoleFiller.mc.world.getBlockState(boost4).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK) && (HoleFiller.mc.world.getBlockState(boost5).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK) && (HoleFiller.mc.world.getBlockState(boost6).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK) && HoleFiller.mc.world.getBlockState(boost8).getBlock() == Blocks.AIR && (HoleFiller.mc.world.getBlockState(boost9).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK);
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleFiller.mc.player.posX), Math.floor(HoleFiller.mc.player.posY), Math.floor(HoleFiller.mc.player.posZ));
    }
    
    public BlockPos getClosestTargetPos() {
        if (this.closestTarget != null) {
            return new BlockPos(Math.floor(this.closestTarget.posX), Math.floor(this.closestTarget.posY), Math.floor(this.closestTarget.posZ));
        }
        return null;
    }
    
    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)HoleFiller.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target != HoleFiller.mc.player && !Mio.friendManager.isFriend(target.getName()) && EntityUtil.isLiving((Entity)target)) {
                if (target.getHealth() <= 0.0f) {
                    continue;
                }
                if (this.closestTarget == null) {
                    this.closestTarget = target;
                }
                else {
                    if (HoleFiller.mc.player.getDistance((Entity)target) >= HoleFiller.mc.player.getDistance((Entity)this.closestTarget)) {
                        continue;
                    }
                    this.closestTarget = target;
                }
            }
        }
    }
    
    private boolean isInRange(final BlockPos blockPos) {
        final NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
        return positions.contains((Object)blockPos);
    }
    
    private List<BlockPos> findCrystalBlocks() {
        final NonNullList positions = NonNullList.create();
        if (this.smart.getValue() && this.closestTarget != null) {
            positions.addAll((Collection)this.getSphere(this.getClosestTargetPos(), this.smartRange.getValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsHole).filter(this::isInRange).collect(Collectors.toList()));
        }
        else if (!this.smart.getValue()) {
            positions.addAll((Collection)this.getSphere(getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
        }
        return (List<BlockPos>)positions;
    }
    
    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = sphere ? (cy - (int)r) : cy;
                while (true) {
                    final float f = (float)y;
                    final float f2 = sphere ? (cy + r) : ((float)(cy + h));
                    if (f >= f2) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleblocks;
    }
    
    private static void setYawAndPitch(final float yaw1, final float pitch1) {
        HoleFiller.yaw = yaw1;
        HoleFiller.pitch = pitch1;
        HoleFiller.isSpoofingAngles = true;
    }
    
    private static void resetRotation() {
        if (HoleFiller.isSpoofingAngles) {
            HoleFiller.yaw = HoleFiller.mc.player.rotationYaw;
            HoleFiller.pitch = HoleFiller.mc.player.rotationPitch;
            HoleFiller.isSpoofingAngles = false;
        }
    }
    
    @Override
    public void onDisable() {
        this.closestTarget = null;
        this.render = null;
        resetRotation();
        super.onDisable();
    }
    
    static {
        HoleFiller.INSTANCE = new HoleFiller();
    }
    
    private enum RenderMode
    {
        Solid, 
        Outline, 
        Both;
    }
    
    private enum Page
    {
        Global, 
        Colors;
    }
}
