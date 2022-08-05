//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.misc;

import il.dev.mio.mod.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.Mio;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import il.dev.mio.mod.modules.Module;

public class PopNotify extends Module
{
    public static HashMap<String, Integer> TotemPopContainer;
    private static PopNotify INSTANCE;
    
    public PopNotify() {
        super("PopNotify", "Counts other players totem pops.", Category.MISC, true, false, false);
        this.setInstance();
    }
    
    public static PopNotify getInstance() {
        if (PopNotify.INSTANCE == null) {
            PopNotify.INSTANCE = new PopNotify();
        }
        return PopNotify.INSTANCE;
    }
    
    private void setInstance() {
        PopNotify.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        PopNotify.TotemPopContainer.clear();
    }
    
    public void onDeath(final EntityPlayer player) {
        if (PopNotify.TotemPopContainer.containsKey(player.getName())) {
            final int l_Count = PopNotify.TotemPopContainer.get(player.getName());
            PopNotify.TotemPopContainer.remove(player.getName());
            if (l_Count == 1) {
                if (Mio.friendManager.isFriend(player.getName())) {
                    Command.sendMessage("§r" + ChatFormatting.AQUA + player.getName() + "§r died after popping §(" + ChatFormatting.AQUA + ChatFormatting.BOLD + l_Count + ChatFormatting.RESET + "§r Totem!");
                }
                else {
                    Command.sendMessage("§r" + ChatFormatting.WHITE + player.getName() + "§r died after popping §(" + ChatFormatting.WHITE + ChatFormatting.BOLD + l_Count + ChatFormatting.RESET + "§r Totem!");
                }
            }
            else if (Mio.friendManager.isFriend(player.getName())) {
                Command.sendMessage("§r" + ChatFormatting.AQUA + player.getName() + "§r died after popping §(" + ChatFormatting.AQUA + ChatFormatting.BOLD + l_Count + ChatFormatting.RESET + "§r Totems!");
            }
            else {
                Command.sendMessage("§r" + ChatFormatting.WHITE + player.getName() + "§r died after popping §(" + ChatFormatting.WHITE + ChatFormatting.BOLD + l_Count + ChatFormatting.RESET + "§r Totems!");
            }
        }
    }
    
    public void onTotemPop(final EntityPlayer player) {
        if (fullNullCheck()) {
            return;
        }
        if (PopNotify.mc.player.equals((Object)player)) {
            return;
        }
        int l_Count = 1;
        if (PopNotify.TotemPopContainer.containsKey(player.getName())) {
            l_Count = PopNotify.TotemPopContainer.get(player.getName());
            PopNotify.TotemPopContainer.put(player.getName(), ++l_Count);
        }
        else {
            PopNotify.TotemPopContainer.put(player.getName(), l_Count);
        }
        if (l_Count == 1) {
            if (Mio.friendManager.isFriend(player.getName())) {
                Command.sendMessage("§r" + ChatFormatting.AQUA + player.getName() + "§r has popped §(" + ChatFormatting.AQUA + ChatFormatting.BOLD + l_Count + ChatFormatting.RESET + "§r Totem.");
            }
            else {
                Command.sendMessage("§r" + ChatFormatting.WHITE + player.getName() + "§r has popped §(" + ChatFormatting.WHITE + ChatFormatting.BOLD + l_Count + ChatFormatting.RESET + "§r Totem.");
            }
        }
        else if (Mio.friendManager.isFriend(player.getName())) {
            Command.sendMessage("§r" + ChatFormatting.AQUA + player.getName() + "§r has popped §(" + ChatFormatting.AQUA + ChatFormatting.BOLD + l_Count + ChatFormatting.RESET + "§r Totems.");
        }
        else {
            Command.sendMessage("§r" + ChatFormatting.WHITE + player.getName() + "§r has popped §(" + ChatFormatting.WHITE + ChatFormatting.BOLD + l_Count + ChatFormatting.RESET + "§r Totems.");
        }
    }
    
    static {
        PopNotify.TotemPopContainer = new HashMap<String, Integer>();
        PopNotify.INSTANCE = new PopNotify();
    }
}
