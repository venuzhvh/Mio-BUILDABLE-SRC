//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.combat;

import net.minecraft.util.EnumHand;
import il.dev.mio.api.util.plugs.MathUtil;
import il.dev.mio.Mio;
import il.dev.mio.mod.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.api.util.world.InventoryUtil;
import net.minecraft.block.BlockWeb;
import java.util.Iterator;
import il.dev.mio.api.util.interact.BlockUtil;
import java.util.Comparator;
import java.util.ArrayList;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import il.dev.mio.api.util.render.RenderUtil;
import java.awt.Color;
import il.dev.mio.api.util.render.ColorUtil;
import il.dev.mio.mod.modules.client.ClickGui;
import il.dev.mio.api.event.events.Render3DEvent;
import net.minecraft.entity.Entity;
import il.dev.mio.api.util.interact.EntityUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import il.dev.mio.api.util.world.Timer;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class WebTrap extends Module
{
    public static boolean isPlacing;
    private final Setting<Page> page;
    private final Setting<Integer> delay;
    private final Setting<Integer> blocksPerPlace;
    private final Setting<Boolean> packet;
    private final Setting<Boolean> disable;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> raytrace;
    private final Setting<Boolean> lowerbody;
    private final Setting<Boolean> upperBody;
    private final Setting<Boolean> render;
    private final Setting<RenderMode> mode;
    private Setting<Float> lineWidth;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Integer> alpha;
    private final Timer timer;
    public EntityPlayer target;
    private boolean didPlace;
    private boolean switchedItem;
    private boolean isSneaking;
    private int lastHotbarSlot;
    private BlockPos position;
    private int placements;
    private boolean smartRotate;
    private BlockPos startPos;
    float yaw;
    float pitch;
    boolean isSpoofing;
    
    public WebTrap() {
        super("WebTrap", "Traps other players in webs", Category.COMBAT, true, false, false);
        this.page = (Setting<Page>)this.register(new Setting("Settings", Page.GLOBAL));
        this.delay = (Setting<Integer>)this.register(new Setting("TickDelay", 50, 0, 250, v -> this.page.getValue() == Page.GLOBAL));
        this.blocksPerPlace = (Setting<Integer>)this.register(new Setting("BPT", 1, 1, 30, v -> this.page.getValue() == Page.GLOBAL));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", false, v -> this.page.getValue() == Page.GLOBAL));
        this.disable = (Setting<Boolean>)this.register(new Setting("AutoDisable", false, v -> this.page.getValue() == Page.GLOBAL));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", true, v -> this.page.getValue() == Page.GLOBAL));
        this.raytrace = (Setting<Boolean>)this.register(new Setting("Raytrace", false, v -> this.page.getValue() == Page.GLOBAL));
        this.lowerbody = (Setting<Boolean>)this.register(new Setting("Feet", true, v -> this.page.getValue() == Page.GLOBAL));
        this.upperBody = (Setting<Boolean>)this.register(new Setting("Face", false, v -> this.page.getValue() == Page.GLOBAL));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", true, v -> this.page.getValue() == Page.GLOBAL));
        this.mode = (Setting<RenderMode>)this.register(new Setting("RenderMode", RenderMode.Both, v -> this.page.getValue() == Page.GLOBAL && this.render.getValue()));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", 1.7f, 0.1f, 5.0f, v -> this.page.getValue() == Page.COLORS));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 150, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 75, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 150, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", 80, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.timer = new Timer();
        this.didPlace = false;
        this.placements = 0;
        this.smartRotate = false;
        this.startPos = null;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            return;
        }
        this.startPos = EntityUtil.getRoundedBlockPos((Entity)WebTrap.mc.player);
        this.lastHotbarSlot = WebTrap.mc.player.inventory.currentItem;
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        final int color1 = this.red.getValue();
        final int color2 = this.green.getValue();
        final int color3 = this.blue.getValue();
        if (this.render.getValue()) {
            if (this.position != null) {
                if (this.mode.getValue().equals(AutoTrap.RenderMode.Solid)) {
                    RenderUtil.drawBoxESP(this.position, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(color1, color2, color3), this.lineWidth.getValue(), false, true, this.alpha.getValue());
                }
                else if (this.mode.getValue().equals(AutoTrap.RenderMode.Outline)) {
                    RenderUtil.drawBoxESP(this.position, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(color1, color2, color3), this.lineWidth.getValue(), true, false, this.alpha.getValue());
                }
                else if (this.mode.getValue().equals(AutoTrap.RenderMode.Both)) {
                    RenderUtil.drawBoxESP(this.position, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(color1, color2, color3), this.lineWidth.getValue(), true, true, this.alpha.getValue());
                }
            }
            final boolean didPlace = true;
            this.didPlace = didPlace;
            if (didPlace) {
                this.position = null;
            }
        }
    }
    
    @Override
    public void onTick() {
        this.smartRotate = false;
        this.doTrap();
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.target != null) {
            return this.target.getName();
        }
        return null;
    }
    
    @Override
    public void onDisable() {
        WebTrap.isPlacing = false;
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        this.switchItem(true);
        this.position = null;
    }
    
    private void doTrap() {
        if (this.check()) {
            return;
        }
        this.doWebTrap();
        if (this.didPlace) {
            this.timer.reset();
            this.position = null;
        }
    }
    
    private void doWebTrap() {
        final List<Vec3d> placeTargets = this.getPlacements();
        this.placeList(placeTargets);
    }
    
    private List<Vec3d> getPlacements() {
        final ArrayList<Vec3d> list = new ArrayList<Vec3d>();
        final Vec3d baseVec = this.target.getPositionVector();
        if (this.lowerbody.getValue()) {
            list.add(baseVec);
        }
        if (this.upperBody.getValue()) {
            list.add(baseVec.add(0.0, 1.0, 0.0));
        }
        return list;
    }
    
    private void placeList(final List<Vec3d> list) {
        list.sort((vec3d, vec3d2) -> Double.compare(WebTrap.mc.player.getDistanceSq(vec3d2.x, vec3d2.y, vec3d2.z), WebTrap.mc.player.getDistanceSq(vec3d.x, vec3d.y, vec3d.z)));
        list.sort(Comparator.comparingDouble(vec3d -> vec3d.y));
        for (final Vec3d vec3d3 : list) {
            final BlockPos position = new BlockPos(vec3d3);
            final int placeability = BlockUtil.isPositionPlaceable(position, this.raytrace.getValue());
            if (placeability != 3 && placeability != 1) {
                continue;
            }
            this.placeBlock(position);
        }
    }
    
    private boolean check() {
        WebTrap.isPlacing = false;
        this.didPlace = false;
        this.placements = 0;
        final int obbySlot = InventoryUtil.findHotbarBlock(BlockWeb.class);
        if (this.isOff()) {
            return true;
        }
        if (this.disable.getValue() && !this.startPos.equals((Object)EntityUtil.getRoundedBlockPos((Entity)WebTrap.mc.player))) {
            this.disable();
            return true;
        }
        if (obbySlot == -1) {
            Command.sendMessage("[" + this.getDisplayName() + "] " + ChatFormatting.RED + "No webs in hotbar. disabling...");
            this.toggle();
            return true;
        }
        if (WebTrap.mc.player.inventory.currentItem != this.lastHotbarSlot && WebTrap.mc.player.inventory.currentItem != obbySlot) {
            this.lastHotbarSlot = WebTrap.mc.player.inventory.currentItem;
        }
        this.switchItem(true);
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        this.target = this.getTarget(10.0);
        return this.target == null || !this.timer.passedMs(this.delay.getValue());
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (final EntityPlayer player : WebTrap.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range) && !player.isInWeb) {
                if (Mio.speedManager.getPlayerSpeed(player) > 30.0) {
                    continue;
                }
                if (target == null) {
                    target = player;
                    distance = WebTrap.mc.player.getDistanceSq((Entity)player);
                }
                else {
                    if (WebTrap.mc.player.getDistanceSq((Entity)player) >= distance) {
                        continue;
                    }
                    target = player;
                    distance = WebTrap.mc.player.getDistanceSq((Entity)player);
                }
            }
        }
        return target;
    }
    
    private void placeBlock(final BlockPos pos) {
        if (this.placements < this.blocksPerPlace.getValue() && WebTrap.mc.player.getDistanceSq(pos) <= MathUtil.square(6.0) && this.switchItem(false)) {
            WebTrap.isPlacing = true;
            this.position = pos;
            this.isSneaking = (this.smartRotate ? BlockUtil.placeBlockSmartRotate(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet.getValue(), this.isSneaking) : BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, false, this.packet.getValue(), this.isSneaking));
            this.didPlace = true;
            ++this.placements;
        }
    }
    
    private boolean switchItem(final boolean back) {
        final boolean[] value = InventoryUtil.switchItem(back, this.lastHotbarSlot, this.switchedItem, InventoryUtil.Switch.NORMAL, BlockWeb.class);
        this.switchedItem = value[0];
        return value[1];
    }
    
    static {
        WebTrap.isPlacing = false;
    }
    
    public enum Page
    {
        GLOBAL, 
        COLORS;
    }
    
    public enum RenderMode
    {
        Solid, 
        Outline, 
        Both;
    }
}
