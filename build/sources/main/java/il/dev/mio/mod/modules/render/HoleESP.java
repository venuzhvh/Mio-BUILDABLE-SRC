//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.render;

import java.awt.Color;
import il.dev.mio.api.util.render.ColorUtil;
import il.dev.mio.mod.modules.client.ClickGui;
import il.dev.mio.api.util.render.RenderUtil;
import il.dev.mio.api.util.interact.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import il.dev.mio.api.event.events.Render3DEvent;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class HoleESP extends Module
{
    private final Setting<Page> page;
    public Setting<Boolean> renderOwn;
    public Setting<Boolean> fov;
    public Setting<Boolean> rainbow;
    private final Setting<Integer> range;
    private final int rangeY = 5;
    public Setting<Boolean> box;
    public Setting<Boolean> gradientBox;
    public Setting<Boolean> invertGradientBox;
    public Setting<Boolean> outline;
    public Setting<Boolean> gradientOutline;
    public Setting<Boolean> invertGradientOutline;
    private final Setting<Boolean> wireframe;
    private final Setting<WireframeMode> wireframeMode;
    public Setting<Double> height;
    private Setting<Integer> boxAlpha;
    private Setting<Float> lineWidth;
    public Setting<Boolean> pulse;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Integer> alpha;
    public Setting<Boolean> safeColor;
    private Setting<Integer> safeRed;
    private Setting<Integer> safeGreen;
    private Setting<Integer> safeBlue;
    private Setting<Integer> safeAlpha;
    public Setting<Boolean> customOutline;
    private Setting<Integer> cRed;
    private Setting<Integer> cGreen;
    private Setting<Integer> cBlue;
    private Setting<Integer> cAlpha;
    private Setting<Integer> safecRed;
    private Setting<Integer> safecGreen;
    private Setting<Integer> safecBlue;
    private Setting<Integer> safecAlpha;
    private static HoleESP INSTANCE;
    private int dynamicAlpha;
    private int dynamicBoxAlpha;
    private int dynamicSafeAlpha;
    private int dynamicCAlpha;
    private int dynamicSafeCAlpha;
    private int currentAlpha;
    
    public HoleESP() {
        super("HoleESP", "Shows safe spots", Category.RENDER, false, false, false);
        this.page = (Setting<Page>)this.register(new Setting("Settings", Page.GLOBAL));
        this.renderOwn = new Setting<Boolean>("RenderOwn", true, v -> this.page.getValue() == Page.GLOBAL);
        this.fov = (Setting<Boolean>)this.register(new Setting("FovOnly", true, v -> this.page.getValue() == Page.GLOBAL));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", false, v -> this.page.getValue() == Page.COLORS));
        this.range = (Setting<Integer>)this.register(new Setting("Range", 5, 0, 25, v -> this.page.getValue() == Page.GLOBAL));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", true, v -> this.page.getValue() == Page.GLOBAL));
        this.gradientBox = (Setting<Boolean>)this.register(new Setting("Fade", false, v -> this.box.getValue() && this.page.getValue() == Page.GLOBAL));
        this.invertGradientBox = (Setting<Boolean>)this.register(new Setting("ReverseBox", false, v -> this.gradientBox.getValue() && this.page.getValue() == Page.GLOBAL));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", true, v -> this.page.getValue() == Page.GLOBAL));
        this.gradientOutline = (Setting<Boolean>)this.register(new Setting("FadeOutline", false, v -> this.outline.getValue() && this.page.getValue() == Page.GLOBAL));
        this.invertGradientOutline = (Setting<Boolean>)this.register(new Setting("ReverseOL", false, v -> this.gradientOutline.getValue() && this.page.getValue() == Page.GLOBAL));
        this.wireframe = (Setting<Boolean>)this.register(new Setting("Wireframe", true, v -> this.page.getValue() == Page.GLOBAL));
        this.wireframeMode = (Setting<WireframeMode>)this.register(new Setting("WireframeMode", WireframeMode.Full, v -> this.page.getValue() == Page.GLOBAL && this.wireframe.getValue()));
        this.height = (Setting<Double>)this.register(new Setting("Height", (-1.1), (-2.0), 2.0, v -> this.page.getValue() == Page.GLOBAL));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", 80, 0, 255, v -> this.box.getValue() && this.page.getValue() == Page.GLOBAL));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", 0.5f, 0.1f, 5.0f, v -> this.outline.getValue() && this.page.getValue() == Page.GLOBAL));
        this.pulse = (Setting<Boolean>)this.register(new Setting("PulseAlpha", false, v -> this.page.getValue() == Page.GLOBAL));
        this.red = (Setting<Integer>)this.register(new Setting("ObbyRed", 150, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.green = (Setting<Integer>)this.register(new Setting("ObbyGreen", 0, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.blue = (Setting<Integer>)this.register(new Setting("ObbyBlue", 0, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", 200, 0, 255, v -> this.page.getValue() == Page.COLORS));
        this.safeColor = new Setting<Boolean>("BedrockColor", true, v -> this.page.getValue() == Page.COLORS);
        this.safeRed = (Setting<Integer>)this.register(new Setting("BedrockRed", 0, 0, 255, v -> this.safeColor.getValue() && this.page.getValue() == Page.COLORS));
        this.safeGreen = (Setting<Integer>)this.register(new Setting("BedrockGreen", 150, 0, 255, v -> this.safeColor.getValue() && this.page.getValue() == Page.COLORS));
        this.safeBlue = (Setting<Integer>)this.register(new Setting("BedrockBlue", 0, 0, 255, v -> this.safeColor.getValue() && this.page.getValue() == Page.COLORS));
        this.safeAlpha = (Setting<Integer>)this.register(new Setting("Alpha", 255, 0, 255, v -> this.safeColor.getValue() && this.page.getValue() == Page.COLORS));
        this.customOutline = (Setting<Boolean>)this.register(new Setting("CustomLine", false, v -> this.outline.getValue() && this.page.getValue() == Page.COLORS));
        this.cRed = (Setting<Integer>)this.register(new Setting("OL-ORed", 255, 0, 255, v -> this.customOutline.getValue() && this.outline.getValue() && this.page.getValue() == Page.COLORS));
        this.cGreen = (Setting<Integer>)this.register(new Setting("OL-OGreen", 0, 0, 255, v -> this.customOutline.getValue() && this.outline.getValue() && this.page.getValue() == Page.COLORS));
        this.cBlue = (Setting<Integer>)this.register(new Setting("OL-OBlue", 0, 0, 255, v -> this.customOutline.getValue() && this.outline.getValue() && this.page.getValue() == Page.COLORS));
        this.cAlpha = (Setting<Integer>)this.register(new Setting("OL-OAlpha", 255, 0, 255, v -> this.customOutline.getValue() && this.outline.getValue() && this.page.getValue() == Page.COLORS));
        this.safecRed = (Setting<Integer>)this.register(new Setting("OL-BRed", 0, 0, 255, v -> this.customOutline.getValue() && this.outline.getValue() && this.safeColor.getValue() && this.page.getValue() == Page.COLORS));
        this.safecGreen = (Setting<Integer>)this.register(new Setting("OL-BGreen", 255, 0, 255, v -> this.customOutline.getValue() && this.outline.getValue() && this.safeColor.getValue() && this.page.getValue() == Page.COLORS));
        this.safecBlue = (Setting<Integer>)this.register(new Setting("OL-BBlue", 0, 0, 255, v -> this.customOutline.getValue() && this.outline.getValue() && this.safeColor.getValue() && this.page.getValue() == Page.COLORS));
        this.safecAlpha = (Setting<Integer>)this.register(new Setting("OL-BAlpha", 255, 0, 255, v -> this.customOutline.getValue() && this.outline.getValue() && this.safeColor.getValue() && this.page.getValue() == Page.COLORS));
        this.dynamicAlpha = 0;
        this.dynamicBoxAlpha = 0;
        this.dynamicSafeAlpha = 0;
        this.dynamicCAlpha = 0;
        this.dynamicSafeCAlpha = 0;
        this.currentAlpha = 0;
        this.setInstance();
    }
    
    private void setInstance() {
        HoleESP.INSTANCE = this;
    }
    
    public static HoleESP getInstance() {
        if (HoleESP.INSTANCE == null) {
            HoleESP.INSTANCE = new HoleESP();
        }
        return HoleESP.INSTANCE;
    }
    
    @Override
    public void onUpdate() {
        if (this.pulse.getValue()) {
            this.doPulse();
        }
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        assert HoleESP.mc.renderViewEntity != null;
        final Vec3i playerPos = new Vec3i(HoleESP.mc.renderViewEntity.posX, HoleESP.mc.renderViewEntity.posY, HoleESP.mc.renderViewEntity.posZ);
        for (int x = playerPos.getX() - this.range.getValue(); x < playerPos.getX() + this.range.getValue(); ++x) {
            for (int z = playerPos.getZ() - this.range.getValue(); z < playerPos.getZ() + this.range.getValue(); ++z) {
                BlockPos pos = null;
                for (int y = playerPos.getY() + 5; y > playerPos.getY() - 5; --y) {
                    pos = new BlockPos(x, y, z);
                    if (HoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && (!pos.equals((Object)new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ)) || this.renderOwn.getValue())) {
                        if (BlockUtil.isPosInFov(pos) || !this.fov.getValue()) {
                            if (HoleESP.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) {
                                if (this.wireframe.getValue()) {
                                    if (this.wireframeMode.getValue().equals(WireframeMode.Flat)) {
                                        if (this.customOutline.getValue()) {
                                            RenderUtil.drawBlockWireframeBottom(pos, this.lineWidth.getValue(), this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicSafeCAlpha : ((int)this.safecAlpha.getValue()));
                                            RenderUtil.drawBlockWireframeBottom2(pos, this.lineWidth.getValue(), this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicSafeCAlpha : ((int)this.safecAlpha.getValue()));
                                        }
                                        else {
                                            RenderUtil.drawBlockWireframeBottom(pos, this.lineWidth.getValue(), this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicSafeAlpha : ((int)this.safeAlpha.getValue()));
                                            RenderUtil.drawBlockWireframeBottom2(pos, this.lineWidth.getValue(), this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicSafeAlpha : ((int)this.safeAlpha.getValue()));
                                        }
                                    }
                                    else if (this.customOutline.getValue()) {
                                        RenderUtil.drawBlockWireframe(pos, this.height.getValue(), this.lineWidth.getValue(), this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicSafeCAlpha : ((int)this.safecAlpha.getValue()));
                                        RenderUtil.drawBlockWireframe2(pos, this.height.getValue(), this.lineWidth.getValue(), this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicSafeCAlpha : ((int)this.safecAlpha.getValue()));
                                    }
                                    else {
                                        RenderUtil.drawBlockWireframe(pos, this.height.getValue(), this.lineWidth.getValue(), this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicSafeAlpha : ((int)this.safeAlpha.getValue()));
                                        RenderUtil.drawBlockWireframe2(pos, this.height.getValue(), this.lineWidth.getValue(), this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicSafeAlpha : ((int)this.safeAlpha.getValue()));
                                    }
                                }
                                RenderUtil.drawBoxESP(pos, ((boolean)this.rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), this.pulse.getValue() ? this.dynamicSafeAlpha : this.safeAlpha.getValue()), this.customOutline.getValue(), new Color(this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), this.pulse.getValue() ? this.dynamicSafeCAlpha : this.safecAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicBoxAlpha : ((int)this.boxAlpha.getValue()), true, this.height.getValue(), this.gradientBox.getValue(), this.gradientOutline.getValue(), this.invertGradientBox.getValue(), this.invertGradientOutline.getValue(), this.currentAlpha);
                            }
                            else if (BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.down()).getBlock()) && BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.east()).getBlock()) && BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.west()).getBlock()) && BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.south()).getBlock())) {
                                if (BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.north()).getBlock())) {
                                    if (this.wireframe.getValue()) {
                                        if (this.wireframeMode.getValue().equals(WireframeMode.Flat)) {
                                            if (this.customOutline.getValue()) {
                                                RenderUtil.drawBlockWireframeBottom(pos, this.lineWidth.getValue(), this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicCAlpha : ((int)this.cAlpha.getValue()));
                                                RenderUtil.drawBlockWireframeBottom2(pos, this.lineWidth.getValue(), this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicCAlpha : ((int)this.cAlpha.getValue()));
                                            }
                                            else {
                                                RenderUtil.drawBlockWireframeBottom(pos, this.lineWidth.getValue(), this.red.getValue(), this.green.getValue(), this.blue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicAlpha : ((int)this.alpha.getValue()));
                                                RenderUtil.drawBlockWireframeBottom2(pos, this.lineWidth.getValue(), this.red.getValue(), this.green.getValue(), this.blue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicAlpha : ((int)this.alpha.getValue()));
                                            }
                                        }
                                        else if (this.customOutline.getValue()) {
                                            RenderUtil.drawBlockWireframe(pos, this.height.getValue(), this.lineWidth.getValue(), this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicCAlpha : ((int)this.cAlpha.getValue()));
                                            RenderUtil.drawBlockWireframe2(pos, this.height.getValue(), this.lineWidth.getValue(), this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicCAlpha : ((int)this.cAlpha.getValue()));
                                        }
                                        else {
                                            RenderUtil.drawBlockWireframe(pos, this.height.getValue(), this.lineWidth.getValue(), this.red.getValue(), this.green.getValue(), this.blue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicAlpha : ((int)this.alpha.getValue()));
                                            RenderUtil.drawBlockWireframe2(pos, this.height.getValue(), this.lineWidth.getValue(), this.red.getValue(), this.green.getValue(), this.blue.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicAlpha : ((int)this.alpha.getValue()));
                                        }
                                    }
                                    RenderUtil.drawBoxESP(pos, ((boolean)this.rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.pulse.getValue() ? this.dynamicAlpha : this.alpha.getValue()), this.customOutline.getValue(), new Color(this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), this.pulse.getValue() ? this.dynamicCAlpha : this.cAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), ((boolean)this.pulse.getValue()) ? this.dynamicBoxAlpha : ((int)this.boxAlpha.getValue()), true, this.height.getValue(), this.gradientBox.getValue(), this.gradientOutline.getValue(), this.invertGradientBox.getValue(), this.invertGradientOutline.getValue(), this.currentAlpha);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void doPulse() {
        final float[] tick = { System.currentTimeMillis() % 4320L / 4320.0f };
        final int currentcolor = Color.HSBtoRGB(tick[0], 0.5f, 0.5f);
        this.dynamicAlpha = (currentcolor >> 16 & 0xFF);
        this.dynamicBoxAlpha = (currentcolor >> 16 & 0xFF);
        this.dynamicSafeAlpha = (currentcolor >> 16 & 0xFF);
        this.dynamicCAlpha = (currentcolor >> 16 & 0xFF);
        this.dynamicSafeCAlpha = (currentcolor >> 16 & 0xFF);
    }
    
    static {
        HoleESP.INSTANCE = new HoleESP();
    }
    
    public enum WireframeMode
    {
        Flat, 
        Full;
    }
    
    public enum Page
    {
        COLORS, 
        GLOBAL;
    }
}
