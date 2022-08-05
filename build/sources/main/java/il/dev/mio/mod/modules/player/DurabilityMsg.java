//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.player;

import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import il.dev.mio.mod.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.api.util.interact.DamageUtil;
import net.minecraft.item.ItemStack;
import il.dev.mio.Mio;
import il.dev.mio.api.event.events.UpdateWalkingPlayerEvent;
import java.util.HashMap;
import il.dev.mio.api.util.world.Timer;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class DurabilityMsg extends Module
{
    private final Setting<Integer> armorThreshhold;
    private final Setting<Boolean> notifySelf;
    private final Setting<Boolean> notification;
    private final Map<EntityPlayer, Integer> entityArmorArraylist;
    private final Timer timer;
    
    public DurabilityMsg() {
        super("DurabilityMsg", "Message friends when their armor is low", Category.PLAYER, true, false, false);
        this.armorThreshhold = (Setting<Integer>)this.register(new Setting("Armor%", 20, 1, 100));
        this.notifySelf = (Setting<Boolean>)this.register(new Setting("NotifySelf", true));
        this.notification = (Setting<Boolean>)this.register(new Setting("Friends", true));
        this.entityArmorArraylist = new HashMap<EntityPlayer, Integer>();
        this.timer = new Timer();
    }
    
    @SubscribeEvent
    public void onUpdate(final UpdateWalkingPlayerEvent event) {
        for (final EntityPlayer player : DurabilityMsg.mc.world.playerEntities) {
            if (!player.isDead) {
                if (!Mio.friendManager.isFriend(player.getName())) {
                    continue;
                }
                for (final ItemStack stack : player.inventory.armorInventory) {
                    if (stack == ItemStack.EMPTY) {
                        continue;
                    }
                    final int percent = DamageUtil.getRoundedDamage(stack);
                    if (percent <= this.armorThreshhold.getValue() && !this.entityArmorArraylist.containsKey(player)) {
                        if (player == DurabilityMsg.mc.player && this.notifySelf.getValue()) {
                            Command.sendMessage(ChatFormatting.RED + "Your " + this.getArmorPieceName(stack) + " low dura!");
                        }
                        if (Mio.friendManager.isFriend(player.getName()) && this.notification.getValue() && player != DurabilityMsg.mc.player) {
                            DurabilityMsg.mc.player.sendChatMessage("/msg " + player.getName() + " Yo, " + player.getName() + ", ur " + this.getArmorPieceName(stack) + " low dura!");
                        }
                        this.entityArmorArraylist.put(player, player.inventory.armorInventory.indexOf((Object)stack));
                    }
                    if (!this.entityArmorArraylist.containsKey(player) || this.entityArmorArraylist.get(player) != player.inventory.armorInventory.indexOf((Object)stack)) {
                        continue;
                    }
                    if (percent <= this.armorThreshhold.getValue()) {
                        continue;
                    }
                    this.entityArmorArraylist.remove(player);
                }
                if (!this.entityArmorArraylist.containsKey(player)) {
                    continue;
                }
                if (player.inventory.armorInventory.get((int)this.entityArmorArraylist.get(player)) != ItemStack.EMPTY) {
                    continue;
                }
                this.entityArmorArraylist.remove(player);
            }
        }
    }
    
    private String getArmorPieceName(final ItemStack stack) {
        if (stack.getItem() == Items.DIAMOND_HELMET || stack.getItem() == Items.GOLDEN_HELMET || stack.getItem() == Items.IRON_HELMET || stack.getItem() == Items.CHAINMAIL_HELMET || stack.getItem() == Items.LEATHER_HELMET) {
            return "helmet is";
        }
        if (stack.getItem() == Items.DIAMOND_CHESTPLATE || stack.getItem() == Items.GOLDEN_CHESTPLATE || stack.getItem() == Items.IRON_CHESTPLATE || stack.getItem() == Items.CHAINMAIL_CHESTPLATE || stack.getItem() == Items.LEATHER_CHESTPLATE) {
            return "chest is";
        }
        if (stack.getItem() == Items.DIAMOND_LEGGINGS || stack.getItem() == Items.GOLDEN_LEGGINGS || stack.getItem() == Items.IRON_LEGGINGS || stack.getItem() == Items.CHAINMAIL_LEGGINGS || stack.getItem() == Items.LEATHER_LEGGINGS) {
            return "leggings are";
        }
        return "boots are";
    }
}
