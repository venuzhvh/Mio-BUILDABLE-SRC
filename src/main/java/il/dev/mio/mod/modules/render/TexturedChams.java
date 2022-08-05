// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.render;

import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class TexturedChams extends Module
{
    public static Setting<Integer> red;
    public static Setting<Integer> green;
    public static Setting<Integer> blue;
    public static Setting<Integer> alpha;
    
    public TexturedChams() {
        super("TexturedChams", "hi yes", Category.RENDER, true, false, true);
        TexturedChams.red = (Setting<Integer>)this.register(new Setting("Red", 168, 0, 255));
        TexturedChams.green = (Setting<Integer>)this.register(new Setting("Green", 0, 0, 255));
        TexturedChams.blue = (Setting<Integer>)this.register(new Setting("Blue", 232, 0, 255));
        TexturedChams.alpha = (Setting<Integer>)this.register(new Setting("Alpha", 150, 0, 255));
    }
}
