//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.misc;

import net.minecraft.util.math.Vec3d;
import il.dev.mio.api.util.render.RenderUtil;
import java.awt.Color;
import net.minecraft.client.renderer.RenderGlobal;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import il.dev.mio.api.util.interact.EntityUtil;
import il.dev.mio.mod.modules.render.ESP;
import il.dev.mio.api.util.render.ColorUtil;
import il.dev.mio.mod.modules.client.ClickGui;
import il.dev.mio.api.event.events.Render3DEvent;
import java.util.Iterator;
import net.minecraft.init.SoundEvents;
import il.dev.mio.mod.command.Command;
import net.minecraft.entity.monster.EntityGhast;
import java.util.HashSet;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import net.minecraft.entity.Entity;
import java.util.Set;
import il.dev.mio.mod.modules.Module;

public class GhastNotifier extends Module
{
    private Set<Entity> ghasts;
    public Setting<Boolean> Chat;
    public Setting<Boolean> CensorCoords;
    public Setting<Boolean> Sound;
    public Setting<Boolean> Render;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> alpha;
    private final Setting<Integer> boxAlpha;
    private int color;
    
    public GhastNotifier() {
        super("GhastNotify", "Helps you find ghasts", Category.MISC, true, false, false);
        this.ghasts = new HashSet<Entity>();
        this.Chat = (Setting<Boolean>)this.register(new Setting("Chat", true));
        this.CensorCoords = (Setting<Boolean>)this.register(new Setting("CensorCoords", false, v -> this.Chat.getValue()));
        this.Sound = (Setting<Boolean>)this.register(new Setting("Sound", true));
        this.Render = (Setting<Boolean>)this.register(new Setting("ESP", false));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 255, 0, 255, v -> this.Render.getValue()));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 255, 0, 255, v -> this.Render.getValue()));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 255, 0, 255, v -> this.Render.getValue()));
        this.alpha = (Setting<Integer>)this.register(new Setting("OlAlpha", 255, 0, 255, v -> this.Render.getValue()));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", 255, 0, 255, v -> this.Render.getValue()));
    }
    
    @Override
    public void onEnable() {
        this.ghasts.clear();
    }
    
    @Override
    public void onUpdate() {
        for (final Entity entity : GhastNotifier.mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityGhast) {
                if (this.ghasts.contains(entity)) {
                    continue;
                }
                if (this.Chat.getValue()) {
                    if (this.CensorCoords.getValue()) {
                        Command.sendMessage("There is a ghast!");
                    }
                    else {
                        Command.sendMessage("There is a ghast at: " + entity.getPosition().getX() + "x, " + entity.getPosition().getY() + "y, " + entity.getPosition().getZ() + "z.");
                    }
                }
                this.ghasts.add(entity);
                if (!this.Sound.getValue()) {
                    continue;
                }
                GhastNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
            }
        }
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        this.color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue());
        if (this.Render.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityGhast && !this.ghasts.contains(entity)) {
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, GhastNotifier.mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    if (++i < 50) {
                        continue;
                    }
                    break;
                }
            }
        }
    }
}
