//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.combat;

import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import il.dev.mio.api.util.interact.BlockInteractionUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.util.Iterator;
import il.dev.mio.api.util.interact.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import il.dev.mio.api.util.world.WorldUtil;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import il.dev.mio.mod.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class AutoFeetWeb extends Module
{
    public Setting<Boolean> alwayson;
    public Setting<Boolean> rotate;
    public Setting<Integer> range;
    int new_slot;
    boolean sneak;
    
    public AutoFeetWeb() {
        super("AutoFeetWeb", "Places webs at your feet", Category.COMBAT, true, false, false);
        this.alwayson = (Setting<Boolean>)this.register(new Setting("Smart", false));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", true));
        this.range = (Setting<Integer>)this.register(new Setting("EnemyRange", 4, 0, 8, v -> this.alwayson.getValue()));
        this.new_slot = -1;
        this.sneak = false;
    }
    
    @Override
    public void onEnable() {
        if (AutoFeetWeb.mc.player != null) {
            this.new_slot = this.find_in_hotbar();
            if (this.new_slot == -1) {
                Command.sendMessage("[" + this.getDisplayName() + "] " + ChatFormatting.RED + "No Webs in hotbar. disabling...");
                this.toggle();
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (AutoFeetWeb.mc.player != null && this.sneak) {
            AutoFeetWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoFeetWeb.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneak = false;
        }
    }
    
    @Override
    public void onUpdate() {
        if (AutoFeetWeb.mc.player == null) {
            return;
        }
        if (this.alwayson.getValue()) {
            final EntityPlayer target = this.find_closest_target();
            if (target == null) {
                return;
            }
            if (AutoFeetWeb.mc.player.getDistance((Entity)target) < this.range.getValue() && this.is_surround()) {
                final int last_slot = AutoFeetWeb.mc.player.inventory.currentItem;
                AutoFeetWeb.mc.player.inventory.currentItem = this.new_slot;
                AutoFeetWeb.mc.playerController.updateController();
                this.place_blocks(WorldUtil.GetLocalPlayerPosFloored());
                AutoFeetWeb.mc.player.inventory.currentItem = last_slot;
            }
        }
        else {
            final int last_slot2 = AutoFeetWeb.mc.player.inventory.currentItem;
            AutoFeetWeb.mc.player.inventory.currentItem = this.new_slot;
            AutoFeetWeb.mc.playerController.updateController();
            this.place_blocks(WorldUtil.GetLocalPlayerPosFloored());
            AutoFeetWeb.mc.player.inventory.currentItem = last_slot2;
            this.disable();
        }
    }
    
    public EntityPlayer find_closest_target() {
        if (AutoFeetWeb.mc.world.playerEntities.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : AutoFeetWeb.mc.world.playerEntities) {
            if (target == AutoFeetWeb.mc.player) {
                continue;
            }
            if (!EntityUtil.isLiving((Entity)target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (closestTarget != null && AutoFeetWeb.mc.player.getDistance((Entity)target) > AutoFeetWeb.mc.player.getDistance((Entity)closestTarget)) {
                continue;
            }
            closestTarget = target;
        }
        return closestTarget;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoFeetWeb.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Item.getItemById(30)) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean is_surround() {
        final BlockPos player_block = WorldUtil.GetLocalPlayerPosFloored();
        return AutoFeetWeb.mc.world.getBlockState(player_block.east()).getBlock() != Blocks.AIR && AutoFeetWeb.mc.world.getBlockState(player_block.west()).getBlock() != Blocks.AIR && AutoFeetWeb.mc.world.getBlockState(player_block.north()).getBlock() != Blocks.AIR && AutoFeetWeb.mc.world.getBlockState(player_block.south()).getBlock() != Blocks.AIR && AutoFeetWeb.mc.world.getBlockState(player_block).getBlock() == Blocks.AIR;
    }
    
    private void place_blocks(final BlockPos pos) {
        if (!AutoFeetWeb.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return;
        }
        if (!BlockInteractionUtil.checkForNeighbours(pos)) {
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (BlockInteractionUtil.canBeClicked(neighbor)) {
                if (BlockInteractionUtil.blackList.contains(AutoFeetWeb.mc.world.getBlockState(neighbor).getBlock())) {
                    AutoFeetWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoFeetWeb.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    this.sneak = true;
                }
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (this.rotate.getValue()) {
                    BlockInteractionUtil.faceVectorPacketInstant(hitVec);
                }
                AutoFeetWeb.mc.playerController.processRightClickBlock(AutoFeetWeb.mc.player, AutoFeetWeb.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                AutoFeetWeb.mc.player.swingArm(EnumHand.MAIN_HAND);
                return;
            }
        }
    }
}
