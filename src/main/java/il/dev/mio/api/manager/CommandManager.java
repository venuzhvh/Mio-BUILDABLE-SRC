// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.manager;

import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.LinkedList;
import il.dev.mio.mod.command.commands.ShrugCommand;
import il.dev.mio.mod.command.commands.CoordsCommand;
import il.dev.mio.mod.command.commands.ReloadSoundCommand;
import il.dev.mio.mod.command.commands.UnloadCommand;
import il.dev.mio.mod.command.commands.ReloadCommand;
import il.dev.mio.mod.command.commands.HelpCommand;
import il.dev.mio.mod.command.commands.FriendCommand;
import il.dev.mio.mod.command.commands.ConfigCommand;
import il.dev.mio.mod.command.commands.PrefixCommand;
import il.dev.mio.mod.command.commands.ModuleCommand;
import il.dev.mio.mod.command.commands.BindCommand;
import il.dev.mio.mod.command.Command;
import java.util.ArrayList;
import il.dev.mio.mod.ModuleCore;

public class CommandManager extends ModuleCore
{
    private final ArrayList<Command> commands;
    private String clientMessage;
    private String prefix;
    
    public CommandManager() {
        super("Command");
        this.commands = new ArrayList<Command>();
        this.clientMessage = "[Mio]";
        this.prefix = ";";
        this.commands.add(new BindCommand());
        this.commands.add(new ModuleCommand());
        this.commands.add(new PrefixCommand());
        this.commands.add(new ConfigCommand());
        this.commands.add(new FriendCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new ReloadCommand());
        this.commands.add(new UnloadCommand());
        this.commands.add(new ReloadSoundCommand());
        this.commands.add(new CoordsCommand());
        this.commands.add(new ShrugCommand());
    }
    
    public static String[] removeElement(final String[] input, final int indexToDelete) {
        final LinkedList<String> result = new LinkedList<String>();
        for (int i = 0; i < input.length; ++i) {
            if (i != indexToDelete) {
                result.add(input[i]);
            }
        }
        return result.toArray(input);
    }
    
    private static String strip(final String str, final String key) {
        if (str.startsWith(key) && str.endsWith(key)) {
            return str.substring(key.length(), str.length() - key.length());
        }
        return str;
    }
    
    public void executeCommand(final String command) {
        final String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        final String name = parts[0].substring(1);
        final String[] args = removeElement(parts, 0);
        for (int i = 0; i < args.length; ++i) {
            if (args[i] != null) {
                args[i] = strip(args[i], "\"");
            }
        }
        for (final Command c : this.commands) {
            if (!c.getName().equalsIgnoreCase(name)) {
                continue;
            }
            c.execute(parts);
            return;
        }
        Command.sendMessage(ChatFormatting.GRAY + "Command not found, type 'help' for the commands list.");
    }
    
    public Command getCommandByName(final String name) {
        for (final Command command : this.commands) {
            if (!command.getName().equals(name)) {
                continue;
            }
            return command;
        }
        return null;
    }
    
    public ArrayList<Command> getCommands() {
        return this.commands;
    }
    
    public String getClientMessage() {
        return this.clientMessage;
    }
    
    public void setClientMessage(final String clientMessage) {
        this.clientMessage = clientMessage;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
}
