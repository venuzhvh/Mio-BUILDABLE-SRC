//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.movement;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.entity.EntityPlayerSP;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class ReverseStep extends Module
{
    public Setting<Boolean> noLag;
    public Setting<Integer> height;
    
    public ReverseStep() {
        super("ReverseStep", "Makes u fall faster", Category.MOVEMENT, true, false, false);
        this.noLag = (Setting<Boolean>)this.register(new Setting("NoLag", true));
        this.height = (Setting<Integer>)this.register(new Setting("Height", 10, 1, 20));
    }
    
    @Override
    public void onTick() {
        if (this.height.getValue() > 0 && this.traceDown() > this.height.getValue()) {
            return;
        }
        if (ReverseStep.mc.player.isEntityInsideOpaqueBlock()) {
            return;
        }
        if (ReverseStep.mc.player.isInWater() || ReverseStep.mc.player.isInLava() || ReverseStep.mc.player.isOnLadder() || ReverseStep.mc.player.isInWeb) {
            return;
        }
        if (ReverseStep.mc.player.onGround) {
            final EntityPlayerSP player = ReverseStep.mc.player;
            player.motionY -= (this.noLag.getValue() ? 0.62f : 1.0f);
        }
    }
    
    @Override
    public void onDisable() {
        ReverseStep.mc.timer.tickLength = 50.0f;
    }
    
    private int traceDown() {
        int ret = 0;
        int tracey;
        for (int y = tracey = (int)Math.round(ReverseStep.mc.player.posY) - 1; tracey >= 0; --tracey) {
            final RayTraceResult trace = ReverseStep.mc.world.rayTraceBlocks(ReverseStep.mc.player.getPositionVector(), new Vec3d(ReverseStep.mc.player.posX, (double)tracey, ReverseStep.mc.player.posZ), false);
            if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                return ret;
            }
            ++ret;
        }
        return ret;
    }
    
    public enum Mode
    {
        MotionY, 
        Strict;
    }
}
