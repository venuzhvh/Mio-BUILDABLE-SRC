// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.command.commands;

import il.dev.mio.Mio;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.mod.command.Command;

public class PrefixCommand extends Command
{
    public PrefixCommand() {
        super("prefix", new String[] { "<char>" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage(ChatFormatting.GREEN + "The current prefix is " + Mio.commandManager.getPrefix());
            return;
        }
        Mio.commandManager.setPrefix(commands[0]);
        Command.sendMessage("Prefix changed to " + ChatFormatting.GRAY + commands[0]);
    }
}
