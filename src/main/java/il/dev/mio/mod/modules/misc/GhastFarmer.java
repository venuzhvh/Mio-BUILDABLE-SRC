//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.misc;

import net.minecraft.init.Items;
import net.minecraft.entity.item.EntityItem;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.init.SoundEvents;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.Entity;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class GhastFarmer extends Module
{
    public Setting<Boolean> notifySound;
    public int currentX;
    public int currentY;
    public int currentZ;
    public int itemX;
    public int itemY;
    public int itemZ;
    public int ghastX;
    public int ghastY;
    public int ghastZ;
    public boolean ding;
    
    public GhastFarmer() {
        super("GhastFarmer", "Auto Ghast Farmer", Category.MISC, false, false, false);
        this.notifySound = (Setting<Boolean>)this.register(new Setting("Sound", false));
        this.ding = false;
    }
    
    @Override
    public void onEnable() {
        if (GhastFarmer.mc.player != null) {
            if (GhastFarmer.mc.world != null) {
                this.currentX = (int)GhastFarmer.mc.player.posX;
                this.currentY = (int)GhastFarmer.mc.player.posY;
                this.currentZ = (int)GhastFarmer.mc.player.posZ;
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (GhastFarmer.mc.player != null) {
            if (GhastFarmer.mc.world != null) {
                GhastFarmer.mc.player.sendChatMessage("#stop");
            }
        }
    }
    
    @Override
    public void onUpdate() {
        if (GhastFarmer.mc.player == null || GhastFarmer.mc.world == null) {
            return;
        }
        Entity ghastEnt = null;
        double dist = Double.longBitsToDouble(Double.doubleToLongBits(0.017520017079696953) ^ 0x7FC8F0C47187D7FBL);
        for (final Entity entity : GhastFarmer.mc.world.loadedEntityList) {
            if (entity instanceof EntityGhast) {
                final double ghastDist;
                if ((ghastDist = GhastFarmer.mc.player.getDistance(entity)) >= dist) {
                    continue;
                }
                dist = ghastDist;
                ghastEnt = entity;
                this.ghastX = (int)entity.posX;
                this.ghastY = (int)entity.posY;
                this.ghastZ = (int)entity.posZ;
                this.ding = true;
            }
        }
        if (this.ding) {
            if (this.notifySound.getValue()) {
                GhastFarmer.mc.player.playSound(SoundEvents.BLOCK_NOTE_BELL, Float.intBitsToFloat(Float.floatToIntBits(5.2897425f) ^ 0x7F294592), Float.intBitsToFloat(Float.floatToIntBits(5.5405655f) ^ 0x7F314C50));
            }
            this.ding = false;
        }
        Entity itemEnt;
        Iterator var4 = mc.world.loadedEntityList.iterator();
        while(var4.hasNext()) {
            itemEnt = (Entity)var4.next();
            double ghastDist;
            if (itemEnt instanceof EntityGhast && (ghastDist = (double)mc.player.getDistance(itemEnt)) < dist) {
                dist = ghastDist;
                ghastEnt = itemEnt;
                this.ghastX = (int)itemEnt.posX;
                this.ghastY = (int)itemEnt.posY;
                this.ghastZ = (int)itemEnt.posZ;
                this.ding = true;
            }
        }
        final ArrayList entityItems = new ArrayList();
        entityItems.addAll(GhastFarmer.mc.world.loadedEntityList.stream().filter(GhastFarmer::lambda$onUpdate$0).map(GhastFarmer::lambda$onUpdate$1).filter(GhastFarmer::lambda$onUpdate$2).collect(Collectors.toList()));
        itemEnt = null;
        if (ghastEnt != null) {
            GhastFarmer.mc.player.sendChatMessage("#goto " + this.ghastX + " " + this.ghastY + " " + this.ghastZ);
        } else if (itemEnt != null) {
            GhastFarmer.mc.player.sendChatMessage("#goto " + this.itemX + " " + this.itemY + " " + this.itemZ);
        }
        else {
            GhastFarmer.mc.player.sendChatMessage("#goto " + this.currentX + " " + this.currentY + " " + this.currentZ);
        }
    }
    
    public static boolean lambda$onUpdate$2(final EntityItem entityItem) {
        final EntityItem entityItem2 = null;
        return entityItem2.getItem().getItem() == Items.GHAST_TEAR;
    }
    
    public static EntityItem lambda$onUpdate$1(final Entity entity) {
        final Entity entity2 = null;
        return (EntityItem)entity2;
    }
    
    public static boolean lambda$onUpdate$0(final Entity entity) {
        final Entity entity2 = null;
        return entity2 instanceof EntityItem;
    }
}
