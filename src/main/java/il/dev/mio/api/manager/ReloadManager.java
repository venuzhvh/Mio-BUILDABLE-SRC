//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.manager;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import il.dev.mio.Mio;
import net.minecraft.network.play.client.CPacketChatMessage;
import il.dev.mio.api.event.events.PacketEvent;
import il.dev.mio.mod.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import il.dev.mio.mod.ModuleCore;

public class ReloadManager extends ModuleCore
{
    public String prefix;
    
    public void init(final String prefix) {
        this.prefix = prefix;
        MinecraftForge.EVENT_BUS.register((Object)this);
        if (!ModuleCore.fullNullCheck()) {
            Command.sendMessage(ChatFormatting.RED + "Mio has been unloaded. Type " + prefix + "reload to reload.");
        }
    }
    
    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        final CPacketChatMessage packet;
        if (event.getPacket() instanceof CPacketChatMessage && (packet = event.getPacket()).getMessage().startsWith(this.prefix) && packet.getMessage().contains("reload")) {
            Mio.load();
            event.setCanceled(true);
        }
    }
}
