// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class RichPresenceUtil
{
    public static DiscordRichPresence presence;
    private static final DiscordRPC rpc;
    private static il.dev.mio.mod.modules.client.DiscordRPC discordrpc;
    private static Thread thread;
    
    public static void start() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        RichPresenceUtil.rpc.Discord_Initialize("948663399124992000", handlers, true, "");
        RichPresenceUtil.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        RichPresenceUtil.presence.details = "Mio v0.4.5";
        RichPresenceUtil.presence.state = il.dev.mio.mod.modules.client.DiscordRPC.INSTANCE.rpcState.getValue();
        RichPresenceUtil.presence.largeImageKey = "10202";
        RichPresenceUtil.presence.largeImageText = "Mio v0.4.5";
        RichPresenceUtil.presence.smallImageKey = "iconmio";
        RichPresenceUtil.presence.smallImageText = "sub to asphyxia";
        RichPresenceUtil.rpc.Discord_UpdatePresence(RichPresenceUtil.presence);
        (RichPresenceUtil.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                RichPresenceUtil.rpc.Discord_RunCallbacks();
                RichPresenceUtil.presence.details = "Mio v0.4.5";
                RichPresenceUtil.presence.state = il.dev.mio.mod.modules.client.DiscordRPC.INSTANCE.rpcState.getValue();
                RichPresenceUtil.rpc.Discord_UpdatePresence(RichPresenceUtil.presence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "DiscordRPC-Callback-Handler")).start();
    }
    
    public static void stop() {
        if (RichPresenceUtil.thread != null && !RichPresenceUtil.thread.isInterrupted()) {
            RichPresenceUtil.thread.interrupt();
        }
        RichPresenceUtil.rpc.Discord_Shutdown();
    }
    
    static {
        rpc = DiscordRPC.INSTANCE;
        RichPresenceUtil.presence = new DiscordRichPresence();
    }
}
