// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.command.commands;

import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.Mio;
import il.dev.mio.mod.command.Command;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("help");
    }
    
    @Override
    public void execute(final String[] commands) {
        Command.sendMessage("Commands: ");
        for (final Command command : Mio.commandManager.getCommands()) {
            Command.sendMessage(ChatFormatting.GRAY + Mio.commandManager.getPrefix() + command.getName());
        }
    }
}
