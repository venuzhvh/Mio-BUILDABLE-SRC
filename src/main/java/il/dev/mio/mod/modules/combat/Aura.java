//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.inventory.ClickType;
import il.dev.mio.api.util.plugs.MathUtil;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import il.dev.mio.Mio;
import il.dev.mio.api.util.interact.DamageUtil;
import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import il.dev.mio.api.util.render.RenderUtil;
import java.awt.Color;
import net.minecraft.client.renderer.RenderGlobal;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import il.dev.mio.api.util.interact.EntityUtil;
import il.dev.mio.mod.modules.client.ClickGui;
import il.dev.mio.api.util.render.ColorUtil;
import il.dev.mio.api.event.events.Render3DEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import il.dev.mio.api.event.events.UpdateWalkingPlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import il.dev.mio.api.util.world.Timer;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class Aura extends Module
{
    private static Aura INSTANCE;
    public Setting<Page> page;
    private Setting<TargetMode> targetMode;
    public Setting<Float> range;
    public Setting<Float> health;
    public Setting<Boolean> delay;
    public Setting<Boolean> rotate;
    public Setting<Boolean> armorBreak;
    public Setting<Boolean> eating;
    public Setting<Boolean> onlySharp;
    public Setting<Boolean> teleport;
    public Setting<Float> raytrace;
    public Setting<Float> teleportRange;
    public Setting<Boolean> lagBack;
    public Setting<Boolean> teekaydelay;
    public Setting<Integer> time32k;
    public Setting<Integer> multi;
    public Setting<Boolean> multi32k;
    public Setting<Boolean> players;
    public Setting<Boolean> mobs;
    public Setting<Boolean> onlyGhasts;
    public Setting<Boolean> animals;
    public Setting<Boolean> vehicles;
    public Setting<Boolean> projectiles;
    public Setting<Boolean> tps;
    public Setting<Boolean> packet;
    public Setting<Boolean> swing;
    public Setting<Boolean> render;
    public Setting<Boolean> sneak;
    private final Timer timer;
    public static Entity target;
    private int color;
    private int white;
    float red;
    float green;
    float blue;
    
    public Aura() {
        super("Aura", "KILL", Category.COMBAT, true, false, false);
        this.page = (Setting<Page>)this.register(new Setting("Settings", Page.Global));
        this.targetMode = (Setting<TargetMode>)this.register(new Setting("Target", TargetMode.Closest, v -> this.page.getValue() == Page.Global));
        this.range = (Setting<Float>)this.register(new Setting("Range", 6.0f, 0.1f, 7.0f, v -> this.page.getValue() == Page.Global));
        this.health = (Setting<Float>)this.register(new Setting("Health", 6.0f, 0.1f, 36.0f, v -> this.targetMode.getValue() == TargetMode.Smart && this.page.getValue() == Page.Targets));
        this.delay = (Setting<Boolean>)this.register(new Setting("Delay", true, v -> this.page.getValue() == Page.Global));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", true, v -> this.page.getValue() == Page.Global));
        this.armorBreak = (Setting<Boolean>)this.register(new Setting("ArmorBreak", false, v -> this.page.getValue() == Page.Global));
        this.eating = (Setting<Boolean>)this.register(new Setting("Eating", true, v -> this.page.getValue() == Page.Global));
        this.onlySharp = (Setting<Boolean>)this.register(new Setting("WeaponOnly", true, v -> this.page.getValue() == Page.Global));
        this.teleport = (Setting<Boolean>)this.register(new Setting("Teleport", false, v -> this.page.getValue() == Page.Advanced));
        this.raytrace = (Setting<Float>)this.register(new Setting("Raytrace", 6.0f, 0.1f, 7.0f, v -> !this.teleport.getValue() && this.page.getValue() == Page.Advanced));
        this.teleportRange = (Setting<Float>)this.register(new Setting("TpRange", 15.0f, 0.1f, 50.0f, v -> this.teleport.getValue() && this.page.getValue() == Page.Advanced));
        this.lagBack = (Setting<Boolean>)this.register(new Setting("LagBack", true, v -> this.teleport.getValue() && this.page.getValue() == Page.Advanced));
        this.teekaydelay = (Setting<Boolean>)this.register(new Setting("32kDelay", false, v -> this.page.getValue() == Page.Advanced));
        this.time32k = (Setting<Integer>)this.register(new Setting("32kTime", 5, 1, 50, v -> this.page.getValue() == Page.Advanced));
        this.multi = (Setting<Integer>)this.register(new Setting("32kPackets", 2, v -> !this.teekaydelay.getValue() && this.page.getValue() == Page.Advanced));
        this.multi32k = (Setting<Boolean>)this.register(new Setting("Multi32k", false, v -> this.page.getValue() == Page.Advanced));
        this.players = (Setting<Boolean>)this.register(new Setting("Players", true, v -> this.page.getValue() == Page.Targets));
        this.mobs = (Setting<Boolean>)this.register(new Setting("Mobs", true, v -> this.page.getValue() == Page.Targets));
        this.onlyGhasts = (Setting<Boolean>)this.register(new Setting("OnlyGhasts", false, v -> this.page.getValue() == Page.Targets && this.mobs.getValue()));
        this.animals = (Setting<Boolean>)this.register(new Setting("Animals", false, v -> this.page.getValue() == Page.Targets));
        this.vehicles = (Setting<Boolean>)this.register(new Setting("Entities", false, v -> this.page.getValue() == Page.Targets));
        this.projectiles = (Setting<Boolean>)this.register(new Setting("Projectiles", false, v -> this.page.getValue() == Page.Targets));
        this.tps = (Setting<Boolean>)this.register(new Setting("TpsSync", true, v -> this.page.getValue() == Page.Global));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", false, v -> this.page.getValue() == Page.Advanced));
        this.swing = (Setting<Boolean>)this.register(new Setting("Swing", true, v -> this.page.getValue() == Page.Global));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", true, v -> this.page.getValue() == Page.Global));
        this.sneak = (Setting<Boolean>)this.register(new Setting("State", false, v -> this.page.getValue() == Page.Advanced));
        this.timer = new Timer();
        this.red = 0.0f;
        this.green = 0.0f;
        this.blue = 0.0f;
        this.setInstance();
    }
    
    public static Aura getInstance() {
        if (Aura.INSTANCE == null) {
            Aura.INSTANCE = new Aura();
        }
        return Aura.INSTANCE;
    }
    
    @Override
    public String getDisplayInfo() {
        final String ModeInfo = String.valueOf(this.targetMode.getValue());
        final String TargetInfo = (Aura.target instanceof EntityPlayer) ? (", " + Aura.target.getName()) : "";
        return ModeInfo + TargetInfo;
    }
    
    private void setInstance() {
        Aura.INSTANCE = this;
    }
    
    @Override
    public void onTick() {
        if (!this.rotate.getValue()) {
            this.doAura();
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0 && this.rotate.getValue()) {
            this.doAura();
        }
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        this.white = ColorUtil.toRGBA(255, 255, 255, 170);
        this.color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue());
        if (this.render.getValue()) {
            int i = 0;
            for (final Entity entity : Aura.mc.world.loadedEntityList) {
                if (entity != Aura.mc.player && entity != null) {
                    if (Aura.mc.player.getDistanceSq(entity) >= 2500.0) {
                        continue;
                    }
                    if (!entity.equals((Object)Aura.target)) {
                        continue;
                    }
                    this.red = ClickGui.getInstance().red.getValue() / 255.0f;
                    this.green = ClickGui.getInstance().green.getValue() / 255.0f;
                    this.blue = ClickGui.getInstance().blue.getValue() / 255.0f;
                    final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, Aura.mc.getRenderPartialTicks());
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
                    RenderGlobal.renderFilledBox(bb, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() / 255.0f) : this.red, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() / 255.0f) : this.green, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() / 255.0f) : this.blue, 100.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    RenderUtil.drawBlockOutline(bb, new Color(255, 255, 255, 255), 0.7f);
                    if (++i < 50) {
                        continue;
                    }
                    break;
                }
            }
        }
    }
    
    private void doAura() {
        if (this.onlySharp.getValue() && !EntityUtil.holdingWeapon((EntityPlayer)Aura.mc.player)) {
            Aura.target = null;
            return;
        }
        final int wait = (!this.delay.getValue() || (EntityUtil.holding32k((EntityPlayer)Aura.mc.player) && !this.teekaydelay.getValue())) ? 0 : ((int)(DamageUtil.getCooldownByWeapon((EntityPlayer)Aura.mc.player) * (this.tps.getValue() ? Mio.serverManager.getTpsFactor() : 1.0f)));
        if (!this.timer.passedMs(wait) || (!this.eating.getValue() && Aura.mc.player.isHandActive() && (!Aura.mc.player.getHeldItemOffhand().getItem().equals(Items.SHIELD) || Aura.mc.player.getActiveHand() != EnumHand.OFF_HAND))) {
            return;
        }
        if (this.targetMode.getValue() != TargetMode.Focus || Aura.target == null || (Aura.mc.player.getDistanceSq(Aura.target) >= MathUtil.square(this.range.getValue()) && (!this.teleport.getValue() || Aura.mc.player.getDistanceSq(Aura.target) >= MathUtil.square(this.teleportRange.getValue()))) || (!Aura.mc.player.canEntityBeSeen(Aura.target) && !EntityUtil.canEntityFeetBeSeen(Aura.target) && Aura.mc.player.getDistanceSq(Aura.target) >= MathUtil.square(this.raytrace.getValue()) && !this.teleport.getValue())) {
            Aura.target = this.getTarget();
        }
        if (Aura.target == null) {
            return;
        }
        if (this.rotate.getValue()) {
            Mio.rotationManager.lookAtEntity(Aura.target);
        }
        if (this.teleport.getValue()) {
            Mio.positionManager.setPositionPacket(Aura.target.posX, EntityUtil.canEntityFeetBeSeen(Aura.target) ? Aura.target.posY : (Aura.target.posY + Aura.target.getEyeHeight()), Aura.target.posZ, true, true, !this.lagBack.getValue());
        }
        if (EntityUtil.holding32k((EntityPlayer)Aura.mc.player) && !this.teekaydelay.getValue()) {
            if (this.multi32k.getValue()) {
                for (final EntityPlayer player : Aura.mc.world.playerEntities) {
                    if (EntityUtil.isValid((Entity)player, this.range.getValue())) {
                        this.teekayAttack((Entity)player);
                    }
                }
            }
            else {
                this.teekayAttack(Aura.target);
            }
            this.timer.reset();
            return;
        }
        if (this.armorBreak.getValue()) {
            Aura.mc.playerController.windowClick(Aura.mc.player.inventoryContainer.windowId, 9, Aura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Aura.mc.player);
            EntityUtil.attackEntity(Aura.target, this.packet.getValue(), this.swing.getValue());
            Aura.mc.playerController.windowClick(Aura.mc.player.inventoryContainer.windowId, 9, Aura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Aura.mc.player);
            EntityUtil.attackEntity(Aura.target, this.packet.getValue(), this.swing.getValue());
        }
        else {
            final boolean sneaking = Aura.mc.player.isSneaking();
            final boolean sprint = Aura.mc.player.isSprinting();
            if (this.sneak.getValue()) {
                if (sneaking) {
                    Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Aura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (sprint) {
                    Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Aura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
            }
            EntityUtil.attackEntity(Aura.target, this.packet.getValue(), this.swing.getValue());
            if (this.sneak.getValue()) {
                if (sprint) {
                    Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Aura.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                }
                if (sneaking) {
                    Aura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Aura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
            }
        }
        this.timer.reset();
    }
    
    private void teekayAttack(final Entity entity) {
        for (int i = 0; i < this.multi.getValue(); ++i) {
            this.startEntityAttackThread(entity, i * this.time32k.getValue());
        }
    }
    
    private void startEntityAttackThread(final Entity entity, final int time) {
        new Thread(() -> {
            Timer timer = new Timer();
            timer.reset();
            try {
                Thread.sleep(time);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            EntityUtil.attackEntity(entity, true, this.swing.getValue());
        }).start();
    }
    
    private Entity getTarget() {
        Entity target = null;
        double distance = (double)(this.teleport.getValue() ? this.teleportRange.getValue() : ((double)(float)this.range.getValue()));
        double maxHealth = 36.0;
        for (final Entity entity : Aura.mc.world.loadedEntityList) {
            if ((this.players.getValue() && entity instanceof EntityPlayer) || (this.animals.getValue() && EntityUtil.isPassive(entity)) || (this.mobs.getValue() && EntityUtil.isMobAggressive(entity)) || (this.mobs.getValue() && this.onlyGhasts.getValue() && entity instanceof EntityGhast) || (this.vehicles.getValue() && EntityUtil.isVehicle(entity)) || (this.projectiles.getValue() && EntityUtil.isProjectile(entity))) {
                if (entity instanceof EntityLivingBase && EntityUtil.isntValid(entity, distance)) {
                    continue;
                }
                if (!this.teleport.getValue() && !Aura.mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && Aura.mc.player.getDistanceSq(entity) > MathUtil.square(this.raytrace.getValue())) {
                    continue;
                }
                if (target == null) {
                    target = entity;
                    distance = Aura.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
                else {
                    if (entity instanceof EntityPlayer && DamageUtil.isArmorLow((EntityPlayer)entity, 18)) {
                        target = entity;
                        break;
                    }
                    if (this.targetMode.getValue() == TargetMode.Smart && EntityUtil.getHealth(entity) < this.health.getValue()) {
                        target = entity;
                        break;
                    }
                    if (this.targetMode.getValue() != TargetMode.Health && Aura.mc.player.getDistanceSq(entity) < distance) {
                        target = entity;
                        distance = Aura.mc.player.getDistanceSq(entity);
                        maxHealth = EntityUtil.getHealth(entity);
                    }
                    if (this.targetMode.getValue() != TargetMode.Health) {
                        continue;
                    }
                    if (EntityUtil.getHealth(entity) >= maxHealth) {
                        continue;
                    }
                    target = entity;
                    distance = Aura.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
            }
        }
        return target;
    }
    
    static {
        Aura.INSTANCE = new Aura();
    }
    
    public enum Page
    {
        Global, 
        Targets, 
        Advanced;
    }
    
    public enum TargetMode
    {
        Focus, 
        Closest, 
        Health, 
        Smart;
    }
}
