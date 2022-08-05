//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class Chams extends Module
{
    private static Chams INSTANCE;
    private final Setting<Page> page;
    public Setting<Boolean> players;
    public Setting<RenderMode> mode;
    public Setting<Boolean> playerModel;
    public Setting<Boolean> lol;
    public Setting<Boolean> sneak;
    public Setting<Boolean> lagChams;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> red1;
    public Setting<Integer> green1;
    public Setting<Integer> blue1;
    public final Setting<Float> alpha;
    public final Setting<Float> lineWidth;
    public Setting<Boolean> rainbow;
    public Setting<Integer> rainbowHue;
    
    public Chams() {
        super("Chams", "Draws a pretty ESP around other players", Category.RENDER, false, false, false);
        this.page = (Setting<Page>)this.register(new Setting("Settings", Page.GLOBAL));
        this.players = (Setting<Boolean>)this.register(new Setting("Render", Boolean.TRUE, v -> this.page.getValue() == Page.GLOBAL));
        this.mode = (Setting<RenderMode>)this.register(new Setting("Mode", RenderMode.Wireframe, v -> this.players.getValue() && this.page.getValue() == Page.GLOBAL));
        this.playerModel = (Setting<Boolean>)this.register(new Setting("PlayerModel", true, v -> this.page.getValue() == Page.GLOBAL));
        this.lol = (Setting<Boolean>)this.register(new Setting("Funny", false, v -> this.page.getValue() == Page.GLOBAL));
        this.sneak = (Setting<Boolean>)this.register(new Setting("Sneak", false, v -> this.page.getValue() == Page.GLOBAL));
        this.lagChams = (Setting<Boolean>)this.register(new Setting("LagChams", true, v -> this.page.getValue() == Page.GLOBAL));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 255, 0, 255, v -> this.players.getValue() && this.page.getValue() == Page.COLORS));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 255, 0, 255, v -> this.players.getValue() && this.page.getValue() == Page.COLORS));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 255, 0, 255, v -> this.players.getValue() && this.page.getValue() == Page.COLORS));
        this.red1 = (Setting<Integer>)this.register(new Setting("WireframeRed", 255, 0, 255, v -> this.players.getValue() && this.page.getValue() == Page.COLORS));
        this.green1 = (Setting<Integer>)this.register(new Setting("WireframeGreen", 255, 0, 255, v -> this.players.getValue() && this.page.getValue() == Page.COLORS));
        this.blue1 = (Setting<Integer>)this.register(new Setting("WireframeBlue", 255, 0, 255, v -> this.players.getValue() && this.page.getValue() == Page.COLORS));
        this.alpha = (Setting<Float>)this.register(new Setting("Alpha", 80.0f, 0.1f, 255.0f, v -> this.players.getValue() && this.page.getValue() == Page.COLORS));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", 1.0f, 0.1f, 3.0f, v -> this.players.getValue() && this.page.getValue() == Page.COLORS));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", Boolean.FALSE, v -> this.page.getValue() == Page.COLORS));
        this.rainbowHue = (Setting<Integer>)this.register(new Setting("Brightness", 100, 0, 600, v -> this.rainbow.getValue() && this.page.getValue() == Page.COLORS));
        this.setInstance();
    }
    
    public static Chams getInstance() {
        if (Chams.INSTANCE == null) {
            Chams.INSTANCE = new Chams();
        }
        return Chams.INSTANCE;
    }
    
    private void setInstance() {
        Chams.INSTANCE = this;
    }
    
    @Override
    public String getDisplayInfo() {
        final String ModeInfo = String.valueOf(this.mode.getValue());
        return ModeInfo;
    }
    
    @SubscribeEvent
    public void onRenderPlayerEvent(final RenderPlayerEvent.Pre event) {
        event.getEntityPlayer().hurtTime = 0;
    }
    
    static {
        Chams.INSTANCE = new Chams();
    }
    
    public enum RenderMode
    {
        Solid, 
        Wireframe, 
        Both;
    }
    
    public enum Page
    {
        COLORS, 
        GLOBAL;
    }
}
