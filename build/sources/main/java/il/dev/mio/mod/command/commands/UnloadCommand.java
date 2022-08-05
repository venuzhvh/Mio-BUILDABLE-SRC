// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.command.commands;

import il.dev.mio.Mio;
import il.dev.mio.mod.command.Command;

public class UnloadCommand extends Command
{
    public UnloadCommand() {
        super("unload", new String[0]);
    }
    
    @Override
    public void execute(final String[] commands) {
        Mio.unload(true);
    }
}
