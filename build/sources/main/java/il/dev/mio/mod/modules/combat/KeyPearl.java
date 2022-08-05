//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import il.dev.mio.api.util.world.InventoryUtil;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import il.dev.mio.mod.ModuleCore;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class KeyPearl extends Module
{
    private Setting<Mode> mode;
    private Setting<Boolean> stopRotation;
    private Setting<Boolean> antiFriend;
    private Setting<Integer> rotation;
    private boolean clicked;
    
    public KeyPearl() {
        super("KeyPearl", "Throws a pearl", Category.PLAYER, false, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", Mode.MiddleClick));
        this.stopRotation = (Setting<Boolean>)this.register(new Setting("RotatePrevent", true));
        this.antiFriend = new Setting<Boolean>("AntiFriend", true);
        this.rotation = (Setting<Integer>)this.register(new Setting("Delay", 10, 0, 100, v -> this.stopRotation.getValue()));
        this.clicked = false;
    }
    
    @Override
    public String getDisplayInfo() {
        final String ModeInfo = String.valueOf(this.mode.getValue());
        return ModeInfo;
    }
    
    @Override
    public void onEnable() {
        if (!ModuleCore.fullNullCheck() && this.mode.getValue() == Mode.Key) {
            this.throwPearl();
            this.disable();
        }
    }
    
    @Override
    public void onTick() {
        if (this.mode.getValue() == Mode.MiddleClick) {
            if (Mouse.isButtonDown(2)) {
                if (!this.clicked) {
                    this.throwPearl();
                }
                this.clicked = true;
            }
            else {
                this.clicked = false;
            }
        }
    }
    
    private void throwPearl() {
        final RayTraceResult result;
        final Entity entity;
        if (this.antiFriend.getValue() && (result = KeyPearl.mc.objectMouseOver) != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
            return;
        }
        final int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
        final boolean bl;
        final boolean offhand = bl = (KeyPearl.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL);
        if (pearlSlot != -1 || offhand) {
            final int oldslot = KeyPearl.mc.player.inventory.currentItem;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(pearlSlot, false);
            }
            KeyPearl.mc.playerController.processRightClick((EntityPlayer)KeyPearl.mc.player, (World)KeyPearl.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }
    
    public enum Mode
    {
        Key, 
        MiddleClick;
    }
}
