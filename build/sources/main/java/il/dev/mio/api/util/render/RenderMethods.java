//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util.render;

import java.awt.Rectangle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.Minecraft;
import java.nio.ByteBuffer;
import java.awt.Color;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class RenderMethods
{
    public static void drawGradientRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final int paramInt1, final int paramInt2) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(paramInt1);
        GL11.glVertex2f(paramFloat1, paramFloat4);
        GL11.glVertex2f(paramFloat3, paramFloat4);
        glColor(paramInt2);
        GL11.glVertex2f(paramFloat3, paramFloat2);
        GL11.glVertex2f(paramFloat1, paramFloat2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
    }
    
    public static void drawBorderedRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, final int paramInt1, final int paramInt2) {
        enableGL2D();
        paramFloat1 *= 2.0f;
        paramFloat3 *= 2.0f;
        paramFloat2 *= 2.0f;
        paramFloat4 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(paramFloat1, paramFloat2, paramFloat4 - 1.0f, paramInt2);
        drawVLine(paramFloat3 - 1.0f, paramFloat2, paramFloat4, paramInt2);
        drawHLine(paramFloat1, paramFloat3 - 1.0f, paramFloat2, paramInt2);
        drawHLine(paramFloat1, paramFloat3 - 2.0f, paramFloat4 - 1.0f, paramInt2);
        drawRect(paramFloat1 + 1.0f, paramFloat2 + 1.0f, paramFloat3 - 1.0f, paramFloat4 - 1.0f, paramInt1);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawOutlinedBox(final AxisAlignedBB paramAxisAlignedBB) {
        if (paramAxisAlignedBB == null) {
            return;
        }
        GL11.glBegin(3);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
    }
    
    public static void drawBorderedRectReliant(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5, final int paramInt1, final int paramInt2) {
        enableGL2D();
        drawRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1);
        glColor(paramInt2);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(paramFloat5);
        GL11.glBegin(3);
        GL11.glVertex2f(paramFloat1, paramFloat2);
        GL11.glVertex2f(paramFloat1, paramFloat4);
        GL11.glVertex2f(paramFloat3, paramFloat4);
        GL11.glVertex2f(paramFloat3, paramFloat2);
        GL11.glVertex2f(paramFloat1, paramFloat2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        disableGL2D();
    }
    
    public static void drawStrip(final int paramInt1, final int paramInt2, final float paramFloat1, final double paramDouble, final float paramFloat2, final float paramFloat3, final int paramInt3) {
        final float f1 = (paramInt3 >> 24 & 0xFF) / 255.0f;
        final float f2 = (paramInt3 >> 16 & 0xFF) / 255.0f;
        final float f3 = (paramInt3 >> 8 & 0xFF) / 255.0f;
        final float f4 = (paramInt3 & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated((double)paramInt1, (double)paramInt2, 0.0);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glLineWidth(paramFloat1);
        if (paramDouble > 0.0) {
            GL11.glBegin(3);
            for (byte b = 0; b < paramDouble; ++b) {
                final float f5 = (float)(b * paramDouble * 3.141592653589793 / paramFloat2);
                final float f6 = (float)(Math.cos(f5) * paramFloat3);
                final float f7 = (float)(Math.sin(f5) * paramFloat3);
                GL11.glVertex2f(f6, f7);
            }
            GL11.glEnd();
        }
        if (paramDouble < 0.0) {
            GL11.glBegin(3);
            for (byte b = 0; b > paramDouble; --b) {
                final float f5 = (float)(b * paramDouble * 3.141592653589793 / paramFloat2);
                final float f6 = (float)(Math.cos(f5) * -paramFloat3);
                final float f7 = (float)(Math.sin(f5) * -paramFloat3);
                GL11.glVertex2f(f6, f7);
            }
            GL11.glEnd();
        }
        disableGL2D();
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }
    
    public static void enableGL3D() {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }
    
    public static void drawBorderedRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5, final int paramInt1, final int paramInt2) {
        enableGL2D();
        glColor(paramInt1);
        drawRect(paramFloat1 + paramFloat5, paramFloat2 + paramFloat5, paramFloat3 - paramFloat5, paramFloat4 - paramFloat5);
        glColor(paramInt2);
        drawRect(paramFloat1 + paramFloat5, paramFloat2, paramFloat3 - paramFloat5, paramFloat2 + paramFloat5);
        drawRect(paramFloat1, paramFloat2, paramFloat1 + paramFloat5, paramFloat4);
        drawRect(paramFloat3 - paramFloat5, paramFloat2, paramFloat3, paramFloat4);
        drawRect(paramFloat1 + paramFloat5, paramFloat4 - paramFloat5, paramFloat3 - paramFloat5, paramFloat4);
        disableGL2D();
    }
    
    public static void glColor(final Color paramColor) {
        GL11.glColor4f(paramColor.getRed() / 255.0f, paramColor.getGreen() / 255.0f, paramColor.getBlue() / 255.0f, paramColor.getAlpha() / 255.0f);
    }
    
    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void drawBox(final AxisAlignedBB paramAxisAlignedBB) {
        if (paramAxisAlignedBB == null) {
            return;
        }
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
    }
    
    public static void drawGradientBorderedRectReliant(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5, final int paramInt1, final int paramInt2, final int paramInt3) {
        enableGL2D();
        drawGradientRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt3, paramInt2);
        glColor(paramInt1);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(paramFloat5);
        GL11.glBegin(3);
        GL11.glVertex2f(paramFloat1, paramFloat2);
        GL11.glVertex2f(paramFloat1, paramFloat4);
        GL11.glVertex2f(paramFloat3, paramFloat4);
        GL11.glVertex2f(paramFloat3, paramFloat2);
        GL11.glVertex2f(paramFloat1, paramFloat2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        disableGL2D();
    }
    
    public static int applyTexture(final int paramInt1, final int paramInt2, final int paramInt3, final ByteBuffer paramByteBuffer, final boolean paramBoolean1, final boolean paramBoolean2) {
        GL11.glBindTexture(3553, paramInt1);
        GL11.glTexParameteri(3553, 10241, paramBoolean1 ? 9729 : 9728);
        GL11.glTexParameteri(3553, 10240, paramBoolean1 ? 9729 : 9728);
        GL11.glTexParameteri(3553, 10242, paramBoolean2 ? 10497 : 10496);
        GL11.glTexParameteri(3553, 10243, paramBoolean2 ? 10497 : 10496);
        GL11.glPixelStorei(3317, 1);
        GL11.glTexImage2D(3553, 0, 32856, paramInt2, paramInt3, 0, 6408, 5121, paramByteBuffer);
        return paramInt1;
    }
    
    public static void disableGL3D() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
    }
    
    public static void enableGL3D(final float paramFloat) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(paramFloat);
    }
    
    public static void drawRoundedRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, final int paramInt1, final int paramInt2) {
        enableGL2D();
        paramFloat1 *= 2.0f;
        paramFloat2 *= 2.0f;
        paramFloat3 *= 2.0f;
        paramFloat4 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(paramFloat1, paramFloat2 + 1.0f, paramFloat4 - 2.0f, paramInt1);
        drawVLine(paramFloat3 - 1.0f, paramFloat2 + 1.0f, paramFloat4 - 2.0f, paramInt1);
        drawHLine(paramFloat1 + 2.0f, paramFloat3 - 3.0f, paramFloat2, paramInt1);
        drawHLine(paramFloat1 + 2.0f, paramFloat3 - 3.0f, paramFloat4 - 1.0f, paramInt1);
        drawHLine(paramFloat1 + 1.0f, paramFloat1 + 1.0f, paramFloat2 + 1.0f, paramInt1);
        drawHLine(paramFloat3 - 2.0f, paramFloat3 - 2.0f, paramFloat2 + 1.0f, paramInt1);
        drawHLine(paramFloat3 - 2.0f, paramFloat3 - 2.0f, paramFloat4 - 2.0f, paramInt1);
        drawHLine(paramFloat1 + 1.0f, paramFloat1 + 1.0f, paramFloat4 - 2.0f, paramInt1);
        drawRect(paramFloat1 + 1.0f, paramFloat2 + 1.0f, paramFloat3 - 1.0f, paramFloat4 - 1.0f, paramInt2);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static double getDiff(final double paramDouble1, final double paramDouble2, final float paramFloat, final double paramDouble3) {
        return paramDouble1 + (paramDouble2 - paramDouble1) * paramFloat - paramDouble3;
    }
    
    public static void drawRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4) {
        GL11.glBegin(7);
        GL11.glVertex2f(paramFloat1, paramFloat4);
        GL11.glVertex2f(paramFloat3, paramFloat4);
        GL11.glVertex2f(paramFloat3, paramFloat2);
        GL11.glVertex2f(paramFloat1, paramFloat2);
        GL11.glEnd();
    }
    
    public static void renderCrosses(final AxisAlignedBB paramAxisAlignedBB) {
        GL11.glBegin(1);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        GL11.glVertex3d(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        GL11.glEnd();
    }
    
    public static void glColor(final int paramInt) {
        final float f1 = (paramInt >> 24 & 0xFF) / 255.0f;
        final float f2 = (paramInt >> 16 & 0xFF) / 255.0f;
        final float f3 = (paramInt >> 8 & 0xFF) / 255.0f;
        final float f4 = (paramInt & 0xFF) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f1);
    }
    
    public static void drawHLine(float paramFloat1, float paramFloat2, final float paramFloat3, final int paramInt) {
        if (paramFloat2 < paramFloat1) {
            final float f = paramFloat1;
            paramFloat1 = paramFloat2;
            paramFloat2 = f;
        }
        drawRect(paramFloat1, paramFloat3, paramFloat2 + 1.0f, paramFloat3 + 1.0f, paramInt);
    }
    
    public static void drawLine(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5) {
        GL11.glDisable(3553);
        GL11.glLineWidth(paramFloat5);
        GL11.glBegin(1);
        GL11.glVertex2f(paramFloat1, paramFloat2);
        GL11.glVertex2f(paramFloat3, paramFloat4);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public static void drawRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final int paramInt) {
        enableGL2D();
        glColor(paramInt);
        drawRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
        disableGL2D();
    }
    
    public static void drawGradientHRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final int paramInt1, final int paramInt2) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(paramInt1);
        GL11.glVertex2f(paramFloat1, paramFloat2);
        GL11.glVertex2f(paramFloat1, paramFloat4);
        glColor(paramInt2);
        GL11.glVertex2f(paramFloat3, paramFloat4);
        GL11.glVertex2f(paramFloat3, paramFloat2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
    }
    
    public static void rectangle(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, final int paramInt) {
        if (paramDouble1 < paramDouble3) {
            final double d = paramDouble1;
            paramDouble1 = paramDouble3;
            paramDouble3 = d;
        }
        if (paramDouble2 < paramDouble4) {
            final double d = paramDouble2;
            paramDouble2 = paramDouble4;
            paramDouble4 = d;
        }
        final float f1 = (paramInt >> 24 & 0xFF) / 255.0f;
        final float f2 = (paramInt >> 16 & 0xFF) / 255.0f;
        final float f3 = (paramInt >> 8 & 0xFF) / 255.0f;
        final float f4 = (paramInt & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f2, f3, f4, f1);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferBuilder.pos(paramDouble1, paramDouble4, 0.0);
        bufferBuilder.pos(paramDouble3, paramDouble4, 0.0);
        bufferBuilder.pos(paramDouble3, paramDouble2, 0.0);
        bufferBuilder.pos(paramDouble1, paramDouble2, 0.0);
        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }
    
    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void drawFullCircle(int paramInt1, int paramInt2, double paramDouble, final int paramInt3) {
        paramDouble *= 2.0;
        paramInt1 *= 2;
        paramInt2 *= 2;
        final float f1 = (paramInt3 >> 24 & 0xFF) / 255.0f;
        final float f2 = (paramInt3 >> 16 & 0xFF) / 255.0f;
        final float f3 = (paramInt3 >> 8 & 0xFF) / 255.0f;
        final float f4 = (paramInt3 & 0xFF) / 255.0f;
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glBegin(6);
        for (byte b = 0; b <= 360; ++b) {
            final double d1 = Math.sin(b * 3.141592653589793 / 180.0) * paramDouble;
            final double d2 = Math.cos(b * 3.141592653589793 / 180.0) * paramDouble;
            GL11.glVertex2d(paramInt1 + d1, paramInt2 + d2);
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawTriangle(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final int paramInt5) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        final float f1 = (paramInt5 >> 24 & 0xFF) / 255.0f;
        final float f2 = (paramInt5 >> 16 & 0xFF) / 255.0f;
        final float f3 = (paramInt5 >> 8 & 0xFF) / 255.0f;
        final float f4 = (paramInt5 & 0xFF) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(1.0f);
        GL11.glShadeModel(7425);
        switch (paramInt3) {
            case 0: {
                GL11.glBegin(2);
                GL11.glVertex2d((double)paramInt1, (double)(paramInt2 + paramInt4));
                GL11.glVertex2d((double)(paramInt1 + paramInt4), (double)(paramInt2 - paramInt4));
                GL11.glVertex2d((double)(paramInt1 - paramInt4), (double)(paramInt2 - paramInt4));
                GL11.glEnd();
                GL11.glBegin(4);
                GL11.glVertex2d((double)paramInt1, (double)(paramInt2 + paramInt4));
                GL11.glVertex2d((double)(paramInt1 + paramInt4), (double)(paramInt2 - paramInt4));
                GL11.glVertex2d((double)(paramInt1 - paramInt4), (double)(paramInt2 - paramInt4));
                GL11.glEnd();
                break;
            }
            case 1: {
                GL11.glBegin(2);
                GL11.glVertex2d((double)paramInt1, (double)paramInt2);
                GL11.glVertex2d((double)paramInt1, (double)(paramInt2 + paramInt4 / 2));
                GL11.glVertex2d((double)(paramInt1 + paramInt4 + paramInt4 / 2), (double)paramInt2);
                GL11.glEnd();
                GL11.glBegin(4);
                GL11.glVertex2d((double)paramInt1, (double)paramInt2);
                GL11.glVertex2d((double)paramInt1, (double)(paramInt2 + paramInt4 / 2));
                GL11.glVertex2d((double)(paramInt1 + paramInt4 + paramInt4 / 2), (double)paramInt2);
                GL11.glEnd();
                break;
            }
            case 3: {
                GL11.glBegin(2);
                GL11.glVertex2d((double)paramInt1, (double)paramInt2);
                GL11.glVertex2d(paramInt1 + paramInt4 * 1.25, (double)(paramInt2 - paramInt4 / 2));
                GL11.glVertex2d(paramInt1 + paramInt4 * 1.25, (double)(paramInt2 + paramInt4 / 2));
                GL11.glEnd();
                GL11.glBegin(4);
                GL11.glVertex2d(paramInt1 + paramInt4 * 1.25, (double)(paramInt2 - paramInt4 / 2));
                GL11.glVertex2d((double)paramInt1, (double)paramInt2);
                GL11.glVertex2d(paramInt1 + paramInt4 * 1.25, (double)(paramInt2 + paramInt4 / 2));
                GL11.glEnd();
                break;
            }
        }
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawGradientRect(final double paramDouble1, final double paramDouble2, final double paramDouble3, final double paramDouble4, final int paramInt1, final int paramInt2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        glColor(paramInt1);
        GL11.glVertex2d(paramDouble3, paramDouble2);
        GL11.glVertex2d(paramDouble1, paramDouble2);
        glColor(paramInt2);
        GL11.glVertex2d(paramDouble1, paramDouble4);
        GL11.glVertex2d(paramDouble3, paramDouble4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static Color rainbow(final long paramLong, final float paramFloat) {
        final float f = (System.nanoTime() + paramLong) / 1.0E10f % 1.0f;
        final long l = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(f, 1.0f, 1.0f)), 16);
        final Color color = new Color((int)l);
        return new Color(color.getRed() / 255.0f * paramFloat, color.getGreen() / 255.0f * paramFloat, color.getBlue() / 255.0f * paramFloat, color.getAlpha() / 255.0f);
    }
    
    public static void drawRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5, final float paramFloat6, final float paramFloat7, final float paramFloat8) {
        enableGL2D();
        GL11.glColor4f(paramFloat5, paramFloat6, paramFloat7, paramFloat8);
        drawRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
        disableGL2D();
    }
    
    public static void glColor(final float paramFloat, final int paramInt1, final int paramInt2, final int paramInt3) {
        final float f1 = 0.003921569f * paramInt1;
        final float f2 = 0.003921569f * paramInt2;
        final float f3 = 0.003921569f * paramInt3;
        GL11.glColor4f(f1, f2, f3, paramFloat);
    }
    
    public static void drawRect(final Rectangle paramRectangle, final int paramInt) {
        drawRect((float)paramRectangle.x, (float)paramRectangle.y, (float)(paramRectangle.x + paramRectangle.width), (float)(paramRectangle.y + paramRectangle.height), paramInt);
    }
    
    public static Color blend(final Color paramColor1, final Color paramColor2, final float paramFloat) {
        final float f = 1.0f - paramFloat;
        final float[] arrayOfFloat1 = new float[3];
        final float[] arrayOfFloat2 = new float[3];
        paramColor1.getColorComponents(arrayOfFloat1);
        paramColor2.getColorComponents(arrayOfFloat2);
        return new Color(arrayOfFloat1[0] * paramFloat + arrayOfFloat2[0] * f, arrayOfFloat1[1] * paramFloat + arrayOfFloat2[1] * f, arrayOfFloat1[2] * paramFloat + arrayOfFloat2[2] * f);
    }
    
    public static void drawHLine(float paramFloat1, float paramFloat2, final float paramFloat3, final int paramInt1, final int paramInt2) {
        if (paramFloat2 < paramFloat1) {
            final float f = paramFloat1;
            paramFloat1 = paramFloat2;
            paramFloat2 = f;
        }
        drawGradientRect(paramFloat1, paramFloat3, paramFloat2 + 1.0f, paramFloat3 + 1.0f, paramInt1, paramInt2);
    }
    
    public static void drawBorderedRect(final Rectangle paramRectangle, final float paramFloat, final int paramInt1, final int paramInt2) {
        final float f1 = (float)paramRectangle.x;
        final float f2 = (float)paramRectangle.y;
        final float f3 = (float)(paramRectangle.x + paramRectangle.width);
        final float f4 = (float)(paramRectangle.y + paramRectangle.height);
        enableGL2D();
        glColor(paramInt1);
        drawRect(f1 + paramFloat, f2 + paramFloat, f3 - paramFloat, f4 - paramFloat);
        glColor(paramInt2);
        drawRect(f1 + 1.0f, f2, f3 - 1.0f, f2 + paramFloat);
        drawRect(f1, f2, f1 + paramFloat, f4);
        drawRect(f3 - paramFloat, f2, f3, f4);
        drawRect(f1 + 1.0f, f4 - paramFloat, f3 - 1.0f, f4);
        disableGL2D();
    }
    
    public static void drawGradientBorderedRect(final double paramDouble1, final double paramDouble2, final double paramDouble3, final double paramDouble4, final float paramFloat, final int paramInt1, final int paramInt2, final int paramInt3) {
        enableGL2D();
        GL11.glPushMatrix();
        glColor(paramInt1);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(paramDouble1, paramDouble2);
        GL11.glVertex2d(paramDouble1, paramDouble4);
        GL11.glVertex2d(paramDouble3, paramDouble4);
        GL11.glVertex2d(paramDouble3, paramDouble2);
        GL11.glVertex2d(paramDouble1, paramDouble2);
        GL11.glVertex2d(paramDouble3, paramDouble2);
        GL11.glVertex2d(paramDouble1, paramDouble4);
        GL11.glVertex2d(paramDouble3, paramDouble4);
        GL11.glEnd();
        GL11.glPopMatrix();
        drawGradientRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramInt2, paramInt3);
        disableGL2D();
    }
    
    public static void drawCircle(float paramFloat1, float paramFloat2, float paramFloat3, final int paramInt1, final int paramInt2) {
        paramFloat3 *= 2.0f;
        paramFloat1 *= 2.0f;
        paramFloat2 *= 2.0f;
        final float f1 = (paramInt2 >> 24 & 0xFF) / 255.0f;
        final float f2 = (paramInt2 >> 16 & 0xFF) / 255.0f;
        final float f3 = (paramInt2 >> 8 & 0xFF) / 255.0f;
        final float f4 = (paramInt2 & 0xFF) / 255.0f;
        final float f5 = (float)(6.2831852 / paramInt1);
        final float f6 = (float)Math.cos(f5);
        final float f7 = (float)Math.sin(f5);
        float f8 = paramFloat3;
        float f9 = 0.0f;
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glBegin(2);
        for (byte b = 0; b < paramInt1; ++b) {
            GL11.glVertex2f(f8 + paramFloat1, f9 + paramFloat2);
            final float f10 = f8;
            f8 = f6 * f8 - f7 * f9;
            f9 = f7 * f10 + f6 * f9;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawVLine(final float paramFloat1, float paramFloat2, float paramFloat3, final int paramInt) {
        if (paramFloat3 < paramFloat2) {
            final float f = paramFloat2;
            paramFloat2 = paramFloat3;
            paramFloat3 = f;
        }
        drawRect(paramFloat1, paramFloat2 + 1.0f, paramFloat1 + 1.0f, paramFloat3, paramInt);
    }
    
    public static void drawGradientBorderedRect(final int i, final int i1, final float v, final float v1, final int i2) {
    }
}
