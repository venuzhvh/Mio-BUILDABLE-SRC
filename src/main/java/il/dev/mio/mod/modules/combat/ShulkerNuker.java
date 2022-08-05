//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.combat;

import il.dev.mio.api.mixin.accessors.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import il.dev.mio.api.event.events.PacketEvent;
import il.dev.mio.api.util.world.RotationUtil;
import java.util.Iterator;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.entity.player.EntityPlayer;
import il.dev.mio.api.util.world.InventoryUtil;
import net.minecraft.init.Items;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ShulkerNuker extends Module
{
    private final Setting<Integer> range;
    private final Setting<Boolean> autoSwitch;
    private final Setting<Boolean> rotate;
    float yaw;
    float pitch;
    boolean isSpoofing;
    
    public ShulkerNuker() {
        super("AntiRegear", "shulker nuker", Category.COMBAT, true, false, true);
        this.range = (Setting<Integer>)this.register(new Setting("Range", 5, 0, 8));
        this.autoSwitch = (Setting<Boolean>)this.register(new Setting("AutoSwitch", true));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", true));
    }
    
    @Override
    public void onUpdate() {
        if (this.getTargetBlock() != null) {
            final int originalSlot = ShulkerNuker.mc.player.inventory.currentItem;
            if (this.autoSwitch.getValue() && InventoryUtil.findItemInHotbar(Items.DIAMOND_PICKAXE) != -1) {
                final int pickSlot = InventoryUtil.findItemInHotbar(Items.DIAMOND_PICKAXE);
                ShulkerNuker.mc.player.inventory.currentItem = pickSlot;
            }
            if (this.rotate.getValue()) {
                this.isSpoofing = true;
                this.lookAtPacket(this.getTargetBlock().getPos().getX() + 0.5, this.getTargetBlock().getPos().getY() - 1, this.getTargetBlock().getPos().getZ() + 0.5, (EntityPlayer)ShulkerNuker.mc.player);
            }
            ShulkerNuker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.getTargetBlock().getPos(), EnumFacing.SOUTH));
            ShulkerNuker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.getTargetBlock().getPos(), EnumFacing.SOUTH));
            ShulkerNuker.mc.player.inventory.currentItem = originalSlot;
        }
        if (this.rotate.getValue() && this.getTargetBlock() == null) {
            this.resetRotations();
        }
    }
    
    public TileEntity getTargetBlock() {
        TileEntity target = null;
        for (final TileEntity shulker : ShulkerNuker.mc.world.loadedTileEntityList) {
            if (shulker instanceof TileEntityShulkerBox && shulker.getDistanceSq(ShulkerNuker.mc.player.posX, ShulkerNuker.mc.player.posY, ShulkerNuker.mc.player.posZ) <= this.range.getValue() * this.range.getValue()) {
                target = shulker;
            }
        }
        return target;
    }
    
    public void resetRotations() {
        this.yaw = ShulkerNuker.mc.player.rotationYaw;
        this.pitch = ShulkerNuker.mc.player.rotationPitch;
    }
    
    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = RotationUtil.calculateLookAt(px, py, pz, me);
        this.setYawAndPitch((float)v[0], (float)v[1]);
    }
    
    private void setYawAndPitch(final float yaw, final float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    // changed when making client buildable, client used to have entire alpine event system for this one line in one module
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.rotate.getValue()) {
            final Packet packet = event.getPacket();
            if (packet instanceof CPacketPlayer) {
                ((ICPacketPlayer)packet).setYaw(this.yaw);
                ((ICPacketPlayer)packet).setPitch(this.pitch);
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (this.rotate.getValue()) {
            this.resetRotations();
        }
    }
}
