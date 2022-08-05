//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util.plugs;

import net.minecraft.client.renderer.culling.Frustum;
import java.util.Objects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.world.World;
import java.awt.Color;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import java.awt.Rectangle;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import il.dev.mio.api.util.render.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import il.dev.mio.api.util.IMinecraftUtil;

public final class WireFrameUtil implements IMinecraftUtil
{
    public static final ICamera camera;
    public static final Tessellator tessellator;
    private static boolean depth;
    private static boolean texture;
    private static boolean clean;
    private static boolean bind;
    private static boolean override;
    
    public static void GlPost() {
        GLPost(WireFrameUtil.depth, WireFrameUtil.texture, WireFrameUtil.clean, WireFrameUtil.bind, WireFrameUtil.override);
    }
    
    private static void GLPost(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override) {
        GlStateManager.depthMask(true);
        if (!override) {
            GL11.glDisable(2848);
        }
        if (bind) {
            GL11.glEnable(2929);
        }
        if (clean) {
            GL11.glEnable(3553);
        }
        if (!texture) {
            GL11.glDisable(3042);
        }
        if (depth) {
            GL11.glEnable(2896);
        }
    }
    
    public static void GLPre(final float lineWidth) {
        WireFrameUtil.depth = GL11.glIsEnabled(2896);
        WireFrameUtil.texture = GL11.glIsEnabled(3042);
        WireFrameUtil.clean = GL11.glIsEnabled(3553);
        WireFrameUtil.bind = GL11.glIsEnabled(2929);
        WireFrameUtil.override = GL11.glIsEnabled(2848);
        GLPre(WireFrameUtil.depth, WireFrameUtil.texture, WireFrameUtil.clean, WireFrameUtil.bind, WireFrameUtil.override, lineWidth);
    }
    
    private static void GLPre(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override, final float lineWidth) {
        if (depth) {
            GL11.glDisable(2896);
        }
        if (!texture) {
            GL11.glEnable(3042);
        }
        GL11.glLineWidth(lineWidth);
        if (clean) {
            GL11.glDisable(3553);
        }
        if (bind) {
            GL11.glDisable(2929);
        }
        if (!override) {
            GL11.glEnable(2848);
        }
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GL11.glHint(3154, 4354);
        GlStateManager.depthMask(false);
    }
    
    public static double getDiff(final double lastI, final double i, final float ticks, final double ownI) {
        return lastI + (i - lastI) * ticks - ownI;
    }
    
    public static void drawLine(final float x, final float y, final float x1, final float y1, final float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public static void drawRect(final Rectangle rectangle, final int color) {
        drawRect((float)rectangle.x, (float)rectangle.y, (float)(rectangle.x + rectangle.width), (float)(rectangle.y + rectangle.height), color);
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final BufferBuilder builder = WireFrameUtil.tessellator.getBuffer();
        builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        builder.pos((double)x, (double)y1, 0.0).color(red, green, blue, alpha).endVertex();
        builder.pos((double)x1, (double)y1, 0.0).color(red, green, blue, alpha).endVertex();
        builder.pos((double)x1, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        builder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        WireFrameUtil.tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1) {
        final BufferBuilder builder = WireFrameUtil.tessellator.getBuffer();
        builder.begin(7, DefaultVertexFormats.POSITION);
        builder.pos((double)x, (double)y1, 0.0).endVertex();
        builder.pos((double)x1, (double)y1, 0.0).endVertex();
        builder.pos((double)x1, (double)y, 0.0).endVertex();
        builder.pos((double)x, (double)y, 0.0).endVertex();
        WireFrameUtil.tessellator.draw();
    }
    
    public static void drawBorderedRect(float x, float y, float x1, float y1, final int insideC, final int borderC) {
        enableGL2D();
        x *= 2.0f;
        x1 *= 2.0f;
        y *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x, y, y1 - 1.0f, borderC);
        drawVLine(x1 - 1.0f, y, y1, borderC);
        drawHLine(x, x1 - 1.0f, y, borderC);
        drawHLine(x, x1 - 2.0f, y1 - 1.0f, borderC);
        drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawOutlineRect(final float x, final float y, final float w, final float h, final float lineWidth, final int c) {
        drawRect(x, y, x - lineWidth, h, c);
        drawRect(w + lineWidth, y, w, h, c);
        drawRect(x, y, w, y - lineWidth, c);
        drawRect(x, h + lineWidth, w, h, c);
    }
    
    public static void disableGL2D(final boolean unused) {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void enableGL2D(final boolean unused) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1, final int color, final int ignored) {
        enableGL2D(false);
        glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        disableGL2D(false);
    }
    
    public static void enableGL2D() {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }
    
    public static void disableGL2D() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawGradientHRect(final float x, final float y, final float x1, final float y1, final int topColor, final int bottomColor) {
        final float alpha = (topColor >> 24 & 0xFF) / 255.0f;
        final float red = (topColor >> 16 & 0xFF) / 255.0f;
        final float green = (topColor >> 8 & 0xFF) / 255.0f;
        final float blue = (topColor & 0xFF) / 255.0f;
        final float alpha2 = (bottomColor >> 24 & 0xFF) / 255.0f;
        final float red2 = (bottomColor >> 16 & 0xFF) / 255.0f;
        final float green2 = (bottomColor >> 8 & 0xFF) / 255.0f;
        final float blue2 = (bottomColor & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final BufferBuilder builder = WireFrameUtil.tessellator.getBuffer();
        builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        builder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        builder.pos((double)x, (double)y1, 0.0).color(red, green, blue, alpha).endVertex();
        builder.pos((double)x1, (double)y1, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        builder.pos((double)x1, (double)y, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        WireFrameUtil.tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    private static void drawProperOutline(final BlockPos pos, final Color color) {
        final IBlockState iblockstate = WireFrameUtil.mc.world.getBlockState(pos);
        final Entity player = WireFrameUtil.mc.getRenderViewEntity();
        final double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * WireFrameUtil.mc.getRenderPartialTicks();
        final double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * WireFrameUtil.mc.getRenderPartialTicks();
        final double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * WireFrameUtil.mc.getRenderPartialTicks();
        RenderGlobal.drawSelectionBoundingBox(iblockstate.getSelectedBoundingBox((World)WireFrameUtil.mc.world, pos).grow(0.0020000000949949026).offset(-d3, -d4, -d5), color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void renderProperOutline(final BlockPos pos, final Color color, final float lineWidth) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(lineWidth);
        drawProperOutline(pos, color);
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawProperBox(final BlockPos pos, final Color color, final int alpha) {
        final IBlockState iblockstate = WireFrameUtil.mc.world.getBlockState(pos);
        final Entity player = WireFrameUtil.mc.getRenderViewEntity();
        final double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * WireFrameUtil.mc.getRenderPartialTicks();
        final double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * WireFrameUtil.mc.getRenderPartialTicks();
        final double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * WireFrameUtil.mc.getRenderPartialTicks();
        RenderGlobal.renderFilledBox(iblockstate.getSelectedBoundingBox((World)WireFrameUtil.mc.world, pos).grow(0.002).offset(-d3, -d4, -d5), color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha / 255.0f);
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final Color color, final float lineWidth) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(lineWidth);
        RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawProperBoxESP(final BlockPos pos, final Color color, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha, final float height) {
        WireFrameUtil.camera.setPosition(Objects.requireNonNull(WireFrameUtil.mc.getRenderViewEntity()).posX, WireFrameUtil.mc.getRenderViewEntity().posY, WireFrameUtil.mc.getRenderViewEntity().posZ);
        if (WireFrameUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(pos))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            if (box) {
                drawProperBox(pos, color, boxAlpha);
            }
            if (outline) {
                drawProperOutline(pos, color);
            }
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawBoxESP(final BlockPos pos, final Color color, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha, final float height) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - WireFrameUtil.mc.getRenderManager().viewerPosX, pos.getY() - WireFrameUtil.mc.getRenderManager().viewerPosY, pos.getZ() - WireFrameUtil.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - WireFrameUtil.mc.getRenderManager().viewerPosX, pos.getY() + height - WireFrameUtil.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - WireFrameUtil.mc.getRenderManager().viewerPosZ);
        WireFrameUtil.camera.setPosition(Objects.requireNonNull(WireFrameUtil.mc.getRenderViewEntity()).posX, WireFrameUtil.mc.getRenderViewEntity().posY, WireFrameUtil.mc.getRenderViewEntity().posZ);
        if (WireFrameUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(pos))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            if (box) {
                RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, boxAlpha / 255.0f);
            }
            if (outline) {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            }
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void renderCrosses(final BlockPos pos, final Color color, final float lineWidth) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - WireFrameUtil.mc.getRenderManager().viewerPosX, pos.getY() - WireFrameUtil.mc.getRenderManager().viewerPosY, pos.getZ() - WireFrameUtil.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - WireFrameUtil.mc.getRenderManager().viewerPosX, pos.getY() + 1 - WireFrameUtil.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - WireFrameUtil.mc.getRenderManager().viewerPosZ);
        WireFrameUtil.camera.setPosition(Objects.requireNonNull(WireFrameUtil.mc.getRenderViewEntity()).posX, WireFrameUtil.mc.getRenderViewEntity().posY, WireFrameUtil.mc.getRenderViewEntity().posZ);
        if (WireFrameUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(pos))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            renderCrosses(bb, color);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void renderCrosses2(final BlockPos pos, final Color color, final float lineWidth) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - WireFrameUtil.mc.getRenderManager().viewerPosX, pos.getY() - WireFrameUtil.mc.getRenderManager().viewerPosY, pos.getZ() - WireFrameUtil.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - WireFrameUtil.mc.getRenderManager().viewerPosX, pos.getY() + 1 - WireFrameUtil.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - WireFrameUtil.mc.getRenderManager().viewerPosZ);
        WireFrameUtil.camera.setPosition(Objects.requireNonNull(WireFrameUtil.mc.getRenderViewEntity()).posX, WireFrameUtil.mc.getRenderViewEntity().posY, WireFrameUtil.mc.getRenderViewEntity().posZ);
        if (WireFrameUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(pos))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            renderCrosses(bb, color);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawHLine(float x, float y, final float x1, final int y1) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
    }
    
    public static void drawGradientHLine(float x, float y, final float x1, final int color1, final int color2) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        drawGradientHRect(x, x1, y + 1.0f, x1 + 1.0f, color1, color2);
    }
    
    public static void drawVLine(final float x, float y, float x1, final int y1) {
        if (x1 < y) {
            final float var5 = y;
            y = x1;
            x1 = var5;
        }
        drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
    }
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void renderCrosses(final AxisAlignedBB bb, final Color color) {
        final int hex = color.getRGB();
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final BufferBuilder bufferbuilder = WireFrameUtil.tessellator.getBuffer();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 1.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, 1.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, 1.0f).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, 1.0f).endVertex();
        WireFrameUtil.tessellator.draw();
    }
    
    static {
        camera = (ICamera)new Frustum();
        tessellator = (Tessellator)Tessellator.getInstance();
        WireFrameUtil.depth = GL11.glIsEnabled(2896);
        WireFrameUtil.texture = GL11.glIsEnabled(3042);
        WireFrameUtil.clean = GL11.glIsEnabled(3553);
        WireFrameUtil.bind = GL11.glIsEnabled(2929);
        WireFrameUtil.override = GL11.glIsEnabled(2848);
    }
}
