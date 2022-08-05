//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.mixin.mixins;

import net.minecraft.client.Minecraft;
import il.dev.mio.Mio;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import il.dev.mio.api.util.render.ColorUtil;
import il.dev.mio.mod.modules.client.ClickGui;
import il.dev.mio.mod.modules.misc.ChatModifier;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.client.gui.ChatLine;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.Gui;

@Mixin({ GuiNewChat.class })
public class MixinGuiNewChat extends Gui
{
    @Shadow
    private boolean isScrolled;
    private float percentComplete;
    private int newLines;
    private long prevMillis;
    private boolean configuring;
    private float animationPercent;
    private int lineBeingDrawn;
    @Final
    public List<ChatLine> drawnChatLines;
    private ChatLine chatLine;
    
    public MixinGuiNewChat() {
        this.prevMillis = System.currentTimeMillis();
    }
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    private void drawRectHook(final int left, final int top, final int right, final int bottom, final int color) {
        if (ChatModifier.getInstance().color.getValue() && ChatModifier.getInstance().clean.getValue().equals(false)) {
            Gui.drawRect(left, top, right, bottom, ColorUtil.toARGB(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue(), 35));
        }
        else {
            Gui.drawRect(left, top, right, bottom, (ChatModifier.getInstance().isOn() && ChatModifier.getInstance().clean.getValue()) ? 0 : color);
        }
    }
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawStringWithShadow(final FontRenderer fontRenderer, final String text, final float x, final float y, final int color) {
        if (text.contains("§(")) {
            Mio.textManager.drawColorSyncString(text, x, y, true);
        }
        else {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color);
        }
        return 0;
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0, remap = false))
    public int drawnChatLinesSize(final List<ChatLine> list) {
        return (ChatModifier.getInstance().isOn() && ChatModifier.getInstance().infinite.getValue()) ? -2147483647 : list.size();
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 2, remap = false))
    public int chatLinesSize(final List<ChatLine> list) {
        return (ChatModifier.getInstance().isOn() && ChatModifier.getInstance().infinite.getValue()) ? -2147483647 : list.size();
    }
}
