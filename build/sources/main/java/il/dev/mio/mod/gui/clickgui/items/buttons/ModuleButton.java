//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.gui.clickgui.items.buttons;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import il.dev.mio.mod.gui.clickgui.Component;
import com.mojang.realmsclient.gui.ChatFormatting;
import il.dev.mio.mod.gui.MioClickGui;
import il.dev.mio.Mio;
import il.dev.mio.mod.modules.client.FontMod;
import il.dev.mio.mod.modules.client.ClickGui;
import java.util.Iterator;
import il.dev.mio.mod.gui.clickgui.setting.Bind;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import java.util.ArrayList;
import il.dev.mio.mod.gui.clickgui.items.Item;
import java.util.List;
import il.dev.mio.mod.modules.Module;

public class ModuleButton extends Button
{
    private final Module module;
    private List<Item> items;
    private boolean subOpen;
    
    public ModuleButton(final Module module) {
        super(module.getName());
        this.items = new ArrayList<Item>();
        this.module = module;
        this.initSettings();
    }
    
    public void initSettings() {
        final ArrayList<Item> newItems = new ArrayList<Item>();
        if (!this.module.getSettings().isEmpty()) {
            for (final Setting setting : this.module.getSettings()) {
                if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled")) {
                    newItems.add(new BooleanButton(setting));
                }
                if (setting.getValue() instanceof Bind && !setting.getName().equalsIgnoreCase("Keybind") && !this.module.getName().equalsIgnoreCase("Hud")) {
                    newItems.add(new BindButton(setting));
                }
                if ((setting.getValue() instanceof String || setting.getValue() instanceof Character) && !setting.getName().equalsIgnoreCase("displayName")) {
                    newItems.add(new StringButton(setting));
                }
                if (setting.isNumberSetting() && setting.hasRestriction()) {
                    newItems.add(new Slider(setting));
                }
                else {
                    if (!setting.isEnumSetting()) {
                        continue;
                    }
                    newItems.add(new EnumButton(setting));
                }
            }
        }
        newItems.add(new BindButton(this.module.getSettingByName("Keybind")));
        this.items = newItems;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!this.items.isEmpty()) {
            if (ClickGui.getInstance().gear.getValue()) {
                if (this.subOpen) {
                    if (FontMod.getInstance().isOn()) {
                        if (this.module.isEnabled()) {
                            Mio.textManager.drawStringWithShadow("-", this.x - 1.5f + this.width - 7.4f + 1.0f, this.y - 2.2f - MioClickGui.getClickGui().getTextOffset(), -1);
                        }
                        else {
                            Mio.textManager.drawStringWithShadow(ChatFormatting.GRAY + "-", this.x - 1.5f + this.width - 7.4f + 1.0f, this.y - 2.2f - MioClickGui.getClickGui().getTextOffset(), -1);
                        }
                    }
                    else if (this.module.isEnabled()) {
                        Mio.textManager.drawStringWithShadow("-", this.x - 1.5f + this.width - 7.4f, this.y - 2.2f - MioClickGui.getClickGui().getTextOffset(), -1);
                    }
                    else {
                        Mio.textManager.drawStringWithShadow(ChatFormatting.GRAY + "-", this.x - 1.5f + this.width - 7.4f, this.y - 2.2f - MioClickGui.getClickGui().getTextOffset(), -1);
                    }
                }
                else if (this.module.isEnabled()) {
                    Mio.textManager.drawStringWithShadow("+", this.x - 1.5f + this.width - 7.4f, this.y - 2.2f - MioClickGui.getClickGui().getTextOffset(), -1);
                }
                else {
                    Mio.textManager.drawStringWithShadow(ChatFormatting.GRAY + "+", this.x - 1.5f + this.width - 7.4f, this.y - 2.2f - MioClickGui.getClickGui().getTextOffset(), -1);
                }
            }
            if (this.subOpen) {
                float height = 1.0f;
                for (final Item item : this.items) {
                    ++Component.counter1[0];
                    if (!item.isHidden()) {
                        item.setLocation(this.x + 1.0f, this.y + (height += 15.0f));
                        item.setHeight(15);
                        item.setWidth(this.width - 9);
                        item.drawScreen(mouseX, mouseY, partialTicks);
                    }
                    item.update();
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!this.items.isEmpty()) {
            if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
                this.subOpen = !this.subOpen;
                ModuleButton.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
            if (this.subOpen) {
                for (final Item item : this.items) {
                    if (item.isHidden()) {
                        continue;
                    }
                    item.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    @Override
    public void onKeyTyped(final char typedChar, final int keyCode) {
        super.onKeyTyped(typedChar, keyCode);
        if (!this.items.isEmpty() && this.subOpen) {
            for (final Item item : this.items) {
                if (item.isHidden()) {
                    continue;
                }
                item.onKeyTyped(typedChar, keyCode);
            }
        }
    }
    
    @Override
    public int getHeight() {
        if (this.subOpen) {
            int height = 14;
            for (final Item item : this.items) {
                if (item.isHidden()) {
                    continue;
                }
                height += item.getHeight() + 1;
            }
            return height + 2;
        }
        return 14;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    @Override
    public void toggle() {
        this.module.toggle();
    }
    
    @Override
    public boolean getState() {
        return this.module.isEnabled();
    }
}
