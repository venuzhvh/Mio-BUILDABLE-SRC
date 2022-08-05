//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.player;

import il.dev.mio.api.event.events.BlockDestructionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import java.text.DecimalFormat;
import il.dev.mio.api.util.IMinecraftUtil;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class Announcer extends Module
{
    public static int blockBrokeDelay;
    static int blockPlacedDelay;
    static int jumpDelay;
    static int attackDelay;
    static int eattingDelay;
    static long lastPositionUpdate;
    static double lastPositionX;
    static double lastPositionY;
    static double lastPositionZ;
    private static double speed;
    String heldItem;
    int blocksPlaced;
    int blocksBroken;
    int eaten;
    public static String walkMessage;
    public static String breakMessage;
    public static String eatMessage;
    private final Setting<Boolean> move;
    private final Setting<Boolean> breakBlock;
    private final Setting<Boolean> eat;
    private final Setting<Double> delay;
    private final Setting<LanguageAn> language;
    
    public Announcer() {
        super("Announcer", "announces yo shit", Category.PLAYER, false, false, false);
        this.heldItem = "";
        this.blocksPlaced = 0;
        this.blocksBroken = 0;
        this.eaten = 0;
        this.move = (Setting<Boolean>)this.register(new Setting("Move", false));
        this.breakBlock = (Setting<Boolean>)this.register(new Setting("Break", false));
        this.eat = (Setting<Boolean>)this.register(new Setting("Eat", false));
        this.delay = (Setting<Double>)this.register(new Setting("Delay", 5.0, 1.0, 20.0));
        this.language = new Setting<LanguageAn>("Language", LanguageAn.ENGLISH);
    }
    
    @Override
    public void onUpdate() {
        ++Announcer.blockBrokeDelay;
        ++Announcer.blockPlacedDelay;
        ++Announcer.jumpDelay;
        ++Announcer.attackDelay;
        ++Announcer.eattingDelay;
        this.heldItem = IMinecraftUtil.Util.mc.player.getHeldItemMainhand().getDisplayName();
        if (this.language.getValue().equals(LanguageAn.ENGLISH)) {
            Announcer.walkMessage = "I just flew over {blocks} blocks with the power of hvhlegend.il!";
            Announcer.breakMessage = "I just destroyed {amount2} {name2} with the power of hvhlegend.il!";
            Announcer.eatMessage = "I just ate {amount1} {name1} with the power of hvhlegend.il";
        }
        if (this.language.getValue().equals(LanguageAn.TURKISH)) {
            Announcer.walkMessage = "hvhlegend.il sayesinde {blocks} blok u\u00e7tum!";
            Announcer.breakMessage = "Az \u00f6nce {amount2} {name2} k\u0131rd\u0131m. Te\u015eekk\u00fcrler hvhlegend.il!";
            Announcer.eatMessage = "Tam olarak {amount1} {name2} yedim. Te\u015eekk\u00fcrler hvhlegend.il";
        }
        if (this.language.getValue().equals(LanguageAn.CHINESE)) {
            Announcer.walkMessage = "\u6211\u521a\u521a\u7528 hvhlegend.il \u8d70\u4e86 {blocks} \u7c73!";
            Announcer.breakMessage = "\u6211\u521a\u521a\u7528 hvhlengeng.il \u7834\u574f\u4e86 {amount2} {name2}!";
            Announcer.eatMessage = "\u6211\u521a\u7528 hvhlegend.il \u5403\u4e86 {amount1} \u4e2a {name1}!";
        }
        if (this.move.getValue() && Announcer.lastPositionUpdate + 5000.0 * this.delay.getValue() < System.currentTimeMillis()) {
            final double d0 = Announcer.lastPositionX - IMinecraftUtil.Util.mc.player.lastTickPosX;
            final double d2 = Announcer.lastPositionY - IMinecraftUtil.Util.mc.player.lastTickPosY;
            final double d3 = Announcer.lastPositionZ - IMinecraftUtil.Util.mc.player.lastTickPosZ;
            Announcer.speed = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
            if (Announcer.speed > 1.0 && Announcer.speed <= 5000.0) {
                final String walkAmount = new DecimalFormat("0").format(Announcer.speed);
                IMinecraftUtil.Util.mc.player.sendChatMessage(Announcer.walkMessage.replace("{blocks}", walkAmount));
            }
            Announcer.lastPositionUpdate = System.currentTimeMillis();
            Announcer.lastPositionX = IMinecraftUtil.Util.mc.player.lastTickPosX;
            Announcer.lastPositionY = IMinecraftUtil.Util.mc.player.lastTickPosY;
            Announcer.lastPositionZ = IMinecraftUtil.Util.mc.player.lastTickPosZ;
        }
    }
    
    @SubscribeEvent
    public void onItemUse(final LivingEntityUseItemEvent event) {
        if (this.eat.getValue() && Announcer.lastPositionUpdate + 5000.0 * this.delay.getValue() < System.currentTimeMillis()) {
            final int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
            if (event.getEntity() == IMinecraftUtil.Util.mc.player && (event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold)) {
                ++this.eaten;
                if (Announcer.eattingDelay >= 300.0 * this.delay.getValue() && this.eat.getValue() && this.eaten > randomNum) {
                    IMinecraftUtil.Util.mc.player.sendChatMessage(Announcer.eatMessage.replace("{amount1}", this.eaten + "").replace("{name1}", IMinecraftUtil.Util.mc.player.getHeldItemMainhand().getDisplayName()));
                    this.eaten = 0;
                    Announcer.eattingDelay = 0;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onBlockBreak(final BlockDestructionEvent event) {
        if (this.breakBlock.getValue() && Announcer.lastPositionUpdate + 5000.0 * this.delay.getValue() < System.currentTimeMillis()) {
            ++this.blocksBroken;
            final int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
            if (Announcer.blockBrokeDelay >= 300.0 * this.delay.getValue()) {
                if (this.breakBlock.getValue() && this.blocksBroken > randomNum) {
                    final String msg = Announcer.breakMessage.replace("{amount2}", this.blocksBroken + "").replace("{name2}", IMinecraftUtil.Util.mc.world.getBlockState(event.getBlockPos()).getBlock().getLocalizedName());
                    IMinecraftUtil.Util.mc.player.sendChatMessage(msg);
                }
                this.blocksBroken = 0;
                Announcer.blockBrokeDelay = 0;
            }
        }
    }
    
    static {
        Announcer.blockBrokeDelay = 0;
        Announcer.blockPlacedDelay = 0;
        Announcer.jumpDelay = 0;
        Announcer.attackDelay = 0;
        Announcer.eattingDelay = 0;
    }
    
    public enum LanguageAn
    {
        ENGLISH, 
        TURKISH, 
        CHINESE;
    }
}
