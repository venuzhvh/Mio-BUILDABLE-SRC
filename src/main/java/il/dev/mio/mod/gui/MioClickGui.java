//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.gui;

import java.io.IOException;
import org.lwjgl.input.Mouse;
import java.util.Iterator;
import il.dev.mio.mod.gui.clickgui.items.Item;
import java.util.function.Function;
import java.util.Comparator;
import il.dev.mio.mod.ModuleCore;
import il.dev.mio.mod.gui.clickgui.items.buttons.Button;
import il.dev.mio.mod.gui.clickgui.items.buttons.ModuleButton;
import il.dev.mio.mod.modules.Module;
import il.dev.mio.Mio;
import il.dev.mio.mod.gui.clickgui.Component;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class MioClickGui extends GuiScreen
{
    private static MioClickGui MioClickGui;
    private static MioClickGui INSTANCE;
    private final ArrayList<Component> components;
    
    public MioClickGui() {
        this.components = new ArrayList<Component>();
        this.setInstance();
        this.load();
    }
    
    public static MioClickGui getInstance() {
        if (il.dev.mio.mod.gui.MioClickGui.INSTANCE == null) {
            il.dev.mio.mod.gui.MioClickGui.INSTANCE = new MioClickGui();
        }
        return il.dev.mio.mod.gui.MioClickGui.INSTANCE;
    }
    
    public static MioClickGui getClickGui() {
        final MioClickGui mioClickGui = il.dev.mio.mod.gui.MioClickGui.MioClickGui;
        return getInstance();
    }
    
    private void setInstance() {
        il.dev.mio.mod.gui.MioClickGui.INSTANCE = this;
    }
    
    private void load() {
        int x = -84;
        for (final Module.Category category : Mio.moduleManager.getCategories()) {
            final String name = category.getName();
            x += 90;
            components.add(new Component(name, x, 4, true) {
                @Override
                public void setupItems() {
                    counter1 = new int[] { 1 };
                    Mio.moduleManager.getModulesByCategory(category).forEach(module -> {
                        if (!module.hidden) {
                            this.addButton(new ModuleButton(module));
                        }
                    });
                }
            });
        }
        this.components.forEach(components -> components.getItems().sort(Comparator.comparing((Function<? super Item, ? extends Comparable>)ModuleCore::getName)));
    }
    
    public void updateModule(final Module module) {
        for (final Component component : this.components) {
            for (final Item item : component.getItems()) {
                if (!(item instanceof ModuleButton)) {
                    continue;
                }
                final ModuleButton button = (ModuleButton)item;
                final Module mod = button.getModule();
                if (module == null) {
                    continue;
                }
                if (!module.equals(mod)) {
                    continue;
                }
                button.initSettings();
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.checkMouseWheel();
        this.drawDefaultBackground();
        this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        this.components.forEach(components -> components.mouseReleased(mouseX, mouseY, releaseButton));
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public final ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void checkMouseWheel() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            this.components.forEach(component -> component.setY(component.getY() - 10));
        }
        else if (dWheel > 0) {
            this.components.forEach(component -> component.setY(component.getY() + 10));
        }
    }
    
    public int getTextOffset() {
        return -6;
    }
    
    public Component getComponentByName(final String name) {
        for (final Component component : this.components) {
            if (!component.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return component;
        }
        return null;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.components.forEach(component -> component.onKeyTyped(typedChar, keyCode));
    }
    
    public void onGuiClosed() {
        try {
            super.onGuiClosed();
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        il.dev.mio.mod.gui.MioClickGui.INSTANCE = new MioClickGui();
    }
}
