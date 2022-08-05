//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.client;

import il.dev.mio.api.event.events.PerspectiveEvent;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.FOVUpdateEvent;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class FovMod extends Module
{
    private static FovMod INSTANCE;
    public Setting<Page> page;
    public Setting<Boolean> customFov;
    public Setting<Float> fov;
    public Setting<Boolean> changeAspect;
    public Setting<Float> aspect;
    public Setting<Boolean> defaultt;
    public Setting<Float> sprint;
    public Setting<Float> speed;
    
    public FovMod() {
        super("FovMod", "Fov modifier", Category.CLIENT, true, false, false);
        this.page = (Setting<Page>)this.register(new Setting("Settings", Page.FOV));
        this.customFov = (Setting<Boolean>)this.register(new Setting("CustomFov", false, v -> this.page.getValue() == Page.FOV));
        this.fov = (Setting<Float>)this.register(new Setting("FOV", 130.0f, (-180.0f), 180.0f, v -> this.page.getValue() == Page.FOV && this.customFov.getValue()));
        this.changeAspect = (Setting<Boolean>)this.register(new Setting("AspectRatio", false, v -> this.page.getValue() == Page.FOV));
        this.aspect = (Setting<Float>)this.register(new Setting("AspectFactor", 1.8f, 0.1f, 3.0f, v -> this.page.getValue() == Page.FOV && this.changeAspect.getValue()));
        this.defaultt = (Setting<Boolean>)this.register(new Setting("Defaults", false, v -> this.page.getValue() == Page.ADVANCED));
        this.sprint = (Setting<Float>)this.register(new Setting("SprintAdd", 1.15f, 1.0f, 2.0f, v -> this.page.getValue() == Page.ADVANCED));
        this.speed = (Setting<Float>)this.register(new Setting("SwiftnessAdd", 1.15f, 1.0f, 2.0f, v -> this.page.getValue() == Page.ADVANCED));
        this.setInstance();
    }
    
    public static FovMod getInstance() {
        if (FovMod.INSTANCE == null) {
            FovMod.INSTANCE = new FovMod();
        }
        return FovMod.INSTANCE;
    }
    
    private void setInstance() {
        FovMod.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void fovUpdate(final FOVUpdateEvent event) {
        float fov = 1.0f;
        if (event.getEntity().isSprinting()) {
            fov = this.sprint.getValue();
        }
        event.setNewfov(fov);
        if (event.getEntity().isPotionActive(MobEffects.SPEED) && event.getEntity().isSprinting()) {
            fov = this.speed.getValue();
        }
        event.setNewfov(fov);
    }
    
    @Override
    public void onUpdate() {
        if (this.customFov.getValue()) {
            FovMod.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, (float)this.fov.getValue());
        }
        if (this.defaultt.getValue()) {
            this.sprint.setValue(1.15f);
            this.speed.setValue(1.15f);
            this.defaultt.setValue(false);
        }
    }
    
    @SubscribeEvent
    public void onPerspectiveEvent(final PerspectiveEvent perspectiveEvent) {
        if (this.changeAspect.getValue()) {
            perspectiveEvent.setAspect(this.aspect.getValue());
        }
    }
    
    static {
        FovMod.INSTANCE = new FovMod();
    }
    
    public enum Page
    {
        FOV, 
        ADVANCED;
    }
}
