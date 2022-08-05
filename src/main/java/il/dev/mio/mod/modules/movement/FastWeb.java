//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class FastWeb extends Module
{
    public float speed;
    int delay;
    public Setting<Mode> mode;
    public Setting<Float> fastspeed;
    
    public FastWeb() {
        super("FastWeb", "So you don't need to keep timer on keybind", Category.MOVEMENT, true, false, false);
        this.speed = 1.0f;
        this.delay = 0;
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", Mode.Fast));
        this.fastspeed = (Setting<Float>)this.register(new Setting("FastSpeed", 3.0f, 1.0f, 5.0f, v -> this.mode.getValue() == Mode.Fast));
    }
    
    @Override
    public String getDisplayInfo() {
        final String ModeInfo = String.valueOf(this.mode.getValue());
        return ModeInfo;
    }
    
    @Override
    public void onUpdate() {
        if (FastWeb.mc.world == null || FastWeb.mc.player == null) {
            return;
        }
        if (FastWeb.mc.player.isInWeb) {
            ++this.delay;
            if (this.mode.getValue().equals(Mode.Fast) && FastWeb.mc.gameSettings.keyBindSneak.isKeyDown()) {
                FastWeb.mc.timer.tickLength = 50.0f;
                final EntityPlayerSP player = FastWeb.mc.player;
                player.motionY -= this.fastspeed.getValue();
            }
            else if (this.mode.getValue().equals(Mode.Strict)) {
                if (FastWeb.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    FastWeb.mc.timer.tickLength = 3.0f;
                }
                if (FastWeb.mc.player.onGround) {
                    FastWeb.mc.timer.tickLength = 50.0f;
                }
                if (!FastWeb.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    FastWeb.mc.timer.tickLength = 50.0f;
                }
                if (!FastWeb.mc.player.isInWeb) {
                    FastWeb.mc.timer.tickLength = 50.0f;
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        FastWeb.mc.timer.tickLength = 50.0f;
    }
    
    public enum Mode
    {
        Strict, 
        Fast;
    }
}
