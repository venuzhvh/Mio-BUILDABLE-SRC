//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.combat;

import net.minecraft.entity.player.EntityPlayer;
import il.dev.mio.api.util.plugs.MathUtil;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import il.dev.mio.Mio;
import net.minecraft.item.Item;
import il.dev.mio.mod.modules.player.XCarry;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.init.Items;
import il.dev.mio.api.util.interact.DamageUtil;
import net.minecraft.entity.Entity;
import il.dev.mio.api.util.interact.EntityUtil;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import il.dev.mio.mod.ModuleCore;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import il.dev.mio.mod.gui.MioClickGui;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import il.dev.mio.api.util.world.InventoryUtil;
import java.util.Queue;
import il.dev.mio.api.util.world.Timer;
import il.dev.mio.mod.gui.clickgui.setting.Bind;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class AutoArmor extends Module
{
    private final Setting<Boolean> mendingTakeOff;
    private final Setting<Integer> closestEnemy;
    private final Setting<Integer> helmetThreshold;
    private final Setting<Integer> chestThreshold;
    private final Setting<Integer> legThreshold;
    private final Setting<Integer> bootsThreshold;
    private final Setting<Boolean> save;
    private final Setting<Integer> saveThreshold;
    private final Setting<Integer> delay;
    private final Setting<Integer> actions;
    private final Setting<Boolean> curse;
    private final Setting<Boolean> tps;
    private final Setting<Boolean> updateController;
    private final Setting<Boolean> shiftClick;
    private final Setting<Bind> elytraBind;
    private final Setting<Bind> noHelmBind;
    private final Timer timer;
    private final Timer elytraTimer;
    private final Queue<InventoryUtil.Task> taskList;
    private final List<Integer> doneSlots;
    private boolean elytraOn;
    private boolean helmOff;
    
    public AutoArmor() {
        super("AutoArmor", "Puts Armor on for you.", Category.COMBAT, true, false, false);
        this.mendingTakeOff = (Setting<Boolean>)this.register(new Setting("AutoMend", false));
        this.closestEnemy = (Setting<Integer>)this.register(new Setting("EnemyRange", 8, 1, 20, v -> this.mendingTakeOff.getValue()));
        this.helmetThreshold = (Setting<Integer>)this.register(new Setting("Helmet%", 80, 1, 100, v -> this.mendingTakeOff.getValue()));
        this.chestThreshold = (Setting<Integer>)this.register(new Setting("Chest%", 80, 1, 100, v -> this.mendingTakeOff.getValue()));
        this.legThreshold = (Setting<Integer>)this.register(new Setting("Legs%", 80, 1, 100, v -> this.mendingTakeOff.getValue()));
        this.bootsThreshold = (Setting<Integer>)this.register(new Setting("Boots%", 80, 1, 100, v -> this.mendingTakeOff.getValue()));
        this.save = (Setting<Boolean>)this.register(new Setting("Save", false));
        this.saveThreshold = (Setting<Integer>)this.register(new Setting("Save%", 5, 1, 10, v -> this.save.getValue()));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", 50, 0, 500));
        this.actions = (Setting<Integer>)this.register(new Setting("Actions", 3, 1, 12));
        this.curse = (Setting<Boolean>)this.register(new Setting("CurseOfBinding", false));
        this.tps = (Setting<Boolean>)this.register(new Setting("TpsSync", true));
        this.updateController = (Setting<Boolean>)this.register(new Setting("Update", true));
        this.shiftClick = (Setting<Boolean>)this.register(new Setting("ShiftClick", false));
        this.elytraBind = (Setting<Bind>)this.register(new Setting("Elytra", new Bind(-1)));
        this.noHelmBind = (Setting<Bind>)this.register(new Setting("NoHelmet", new Bind(-1)));
        this.timer = new Timer();
        this.elytraTimer = new Timer();
        this.taskList = new ConcurrentLinkedQueue<InventoryUtil.Task>();
        this.doneSlots = new ArrayList<Integer>();
        this.elytraOn = false;
        this.helmOff = false;
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState() && !(AutoArmor.mc.currentScreen instanceof MioClickGui) && this.elytraBind.getValue().getKey() == Keyboard.getEventKey()) {
            this.elytraOn = !this.elytraOn;
        }
        if (Keyboard.getEventKeyState() && !(AutoArmor.mc.currentScreen instanceof MioClickGui) && this.noHelmBind.getValue().getKey() == Keyboard.getEventKey()) {
            this.helmOff = !this.helmOff;
        }
    }
    
    @Override
    public void onLogin() {
        this.timer.reset();
        this.elytraTimer.reset();
    }
    
    @Override
    public void onDisable() {
        this.taskList.clear();
        this.doneSlots.clear();
        this.elytraOn = false;
        this.helmOff = false;
    }
    
    @Override
    public void onLogout() {
        this.taskList.clear();
        this.doneSlots.clear();
    }
    
    @Override
    public void onTick() {
        if (ModuleCore.fullNullCheck() || (AutoArmor.mc.currentScreen instanceof GuiContainer && !(AutoArmor.mc.currentScreen instanceof GuiInventory))) {
            return;
        }
        if (this.taskList.isEmpty()) {
            if (this.mendingTakeOff.getValue() && InventoryUtil.holdingItem(ItemExpBottle.class) && AutoArmor.mc.gameSettings.keyBindUseItem.isKeyDown() && (this.isSafe() || EntityUtil.isSafe((Entity)AutoArmor.mc.player, 1, false, true))) {
                final ItemStack helm = AutoArmor.mc.player.inventoryContainer.getSlot(5).getStack();
                final int helmDamage;
                if (!helm.isEmpty && (helmDamage = DamageUtil.getRoundedDamage(helm)) >= this.helmetThreshold.getValue()) {
                    this.takeOffSlot(5);
                }
                final ItemStack chest2 = AutoArmor.mc.player.inventoryContainer.getSlot(6).getStack();
                final int chestDamage;
                if (!chest2.isEmpty && (chestDamage = DamageUtil.getRoundedDamage(chest2)) >= this.chestThreshold.getValue()) {
                    this.takeOffSlot(6);
                }
                final ItemStack legging2 = AutoArmor.mc.player.inventoryContainer.getSlot(7).getStack();
                final int leggingDamage;
                if (!legging2.isEmpty && (leggingDamage = DamageUtil.getRoundedDamage(legging2)) >= this.legThreshold.getValue()) {
                    this.takeOffSlot(7);
                }
                final ItemStack feet2 = AutoArmor.mc.player.inventoryContainer.getSlot(8).getStack();
                final int bootDamage;
                if (!feet2.isEmpty && (bootDamage = DamageUtil.getRoundedDamage(feet2)) >= this.bootsThreshold.getValue()) {
                    this.takeOffSlot(8);
                }
                return;
            }
            if (this.save.getValue()) {
                final ItemStack helm = AutoArmor.mc.player.inventoryContainer.getSlot(5).getStack();
                final int helmDamage;
                if (this.save.getValue() && !helm.isEmpty && (helmDamage = DamageUtil.getRoundedDamage(helm)) <= this.saveThreshold.getValue()) {
                    this.takeOffSlot(5);
                }
                final ItemStack chest2 = AutoArmor.mc.player.inventoryContainer.getSlot(6).getStack();
                final int chestDamage;
                if (this.save.getValue() && !chest2.isEmpty && (chestDamage = DamageUtil.getRoundedDamage(chest2)) <= this.saveThreshold.getValue()) {
                    this.takeOffSlot(6);
                }
                final ItemStack legging2 = AutoArmor.mc.player.inventoryContainer.getSlot(7).getStack();
                final int leggingDamage;
                if (this.save.getValue() && !legging2.isEmpty && (leggingDamage = DamageUtil.getRoundedDamage(legging2)) <= this.saveThreshold.getValue()) {
                    this.takeOffSlot(7);
                }
                final ItemStack feet2 = AutoArmor.mc.player.inventoryContainer.getSlot(8).getStack();
                final int bootDamage;
                if (this.save.getValue() && !feet2.isEmpty && (bootDamage = DamageUtil.getRoundedDamage(feet2)) <= this.saveThreshold.getValue()) {
                    this.takeOffSlot(8);
                }
            }
            final ItemStack helm2 = AutoArmor.mc.player.inventoryContainer.getSlot(5).getStack();
            final int slot4;
            if (!this.helmOff && helm2.getItem() == Items.AIR && (slot4 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.HEAD, this.curse.getValue(), XCarry.getInstance().isOn())) != -1) {
                this.getSlotOn(5, slot4);
            }
            else if (this.helmOff && helm2.getItem() != Items.AIR) {
                this.takeOffSlot(5);
            }
            final ItemStack chest3;
            if ((chest3 = AutoArmor.mc.player.inventoryContainer.getSlot(6).getStack()).getItem() == Items.AIR) {
                if (this.taskList.isEmpty()) {
                    if (this.elytraOn && this.elytraTimer.passedMs(500L)) {
                        final int elytraSlot = InventoryUtil.findItemInventorySlot(Items.ELYTRA, false, XCarry.getInstance().isOn());
                        if (elytraSlot != -1) {
                            if ((elytraSlot < 5 && elytraSlot > 1) || !this.shiftClick.getValue()) {
                                this.taskList.add(new InventoryUtil.Task(elytraSlot));
                                this.taskList.add(new InventoryUtil.Task(6));
                            }
                            else {
                                this.taskList.add(new InventoryUtil.Task(elytraSlot, true));
                            }
                            if (this.updateController.getValue()) {
                                this.taskList.add(new InventoryUtil.Task());
                            }
                            this.elytraTimer.reset();
                        }
                    }
                    else {
                        final int slot5;
                        if (!this.elytraOn && (slot5 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.CHEST, this.curse.getValue(), XCarry.getInstance().isOn())) != -1) {
                            this.getSlotOn(6, slot5);
                        }
                    }
                }
            }
            else if (this.elytraOn && chest3.getItem() != Items.ELYTRA && this.elytraTimer.passedMs(500L)) {
                if (this.taskList.isEmpty()) {
                    final int slot5 = InventoryUtil.findItemInventorySlot(Items.ELYTRA, false, XCarry.getInstance().isOn());
                    if (slot5 != -1) {
                        this.taskList.add(new InventoryUtil.Task(slot5));
                        this.taskList.add(new InventoryUtil.Task(6));
                        this.taskList.add(new InventoryUtil.Task(slot5));
                        if (this.updateController.getValue()) {
                            this.taskList.add(new InventoryUtil.Task());
                        }
                    }
                    this.elytraTimer.reset();
                }
            }
            else if (!this.elytraOn && chest3.getItem() == Items.ELYTRA && this.elytraTimer.passedMs(500L) && this.taskList.isEmpty()) {
                int slot5 = InventoryUtil.findItemInventorySlot((Item)Items.DIAMOND_CHESTPLATE, false, XCarry.getInstance().isOn());
                if (slot5 == -1 && (slot5 = InventoryUtil.findItemInventorySlot((Item)Items.IRON_CHESTPLATE, false, XCarry.getInstance().isOn())) == -1 && (slot5 = InventoryUtil.findItemInventorySlot((Item)Items.GOLDEN_CHESTPLATE, false, XCarry.getInstance().isOn())) == -1 && (slot5 = InventoryUtil.findItemInventorySlot((Item)Items.CHAINMAIL_CHESTPLATE, false, XCarry.getInstance().isOn())) == -1) {
                    slot5 = InventoryUtil.findItemInventorySlot((Item)Items.LEATHER_CHESTPLATE, false, XCarry.getInstance().isOn());
                }
                if (slot5 != -1) {
                    this.taskList.add(new InventoryUtil.Task(slot5));
                    this.taskList.add(new InventoryUtil.Task(6));
                    this.taskList.add(new InventoryUtil.Task(slot5));
                    if (this.updateController.getValue()) {
                        this.taskList.add(new InventoryUtil.Task());
                    }
                }
                this.elytraTimer.reset();
            }
            final ItemStack legging3 = AutoArmor.mc.player.inventoryContainer.getSlot(7).getStack();
            final ItemStack legging4;
            final int slot6;
            if ((legging4 = AutoArmor.mc.player.inventoryContainer.getSlot(7).getStack()).getItem() == Items.AIR && (slot6 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.LEGS, this.curse.getValue(), XCarry.getInstance().isOn())) != -1) {
                this.getSlotOn(7, slot6);
            }
            final ItemStack feet3 = AutoArmor.mc.player.inventoryContainer.getSlot(8).getStack();
            final ItemStack feet4;
            final int slot7;
            if ((feet4 = AutoArmor.mc.player.inventoryContainer.getSlot(8).getStack()).getItem() == Items.AIR && (slot7 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.FEET, this.curse.getValue(), XCarry.getInstance().isOn())) != -1) {
                this.getSlotOn(8, slot7);
            }
        }
        if (this.timer.passedMs((int)(this.delay.getValue() * (this.tps.getValue() ? Mio.serverManager.getTpsFactor() : 1.0f)))) {
            if (!this.taskList.isEmpty()) {
                for (int i = 0; i < this.actions.getValue(); ++i) {
                    final InventoryUtil.Task task = this.taskList.poll();
                    if (task != null) {
                        task.run();
                    }
                }
            }
            this.timer.reset();
        }
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.elytraOn) {
            return "Elytra";
        }
        return null;
    }
    
    private void takeOffSlot(final int slot) {
        if (this.taskList.isEmpty()) {
            int target = -1;
            for (final int i : InventoryUtil.findEmptySlots(XCarry.getInstance().isOn())) {
                if (this.doneSlots.contains(target)) {
                    continue;
                }
                target = i;
                this.doneSlots.add(i);
            }
            if (target != -1) {
                if ((target < 5 && target > 0) || !this.shiftClick.getValue()) {
                    this.taskList.add(new InventoryUtil.Task(slot));
                    this.taskList.add(new InventoryUtil.Task(target));
                }
                else {
                    this.taskList.add(new InventoryUtil.Task(slot, true));
                }
                if (this.updateController.getValue()) {
                    this.taskList.add(new InventoryUtil.Task());
                }
            }
        }
    }
    
    private void getSlotOn(final int slot, final int target) {
        if (this.taskList.isEmpty()) {
            this.doneSlots.remove((Object)target);
            if ((target < 5 && target > 0) || !this.shiftClick.getValue()) {
                this.taskList.add(new InventoryUtil.Task(target));
                this.taskList.add(new InventoryUtil.Task(slot));
            }
            else {
                this.taskList.add(new InventoryUtil.Task(target, true));
            }
            if (this.updateController.getValue()) {
                this.taskList.add(new InventoryUtil.Task());
            }
        }
    }
    
    private boolean isSafe() {
        final EntityPlayer closest = EntityUtil.getClosestEnemy(this.closestEnemy.getValue());
        return closest == null || AutoArmor.mc.player.getDistanceSq((Entity)closest) >= MathUtil.square(this.closestEnemy.getValue());
    }
}
