//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketAnimation;
import il.dev.mio.mod.ModuleCore;
import il.dev.mio.api.event.events.Packet;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import il.dev.mio.api.util.IMinecraftUtil;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class Swing extends Module
{
    public Setting<Hand> hand;
    private static Swing INSTANCE;
    
    public Swing() {
        super("Swing", "Changes the hand you swing with", Category.PLAYER, false, false, false);
        this.hand = (Setting<Hand>)this.register(new Setting("Mode", Hand.Offhand));
        this.setInstance();
    }
    
    public static Swing getINSTANCE() {
        if (Swing.INSTANCE == null) {
            Swing.INSTANCE = new Swing();
        }
        return Swing.INSTANCE;
    }
    
    private void setInstance() {
        Swing.INSTANCE = this;
    }
    
    @Override
    public String getDisplayInfo() {
        final String ModeInfo = String.valueOf(this.hand.getValue());
        return ModeInfo;
    }
    
    @Override
    public void onUpdate() {
        if (IMinecraftUtil.Util.mc.world == null) {
            return;
        }
        if (this.hand.getValue().equals(Hand.Offhand)) {
            IMinecraftUtil.Util.mc.player.swingingHand = EnumHand.OFF_HAND;
        }
        else if (this.hand.getValue().equals(Hand.Mainhand)) {
            IMinecraftUtil.Util.mc.player.swingingHand = EnumHand.MAIN_HAND;
        }
        else if (this.hand.getValue().equals(Hand.Cancel) && Swing.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && Swing.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            Swing.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            Swing.mc.entityRenderer.itemRenderer.itemStackMainHand = Swing.mc.player.getHeldItemMainhand();
        }
    }
    
    @SubscribeEvent
    public void onPacket(final Packet event) {
        if (ModuleCore.nullCheck() || event.getType() == Packet.Type.INCOMING) {
            return;
        }
        if (event.getPacket() instanceof CPacketAnimation) {
            event.setCanceled(true);
        }
    }
    
    static {
        Swing.INSTANCE = new Swing();
    }
    
    public enum Hand
    {
        Offhand, 
        Mainhand, 
        Cancel;
    }
}
