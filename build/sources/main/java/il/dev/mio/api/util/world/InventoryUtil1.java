//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util.world;

import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import java.util.Iterator;
import net.minecraft.block.Block;
import il.dev.mio.api.util.IMinecraftUtil;

public class InventoryUtil1 implements IMinecraftUtil
{
    public static int getHandSlot() {
        return InventoryUtil1.mc.player.inventory.currentItem;
    }
    
    public static int getSlot(final Block block) {
        try {
            for (final ItemStackUtil itemStack : getAllItems()) {
                if (Block.getBlockFromItem(itemStack.itemStack.getItem()).equals(block)) {
                    return itemStack.slotId;
                }
            }
        }
        catch (Exception ex) {}
        return -1;
    }
    
    public static int getSlot(final Item item) {
        try {
            for (final ItemStackUtil itemStack : getAllItems()) {
                if (itemStack.itemStack.getItem().equals(item)) {
                    return itemStack.slotId;
                }
            }
        }
        catch (Exception ex) {}
        return -1;
    }
    
    public static void clickSlot(final int id) {
        if (id != -1) {
            InventoryUtil1.mc.playerController.windowClick(InventoryUtil1.mc.player.openContainer.windowId, getClickSlot(id), 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil1.mc.player);
        }
    }
    
    public static void clickSlot(final int id, final int otherRows) {
        if (id != -1) {
            InventoryUtil1.mc.playerController.windowClick(InventoryUtil1.mc.player.openContainer.windowId, getClickSlot(id) + otherRows, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil1.mc.player);
        }
    }
    
    public static int getClickSlot(int id) {
        if (id == -1) {
            return id;
        }
        if (id < 9) {
            id += 36;
            return id;
        }
        if (id == 39) {
            id = 5;
        }
        else if (id == 38) {
            id = 6;
        }
        else if (id == 37) {
            id = 7;
        }
        else if (id == 36) {
            id = 8;
        }
        else if (id == 40) {
            id = 45;
        }
        return id;
    }
    
    public static void switchItem(final int slot, final boolean sleep) throws InterruptedException {
        if (slot < 9) {
            InventoryUtil1.mc.player.inventory.currentItem = slot;
        }
        else {
            clickSlot(slot);
            if (sleep) {
                Thread.sleep(200L);
            }
            clickSlot(8);
            if (sleep) {
                Thread.sleep(200L);
            }
            clickSlot(slot);
            if (sleep) {
                Thread.sleep(200L);
            }
            InventoryUtil1.mc.player.inventory.currentItem = 8;
            if (sleep) {
                Thread.sleep(100L);
            }
        }
    }
    
    public static ItemStack getItemStack(final int id) {
        try {
            return InventoryUtil1.mc.player.inventory.getStackInSlot(id);
        }
        catch (NullPointerException e) {
            return null;
        }
    }
    
    public static int getAmountOfItem(final Item item) {
        int count = 0;
        for (final ItemStackUtil itemStack : getAllItems()) {
            if (itemStack.itemStack != null && itemStack.itemStack.getItem().equals(item)) {
                count += itemStack.itemStack.getCount();
            }
        }
        return count;
    }
    
    public static int getAmountOfBlock(final Block block) {
        int count = 0;
        for (final ItemStackUtil itemStack : getAllItems()) {
            if (Block.getBlockFromItem(itemStack.itemStack.getItem()).equals(block)) {
                count += itemStack.itemStack.getCount();
            }
        }
        return count;
    }
    
    public static boolean hasItem(final Item item) {
        return getAmountOfItem(item) != 0;
    }
    
    public static boolean hasHotbarItem(final Item item) {
        for (int i = 0; i < 9; ++i) {
            if (getItemStack(i).getItem() == item) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasBlock(final Block block) {
        return getSlot(block) != -1;
    }
    
    public static boolean isFull() {
        for (final ItemStackUtil itemStack : getAllItems()) {
            if (itemStack.itemStack.getItem() == Items.AIR) {
                return false;
            }
        }
        return true;
    }
    
    public static int getEmptySlots() {
        int count = 0;
        for (final ItemStackUtil itemStack : getAllItems()) {
            if (itemStack.itemStack.getItem() == Items.AIR) {
                ++count;
            }
        }
        return count;
    }
    
    public static int getEmptySlot() {
        for (final ItemStackUtil itemStack : getAllItems()) {
            if (itemStack.itemStack.getItem() == Items.AIR) {
                return itemStack.slotId;
            }
        }
        return -1;
    }
    
    public static ArrayList<ItemStackUtil> getAllItems() {
        final ArrayList<ItemStackUtil> items = new ArrayList<ItemStackUtil>();
        for (int i = 0; i < 36; ++i) {
            items.add(new ItemStackUtil(getItemStack(i), i));
        }
        return items;
    }
    
    public static class ItemStackUtil
    {
        public ItemStack itemStack;
        public int slotId;
        
        public ItemStackUtil(final ItemStack itemStack, final int slotId) {
            this.itemStack = itemStack;
            this.slotId = slotId;
        }
    }
}
