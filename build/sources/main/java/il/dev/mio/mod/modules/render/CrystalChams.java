//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.render;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import il.dev.mio.api.util.interact.EntityUtil;
import il.dev.mio.api.event.events.RenderEntityModelEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import il.dev.mio.api.event.events.PacketEvent;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.Map;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class CrystalChams extends Module
{
    private final Setting<Page> page;
    public Setting<Boolean> chams;
    public Setting<Boolean> throughWalls;
    public Setting<Boolean> wireframe;
    public Setting<Float> scale;
    public Setting<Boolean> changeSpeed;
    public Setting<Float> spinSpeed;
    public Setting<Float> floatFactor;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> alpha;
    public Setting<Float> lineWidth;
    public Setting<Boolean> rainbow;
    public Setting<Integer> saturation;
    public Setting<Integer> brightness;
    public Setting<Integer> speed;
    public Map<EntityEnderCrystal, Float> scaleMap;
    public static CrystalChams INSTANCE;
    
    public CrystalChams() {
        super("CrystalChams", "Crystal render modifications", Category.RENDER, true, false, false);
        this.page = (Setting<Page>)this.register(new Setting("Settings", Page.GLOBAL));
        this.chams = (Setting<Boolean>)this.register(new Setting("Fill", false, v -> this.page.getValue() == Page.GLOBAL));
        this.throughWalls = (Setting<Boolean>)this.register(new Setting("XQZ", true, v -> this.page.getValue() == Page.GLOBAL && this.chams.getValue()));
        this.wireframe = (Setting<Boolean>)this.register(new Setting("Wireframe", false, v -> this.page.getValue() == Page.GLOBAL));
        this.scale = (Setting<Float>)this.register(new Setting("Scale", 1.0f, 0.1f, 1.0f, v -> this.page.getValue() == Page.GLOBAL));
        this.changeSpeed = (Setting<Boolean>)this.register(new Setting("ChangeSpeed", false, v -> this.page.getValue() == Page.GLOBAL));
        this.spinSpeed = (Setting<Float>)this.register(new Setting("SpinSpeed", 1.0f, 0.0f, 10.0f, v -> this.page.getValue() == Page.GLOBAL && this.changeSpeed.getValue()));
        this.floatFactor = (Setting<Float>)this.register(new Setting("FloatFactor", 1.0f, 0.0f, 1.0f, v -> this.page.getValue() == Page.GLOBAL && this.changeSpeed.getValue()));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 255, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 255, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 255, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", 255, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", 1.0f, 0.1f, 3.0f, v -> this.page.getValue() == Page.COLORS));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", false, v -> this.page.getValue() == Page.COLORS));
        this.saturation = (Setting<Integer>)this.register(new Setting("Saturation", 50, 0, 100, v -> this.rainbow.getValue() && this.page.getValue() == Page.COLORS));
        this.brightness = (Setting<Integer>)this.register(new Setting("Brightness", 100, 0, 100, v -> this.rainbow.getValue() && this.page.getValue() == Page.COLORS));
        this.speed = (Setting<Integer>)this.register(new Setting("RainbowSpeed", 40, 1, 100, v -> this.rainbow.getValue() && this.page.getValue() == Page.COLORS));
        this.scaleMap = new ConcurrentHashMap<EntityEnderCrystal, Float>();
        CrystalChams.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        for (final Entity crystal : CrystalChams.mc.world.loadedEntityList) {
            if (!(crystal instanceof EntityEnderCrystal)) {
                continue;
            }
            if (!this.scaleMap.containsKey(crystal)) {
                this.scaleMap.put((EntityEnderCrystal)crystal, 3.125E-4f);
            }
            else {
                this.scaleMap.put((EntityEnderCrystal)crystal, this.scaleMap.get(crystal) + 3.125E-4f);
            }
            if (this.scaleMap.get(crystal) < 0.0625f * this.scale.getValue()) {
                continue;
            }
            this.scaleMap.remove(crystal);
        }
    }
    
    @SubscribeEvent
    public void onReceivePacket(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketDestroyEntities) {
            final SPacketDestroyEntities packet = event.getPacket();
            for (final int id : packet.getEntityIDs()) {
                final Entity entity = CrystalChams.mc.world.getEntityByID(id);
                if (entity instanceof EntityEnderCrystal) {
                    this.scaleMap.remove(entity);
                }
            }
        }
    }
    
    public void onRenderModel(final RenderEntityModelEvent event) {
        if (event.getStage() != 0 || !(event.entity instanceof EntityEnderCrystal) || !this.wireframe.getValue()) {
            return;
        }
        final Color color = EntityUtil.getColor(event.entity, this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue(), false);
        final boolean fancyGraphics = CrystalChams.mc.gameSettings.fancyGraphics;
        CrystalChams.mc.gameSettings.fancyGraphics = false;
        final float gamma = CrystalChams.mc.gameSettings.gammaSetting;
        CrystalChams.mc.gameSettings.gammaSetting = 10000.0f;
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        GL11.glPolygonMode(1032, 6913);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GlStateManager.glLineWidth((float)this.lineWidth.getValue());
        event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public enum Page
    {
        COLORS, 
        GLOBAL;
    }
}
