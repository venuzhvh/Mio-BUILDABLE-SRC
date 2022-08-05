// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.command.commands;

import il.dev.mio.Mio;
import il.dev.mio.mod.command.Command;

public class ReloadCommand extends Command
{
    public ReloadCommand() {
        super("reload", new String[0]);
    }
    
    @Override
    public void execute(final String[] commands) {
        Mio.reload();
    }
}
