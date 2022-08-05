//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.misc;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import il.dev.mio.mod.modules.Module;

public class Coords extends Module
{
    String coords;
    
    public Coords() {
        super("Coords", "copies your current position to the clipboard", Category.MISC, true, false, false);
    }
    
    @Override
    public void onEnable() {
        final int posX = (int)Coords.mc.player.posX;
        final int posY = (int)Coords.mc.player.posY;
        final int posZ = (int)Coords.mc.player.posZ;
        this.coords = "X: " + posX + " Y: " + posY + " Z: " + posZ;
        final String myString = this.coords;
        final StringSelection stringSelection = new StringSelection(myString);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        this.toggle();
    }
}
