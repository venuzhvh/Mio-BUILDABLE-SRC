//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.render;

import java.awt.Color;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class CustomFog extends Module
{
    public final Setting<Integer> timeSetting;
    private final Setting<Boolean> color;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Boolean> rainbow;
    private final Setting<Integer> red1;
    private final Setting<Integer> green1;
    private final Setting<Integer> blue1;
    private final Setting<Boolean> rainbow1;
    private final Setting<Integer> red2;
    private final Setting<Integer> green2;
    private final Setting<Integer> blue2;
    private final Setting<Boolean> rainbow2;
    private final Setting<Float> rsat;
    private final Setting<Float> rbrightness;
    private static CustomFog INSTANCE;
    
    public CustomFog() {
        super("CustomFog", "CustomFog", Category.RENDER, true, false, false);
        this.timeSetting = (Setting<Integer>)this.register(new Setting("Time", 12000, 0, 23000));
        this.color = (Setting<Boolean>)this.register(new Setting("Color", false));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 255, 0, 255, v -> this.color.getValue()));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 255, 0, 255, v -> this.color.getValue()));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 255, 0, 255, v -> this.color.getValue()));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", false, v -> this.color.getValue()));
        this.red1 = (Setting<Integer>)this.register(new Setting("NetherRed", 255, 0, 255, v -> this.color.getValue()));
        this.green1 = (Setting<Integer>)this.register(new Setting("NetherGreen", 255, 0, 255, v -> this.color.getValue()));
        this.blue1 = (Setting<Integer>)this.register(new Setting("NetherBlue", 255, 0, 255, v -> this.color.getValue()));
        this.rainbow1 = (Setting<Boolean>)this.register(new Setting("NetherRainbow", false, v -> this.color.getValue()));
        this.red2 = (Setting<Integer>)this.register(new Setting("EndRed", 255, 0, 255, v -> this.color.getValue()));
        this.green2 = (Setting<Integer>)this.register(new Setting("EndGreen", 255, 0, 255, v -> this.color.getValue()));
        this.blue2 = (Setting<Integer>)this.register(new Setting("EndBlue", 255, 0, 255, v -> this.color.getValue()));
        this.rainbow2 = (Setting<Boolean>)this.register(new Setting("EndRainbow", false, v -> this.color.getValue()));
        this.rsat = (Setting<Float>)this.register(new Setting("RSaturation", 0.8f, 0.1f, 1.0f, v -> this.color.getValue()));
        this.rbrightness = (Setting<Float>)this.register(new Setting("RBrightness", 0.8f, 0.1f, 1.0f, v -> this.color.getValue()));
    }
    
    public static CustomFog getInstance() {
        if (CustomFog.INSTANCE == null) {
            CustomFog.INSTANCE = new CustomFog();
        }
        return CustomFog.INSTANCE;
    }
    
    private void setInstance() {
        CustomFog.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.rainbow.getValue()) {
            this.doRainbow();
        }
        if (this.rainbow1.getValue()) {
            this.doRainbowNether();
        }
        if (this.rainbow2.getValue()) {
            this.doRainbowEnd();
        }
        CustomFog.mc.world.setWorldTime((long)this.timeSetting.getValue());
    }
    
    @SubscribeEvent
    public void fog(final EntityViewRenderEvent.FogColors event) {
        if (this.color.getValue()) {
            if (CustomFog.mc.player.dimension == 0) {
                event.setRed(this.red.getValue() / 255.0f);
                event.setGreen(this.green.getValue() / 255.0f);
                event.setBlue(this.blue.getValue() / 255.0f);
            }
            else if (CustomFog.mc.player.dimension == -1) {
                event.setRed(this.red1.getValue() / 255.0f);
                event.setGreen(this.green1.getValue() / 255.0f);
                event.setBlue(this.blue1.getValue() / 255.0f);
            }
            else if (CustomFog.mc.player.dimension == 1) {
                event.setRed(this.red2.getValue() / 255.0f);
                event.setGreen(this.green2.getValue() / 255.0f);
                event.setBlue(this.blue2.getValue() / 255.0f);
            }
        }
    }
    
    @SubscribeEvent
    public void fog_density(final EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }
    
    public void doRainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], this.rsat.getValue(), this.rbrightness.getValue());
        this.red.setValue(color_rgb_o >> 16 & 0xFF);
        this.green.setValue(color_rgb_o >> 8 & 0xFF);
        this.blue.setValue(color_rgb_o & 0xFF);
    }
    
    public void doRainbowNether() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], this.rsat.getValue(), this.rbrightness.getValue());
        this.red1.setValue(color_rgb_o >> 16 & 0xFF);
        this.green1.setValue(color_rgb_o >> 8 & 0xFF);
        this.blue1.setValue(color_rgb_o & 0xFF);
    }
    
    public void doRainbowEnd() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], this.rsat.getValue(), this.rbrightness.getValue());
        this.red2.setValue(color_rgb_o >> 16 & 0xFF);
        this.green2.setValue(color_rgb_o >> 8 & 0xFF);
        this.blue2.setValue(color_rgb_o & 0xFF);
    }
    
    static {
        CustomFog.INSTANCE = new CustomFog();
    }
}
