//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.movement;

import java.util.Map;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.HashMap;
import il.dev.mio.api.util.plugs.HoleTpUtil;
import net.minecraft.init.Blocks;
import il.dev.mio.api.util.IMinecraftUtil;
import net.minecraft.util.math.BlockPos;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class HoleTP extends Module
{
    public Setting<Boolean> onground;
    public Setting<Integer> activateHeight;
    public Setting<Boolean> Step;
    BlockPos playerPos;
    
    public HoleTP() {
        super("HoleTp", "bawls", Category.MOVEMENT, true, false, false);
        this.onground = (Setting<Boolean>)this.register(new Setting("OnGround", true));
        this.activateHeight = (Setting<Integer>)this.register(new Setting("PullHeight", 2, 1, 5));
        this.Step = new Setting<Boolean>("FastFall", false);
    }
    
    @Override
    public void onUpdate() {
        if (IMinecraftUtil.Util.mc.player == null) {
            return;
        }
        if (IMinecraftUtil.Util.mc.player.posY < 0.0) {
            return;
        }
        final double blockX = Math.floor(IMinecraftUtil.Util.mc.player.posX);
        final double blockZ = Math.floor(IMinecraftUtil.Util.mc.player.posZ);
        final double offsetX = Math.abs(IMinecraftUtil.Util.mc.player.posX - blockX);
        final double offsetZ = Math.abs(IMinecraftUtil.Util.mc.player.posZ - blockZ);
        if (this.onground.getValue() && (offsetX < 0.30000001192092896 || offsetX > 0.699999988079071 || offsetZ < 0.30000001192092896 || offsetZ > 0.699999988079071)) {
            return;
        }
        this.playerPos = new BlockPos(blockX, IMinecraftUtil.Util.mc.player.posY, blockZ);
        if (IMinecraftUtil.Util.mc.world.getBlockState(this.playerPos).getBlock() != Blocks.AIR) {
            return;
        }
        BlockPos currentBlock = this.playerPos.down();
        for (int i = 0; i < this.activateHeight.getValue(); ++i) {
            currentBlock = currentBlock.down();
            if (IMinecraftUtil.Util.mc.world.getBlockState(currentBlock).getBlock() != Blocks.AIR) {
                final HashMap<HoleTpUtil.BlockOffset, HoleTpUtil.BlockSafety> sides = HoleTpUtil.getUnsafeSides(currentBlock.up());
                sides.entrySet().removeIf(entry -> entry.getValue() == HoleTpUtil.BlockSafety.RESISTANT);
                if (sides.size() == 0) {
                    IMinecraftUtil.Util.mc.player.motionX = 0.0;
                    IMinecraftUtil.Util.mc.player.motionZ = 0.0;
                }
            }
        }
        if (this.Step.getValue()) {
            if (HoleTP.mc.player == null || HoleTP.mc.world == null || HoleTP.mc.player.isInWater() || HoleTP.mc.player.isInLava()) {
                return;
            }
            if (HoleTP.mc.player.onGround) {
                final EntityPlayerSP player = HoleTP.mc.player;
                --player.motionY;
            }
        }
    }
}
