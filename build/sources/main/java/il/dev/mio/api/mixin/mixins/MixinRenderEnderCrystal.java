//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import il.dev.mio.api.util.interact.EntityUtil;
import java.awt.Color;
import il.dev.mio.api.util.render.RenderUtil;
import org.lwjgl.opengl.GL11;
import il.dev.mio.api.event.events.RenderEntityModelEvent;
import net.minecraft.client.renderer.GlStateManager;
import il.dev.mio.mod.modules.render.CrystalChams;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderEnderCrystal.class })
public class MixinRenderEnderCrystal
{
    @Shadow
    @Final
    private static ResourceLocation ENDER_CRYSTAL_TEXTURES;
    private static ResourceLocation glint;
    
    @Redirect(method = { "doRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderModelBaseHook(final ModelBase model, final Entity entity, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (CrystalChams.INSTANCE.isEnabled()) {
            GlStateManager.scale((float)CrystalChams.INSTANCE.scale.getValue(), (float)CrystalChams.INSTANCE.scale.getValue(), (float)CrystalChams.INSTANCE.scale.getValue());
        }
        if (CrystalChams.INSTANCE.isEnabled() && CrystalChams.INSTANCE.wireframe.getValue()) {
            RenderEntityModelEvent event = new RenderEntityModelEvent(0, model, entity, limbSwing, limbSwingAmount * 1.0f, ageInTicks * 1.0f, netHeadYaw, headPitch, scale);
            if (CrystalChams.INSTANCE.isEnabled() && CrystalChams.INSTANCE.changeSpeed.getValue()) {
                event = new RenderEntityModelEvent(0, model, entity, limbSwing, limbSwingAmount * CrystalChams.INSTANCE.spinSpeed.getValue(), ageInTicks * CrystalChams.INSTANCE.floatFactor.getValue(), netHeadYaw, headPitch, scale);
            }
            CrystalChams.INSTANCE.onRenderModel(event);
        }
        if (CrystalChams.INSTANCE.isEnabled() && CrystalChams.INSTANCE.chams.getValue()) {
            GL11.glPushAttrib(1048575);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(2960);
            if (CrystalChams.INSTANCE.rainbow.getValue()) {
                final Color rainbowColor1 = new Color(RenderUtil.getRainbow(CrystalChams.INSTANCE.speed.getValue() * 100, 0, CrystalChams.INSTANCE.saturation.getValue() / 100.0f, CrystalChams.INSTANCE.brightness.getValue() / 100.0f));
                final Color rainbowColor2 = EntityUtil.getColor(entity, rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue(), CrystalChams.INSTANCE.alpha.getValue(), true);
                if (CrystalChams.INSTANCE.throughWalls.getValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glEnable(10754);
                GL11.glColor4f(rainbowColor2.getRed() / 255.0f, rainbowColor2.getGreen() / 255.0f, rainbowColor2.getBlue() / 255.0f, CrystalChams.INSTANCE.alpha.getValue() / 255.0f);
                if (CrystalChams.INSTANCE.isEnabled() && CrystalChams.INSTANCE.changeSpeed.getValue()) {
                    model.render(entity, limbSwing, limbSwingAmount * CrystalChams.INSTANCE.spinSpeed.getValue(), ageInTicks * CrystalChams.INSTANCE.floatFactor.getValue(), netHeadYaw, headPitch, scale);
                }
                else {
                    model.render(entity, limbSwing, limbSwingAmount * 1.0f, ageInTicks * 1.0f, netHeadYaw, headPitch, scale);
                }
                if (CrystalChams.INSTANCE.throughWalls.getValue()) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
            }
            else {
                final Color color;
                final Color visibleColor = color = EntityUtil.getColor(entity, CrystalChams.INSTANCE.red.getValue(), CrystalChams.INSTANCE.green.getValue(), CrystalChams.INSTANCE.blue.getValue(), CrystalChams.INSTANCE.alpha.getValue(), true);
                if (CrystalChams.INSTANCE.throughWalls.getValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glEnable(10754);
                GL11.glColor4f(visibleColor.getRed() / 255.0f, visibleColor.getGreen() / 255.0f, visibleColor.getBlue() / 255.0f, CrystalChams.INSTANCE.alpha.getValue() / 255.0f);
                if (CrystalChams.INSTANCE.isEnabled() && CrystalChams.INSTANCE.changeSpeed.getValue()) {
                    model.render(entity, limbSwing, limbSwingAmount * CrystalChams.INSTANCE.spinSpeed.getValue(), ageInTicks * CrystalChams.INSTANCE.floatFactor.getValue(), netHeadYaw, headPitch, scale);
                }
                else {
                    model.render(entity, limbSwing, limbSwingAmount * 1.0f, ageInTicks * 1.0f, netHeadYaw, headPitch, scale);
                }
                if (CrystalChams.INSTANCE.throughWalls.getValue()) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
            }
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
        }
        else if (CrystalChams.INSTANCE.isEnabled() && CrystalChams.INSTANCE.changeSpeed.getValue()) {
            model.render(entity, limbSwing, limbSwingAmount * CrystalChams.INSTANCE.spinSpeed.getValue(), ageInTicks * CrystalChams.INSTANCE.floatFactor.getValue(), netHeadYaw, headPitch, scale);
        }
        else {
            model.render(entity, limbSwing, limbSwingAmount * 1.0f, ageInTicks * 1.0f, netHeadYaw, headPitch, scale);
        }
        if (CrystalChams.INSTANCE.isEnabled()) {
            GlStateManager.scale(1.0f / CrystalChams.INSTANCE.scale.getValue(), 1.0f / CrystalChams.INSTANCE.scale.getValue(), 1.0f / CrystalChams.INSTANCE.scale.getValue());
        }
    }
    
    static {
        MixinRenderEnderCrystal.glint = new ResourceLocation("textures/glint.png");
    }
}
