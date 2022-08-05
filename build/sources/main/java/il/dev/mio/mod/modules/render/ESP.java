//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.render;

import net.minecraft.block.BlockLiquid;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Blocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import net.minecraft.util.math.Vec3i;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.client.Minecraft;
import il.dev.mio.Mio;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityXPOrb;
import il.dev.mio.api.util.render.RenderUtil;
import java.awt.Color;
import net.minecraft.client.renderer.RenderGlobal;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import il.dev.mio.api.util.interact.EntityUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import il.dev.mio.mod.modules.client.ClickGui;
import il.dev.mio.api.util.render.ColorUtil;
import il.dev.mio.api.event.events.Render3DEvent;
import java.util.ArrayList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class ESP extends Module
{
    private static ESP INSTANCE;
    private final Setting<Page> page;
    private final Setting<Boolean> items;
    private final Setting<Boolean> xporbs;
    private final Setting<Boolean> xpbottles;
    private final Setting<Boolean> pearl;
    private final Setting<Boolean> players;
    private final Setting<Boolean> burrow;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> redf;
    private final Setting<Integer> greenf;
    private final Setting<Integer> bluef;
    private final Setting<Integer> boxAlpha;
    private final Setting<Integer> alpha;
    private int color;
    private int white;
    private final List<BlockPos> burrowBlocks;
    private static final ResourceLocation obby;
    private static final ResourceLocation echest;
    private static final ResourceLocation web;
    
    public ESP() {
        super("ESP", "Cool awesome esp", Category.RENDER, true, false, false);
        this.page = (Setting<Page>)this.register(new Setting("Settings", Page.GLOBAL));
        this.items = (Setting<Boolean>)this.register(new Setting("Items", true, v -> this.page.getValue() == Page.GLOBAL));
        this.xporbs = (Setting<Boolean>)this.register(new Setting("XpOrbs", false, v -> this.page.getValue() == Page.GLOBAL));
        this.xpbottles = (Setting<Boolean>)this.register(new Setting("XpBottles", false, v -> this.page.getValue() == Page.GLOBAL));
        this.pearl = (Setting<Boolean>)this.register(new Setting("Pearls", true, v -> this.page.getValue() == Page.GLOBAL));
        this.players = (Setting<Boolean>)this.register(new Setting("Players", false, v -> this.page.getValue() == Page.GLOBAL));
        this.burrow = (Setting<Boolean>)this.register(new Setting("Burrow", false, v -> this.page.getValue() == Page.GLOBAL));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 255, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 255, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 255, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.redf = new Setting<Integer>("Red", 0, 0, 255, v -> this.page.getValue() == Page.COLORS);
        this.greenf = new Setting<Integer>("Green", 191, 0, 255, v -> this.page.getValue() == Page.COLORS);
        this.bluef = new Setting<Integer>("Blue", 255, 0, 255, v -> this.page.getValue() == Page.COLORS);
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", 70, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.alpha = (Setting<Integer>)this.register(new Setting("OlAlpha", 100, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.burrowBlocks = new ArrayList<BlockPos>();
        this.setInstance();
    }
    
    public static ESP getInstance() {
        if (ESP.INSTANCE == null) {
            ESP.INSTANCE = new ESP();
        }
        return ESP.INSTANCE;
    }
    
    private void setInstance() {
        ESP.INSTANCE = this;
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        this.white = ColorUtil.toRGBA(255, 255, 255, 170);
        this.color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue());
        if (this.items.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityItem) {
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.getRenderPartialTicks());
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
        if (this.xporbs.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityXPOrb) {
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.getRenderPartialTicks());
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
        if (this.players.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity != ESP.mc.player && entity != null) {
                    if (!(entity instanceof EntityPlayer)) {
                        continue;
                    }
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.getRenderPartialTicks());
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
                    if (Mio.friendManager.isFriend(entity.getName()) || entity == Minecraft.getMinecraft().player) {
                        RenderGlobal.renderFilledBox(bb, this.redf.getValue() / 255.0f, this.greenf.getValue() / 255.0f, this.bluef.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    }
                    else {
                        RenderGlobal.renderFilledBox(bb, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.boxAlpha.getValue() / 255.0f);
                    }
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    if (Mio.friendManager.isFriend(entity.getName()) || entity == Minecraft.getMinecraft().player) {
                        RenderUtil.drawBlockOutline(bb, new Color(this.redf.getValue(), this.greenf.getValue(), this.bluef.getValue(), this.alpha.getValue()), 1.0f);
                    }
                    else {
                        RenderUtil.drawBlockOutline(bb, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                    }
                    if (++i < 50) {
                        continue;
                    }
                    break;
                }
            }
        }
        if (this.pearl.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderPearl) {
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.getRenderPartialTicks());
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
        if (this.xpbottles.getValue()) {
            int i = 0;
            for (final Entity entity : ESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityExpBottle) {
                    if (ESP.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, ESP.mc.getRenderPartialTicks());
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
        if (this.burrow.getValue()) {
            for (final BlockPos burrowPos : this.burrowBlocks) {
                if (this.burrowBlocks != null) {
                    this.renderPrikol(new BlockPos((Vec3i)burrowPos));
                }
            }
        }
    }
    
    private void renderPrikol(final BlockPos pos) {
        final String text = "";
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        final double x = pos.getX() + 0.5;
        final double y = pos.getY() + 0.5;
        final double z = pos.getZ() + 0.5;
        final float scale = 0.030833336f;
        GlStateManager.translate(x - ESP.mc.getRenderManager().renderPosX, y - ESP.mc.getRenderManager().renderPosY, z - ESP.mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-ESP.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(ESP.mc.player.rotationPitch, (ESP.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        final int distance = (int)ESP.mc.player.getDistance(x, y, z);
        float scaleD = distance / 2.0f / 3.0f;
        if (scaleD < 1.0f) {
            scaleD = 1.0f;
        }
        GlStateManager.scale(scaleD, scaleD, scaleD);
        final FontRenderer m = ESP.mc.fontRenderer;
        GlStateManager.translate(-(m.getStringWidth(text) / 2.0), 0.0, 0.0);
        m.drawStringWithShadow(text, 0.0f, 6.0f, new Color(255, 255, 255).getRGB());
        RenderUtil.drawBetterCircle(m.getStringWidth(text) / 2.0f + 1.5f, -5.0f, 16.0f, ColorUtil.toRGBA(this.red.getValue(), this.green.getValue(), this.blue.getValue(), 170));
        for (final EntityPlayer p : ESP.mc.world.playerEntities) {
            final BlockPos footPos = new BlockPos(Math.floor(p.posX), Math.floor(p.posY + 0.2), Math.floor(p.posZ));
            if (ESP.mc.world.getBlockState(footPos).getBlock().equals(Blocks.ENDER_CHEST)) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(ESP.echest);
            }
            else if (ESP.mc.world.getBlockState(footPos).getBlock().equals(Blocks.WEB)) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(ESP.web);
            }
            else {
                Minecraft.getMinecraft().getTextureManager().bindTexture(ESP.obby);
            }
        }
        Gui.drawScaledCustomSizeModalRect((int)(m.getStringWidth(text) / 2.0f) - 10, -17, 0.0f, 0.0f, 12, 12, 24, 24, 12.0f, 12.0f);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void onTick() {
        this.burrowBlocks.clear();
        for (final EntityPlayer p : ESP.mc.world.playerEntities) {
            final BlockPos footPos = new BlockPos(Math.floor(p.posX), Math.floor(p.posY + 0.2), Math.floor(p.posZ));
            if (!(ESP.mc.world.getBlockState(footPos).getBlock() instanceof BlockLiquid) && ESP.mc.player.getDistanceSq(footPos) <= 200.0 && p != ESP.mc.player && (ESP.mc.world.getBlockState(footPos).getBlock().fullBlock || ESP.mc.world.getBlockState(footPos).getBlock().equals(Blocks.WEB) || ESP.mc.world.getBlockState(footPos).getBlock().equals(Blocks.ENDER_CHEST))) {
                this.burrowBlocks.add(footPos);
            }
        }
    }
    
    static {
        ESP.INSTANCE = new ESP();
        obby = new ResourceLocation("textures/obby.png");
        echest = new ResourceLocation("textures/echest.png");
        web = new ResourceLocation("textures/web.png");
    }
    
    public enum Page
    {
        COLORS, 
        GLOBAL;
    }
    
    public enum ItemsMode
    {
        Box, 
        Text;
    }
}
