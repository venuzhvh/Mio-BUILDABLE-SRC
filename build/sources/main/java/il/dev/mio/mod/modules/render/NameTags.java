//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.render;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.client.gui.Gui;
import il.dev.mio.mod.modules.client.FontMod;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.mod.modules.misc.PopNotify;
import net.minecraft.util.math.MathHelper;
import il.dev.mio.Mio;
import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.client.entity.EntityPlayerSP;
import il.dev.mio.api.event.events.Render3DEvent;
import java.awt.Color;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import java.util.HashMap;
import java.awt.Font;
import java.util.Map;
import net.minecraft.client.renderer.culling.ICamera;
import il.dev.mio.mod.gui.font.CustomFont;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class NameTags extends Module
{
    private static NameTags INSTANCE;
    private final Setting<Boolean> armor;
    private final Setting<Boolean> enchant;
    private final Setting<Boolean> reversed;
    private final Setting<Boolean> durability;
    private final Setting<Boolean> health;
    private final Setting<Boolean> gameMode;
    private final Setting<Boolean> ping;
    private final Setting<Boolean> item;
    private final Setting<Boolean> invisibles;
    private final Setting<Boolean> pops;
    private final Setting<Boolean> inside;
    private final Setting<Boolean> outline;
    public Setting<Boolean> outlineRainbow;
    private final Setting<Integer> Ored;
    private final Setting<Integer> Ogreen;
    private final Setting<Integer> Oblue;
    private final CustomFont customFont;
    private ICamera camera;
    boolean shownItem;
    private Map glCapMap;
    
    public NameTags() {
        super("NameTags", "Kewl nametags", Category.RENDER, false, false, false);
        this.armor = (Setting<Boolean>)this.register(new Setting("Armor", true));
        this.enchant = (Setting<Boolean>)this.register(new Setting("Enchants", true, v -> this.armor.getValue()));
        this.reversed = (Setting<Boolean>)this.register(new Setting("Reversed", false, v -> this.armor.getValue()));
        this.durability = (Setting<Boolean>)this.register(new Setting("Durability", true));
        this.health = (Setting<Boolean>)this.register(new Setting("Health", true));
        this.gameMode = (Setting<Boolean>)this.register(new Setting("Gamemode", false));
        this.ping = (Setting<Boolean>)this.register(new Setting("Ping", true));
        this.item = (Setting<Boolean>)this.register(new Setting("ItemName", true));
        this.invisibles = (Setting<Boolean>)this.register(new Setting("Invisibles", true));
        this.pops = (Setting<Boolean>)this.register(new Setting("Pops", true));
        this.inside = new Setting<Boolean>("Rectangle", true);
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", true));
        this.outlineRainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", false));
        this.Ored = (Setting<Integer>)this.register(new Setting("Red", 255, 0, 255, v -> this.outline.getValue() && this.outlineRainbow.getValue().equals(false)));
        this.Ogreen = (Setting<Integer>)this.register(new Setting("Green", 150, 0, 255, v -> this.outline.getValue() && this.outlineRainbow.getValue().equals(false)));
        this.Oblue = (Setting<Integer>)this.register(new Setting("Blue", 255, 0, 255, v -> this.outline.getValue() && this.outlineRainbow.getValue().equals(false)));
        this.customFont = new CustomFont(new Font("Verdana", 0, 17), true, true);
        this.setInstance();
        this.glCapMap = new HashMap();
        this.camera = (ICamera)new Frustum();
    }
    
    public static NameTags getInstance() {
        if (NameTags.INSTANCE == null) {
            NameTags.INSTANCE = new NameTags();
        }
        return NameTags.INSTANCE;
    }
    
    private void setInstance() {
        NameTags.INSTANCE = this;
    }
    
    public void drawOutlineLine(double left, double top, double right, double bottom, final float width, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(width);
        if (left < right) {
            final double j = left;
            left = right;
            right = j;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        float a1 = 0.0f;
        float r1 = 0.0f;
        float g1 = 0.0f;
        float b1 = 0.0f;
        final float a2 = 0.0f;
        final float r2 = 0.0f;
        final float g2 = 0.0f;
        final float b2 = 0.0f;
        final float a3 = 0.0f;
        final float r3 = 0.0f;
        final float g3 = 0.0f;
        final float b3 = 0.0f;
        float a4;
        float r4;
        float g4;
        float b4;
        if (this.outlineRainbow.getValue()) {
            final int rainbow = rainbow(1).getRGB();
            final int rainbow2 = rainbow(1000).getRGB();
            a4 = (rainbow >> 24 & 0xFF) / 255.0f;
            r4 = (rainbow >> 16 & 0xFF) / 255.0f;
            g4 = (rainbow >> 8 & 0xFF) / 255.0f;
            b4 = (rainbow & 0xFF) / 255.0f;
            a1 = (rainbow2 >> 24 & 0xFF) / 255.0f;
            r1 = (rainbow2 >> 16 & 0xFF) / 255.0f;
            g1 = (rainbow2 >> 8 & 0xFF) / 255.0f;
            b1 = (rainbow2 & 0xFF) / 255.0f;
        }
        else {
            a4 = (color >> 24 & 0xFF) / 255.0f;
            r4 = (color >> 16 & 0xFF) / 255.0f;
            g4 = (color >> 8 & 0xFF) / 255.0f;
            b4 = (color & 0xFF) / 255.0f;
        }
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        if (this.outlineRainbow.getValue()) {
            bufferbuilder.pos(left, bottom, 0.0).color(r4, g4, b4, a4).endVertex();
            bufferbuilder.pos(right, bottom, 0.0).color(r1, g1, b1, a1).endVertex();
            bufferbuilder.pos(right, top, 0.0).color(r4, g4, b4, a4).endVertex();
            bufferbuilder.pos(left, top, 0.0).color(r1, g1, b1, a1).endVertex();
            bufferbuilder.pos(left, bottom, 0.0).color(r4, g4, b4, a4).endVertex();
        }
        else {
            bufferbuilder.pos(left, bottom, 0.0).color(r4, g4, b4, a4).endVertex();
            bufferbuilder.pos(right, bottom, 0.0).color(r4, g4, b4, a4).endVertex();
            bufferbuilder.pos(right, top, 0.0).color(r4, g4, b4, a4).endVertex();
            bufferbuilder.pos(left, top, 0.0).color(r4, g4, b4, a4).endVertex();
            bufferbuilder.pos(left, bottom, 0.0).color(r4, g4, b4, a4).endVertex();
        }
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static Color rainbow(final int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360.0;
        return Color.getHSBColor((float)(rainbowState / 360.0), 1.0f, 1.0f);
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (NameTags.mc.player != null) {
            final EntityPlayerSP entityPlayerSP = (EntityPlayerSP)((NameTags.mc.getRenderViewEntity() == null) ? NameTags.mc.player : NameTags.mc.getRenderViewEntity());
            final double d3 = entityPlayerSP.lastTickPosX + (entityPlayerSP.posX - entityPlayerSP.lastTickPosX) * event.getPartialTicks();
            final double d4 = entityPlayerSP.lastTickPosY + (entityPlayerSP.posY - entityPlayerSP.lastTickPosY) * event.getPartialTicks();
            final double d5 = entityPlayerSP.lastTickPosZ + (entityPlayerSP.posZ - entityPlayerSP.lastTickPosZ) * event.getPartialTicks();
            this.camera.setPosition(d3, d4, d5);
            List<EntityPlayer> players = NameTags.mc.world.playerEntities;
            players.sort(Comparator.comparing(entityPlayer -> entityPlayerSP.getDistance((Entity)entityPlayer)).reversed());
            for (EntityPlayer p : players) {
                final NetworkPlayerInfo npi = NameTags.mc.player.connection.getPlayerInfo(p.getGameProfile().getId());
                if ((this.camera.isBoundingBoxInFrustum(p.getEntityBoundingBox()) || this.camera.isBoundingBoxInFrustum(p.getEntityBoundingBox().offset(0.0, 2.0, 0.0))) && p != NameTags.mc.getRenderViewEntity() && p.isEntityAlive()) {
                    final double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * NameTags.mc.timer.renderPartialTicks - NameTags.mc.renderManager.renderPosX;
                    final double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * NameTags.mc.timer.renderPartialTicks - NameTags.mc.renderManager.renderPosY;
                    final double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * NameTags.mc.timer.renderPartialTicks - NameTags.mc.renderManager.renderPosZ;
                    if (npi != null && this.getShortName(npi.getGameType().getName()).equalsIgnoreCase("SP") && !this.invisibles.getValue()) {
                        continue;
                    }
                    if (p.getName().startsWith("Body #")) {
                        continue;
                    }
                    this.renderNametag(p, pX, pY, pZ);
                }
            }
        }
    }
    
    public String getShortName(final String gameType) {
        if (gameType.equalsIgnoreCase("survival")) {
            return "S";
        }
        if (gameType.equalsIgnoreCase("creative")) {
            return "C";
        }
        if (gameType.equalsIgnoreCase("adventure")) {
            return "A";
        }
        return gameType.equalsIgnoreCase("spectator") ? "SP" : "NONE";
    }
    
    public String getHealth(final float health) {
        if (health > 18.0f) {
            return "a";
        }
        if (health > 16.0f) {
            return "2";
        }
        if (health > 12.0f) {
            return "e";
        }
        if (health > 8.0f) {
            return "6";
        }
        return (health > 5.0f) ? "c" : "4";
    }
    
    private String getName(final EntityPlayer player) {
        return player.getName();
    }
    
    public void renderNametag(final EntityPlayer player, final double x, final double y, final double z) {
        this.shownItem = false;
        GlStateManager.pushMatrix();
        final NetworkPlayerInfo npi = NameTags.mc.player.connection.getPlayerInfo(player.getGameProfile().getId());
        final boolean isFriend = Mio.friendManager.isFriend(player.getName());
        StringBuilder var10000 = new StringBuilder().append(isFriend ? ("§" + (isFriend ? "b" : "c")) : (player.isSneaking() ? "§5" : "§r")).append(this.getName(player)).append((this.gameMode.getValue() && npi != null) ? (" [" + this.getShortName(npi.getGameType().getName()) + "]") : "").append((this.ping.getValue() && npi != null) ? (" " + npi.getResponseTime() + "ms") : "").append(this.health.getValue() ? (" §" + this.getHealth(player.getHealth() + player.getAbsorptionAmount()) + MathHelper.ceil(player.getHealth() + player.getAbsorptionAmount())) : "");
        if (Chams.getInstance().isOn() && Chams.getInstance().sneak.getValue()) {
            var10000 = new StringBuilder().append(isFriend ? ("§" + (isFriend ? "b" : "c")) : (player.isSneaking() ? "§r" : "§r")).append(this.getName(player)).append((this.gameMode.getValue() && npi != null) ? (" [" + this.getShortName(npi.getGameType().getName()) + "]") : "").append((this.ping.getValue() && npi != null) ? (" " + npi.getResponseTime() + "ms") : "").append(this.health.getValue() ? (" §" + this.getHealth(player.getHealth() + player.getAbsorptionAmount()) + MathHelper.ceil(player.getHealth() + player.getAbsorptionAmount())) : "");
        }
        PopNotify.getInstance();
        String var10002;
        if (PopNotify.TotemPopContainer.get(player.getName()) != null && this.pops.getValue()) {
            final StringBuilder var10001 = new StringBuilder().append(" ").append(ChatFormatting.DARK_RED).append("-");
            PopNotify.getInstance();
            var10002 = var10001.append(PopNotify.TotemPopContainer.get(player.getName())).toString();
        }
        else {
            var10002 = "";
        }
        String name = var10000.append(var10002).toString();
        name = name.replace(".0", "");
        final EntityPlayerSP entityPlayerSP = (EntityPlayerSP)((NameTags.mc.getRenderViewEntity() == null) ? NameTags.mc.player : NameTags.mc.getRenderViewEntity());
        final float distance = entityPlayerSP.getDistance((Entity)player);
        float var10003 = ((distance / 5.0f <= 2.0f) ? 2.0f : (distance / 5.0f * 1.41f)) * 2.5f * 0.0040999996f;
        if (distance <= 8.0) {
            var10003 = 0.0245f;
        }
        GL11.glTranslated((double)(float)x, (float)y + 2.45f - (player.isSneaking() ? 0.4 : 0.0) + ((distance / 5.0f > 2.0f) ? (distance / 12.0f - 0.7) : 0.0), (double)(float)z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-NameTags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        final float var10004 = (NameTags.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f;
        GL11.glRotatef(NameTags.mc.getRenderManager().playerViewX, var10004, 0.0f, 0.0f);
        GL11.glScalef(-var10003, -var10003, var10003);
        this.disableGlCap(2896, 2929);
        this.enableGlCap(3042);
        GL11.glBlendFunc(770, 771);
        int width;
        if (FontMod.getInstance().isOn()) {
            width = this.customFont.getStringWidth(name) / 2 + 1;
        }
        else {
            width = NameTags.mc.fontRenderer.getStringWidth(name) / 2 + 1;
        }
        final int color = new Color(0, 0, 0).getRGB();
        int outlineColor = isFriend ? new Color(0, 213, 255, 255).getRGB() : new Color(this.Ored.getValue(), this.Ogreen.getValue(), this.Oblue.getValue(), 255).getRGB();
        if (player.isSneaking()) {
            if (Chams.getInstance().isOn() && Chams.getInstance().sneak.getValue()) {
                outlineColor = (isFriend ? new Color(0, 213, 255, 255).getRGB() : new Color(this.Ored.getValue(), this.Ogreen.getValue(), this.Oblue.getValue(), 255).getRGB());
            }
            else {
                outlineColor = (isFriend ? new Color(0, 213, 255, 255).getRGB() : new Color(170, 0, 170, 255).getRGB());
            }
        }
        if (this.inside.getValue()) {
            Gui.drawRect(-width - 1, 8, width + 1, 19, changeAlpha(color, 120));
        }
        if (this.outline.getValue()) {
            this.drawOutlineLine(-width - 1, 8.0, width + 1, 19.0, 1.3f, outlineColor);
        }
        if (FontMod.getInstance().isOn()) {
            this.customFont.drawStringWithShadow(name, -width, 8.649999618530273, -1);
        }
        else {
            NameTags.mc.fontRenderer.drawStringWithShadow(name, (float)(-width), 9.2f, -1);
        }
        if (this.armor.getValue()) {
            int xOffset = -8;
            final Item mainhand = player.getHeldItemMainhand().getItem();
            final Item offhand = player.getHeldItemOffhand().getItem();
            if (mainhand != Items.AIR && offhand == Items.AIR) {
                xOffset = -16;
            }
            else if (mainhand == Items.AIR && offhand != Items.AIR) {
                xOffset = 0;
            }
            int index = 0;
            for (final ItemStack renderOffhand : player.inventory.armorInventory) {
                if (renderOffhand != null) {
                    xOffset -= 8;
                    if (renderOffhand.getItem() == Items.AIR) {
                        continue;
                    }
                    ++index;
                }
            }
            if (player.getHeldItemOffhand().getItem() != Items.AIR) {
                ++index;
            }
            final int cacheX = xOffset - 8;
            xOffset += 8 * (5 - index) - ((index == 0) ? 4 : 0);
            Label_1762: {
                Label_1691: {
                    if (this.reversed.getValue()) {
                        if (player.getHeldItemOffhand().getItem() != Items.AIR) {
                            break Label_1691;
                        }
                    }
                    else if (player.getHeldItemMainhand().getItem() != Items.AIR) {
                        break Label_1691;
                    }
                    if (!this.reversed.getValue()) {
                        this.shownItem = true;
                    }
                    break Label_1762;
                }
                xOffset -= 10;
                if (this.reversed.getValue()) {
                    final ItemStack renderOffhand = player.getHeldItemOffhand().copy();
                    this.renderItem(player, renderOffhand, xOffset, 7, cacheX, false);
                }
                else {
                    final ItemStack renderOffhand = player.getHeldItemMainhand().copy();
                    this.renderItem(player, renderOffhand, xOffset, 7, cacheX, true);
                }
                xOffset += 18;
            }
            if (this.reversed.getValue()) {
                for (index = 0; index <= 3; ++index) {
                    final ItemStack armourStack2 = (ItemStack)player.inventory.armorInventory.get(index);
                    if (armourStack2 != null && armourStack2.getItem() != Items.AIR) {
                        final ItemStack renderStack2 = armourStack2.copy();
                        this.renderItem(player, renderStack2, xOffset, 7, cacheX, false);
                        xOffset += 16;
                    }
                }
            }
            else {
                for (index = 3; index >= 0; --index) {
                    final ItemStack armourStack2 = (ItemStack)player.inventory.armorInventory.get(index);
                    if (armourStack2 != null && armourStack2.getItem() != Items.AIR) {
                        final ItemStack renderStack2 = armourStack2.copy();
                        this.renderItem(player, renderStack2, xOffset, 7, cacheX, false);
                        xOffset += 16;
                    }
                }
            }
            Label_2040: {
                if (this.reversed.getValue()) {
                    if (player.getHeldItemMainhand().getItem() == Items.AIR) {
                        break Label_2040;
                    }
                }
                else if (player.getHeldItemOffhand().getItem() == Items.AIR) {
                    break Label_2040;
                }
                xOffset += 0;
                if (this.reversed.getValue()) {
                    final ItemStack renderOffhand = player.getHeldItemMainhand().copy();
                    this.renderItem(player, renderOffhand, xOffset, 7, cacheX, true);
                }
                else {
                    final ItemStack renderOffhand = player.getHeldItemOffhand().copy();
                    this.renderItem(player, renderOffhand, xOffset, 7, cacheX, false);
                }
                xOffset += 8;
            }
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
        }
        else if (this.durability.getValue()) {
            int xOffset = 0;
            int count = 0;
            for (final ItemStack armourStack3 : player.inventory.armorInventory) {
                if (armourStack3 != null) {
                    xOffset -= 8;
                    if (armourStack3.getItem() == Items.AIR) {
                        continue;
                    }
                    ++count;
                }
            }
            if (player.getHeldItemOffhand().getItem() != Items.AIR) {
                ++count;
            }
            final int cacheX2 = xOffset - 8;
            xOffset += 8 * (5 - count) - ((count == 0) ? 4 : 0);
            if (this.reversed.getValue()) {
                for (int index = 0; index <= 3; ++index) {
                    final ItemStack armourStack4 = (ItemStack)player.inventory.armorInventory.get(index);
                    if (armourStack4 != null && armourStack4.getItem() != Items.AIR) {
                        final ItemStack renderOffhand = armourStack4.copy();
                        this.renderDurabilityText(player, renderOffhand, xOffset, 12);
                        xOffset += 16;
                    }
                }
            }
            else {
                for (int index = 3; index >= 0; --index) {
                    final ItemStack armourStack4 = (ItemStack)player.inventory.armorInventory.get(index);
                    if (armourStack4 != null && armourStack4.getItem() != Items.AIR) {
                        final ItemStack renderOffhand = armourStack4.copy();
                        this.renderDurabilityText(player, renderOffhand, xOffset, 12);
                        xOffset += 16;
                    }
                }
            }
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
        this.resetCaps();
        GlStateManager.resetColor();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void renderItem(final EntityPlayer player, final ItemStack stack, final int x, final int y, final int nameX, final boolean showHeldItemText) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        NameTags.mc.getRenderItem().zLevel = -100.0f;
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        NameTags.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y / 2 - 12);
        if (this.durability.getValue()) {
            NameTags.mc.getRenderItem().renderItemOverlays(NameTags.mc.fontRenderer, stack, x, y / 2 - 12);
        }
        NameTags.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        this.renderEnchantText(player, stack, x, y - 18);
        if (!this.shownItem && this.item.getValue() && showHeldItemText) {
            if (FontMod.getInstance().isOn()) {
                this.customFont.drawString(stack.getDisplayName().equalsIgnoreCase("Air") ? "" : stack.getDisplayName(), nameX * 2 + 95 - this.customFont.getStringWidth(stack.getDisplayName()) / 2, y - 37, Color.GRAY.getRGB(), true);
            }
            else {
                NameTags.mc.fontRenderer.drawStringWithShadow(stack.getDisplayName().equalsIgnoreCase("Air") ? "" : stack.getDisplayName(), (float)(nameX * 2 + 95 - NameTags.mc.fontRenderer.getStringWidth(stack.getDisplayName()) / 2), (float)(y - 37), Color.GRAY.getRGB());
            }
            this.shownItem = true;
        }
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    private void renderDurabilityText(final EntityPlayer player, final ItemStack stack, final int x, final int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool) {
            final float green = (stack.getMaxDamage() - (float)stack.getItemDamage()) / stack.getMaxDamage();
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            if (FontMod.getInstance().isOn()) {
                this.customFont.drawStringWithShadow(dmg + "%", x * 2 + 4, y - 10, ColourHolder.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
            }
            else {
                NameTags.mc.fontRenderer.drawStringWithShadow(dmg + "%", (float)(x * 2 + 4), (float)(y - 10), ColourHolder.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
            }
        }
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    public void renderEnchantText(final EntityPlayer player, final ItemStack stack, final int x, final int y) {
        int encY = y;
        int yCount = y;
        if ((stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool) && this.durability.getValue()) {
            final float green = (stack.getMaxDamage() - (float)stack.getItemDamage()) / stack.getMaxDamage();
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            if (FontMod.getInstance().isOn()) {
                this.customFont.drawStringWithShadow(dmg + "%", x * 2 + 4, y - 10, ColourHolder.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
            }
            else {
                NameTags.mc.fontRenderer.drawStringWithShadow(dmg + "%", (float)(x * 2 + 4), (float)(y - 10), ColourHolder.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
            }
        }
        if (this.enchant.getValue()) {
            final NBTTagList enchants = stack.getEnchantmentTagList();
            if (enchants != null) {
                for (int index = 0; index < enchants.tagCount(); ++index) {
                    final short id = enchants.getCompoundTagAt(index).getShort("id");
                    final short level = enchants.getCompoundTagAt(index).getShort("lvl");
                    final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                    if (enc != null && !enc.isCurse()) {
                        String encName = (level == 1) ? enc.getTranslatedName((int)level).substring(0, 3).toLowerCase() : (enc.getTranslatedName((int)level).substring(0, 2).toLowerCase() + level);
                        encName = encName.substring(0, 1).toUpperCase() + encName.substring(1);
                        if (encName.contains("Pr") || encName.contains("Bl")) {
                            GL11.glPushMatrix();
                            GL11.glScalef(1.0f, 1.0f, 0.0f);
                            if (FontMod.getInstance().isOn()) {
                                this.customFont.drawStringWithShadow(encName, x * 2 + 3, yCount, -1);
                            }
                            else {
                                NameTags.mc.fontRenderer.drawStringWithShadow(encName, (float)(x * 2 + 3), (float)yCount, -1);
                            }
                            GL11.glScalef(1.0f, 1.0f, 1.0f);
                            GL11.glPopMatrix();
                            encY += 8;
                            yCount += 8;
                        }
                    }
                }
            }
        }
    }
    
    public static final int changeAlpha(int origColor, final int userInputedAlpha) {
        origColor &= 0xFFFFFF;
        return userInputedAlpha << 24 | origColor;
    }
    
    public void resetCaps() {
    }
    
    public void enableGlCap(final int cap) {
        this.setGlCap(cap, true);
    }
    
    public void enableGlCap(final int... caps) {
        final int[] var2 = caps;
        for (int var3 = caps.length, var4 = 0; var4 < var3; ++var4) {
            final int cap = var2[var4];
            this.setGlCap(cap, true);
        }
    }
    
    public void disableGlCap(final int... caps) {
        final int[] var2 = caps;
        for (int var3 = caps.length, var4 = 0; var4 < var3; ++var4) {
            final int cap = var2[var4];
            this.setGlCap(cap, false);
        }
    }
    
    public void setGlCap(final int cap, final boolean state) {
        this.glCapMap.put(cap, GL11.glGetBoolean(cap));
        this.setGlState(cap, state);
    }
    
    public void setGlState(final int cap, final boolean state) {
        if (state) {
            GL11.glEnable(cap);
        }
        else {
            GL11.glDisable(cap);
        }
    }
    
    static {
        NameTags.INSTANCE = new NameTags();
    }
    
    public static class ColourHolder
    {
        int r;
        int g;
        int b;
        int a;
        
        public ColourHolder(final int r, final int g, final int b, final int a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }
        
        public static int toHex(final int r, final int g, final int b) {
            return 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
        }
        
        public int getB() {
            return this.b;
        }
        
        public int getG() {
            return this.g;
        }
        
        public int getR() {
            return this.r;
        }
        
        public int getA() {
            return this.a;
        }
        
        public ColourHolder setR(final int r) {
            this.r = r;
            return this;
        }
        
        public ColourHolder setB(final int b) {
            this.b = b;
            return this;
        }
        
        public ColourHolder setG(final int g) {
            this.g = g;
            return this;
        }
        
        public ColourHolder setA(final int a) {
            this.a = a;
            return this;
        }
        
        public ColourHolder clone() {
            return new ColourHolder(this.r, this.g, this.b, this.a);
        }
    }
}
