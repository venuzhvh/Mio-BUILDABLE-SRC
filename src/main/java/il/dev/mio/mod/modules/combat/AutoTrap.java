//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.combat;

import net.minecraft.util.EnumHand;
import net.minecraft.block.BlockEnderChest;
import il.dev.mio.api.util.plugs.MathUtil;
import il.dev.mio.Mio;
import il.dev.mio.mod.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.api.util.world.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import il.dev.mio.api.util.interact.BlockUtil;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import java.util.Iterator;
import java.util.List;
import il.dev.mio.api.util.render.RenderUtil;
import java.awt.Color;
import il.dev.mio.api.util.render.ColorUtil;
import il.dev.mio.mod.modules.client.ClickGui;
import net.minecraft.util.math.Vec3d;
import il.dev.mio.api.util.interact.EntityUtil;
import il.dev.mio.api.event.events.Render3DEvent;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import java.util.Map;
import il.dev.mio.api.util.world.Timer;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class AutoTrap extends Module
{
    public static boolean isPlacing;
    private final Setting<Page> page;
    private final Setting<Integer> delay;
    private final Setting<Integer> blocksPerPlace;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> raytrace;
    private final Setting<Boolean> antiScaffold;
    private final Setting<Boolean> antiStep;
    private final Setting<Boolean> noGhost;
    private final Setting<Boolean> render;
    private final Setting<RenderMode> mode;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Integer> alpha;
    private final Timer timer;
    private final Map<BlockPos, Integer> retries;
    private final Timer retryTimer;
    public EntityPlayer target;
    private boolean didPlace;
    private BlockPos position;
    private boolean switchedItem;
    private boolean isSneaking;
    private int lastHotbarSlot;
    private int placements;
    private BlockPos startPos;
    private boolean offHand;
    private int currentAlpha;
    
    public AutoTrap() {
        super("AutoTrap", "Traps other players", Category.COMBAT, true, false, false);
        this.page = (Setting<Page>)this.register(new Setting("Settings", Page.GLOBAL));
        this.delay = (Setting<Integer>)this.register(new Setting("TickDelay", 50, 0, 250, v -> this.page.getValue() == Page.GLOBAL));
        this.blocksPerPlace = (Setting<Integer>)this.register(new Setting("BPT", 2, 1, 30, v -> this.page.getValue() == Page.GLOBAL));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", true, v -> this.page.getValue() == Page.GLOBAL));
        this.raytrace = (Setting<Boolean>)this.register(new Setting("Raytrace", false, v -> this.page.getValue() == Page.GLOBAL));
        this.antiScaffold = (Setting<Boolean>)this.register(new Setting("Extra", false, v -> this.page.getValue() == Page.GLOBAL));
        this.antiStep = (Setting<Boolean>)this.register(new Setting("AntiStep", false, v -> this.page.getValue() == Page.GLOBAL));
        this.noGhost = (Setting<Boolean>)this.register(new Setting("Packet", false, v -> this.page.getValue() == Page.GLOBAL));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", true, v -> this.page.getValue() == Page.GLOBAL));
        this.mode = (Setting<RenderMode>)this.register(new Setting("RenderMode", RenderMode.Both, v -> this.page.getValue() == Page.GLOBAL && this.render.getValue()));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 150, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 75, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 150, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", 80, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.timer = new Timer();
        this.retries = new HashMap<BlockPos, Integer>();
        this.retryTimer = new Timer();
        this.didPlace = false;
        this.placements = 0;
        this.startPos = null;
        this.offHand = false;
        this.currentAlpha = 0;
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        final int color1 = this.red.getValue();
        final int color2 = this.green.getValue();
        final int color3 = this.blue.getValue();
        if (this.render.getValue() && this.target != null) {
            final List<Vec3d> placeList = EntityUtil.targets(this.target.getPositionVector(), this.antiScaffold.getValue(), this.antiStep.getValue(), false, false, false, this.raytrace.getValue());
            for (final Vec3d pos : placeList) {
                RenderUtil.drawBoxESP(new BlockPos(pos), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(color1, color2, color3), true, new Color(255, 255, 255, 255), 0.7f, this.mode.getValue().equals(RenderMode.Both) || this.mode.getValue().equals(RenderMode.Outline), this.mode.getValue().equals(RenderMode.Both) || this.mode.getValue().equals(RenderMode.Solid), this.alpha.getValue(), true, 0.0, false, false, false, false, this.currentAlpha);
            }
            if (this.target == this.getTarget(10.0, true)) {
                this.position = null;
            }
        }
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            return;
        }
        this.startPos = EntityUtil.getRoundedBlockPos((Entity)AutoTrap.mc.player);
        this.lastHotbarSlot = AutoTrap.mc.player.inventory.currentItem;
        this.retries.clear();
    }
    
    @Override
    public void onTick() {
        if (fullNullCheck()) {
            return;
        }
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
        AutoTrap.isPlacing = false;
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        this.position = null;
    }
    
    private void doTrap() {
        if (this.check()) {
            return;
        }
        this.doStaticTrap();
        if (this.didPlace) {
            this.timer.reset();
        }
    }
    
    private void doStaticTrap() {
        final List<Vec3d> placeTargets = EntityUtil.targets(this.target.getPositionVector(), this.antiScaffold.getValue(), this.antiStep.getValue(), false, false, false, this.raytrace.getValue());
        this.placeList(placeTargets);
    }
    
    public void placeList(final List<Vec3d> list) {
        list.sort((vec3d, vec3d2) -> Double.compare(AutoTrap.mc.player.getDistanceSq(vec3d2.x, vec3d2.y, vec3d2.z), AutoTrap.mc.player.getDistanceSq(vec3d.x, vec3d.y, vec3d.z)));
        list.sort(Comparator.comparingDouble(vec3d -> vec3d.y));
        for (final Vec3d vec3d3 : list) {
            final BlockPos position = new BlockPos(vec3d3);
            final int placeability = BlockUtil.isPositionPlaceable(position, this.raytrace.getValue());
            if (placeability == 1 && (this.retries.get(position) == null || this.retries.get(position) < 4)) {
                this.placeBlock(position);
                this.retries.put(position, (this.retries.get(position) == null) ? 1 : (this.retries.get(position) + 1));
                this.retryTimer.reset();
            }
            else {
                if (placeability != 3) {
                    continue;
                }
                this.placeBlock(position);
            }
        }
    }
    
    private boolean check() {
        AutoTrap.isPlacing = false;
        this.didPlace = false;
        this.placements = 0;
        final int obbySlot2 = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (obbySlot2 == -1) {
            this.toggle();
        }
        final int obbySlot3 = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (this.isOff()) {
            return true;
        }
        if (!this.startPos.equals((Object)EntityUtil.getRoundedBlockPos((Entity)AutoTrap.mc.player))) {
            this.disable();
            return true;
        }
        if (this.retryTimer.passedMs(2000L)) {
            this.retries.clear();
            this.retryTimer.reset();
        }
        if (obbySlot3 == -1) {
            Command.sendMessage("[" + this.getDisplayName() + "] " + ChatFormatting.RED + "No obi in hotbar. disabling...");
            this.disable();
            return true;
        }
        if (AutoTrap.mc.player.inventory.currentItem != this.lastHotbarSlot && AutoTrap.mc.player.inventory.currentItem != obbySlot3) {
            this.lastHotbarSlot = AutoTrap.mc.player.inventory.currentItem;
        }
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        this.target = this.getTarget(10.0, true);
        return this.target == null || !this.timer.passedMs(this.delay.getValue());
    }
    
    private EntityPlayer getTarget(final double range, final boolean trapped) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (final EntityPlayer player : AutoTrap.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range) && (!trapped || !EntityUtil.isTrapped(player, this.antiScaffold.getValue(), this.antiStep.getValue(), false, false, false))) {
                if (Mio.speedManager.getPlayerSpeed(player) > 10.0) {
                    continue;
                }
                if (target == null) {
                    target = player;
                    distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
                }
                else {
                    if (AutoTrap.mc.player.getDistanceSq((Entity)player) >= distance) {
                        continue;
                    }
                    target = player;
                    distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
                }
            }
        }
        return target;
    }
    
    private void placeBlock(final BlockPos pos) {
        if (this.placements < this.blocksPerPlace.getValue() && AutoTrap.mc.player.getDistanceSq(pos) <= MathUtil.square(5.0)) {
            AutoTrap.isPlacing = true;
            final int originalSlot = AutoTrap.mc.player.inventory.currentItem;
            final int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
            final int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
            if (obbySlot == -1 && eChestSot == -1) {
                this.toggle();
            }
            this.position = pos;
            if (this.rotate.getValue()) {
                AutoTrap.mc.player.inventory.currentItem = ((obbySlot == -1) ? eChestSot : obbySlot);
                AutoTrap.mc.playerController.updateController();
                this.isSneaking = BlockUtil.placeBlock(pos, this.offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), this.isSneaking);
                AutoTrap.mc.player.inventory.currentItem = originalSlot;
                AutoTrap.mc.playerController.updateController();
            }
            else {
                AutoTrap.mc.player.inventory.currentItem = ((obbySlot == -1) ? eChestSot : obbySlot);
                AutoTrap.mc.playerController.updateController();
                this.isSneaking = BlockUtil.placeBlock(pos, this.offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), this.isSneaking);
                AutoTrap.mc.player.inventory.currentItem = originalSlot;
                AutoTrap.mc.playerController.updateController();
            }
            this.didPlace = true;
            ++this.placements;
        }
    }
    
    static {
        AutoTrap.isPlacing = false;
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
