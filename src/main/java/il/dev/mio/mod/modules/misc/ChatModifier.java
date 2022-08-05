//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.misc;

import net.minecraft.util.text.TextComponentString;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import il.dev.mio.Mio;
import net.minecraft.network.play.client.CPacketChatMessage;
import il.dev.mio.api.event.events.PacketEvent;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class ChatModifier extends Module
{
    private static ChatModifier INSTANCE;
    public Setting<Boolean> color;
    public Setting<Boolean> clean;
    public Setting<Boolean> infinite;
    public Setting<Boolean> suffix;
    public Setting<Boolean> suffix2b;
    public Setting<Boolean> time;
    public Setting<Bracket> bracket;
    public boolean check;
    
    public ChatModifier() {
        super("BetterChat", "Modifies your chat", Category.MISC, true, false, false);
        this.color = (Setting<Boolean>)this.register(new Setting("Color", false, v -> this.clean.getValue().equals(false)));
        this.clean = (Setting<Boolean>)this.register(new Setting("CleanChat", false, v -> this.color.getValue().equals(false)));
        this.infinite = (Setting<Boolean>)this.register(new Setting("InfiniteChat", false, "Makes your chat infinite."));
        this.suffix = (Setting<Boolean>)this.register(new Setting("Suffix", false));
        this.suffix2b = (Setting<Boolean>)this.register(new Setting("2b2tSuffix", false, v -> this.suffix.getValue()));
        this.time = (Setting<Boolean>)this.register(new Setting("TimeStamps", false, "time stamps in chat"));
        this.bracket = (Setting<Bracket>)this.register(new Setting("TSBracket", Bracket.Triangle, v -> this.time.getValue()));
        this.setInstance();
    }
    
    public static ChatModifier getInstance() {
        if (ChatModifier.INSTANCE == null) {
            ChatModifier.INSTANCE = new ChatModifier();
        }
        return ChatModifier.INSTANCE;
    }
    
    private void setInstance() {
        ChatModifier.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            final String s = ((CPacketChatMessage) event.getPacket()).getMessage();
            this.check = !s.startsWith(Mio.commandManager.getPrefix());
        }
        if (this.suffix.getValue() && event.getPacket() instanceof CPacketChatMessage) {
            if (this.suffix2b.getValue()) {
                final CPacketChatMessage packet = event.getPacket();
                String s2 = packet.getMessage();
                if (s2.startsWith("/") || s2.startsWith("!")) {
                    return;
                }
                s2 += " | mio";
                if (s2.length() >= 256) {
                    s2 = s2.substring(0, 256);
                }
                packet.message = s2;
            }
            else {
                final CPacketChatMessage packet = event.getPacket();
                String s2 = packet.getMessage();
                if (s2.startsWith("/") || s2.startsWith("!")) {
                    return;
                }
                s2 += " \u22c6 \u1d0d\u026a\u1d0f";
                if (s2.length() >= 256) {
                    s2 = s2.substring(0, 256);
                }
                packet.message = s2;
            }
        }
    }
    
    @SubscribeEvent
    public void onClientChatReceived(final ClientChatReceivedEvent event) {
        if (this.time.getValue()) {
            final Date date = new Date();
            final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
            final String strDate = dateFormatter.format(date);
            final String leBracket1 = this.bracket.getValue().equals(Bracket.Triangle) ? "<" : "[";
            final String leBracket2 = this.bracket.getValue().equals(Bracket.Triangle) ? ">" : "]";
            final TextComponentString time = new TextComponentString(ChatFormatting.GRAY + leBracket1 + ChatFormatting.WHITE + strDate + ChatFormatting.GRAY + leBracket2 + ChatFormatting.RESET + " ");
            event.setMessage(time.appendSibling(event.getMessage()));
        }
    }
    
    static {
        ChatModifier.INSTANCE = new ChatModifier();
    }
    
    private enum Bracket
    {
        Square, 
        Triangle;
    }
}
