//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

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
import il.dev.mio.mod.command.Command;

public class CoordsCommand extends Command
{
    String coords;
    
    public CoordsCommand() {
        super("coords");
    }
    
    @Override
    public void execute(final String[] commands) {
        final int posX = (int)CoordsCommand.mc.player.posX;
        final int posY = (int)CoordsCommand.mc.player.posY;
        final int posZ = (int)CoordsCommand.mc.player.posZ;
        this.coords = "X: " + posX + " Y: " + posY + " Z: " + posZ;
        final String myString = this.coords;
        final StringSelection stringSelection = new StringSelection(myString);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        Command.sendMessage(ChatFormatting.GRAY + "Coords copied.");
    }
}
