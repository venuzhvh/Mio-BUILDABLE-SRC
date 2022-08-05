//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.render;

import net.minecraft.util.EnumHandSide;
import net.minecraft.entity.player.EntityPlayer;
import il.dev.mio.api.util.interact.EntityUtil;
import net.minecraft.util.EnumHand;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class HandModifier extends Module
{
    private static HandModifier INSTANCE;
    public Setting<Settings> settings;
    public Setting<Boolean> chams;
    public Setting<RenderMode> mode;
    public Setting<Boolean> changeSwing;
    public Setting<Swing> swing;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> alpha;
    public Setting<Boolean> anim;
    public Setting<Boolean> swordChange;
    public Setting<Boolean> slowSwing;
    public Setting<Double> mainX;
    public Setting<Double> mainY;
    public Setting<Double> offX;
    public Setting<Double> offY;
    
    public HandModifier() {
        super("HandModifier", "hand modifier", Category.RENDER, true, false, false);
        this.settings = (Setting<Settings>)this.register(new Setting("Settings", Settings.GLOBAL));
        this.chams = (Setting<Boolean>)this.register(new Setting("HandChams", false, v -> this.settings.getValue() == Settings.GLOBAL));
        this.mode = (Setting<RenderMode>)this.register(new Setting("Mode", RenderMode.WIREFRAME, v -> this.settings.getValue() == Settings.GLOBAL && this.chams.getValue()));
        this.changeSwing = (Setting<Boolean>)this.register(new Setting("Swing", false, v -> this.settings.getValue() == Settings.GLOBAL));
        this.swing = (Setting<Swing>)this.register(new Setting("SwingHand", Swing.Mainhand, v -> this.settings.getValue() == Settings.GLOBAL && this.changeSwing.getValue()));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 255, 0, 255, v -> this.chams.getValue() && this.settings.getValue() == Settings.COLORS));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 255, 0, 255, v -> this.chams.getValue() && this.settings.getValue() == Settings.COLORS));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 255, 0, 255, v -> this.chams.getValue() && this.settings.getValue() == Settings.COLORS));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", 150, 0, 255, v -> this.chams.getValue() && this.settings.getValue() == Settings.COLORS));
        this.anim = (Setting<Boolean>)this.register(new Setting("NoSwapDelay", false, v -> this.settings.getValue() == Settings.GLOBAL));
        this.swordChange = (Setting<Boolean>)this.register(new Setting("SwordHandSwap", false, v -> this.settings.getValue() == Settings.GLOBAL));
        this.slowSwing = (Setting<Boolean>)this.register(new Setting("SlowSwing", false, v -> this.settings.getValue() == Settings.GLOBAL));
        this.mainX = (Setting<Double>)this.register(new Setting("MainX", 0.0, (-1.0), 1.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainY = (Setting<Double>)this.register(new Setting("MainY", 0.0, (-1.0), 1.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offX = (Setting<Double>)this.register(new Setting("OffX", 0.0, (-1.0), 1.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offY = (Setting<Double>)this.register(new Setting("OffY", 0.0, (-1.0), 1.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.setInstance();
    }
    
    public static HandModifier getINSTANCE() {
        if (HandModifier.INSTANCE == null) {
            HandModifier.INSTANCE = new HandModifier();
        }
        return HandModifier.INSTANCE;
    }
    
    private void setInstance() {
        HandModifier.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.anim.getValue() && HandModifier.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            HandModifier.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            HandModifier.mc.entityRenderer.itemRenderer.itemStackMainHand = HandModifier.mc.player.getHeldItemMainhand();
        }
        if (this.changeSwing.getValue()) {
            if (this.swing.getValue() == Swing.Offhand) {
                HandModifier.mc.player.swingingHand = EnumHand.OFF_HAND;
            }
            else if (this.swing.getValue() == Swing.Mainhand) {
                HandModifier.mc.player.swingingHand = EnumHand.MAIN_HAND;
            }
        }
        if (this.swordChange.getValue()) {
            if (EntityUtil.holdingWeapon((EntityPlayer)HandModifier.mc.player)) {
                HandModifier.mc.player.setPrimaryHand(EnumHandSide.LEFT);
            }
            else {
                HandModifier.mc.player.setPrimaryHand(EnumHandSide.RIGHT);
            }
        }
    }
    
    static {
        HandModifier.INSTANCE = new HandModifier();
    }
    
    public enum RenderMode
    {
        SOLID, 
        WIREFRAME;
    }
    
    private enum Settings
    {
        GLOBAL, 
        TRANSLATE, 
        COLORS;
    }
    
    private enum Swing
    {
        Mainhand, 
        Offhand;
    }
}
