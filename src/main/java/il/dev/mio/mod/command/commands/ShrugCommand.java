// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.command.commands;

import java.awt.datatransfer.Clipboard;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import il.dev.mio.api.util.render.TextUtill;
import il.dev.mio.mod.command.Command;

public class ShrugCommand extends Command
{
    public ShrugCommand() {
        super("shrug");
    }
    
    @Override
    public void execute(final String[] commands) {
        final StringSelection stringSelection = new StringSelection(TextUtill.shrug);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        Command.sendMessage(ChatFormatting.GRAY + "copied le shrug to ur clipboard");
    }
}
