//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.client;

import il.dev.mio.api.event.events.ClientEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraft.entity.Entity;
import java.util.function.ToIntFunction;
import net.minecraft.init.Items;
import net.minecraft.client.renderer.GlStateManager;
import il.dev.mio.api.util.plugs.MathUtil;
import il.dev.mio.api.util.interact.EntityUtil;
import il.dev.mio.api.util.world.RotationUtil;
import java.util.Iterator;
import net.minecraft.util.math.MathHelper;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import net.minecraft.potion.PotionEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import il.dev.mio.mod.modules.misc.ShulkerPreview;
import il.dev.mio.api.util.render.RenderUtil;
import org.apache.commons.codec.digest.DigestUtils;
import il.dev.mio.Mio;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.api.util.render.ColorUtil;
import il.dev.mio.api.event.events.Render2DEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import il.dev.mio.api.util.render.TextUtil;
import java.util.Map;
import il.dev.mio.api.util.world.Timer;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import il.dev.mio.mod.modules.Module;

public class HUD extends Module
{
    private static final ResourceLocation box;
    private static final ItemStack totem;
    private static RenderItem itemRender;
    public static HUD INSTANCE;
    private final Setting<Page> page;
    private final Setting<Boolean> grayNess;
    private final Setting<Boolean> renderingUp;
    private final Setting<Boolean> skeetCC;
    private final Setting<SkeetColor> skeetColor;
    private final Setting<Boolean> waterMark;
    public Setting<Integer> waterMarkY;
    private final Setting<Boolean> waterMark2;
    private final Setting<Boolean> pvp;
    private final Setting<Boolean> coords;
    private final Setting<Boolean> direction;
    private final Setting<Boolean> armor;
    private final Setting<Boolean> lag;
    private final Setting<Boolean> greeter;
    private final Setting<GreeterMode> greetermode;
    private final Setting<String> welcomer;
    private final Setting<Boolean> potions;
    private final Setting<Boolean> speed;
    private final Setting<Boolean> textRadar;
    private final Setting<Boolean> tps;
    private final Setting<Boolean> fps;
    private final Setting<Boolean> arrayList;
    private final Setting<Boolean> forgeHax;
    private final Setting<Boolean> hideInChat;
    private final Timer timer;
    private Map<String, Integer> players;
    public Setting<String> command;
    public Setting<TextUtil.Color> bracketColor;
    public Setting<TextUtil.Color> commandColor;
    public Setting<String> commandBracket;
    public Setting<String> commandBracket2;
    public Setting<Boolean> notifyToggles;
    public Setting<Boolean> magenDavid;
    public Setting<Integer> animationHorizontalTime;
    public Setting<Integer> animationVerticalTime;
    public Setting<RenderingMode> renderingMode;
    public Setting<Boolean> time;
    public Setting<Integer> lagTime;
    private int color;
    public float hue;
    private boolean shouldIncrement;
    private int hitMarkerTimer;
    public static String hash;
    private final List<Module> moduleList;
    
    public HUD() {
        super("HUD", "HUD Elements rendered on your screen", Category.CLIENT, true, false, false);
        this.page = (Setting<Page>)this.register(new Setting("Page", Page.GLOBAL));
        this.grayNess = (Setting<Boolean>)this.register(new Setting("Gray", true, v -> this.page.getValue() == Page.GLOBAL));
        this.renderingUp = (Setting<Boolean>)this.register(new Setting("RenderingUp", true, v -> this.page.getValue() == Page.GLOBAL));
        this.skeetCC = (Setting<Boolean>)this.register(new Setting("SkeetMode", true, v -> this.page.getValue() == Page.ELEMENTS));
        this.skeetColor = (Setting<SkeetColor>)this.register(new Setting("LineColor", SkeetColor.RollingRGB, v -> this.page.getValue() == Page.ELEMENTS && this.skeetCC.getValue()));
        this.waterMark = (Setting<Boolean>)this.register(new Setting("WatermarkMio", true, v -> this.page.getValue() == Page.ELEMENTS));
        this.waterMarkY = (Setting<Integer>)this.register(new Setting("MioWmHeight", 2, 2, 12, v -> this.waterMark.getValue() && this.page.getValue() == Page.ELEMENTS));
        this.waterMark2 = (Setting<Boolean>)this.register(new Setting("WatermarkHL", true, v -> this.page.getValue() == Page.ELEMENTS));
        this.pvp = (Setting<Boolean>)this.register(new Setting("PvpInfo", true, v -> this.page.getValue() == Page.ELEMENTS));
        this.coords = (Setting<Boolean>)this.register(new Setting("Position(XYZ)", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.direction = (Setting<Boolean>)this.register(new Setting("Direction", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.armor = (Setting<Boolean>)this.register(new Setting("Armor", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.lag = (Setting<Boolean>)this.register(new Setting("LagNotifier", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.greeter = (Setting<Boolean>)this.register(new Setting("Welcomer", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.greetermode = (Setting<GreeterMode>)this.register(new Setting("Mode", GreeterMode.PLAYER, v -> this.greeter.getValue()));
        this.welcomer = (Setting<String>)this.register(new Setting("WelcomerText", "hihi", v -> this.greeter.getValue() && this.greetermode.getValue() == GreeterMode.CUSTOM && this.page.getValue() == Page.ELEMENTS));
        this.potions = (Setting<Boolean>)this.register(new Setting("Potions", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.speed = (Setting<Boolean>)this.register(new Setting("Speed", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.textRadar = (Setting<Boolean>)this.register(new Setting("TextRadar", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.tps = (Setting<Boolean>)this.register(new Setting("TPS", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.fps = (Setting<Boolean>)this.register(new Setting("FPS", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.arrayList = (Setting<Boolean>)this.register(new Setting("ActiveModules", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.forgeHax = (Setting<Boolean>)this.register(new Setting("ForgeHax", false, v -> this.page.getValue() == Page.ELEMENTS && this.arrayList.getValue()));
        this.hideInChat = (Setting<Boolean>)this.register(new Setting("HideInChat", true, v -> this.page.getValue() == Page.ELEMENTS && this.arrayList.getValue()));
        this.timer = new Timer();
        this.players = new HashMap<String, Integer>();
        this.command = new Setting<String>("Command", "Mio");
        this.bracketColor = new Setting<TextUtil.Color>("BracketColor", TextUtil.Color.WHITE);
        this.commandColor = new Setting<TextUtil.Color>("NameColor", TextUtil.Color.LIGHT_PURPLE);
        this.commandBracket = new Setting<String>("Bracket", "[");
        this.commandBracket2 = new Setting<String>("Bracket2", "]");
        this.notifyToggles = new Setting<Boolean>("ChatNotify", true, "notifys in chat");
        this.magenDavid = new Setting<Boolean>("gear", false, "1");
        this.animationHorizontalTime = new Setting<Integer>("AnimationHTime", 500, 1, 1000, v -> this.arrayList.getValue());
        this.animationVerticalTime = new Setting<Integer>("AnimationVTime", 50, 1, 500, v -> this.arrayList.getValue());
        this.renderingMode = (Setting<RenderingMode>)this.register(new Setting("Ordering", RenderingMode.Length, v -> this.page.getValue() == Page.GLOBAL));
        this.time = (Setting<Boolean>)this.register(new Setting("Time", false, v -> this.page.getValue() == Page.ELEMENTS));
        this.lagTime = (Setting<Integer>)this.register(new Setting("LagTime", 1000, 0, 2000, v -> this.page.getValue() == Page.GLOBAL));
        this.moduleList = new ArrayList<Module>();
        this.setInstance();
    }
    
    public static HUD getInstance() {
        if (HUD.INSTANCE == null) {
            HUD.INSTANCE = new HUD();
        }
        return HUD.INSTANCE;
    }
    
    private void setInstance() {
        HUD.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedMs(500L)) {
            this.players = this.getTextRadarPlayers();
            this.timer.reset();
        }
        if (this.shouldIncrement) {
            ++this.hitMarkerTimer;
        }
        if (this.hitMarkerTimer == 10) {
            this.hitMarkerTimer = 0;
            this.shouldIncrement = false;
        }
    }
    
    @Override
    public void onRender2D(final Render2DEvent event) {
        if (fullNullCheck()) {
            return;
        }
        final int width = this.renderer.scaledWidth;
        final int height = this.renderer.scaledHeight;
        this.color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue());
        if (this.waterMark.getValue()) {
            final String wm1 = "Mio " + ChatFormatting.WHITE + "v0.4.5" + "+" + HUD.hash;
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(wm1, 2.0f, this.waterMarkY.getValue(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] arrayOfInt = { 1 };
                    final char[] stringToCharArray = wm1.toCharArray();
                    float f = 0.0f;
                    for (final char c : stringToCharArray) {
                        this.renderer.drawString(String.valueOf(c), 2.0f + f, this.waterMarkY.getValue(), ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        f += this.renderer.getStringWidth(String.valueOf(c));
                        ++arrayOfInt[0];
                    }
                }
            }
            else {
                this.renderer.drawString(wm1, 2.0f, this.waterMarkY.getValue(), this.color, true);
            }
            final StringBuilder versionHash = new StringBuilder();
            for (final Module m : Mio.moduleManager.modules) {
                versionHash.append(m.getClass().hashCode());
            }
            HUD.hash = DigestUtils.md5Hex(versionHash.toString()).substring(0, 8);
        }
        if (this.skeetCC.getValue()) {
            if (this.skeetColor.getValue().equals(SkeetColor.RollingRGB)) {
                final int[] arrayOfInt2 = { 1 };
                RenderUtil.drawHGradientRect(0.0f, 0.0f, (float)width, 1.0f, ColorUtil.rainbow(arrayOfInt2[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), ColorUtil.rainbow(20 * ClickGui.getInstance().rainbowHue.getValue()).getRGB());
                ++arrayOfInt2[0];
            }
            else if (this.skeetColor.getValue().equals(SkeetColor.StaticRGB)) {
                RenderUtil.drawLine(0.0f, 0.0f, (float)width, 0.0f, 4.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB());
            }
            else {
                RenderUtil.drawLine(0.0f, 0.0f, (float)width, 0.0f, 4.0f, this.color);
            }
        }
        if (this.textRadar.getValue()) {
            if (this.waterMark.getValue()) {
                this.drawTextRadar(ShulkerPreview.getInstance().isOff() ? 0 : (this.waterMarkY.getValue() + 2));
            }
            else {
                this.drawTextRadar(ShulkerPreview.getInstance().isOff() ? 0 : 2);
            }
        }
        if (this.pvp.getValue()) {
            this.renderPvpInfo();
        }
        this.color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue());
        if (this.waterMark2.getValue()) {
            final String wm2 = "hvhlegend.il";
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(wm2, 2.0f, 170.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] arrayOfInt = { 1 };
                    final char[] stringToCharArray = wm2.toCharArray();
                    float f = 0.0f;
                    for (final char c : stringToCharArray) {
                        this.renderer.drawString(String.valueOf(c), 2.0f, 170.0f, ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        f += this.renderer.getStringWidth(String.valueOf(c));
                        ++arrayOfInt[0];
                    }
                }
            }
            else {
                this.renderer.drawString(wm2, 2.0f, 170.0f, this.color, true);
            }
        }
        final int[] counter1 = { 1 };
        final ScaledResolution res = new ScaledResolution(HUD.mc);
        final boolean inChat = HUD.mc.currentScreen instanceof GuiChat;
        final long enabledMods = Mio.moduleManager.modules.stream().filter(module -> module.isEnabled() && module.isDrawn()).count();
        int j = (inChat && !this.renderingUp.getValue()) ? 14 : 0;
        if (this.arrayList.getValue()) {
            if (this.renderingUp.getValue()) {
                if (inChat && this.hideInChat.getValue()) {
                    this.renderer.drawString(enabledMods + " mods enabled", (float)(width - 2 - this.renderer.getStringWidth(enabledMods + " mods enabled")), (float)(2 + j), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                }
                else if (this.renderingMode.getValue() == RenderingMode.ABC) {
                    for (int k = 0; k < Mio.moduleManager.sortedModulesABC.size(); ++k) {
                        String str = Mio.moduleManager.sortedModulesABC.get(k);
                        if (this.forgeHax.getValue()) {
                            str = Mio.moduleManager.sortedModulesABC.get(k) + ChatFormatting.RESET + "<";
                        }
                        this.renderer.drawString(str, (float)(width - 2 - this.renderer.getStringWidth(str)), (float)(2 + j * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                        ++j;
                        ++counter1[0];
                    }
                }
                else {
                    for (int k = 0; k < Mio.moduleManager.sortedModules.size(); ++k) {
                        final Module module2 = Mio.moduleManager.sortedModules.get(k);
                        String str2 = module2.getDisplayName() + ChatFormatting.GRAY + ((module2.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module2.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                        if (this.forgeHax.getValue()) {
                            str2 = module2.getDisplayName() + ChatFormatting.GRAY + ((module2.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module2.getDisplayInfo() + ChatFormatting.GRAY + "]" + ChatFormatting.RESET + "<") : (ChatFormatting.RESET + "<"));
                        }
                        this.renderer.drawString(str2, (float)(width - 2 - this.renderer.getStringWidth(str2)), (float)(2 + j * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                        ++j;
                        ++counter1[0];
                    }
                }
            }
            else if (inChat && this.hideInChat.getValue()) {
                this.renderer.drawString(enabledMods + " mods enabled", (float)(width - 2 - this.renderer.getStringWidth(enabledMods + " mods enabled")), (float)(height - j - 11), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
            }
            else if (this.renderingMode.getValue() == RenderingMode.ABC) {
                for (int k = 0; k < Mio.moduleManager.sortedModulesABC.size(); ++k) {
                    String str = Mio.moduleManager.sortedModulesABC.get(k);
                    if (this.forgeHax.getValue()) {
                        str = Mio.moduleManager.sortedModulesABC.get(k) + ChatFormatting.RESET + "<";
                    }
                    j += 10;
                    this.renderer.drawString(str, (float)(width - 2 - this.renderer.getStringWidth(str)), (float)(height - j), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
            else {
                for (int k = 0; k < Mio.moduleManager.sortedModules.size(); ++k) {
                    final Module module2 = Mio.moduleManager.sortedModules.get(k);
                    String str2 = module2.getDisplayName() + ChatFormatting.GRAY + ((module2.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module2.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                    if (this.forgeHax.getValue()) {
                        str2 = module2.getDisplayName() + ChatFormatting.GRAY + ((module2.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module2.getDisplayInfo() + ChatFormatting.GRAY + "]" + ChatFormatting.RESET + "<") : (ChatFormatting.RESET + "<"));
                    }
                    j += 10;
                    this.renderer.drawString(str2, (float)(width - 2 - this.renderer.getStringWidth(str2)), (float)(height - j), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
        }
        final String grayString = this.grayNess.getValue() ? String.valueOf(ChatFormatting.GRAY) : "";
        int i = (HUD.mc.currentScreen instanceof GuiChat && this.renderingUp.getValue()) ? 13 : (this.renderingUp.getValue() ? -2 : 0);
        if (this.renderingUp.getValue()) {
            if (this.potions.getValue()) {
                final List<PotionEffect> effects = new ArrayList<PotionEffect>(Minecraft.getMinecraft().player.getActivePotionEffects());
                for (final PotionEffect potionEffect : effects) {
                    final String str3 = Mio.potionManager.getColoredPotionString(potionEffect);
                    i += 10;
                    this.renderer.drawString(str3, (float)(width - this.renderer.getStringWidth(str3) - 2), (float)(height - 2 - i), potionEffect.getPotion().getLiquidColor(), true);
                }
            }
            if (this.speed.getValue()) {
                final String str2 = grayString + "Speed " + ChatFormatting.WHITE + Mio.speedManager.getSpeedKpH() + " km/h";
                i += 10;
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            if (this.time.getValue()) {
                final String str2 = grayString + "Time " + ChatFormatting.WHITE + new SimpleDateFormat("h:mm a").format(new Date());
                i += 10;
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            if (this.tps.getValue()) {
                final String str2 = grayString + "TPS " + ChatFormatting.WHITE + Mio.serverManager.getTPS();
                i += 10;
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            final String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
            final String str4 = grayString + "Ping " + ChatFormatting.WHITE + Mio.serverManager.getPing();
            if (this.renderer.getStringWidth(str4) > this.renderer.getStringWidth(fpsText)) {
                if (this.fps.getValue()) {
                    i += 10;
                    this.renderer.drawString(fpsText, (float)(width - this.renderer.getStringWidth(fpsText) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
            else if (this.fps.getValue()) {
                i += 10;
                this.renderer.drawString(fpsText, (float)(width - this.renderer.getStringWidth(fpsText) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
        }
        else {
            if (this.potions.getValue()) {
                final List<PotionEffect> effects = new ArrayList<PotionEffect>(Minecraft.getMinecraft().player.getActivePotionEffects());
                for (final PotionEffect potionEffect : effects) {
                    final String str3 = Mio.potionManager.getColoredPotionString(potionEffect);
                    this.renderer.drawString(str3, (float)(width - this.renderer.getStringWidth(str3) - 2), (float)(2 + i++ * 10), potionEffect.getPotion().getLiquidColor(), true);
                }
            }
            if (this.speed.getValue()) {
                final String str2 = grayString + "Speed " + ChatFormatting.WHITE + Mio.speedManager.getSpeedKpH() + " km/h";
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            if (this.time.getValue()) {
                final String str2 = grayString + "Time " + ChatFormatting.WHITE + new SimpleDateFormat("h:mm a").format(new Date());
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            if (this.tps.getValue()) {
                final String str2 = grayString + "TPS " + ChatFormatting.WHITE + Mio.serverManager.getTPS();
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            final String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
            final String str4 = grayString + "Ping " + ChatFormatting.WHITE + Mio.serverManager.getPing();
            if (this.renderer.getStringWidth(str4) > this.renderer.getStringWidth(fpsText)) {
                if (this.fps.getValue()) {
                    this.renderer.drawString(fpsText, (float)(width - this.renderer.getStringWidth(fpsText) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
            else if (this.fps.getValue()) {
                this.renderer.drawString(fpsText, (float)(width - this.renderer.getStringWidth(fpsText) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
        }
        final boolean inHell = HUD.mc.world.getBiome(HUD.mc.player.getPosition()).getBiomeName().equals("Hell");
        final int posX = (int)HUD.mc.player.posX;
        final int posY = (int)HUD.mc.player.posY;
        final int posZ = (int)HUD.mc.player.posZ;
        final float nether = inHell ? 8.0f : 0.125f;
        final int hposX = (int)(HUD.mc.player.posX * nether);
        final int hposZ = (int)(HUD.mc.player.posZ * nether);
        final int yawPitch = (int)MathHelper.wrapDegrees(HUD.mc.player.rotationYaw);
        final int p = this.coords.getValue() ? 0 : 11;
        i = ((HUD.mc.currentScreen instanceof GuiChat) ? 14 : 0);
        final String coordinates = "XYZ: " + ChatFormatting.WHITE + (inHell ? (posX + ", " + posY + ", " + posZ + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + hposX + ", " + hposZ + ChatFormatting.RESET + "]" + ChatFormatting.WHITE) : (posX + ", " + posY + ", " + posZ + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + hposX + ", " + hposZ + ChatFormatting.RESET + "]"));
        String direction = this.direction.getValue() ? Mio.rotationManager.getDirection4D(false) : "";
        String yaw = this.direction.getValue() ? ("Yaw: " + ChatFormatting.WHITE + yawPitch) : "";
        final String coords = this.coords.getValue() ? coordinates : "";
        i += 10;
        if (HUD.mc.currentScreen instanceof GuiChat && this.direction.getValue()) {
            yaw = "";
            direction = "Yaw: " + ChatFormatting.WHITE + yawPitch + ChatFormatting.RESET + " " + getFacingDirectionShort();
        }
        if (ClickGui.getInstance().rainbow.getValue()) {
            final String rainbowCoords = this.coords.getValue() ? ("XYZ: " + ChatFormatting.WHITE + (inHell ? (posX + ", " + posY + ", " + posZ + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + hposX + ", " + hposZ + ChatFormatting.RESET + "]" + ChatFormatting.WHITE) : (posX + ", " + posY + ", " + posZ + ChatFormatting.RESET + " [" + ChatFormatting.WHITE + hposX + ", " + hposZ + ChatFormatting.RESET + "]"))) : "";
            if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                this.renderer.drawString(direction, 2.0f, (float)(height - i - 11 + p), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                this.renderer.drawString(yaw, 2.0f, (float)(height - i - 22 + p), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                this.renderer.drawString(rainbowCoords, 2.0f, (float)(height - i), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
            }
            else {
                final int[] counter2 = { 1 };
                final char[] stringToCharArray2 = direction.toCharArray();
                float s = 0.0f;
                for (final char c2 : stringToCharArray2) {
                    this.renderer.drawString(String.valueOf(c2), 2.0f + s, (float)(height - i - 11 + p), ColorUtil.rainbow(counter2[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    s += this.renderer.getStringWidth(String.valueOf(c2));
                    ++counter2[0];
                }
                final int[] counter3 = { 1 };
                final char[] stringToCharArray3 = rainbowCoords.toCharArray();
                float u = 0.0f;
                for (final char c3 : stringToCharArray3) {
                    this.renderer.drawString(String.valueOf(c3), 2.0f + u, (float)(height - i), ColorUtil.rainbow(counter3[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    u += this.renderer.getStringWidth(String.valueOf(c3));
                    ++counter3[0];
                }
                final int[] counter4 = { 1 };
                final char[] stringToCharArray4 = yaw.toCharArray();
                float g = 0.0f;
                for (final char c4 : stringToCharArray4) {
                    this.renderer.drawString(String.valueOf(c4), 2.0f + g, (float)(height - i - 22 + p), ColorUtil.rainbow(counter4[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    g += this.renderer.getStringWidth(String.valueOf(c4));
                    ++counter4[0];
                }
            }
        }
        else {
            this.renderer.drawString(direction, 2.0f, (float)(height - i - 11 + p), this.color, true);
            this.renderer.drawString(yaw, 2.0f, (float)(height - i - 22 + p), this.color, true);
            this.renderer.drawString(coords, 2.0f, (float)(height - i), this.color, true);
        }
        if (this.armor.getValue()) {
            this.renderArmorHUD(true);
        }
        if (this.greeter.getValue()) {
            this.renderGreeter();
        }
        if (this.lag.getValue()) {
            this.renderLag();
        }
    }
    
    public static String getFacingDirectionShort() {
        final int dirnumber = RotationUtil.getDirection4D();
        if (dirnumber == 0) {
            return "(+Z)";
        }
        if (dirnumber == 1) {
            return "(-X)";
        }
        if (dirnumber == 2) {
            return "(-Z)";
        }
        if (dirnumber == 3) {
            return "(+X)";
        }
        return "Loading...";
    }
    
    public Map<String, Integer> getTextRadarPlayers() {
        return EntityUtil.getTextRadarPlayers();
    }
    
    public void renderGreeter() {
        final int width = this.renderer.scaledWidth;
        String text = "Welcome, ";
        if (this.greetermode.getValue().equals(GreeterMode.PLAYER)) {
            if (this.greeter.getValue()) {
                text = text + ChatFormatting.WHITE + HUD.mc.player.getDisplayNameString() + ChatFormatting.RESET + " :')";
            }
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(text, width / 2.0f - this.renderer.getStringWidth(text) / 2.0f + 2.0f, 2.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] counter1 = { 1 };
                    final char[] stringToCharArray = text.toCharArray();
                    float i = 0.0f;
                    for (final char c : stringToCharArray) {
                        this.renderer.drawString(String.valueOf(c), width / 2.0f - this.renderer.getStringWidth(text) / 2.0f + 2.0f + i, 2.0f, ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        i += this.renderer.getStringWidth(String.valueOf(c));
                        ++counter1[0];
                    }
                }
            }
            else {
                this.renderer.drawString(text, width / 2.0f - this.renderer.getStringWidth(text) / 2.0f + 2.0f, 2.0f, this.color, true);
            }
        }
        else {
            String lel = this.welcomer.getValue();
            if (this.greeter.getValue()) {
                lel = this.welcomer.getValue();
            }
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(lel, width / 2.0f - this.renderer.getStringWidth(lel) / 2.0f + 2.0f, 2.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] counter2 = { 1 };
                    final char[] stringToCharArray2 = lel.toCharArray();
                    float j = 0.0f;
                    for (final char c2 : stringToCharArray2) {
                        this.renderer.drawString(String.valueOf(c2), width / 2.0f - this.renderer.getStringWidth(lel) / 2.0f + 2.0f + j, 2.0f, ColorUtil.rainbow(counter2[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        j += this.renderer.getStringWidth(String.valueOf(c2));
                        ++counter2[0];
                    }
                }
            }
            else {
                this.renderer.drawString(lel, width / 2.0f - this.renderer.getStringWidth(lel) / 2.0f + 2.0f, 2.0f, this.color, true);
            }
        }
    }
    
    public void renderLag() {
        final int width = this.renderer.scaledWidth;
        if (Mio.serverManager.isServerNotResponding()) {
            final String text = ChatFormatting.RED + "Server is lagging for " + MathUtil.round(Mio.serverManager.serverRespondingTime() / 1000.0f, 1) + "s.";
            this.renderer.drawString(text, width / 2.0f - this.renderer.getStringWidth(text) / 2.0f + 2.0f, 20.0f, this.color, true);
        }
    }
    
    public void renderArmorHUD(final boolean percent) {
        final int width = this.renderer.scaledWidth;
        final int height = this.renderer.scaledHeight;
        GlStateManager.enableTexture2D();
        final int i = width / 2;
        int iteration = 0;
        final int y = height - 55 - ((HUD.mc.player.isInWater() && HUD.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
        for (final ItemStack is : HUD.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(HUD.mc.fontRenderer, is, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            this.renderer.drawStringWithShadow(s, (float)(x + 19 - 2 - this.renderer.getStringWidth(s)), (float)(y + 9), 16777215);
            if (!percent) {
                continue;
            }
            int dmg = 0;
            final int itemDurability = is.getMaxDamage() - is.getItemDamage();
            final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
            final float red = 1.0f - green;
            if (percent) {
                dmg = 100 - (int)(red * 100.0f);
            }
            else {
                dmg = itemDurability;
            }
            this.renderer.drawStringWithShadow(dmg + "", (float)(x + 8 - this.renderer.getStringWidth(dmg + "") / 2), (float)(y - 11), ColorUtil.toRGBA((int)(red * 255.0f), (int)(green * 255.0f), 0));
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
    
    public void renderPvpInfo() {
        final String fwOn = "AFW:" + ChatFormatting.GREEN + " 1";
        final String fwOff = "AFW:" + ChatFormatting.DARK_RED + " 0";
        int totems1 = HUD.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (HUD.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            totems1 += HUD.mc.player.getHeldItemOffhand().getCount();
        }
        final String tots = "" + ChatFormatting.GREEN + totems1;
        final int ping1 = Mio.serverManager.getPing();
        final String arOn = "AR:" + ChatFormatting.GREEN + " 1";
        final String arOff = "AR:" + ChatFormatting.DARK_RED + " 0";
        String color1;
        if (ping1 < 40) {
            color1 = TextUtil.GREEN;
        }
        else if (ping1 < 65) {
            color1 = TextUtil.DARK_GREEN;
        }
        else if (ping1 < 80) {
            color1 = TextUtil.YELLOW;
        }
        else if (ping1 < 110) {
            color1 = TextUtil.RED;
        }
        else if (ping1 < 160) {
            color1 = TextUtil.DARK_RED;
        }
        else {
            color1 = TextUtil.DARK_RED;
        }
        String colorSafe;
        if (!EntityUtil.isSafe((Entity)HUD.mc.player, 0, true, false)) {
            colorSafe = TextUtil.DARK_RED;
        }
        else if (!EntityUtil.isSafe((Entity)HUD.mc.player, -1, false, false)) {
            colorSafe = TextUtil.YELLOW;
        }
        else {
            colorSafe = TextUtil.GREEN;
        }
        final String safe = "" + colorSafe + "Safe";
        final String ping2 = "" + color1 + ping1 + " MS";
        if (Mio.moduleManager.getModuleByName("AutoFeetWeb").isEnabled()) {
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(fwOn, 2.0f, 190.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] arrayOfInt = { 1 };
                    final char[] stringToCharArray = fwOn.toCharArray();
                    float f = 0.0f;
                    final char[] var13 = stringToCharArray;
                    for (int var14 = stringToCharArray.length, var15 = 0; var15 < var14; ++var15) {
                        final char c = var13[var15];
                        this.renderer.drawString(String.valueOf(c), 2.0f + f, 190.0f, ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        f += this.renderer.getStringWidth(String.valueOf(c));
                        final int var16 = arrayOfInt[0]++;
                    }
                }
            }
            else {
                this.renderer.drawString(fwOn, 2.0f, 190.0f, this.color, true);
            }
        }
        if (ClickGui.getInstance().rainbow.getValue()) {
            if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                this.renderer.drawString(safe, 2.0f, 220.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
            }
            else {
                final int[] arrayOfInt = { 1 };
                final char[] stringToCharArray = safe.toCharArray();
                float f = 0.0f;
                final char[] var13 = stringToCharArray;
                for (int var14 = stringToCharArray.length, var15 = 0; var15 < var14; ++var15) {
                    final char c = var13[var15];
                    this.renderer.drawString(String.valueOf(c), 2.0f + f, 220.0f, ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    f += this.renderer.getStringWidth(String.valueOf(c));
                    final int var16 = arrayOfInt[0]++;
                }
            }
        }
        else {
            this.renderer.drawString(safe, 2.0f, 220.0f, this.color, true);
        }
        if (ClickGui.getInstance().rainbow.getValue()) {
            if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                this.renderer.drawString(tots, 2.0f, 210.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
            }
            else {
                final int[] arrayOfInt = { 1 };
                final char[] stringToCharArray = tots.toCharArray();
                float f = 0.0f;
                final char[] var13 = stringToCharArray;
                for (int var14 = stringToCharArray.length, var15 = 0; var15 < var14; ++var15) {
                    final char c = var13[var15];
                    this.renderer.drawString(String.valueOf(c), 2.0f + f, 210.0f, ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    f += this.renderer.getStringWidth(String.valueOf(c));
                    final int var16 = arrayOfInt[0]++;
                }
            }
        }
        else {
            this.renderer.drawString(tots, 2.0f, 210.0f, this.color, true);
        }
        if (ClickGui.getInstance().rainbow.getValue()) {
            if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                this.renderer.drawString(ping2, 2.0f, 200.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
            }
            else {
                final int[] arrayOfInt = { 1 };
                final char[] stringToCharArray = ping2.toCharArray();
                float f = 0.0f;
                final char[] var13 = stringToCharArray;
                for (int var14 = stringToCharArray.length, var15 = 0; var15 < var14; ++var15) {
                    final char c = var13[var15];
                    this.renderer.drawString(String.valueOf(c), 2.0f + f, 200.0f, ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    f += this.renderer.getStringWidth(String.valueOf(c));
                    final int var16 = arrayOfInt[0]++;
                }
            }
        }
        else {
            this.renderer.drawString(ping2, 2.0f, 200.0f, this.color, true);
        }
        if (Mio.moduleManager.getModuleByName("AntiRegear").isEnabled()) {
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(arOn, 2.0f, 180.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] arrayOfInt = { 1 };
                    final char[] stringToCharArray = arOn.toCharArray();
                    float f = 0.0f;
                    final char[] var13 = stringToCharArray;
                    for (int var14 = stringToCharArray.length, var15 = 0; var15 < var14; ++var15) {
                        final char c = var13[var15];
                        this.renderer.drawString(String.valueOf(c), 2.0f + f, 180.0f, ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        f += this.renderer.getStringWidth(String.valueOf(c));
                        final int var16 = arrayOfInt[0]++;
                    }
                }
            }
            else {
                this.renderer.drawString(arOn, 2.0f, 180.0f, this.color, true);
            }
        }
        if (Mio.moduleManager.getModuleByName("AutoFeetWeb").isDisabled()) {
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(fwOff, 2.0f, 190.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] arrayOfInt = { 1 };
                    final char[] stringToCharArray = fwOff.toCharArray();
                    float f = 0.0f;
                    final char[] var13 = stringToCharArray;
                    for (int var14 = stringToCharArray.length, var15 = 0; var15 < var14; ++var15) {
                        final char c = var13[var15];
                        this.renderer.drawString(String.valueOf(c), 2.0f + f, 190.0f, ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        f += this.renderer.getStringWidth(String.valueOf(c));
                        final int var16 = arrayOfInt[0]++;
                    }
                }
            }
            else {
                this.renderer.drawString(fwOff, 2.0f, 190.0f, this.color, true);
            }
        }
        if (Mio.moduleManager.getModuleByName("AntiRegear").isDisabled()) {
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(arOff, 2.0f, 180.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] arrayOfInt = { 1 };
                    final char[] stringToCharArray = arOff.toCharArray();
                    float f = 0.0f;
                    final char[] var13 = stringToCharArray;
                    for (int var14 = stringToCharArray.length, var15 = 0; var15 < var14; ++var15) {
                        final char c = var13[var15];
                        this.renderer.drawString(String.valueOf(c), 2.0f + f, 180.0f, ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        f += this.renderer.getStringWidth(String.valueOf(c));
                        final int var16 = arrayOfInt[0]++;
                    }
                }
            }
            else {
                this.renderer.drawString(arOff, 2.0f, 180.0f, this.color, true);
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final AttackEntityEvent event) {
        this.shouldIncrement = true;
    }
    
    @Override
    public void onLoad() {
        Mio.commandManager.setClientMessage(this.getCommandMessage());
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        if (event.getStage() == 2 && this.equals(event.getSetting().getFeature())) {
            Mio.commandManager.setClientMessage(this.getCommandMessage());
        }
    }
    
    public String getCommandMessage() {
        return TextUtil.coloredString(this.commandBracket.getPlannedValue(), this.bracketColor.getPlannedValue()) + TextUtil.coloredString(this.command.getPlannedValue(), this.commandColor.getPlannedValue()) + TextUtil.coloredString(this.commandBracket2.getPlannedValue(), this.bracketColor.getPlannedValue());
    }
    
    public String getRawCommandMessage() {
        return this.commandBracket.getValue() + this.command.getValue() + this.commandBracket2.getValue();
    }
    
    public void drawTextRadar(final int yOffset) {
        if (!this.players.isEmpty()) {
            int y = this.renderer.getFontHeight() + 7 + yOffset;
            for (final Map.Entry<String, Integer> player : this.players.entrySet()) {
                final String text = player.getKey() + " ";
                final int textheight = this.renderer.getFontHeight() + 1;
                if (ClickGui.getInstance().rainbow.getValue()) {
                    if (ClickGui.getInstance().rainbowModeHud.getValue() != ClickGui.rainbowMode.Static) {
                        continue;
                    }
                    this.renderer.drawString(text, 2.0f, (float)y, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    y += textheight;
                }
                else {
                    this.renderer.drawString(text, 2.0f, (float)y, this.color, true);
                    y += textheight;
                }
            }
        }
    }
    
    static {
        box = new ResourceLocation("textures/gui/container/shulker_box.png");
        totem = new ItemStack(Items.TOTEM_OF_UNDYING);
        HUD.INSTANCE = new HUD();
    }
    
    private enum SkeetColor
    {
        RollingRGB, 
        StaticRGB, 
        Single;
    }
    
    public enum RenderingMode
    {
        Length, 
        ABC;
    }
    
    public enum GreeterMode
    {
        PLAYER, 
        CUSTOM;
    }
    
    public enum Page
    {
        ELEMENTS, 
        GLOBAL;
    }
}
