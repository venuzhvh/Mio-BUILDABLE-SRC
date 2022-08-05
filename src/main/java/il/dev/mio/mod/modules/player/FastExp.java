//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.player;

import il.dev.mio.api.util.world.InventoryUtil;
import net.minecraft.item.ItemExpBottle;
import il.dev.mio.mod.modules.Module;

public class FastExp extends Module
{
    public FastExp() {
        super("FastExp", "Fast projectile.", Category.PLAYER, true, false, false);
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        if (InventoryUtil.holdingItem(ItemExpBottle.class)) {
            FastExp.mc.rightClickDelayTimer = 0;
        }
    }
}
