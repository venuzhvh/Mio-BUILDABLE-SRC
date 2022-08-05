//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.render;

import java.util.Iterator;
import il.dev.mio.api.util.IMinecraftUtil;
import com.google.common.collect.Maps;
import java.util.Map;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.Display;
import il.dev.mio.api.util.interact.EntityUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;
import il.dev.mio.api.util.render.RenderUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.player.EntityPlayer;
import il.dev.mio.api.event.events.Render2DEvent;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class Tracers extends Module
{
    private final Setting<Integer> fadeDistance;
    private final Setting<Boolean> invisibles;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> radius;
    private final Setting<Float> size;
    private final Setting<Boolean> outline;
    private final EntityListener entityListener;
    
    public Tracers() {
        super("Tracers", "Points to the players on your screen", Category.RENDER, true, false, false);
        this.fadeDistance = (Setting<Integer>)this.register(new Setting("Range", 100, 10, 200));
        this.invisibles = (Setting<Boolean>)this.register(new Setting("Invisibles", false));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 255, 0, 255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 255, 0, 255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 255, 0, 255));
        this.radius = (Setting<Integer>)this.register(new Setting("Radius", 80, 10, 200));
        this.size = (Setting<Float>)this.register(new Setting("Size", 7.5f, 5.0f, 25.0f));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", true));
        this.entityListener = new EntityListener();
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        entityListener.render();
        mc.world.loadedEntityList.forEach((o) -> {
            if (o instanceof EntityPlayer && this.isValid((EntityPlayer)o)) {
                EntityPlayer entity = (EntityPlayer)o;
                Vec3d pos = (Vec3d)this.entityListener.getEntityLowerBounds().get(entity);
                if (pos != null && !this.isOnScreen(pos) && !RenderUtil.isInViewFrustrum(entity)) {
                    Color color = EntityUtil.getColor(entity, (Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), (int)MathHelper.clamp(255.0F - 255.0F / (float)(Integer)this.fadeDistance.getValue() * mc.player.getDistance(entity), 100.0F, 255.0F), true);
                    int x = Display.getWidth() / 2 / (mc.gameSettings.guiScale == 0 ? 1 : mc.gameSettings.guiScale);
                    int y = Display.getHeight() / 2 / (mc.gameSettings.guiScale == 0 ? 1 : mc.gameSettings.guiScale);
                    float yaw = this.getRotations(entity) - mc.player.rotationYaw;
                    GL11.glTranslatef((float)x, (float)y, 0.0F);
                    GL11.glRotatef(yaw, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef((float)(-x), (float)(-y), 0.0F);
                    RenderUtil.drawTracerPointer((float)x, (float)(y - (Integer)this.radius.getValue()), (Float)this.size.getValue(), 2.0F, 1.0F, (Boolean)this.outline.getValue(), 1.1F, color.getRGB());
                    GL11.glTranslatef((float)x, (float)y, 0.0F);
                    GL11.glRotatef(-yaw, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef((float)(-x), (float)(-y), 0.0F);
                }
            }

        });
    }
    
    private boolean isOnScreen(final Vec3d pos) {
        if (pos.x <= -1.0) {
            return false;
        }
        if (pos.y >= 1.0) {
            return false;
        }
        if (pos.x <= -1.0) {
            return false;
        }
        if (pos.z >= 1.0) {
            return false;
        }
        final int n = (Tracers.mc.gameSettings.guiScale == 0) ? 1 : Tracers.mc.gameSettings.guiScale;
        if (pos.x / n < 0.0) {
            return false;
        }
        final int n2 = (Tracers.mc.gameSettings.guiScale == 0) ? 1 : Tracers.mc.gameSettings.guiScale;
        if (pos.x / n2 > Display.getWidth()) {
            return false;
        }
        final int n3 = (Tracers.mc.gameSettings.guiScale == 0) ? 1 : Tracers.mc.gameSettings.guiScale;
        if (pos.y / n3 < 0.0) {
            return false;
        }
        final int n4 = (Tracers.mc.gameSettings.guiScale == 0) ? 1 : Tracers.mc.gameSettings.guiScale;
        return pos.y / n4 <= Display.getHeight();
    }
    
    private boolean isValid(final EntityPlayer entity) {
        return entity != Tracers.mc.player && (!entity.isInvisible() || this.invisibles.getValue()) && entity.isEntityAlive();
    }
    
    private float getRotations(final EntityLivingBase ent) {
        final double x = ent.posX - Tracers.mc.player.posX;
        final double z = ent.posZ - Tracers.mc.player.posZ;
        return (float)(-(Math.atan2(x, z) * 57.29577951308232));
    }
    
    private enum Mode
    {
        Tracer, 
        Arrow;
    }
    
    private static class EntityListener
    {
        private final Map<Entity, Vec3d> entityUpperBounds;
        private final Map<Entity, Vec3d> entityLowerBounds;
        
        private EntityListener() {
            this.entityUpperBounds = Maps.newHashMap();
            this.entityLowerBounds = Maps.newHashMap();
        }
        
        private void render() {
            if (!this.entityUpperBounds.isEmpty()) {
                this.entityUpperBounds.clear();
            }
            if (!this.entityLowerBounds.isEmpty()) {
                this.entityLowerBounds.clear();
            }
            for (final Entity e : IMinecraftUtil.mc.world.loadedEntityList) {
                final Vec3d bound = this.getEntityRenderPosition(e);
                bound.add(new Vec3d(0.0, e.height + 0.2, 0.0));
                final Vec3d upperBounds = RenderUtil.to2D(bound.x, bound.y, bound.z);
                final Vec3d lowerBounds = RenderUtil.to2D(bound.x, bound.y - 2.0, bound.z);
                if (upperBounds != null) {
                    if (lowerBounds == null) {
                        continue;
                    }
                    this.entityUpperBounds.put(e, upperBounds);
                    this.entityLowerBounds.put(e, lowerBounds);
                }
            }
        }
        
        private Vec3d getEntityRenderPosition(final Entity entity) {
            final double partial = IMinecraftUtil.mc.timer.renderPartialTicks;
            final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partial - IMinecraftUtil.mc.getRenderManager().viewerPosX;
            final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partial - IMinecraftUtil.mc.getRenderManager().viewerPosY;
            final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partial - IMinecraftUtil.mc.getRenderManager().viewerPosZ;
            return new Vec3d(x, y, z);
        }
        
        public Map<Entity, Vec3d> getEntityLowerBounds() {
            return this.entityLowerBounds;
        }
    }
}
