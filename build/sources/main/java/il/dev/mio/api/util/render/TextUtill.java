// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.util.render;

import java.util.Random;
import java.util.regex.Pattern;

public class TextUtill
{
    public static final String SECTIONSIGN = "§";
    public static final String BLACK = "§0";
    public static final String DARK_BLUE = "§1";
    public static final String DARK_GREEN = "§2";
    public static final String DARK_AQUA = "§3";
    public static final String DARK_RED = "§4";
    public static final String DARK_PURPLE = "§5";
    public static final String GOLD = "§6";
    public static final String GRAY = "§7";
    public static final String DARK_GRAY = "§8";
    public static final String BLUE = "§9";
    public static final String GREEN = "§a";
    public static final String AQUA = "§b";
    public static final String RED = "§c";
    public static final String LIGHT_PURPLE = "§d";
    public static final String YELLOW = "§e";
    public static final String WHITE = "§f";
    public static final String OBFUSCATED = "§k";
    public static final String BOLD = "§l";
    public static final String STRIKE = "§m";
    public static final String UNDERLINE = "§n";
    public static final String ITALIC = "§o";
    public static final String RESET = "§r";
    public static final String blank = " \u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592";
    public static final String line1 = " \u2588\u2588\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588";
    public static final String line2 = " \u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2592";
    public static final String line3 = " \u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2588\u2588";
    public static final String line4 = " \u2588\u2592\u2592\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2592\u2592\u2588";
    public static final String line5 = " \u2588\u2592\u2592\u2592\u2588\u2592\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588";
    public static final String pword = "  \u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\n \u2588\u2588\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\n \u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2592\n \u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2588\u2588\n \u2588\u2592\u2592\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2588\u2592\u2592\u2592\u2588\n \u2588\u2592\u2592\u2592\u2588\u2592\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\n \u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592";
    private static final Pattern STRIP_COLOR_PATTERN;
    private static final Random rand;
    public static String shrug;
    
    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }
        return TextUtill.STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }
    
    public static String coloredString(final String string, final Color color) {
        String coloredString = string;
        switch (color) {
            case AQUA: {
                coloredString = "§b" + coloredString + "§r";
                break;
            }
            case WHITE: {
                coloredString = "§f" + coloredString + "§r";
                break;
            }
            case BLACK: {
                coloredString = "§0" + coloredString + "§r";
                break;
            }
            case DARK_BLUE: {
                coloredString = "§1" + coloredString + "§r";
                break;
            }
            case DARK_GREEN: {
                coloredString = "§2" + coloredString + "§r";
                break;
            }
            case DARK_AQUA: {
                coloredString = "§3" + coloredString + "§r";
                break;
            }
            case DARK_RED: {
                coloredString = "§4" + coloredString + "§r";
                break;
            }
            case DARK_PURPLE: {
                coloredString = "§5" + coloredString + "§r";
                break;
            }
            case GOLD: {
                coloredString = "§6" + coloredString + "§r";
                break;
            }
            case DARK_GRAY: {
                coloredString = "§8" + coloredString + "§r";
                break;
            }
            case GRAY: {
                coloredString = "§7" + coloredString + "§r";
                break;
            }
            case BLUE: {
                coloredString = "§9" + coloredString + "§r";
                break;
            }
            case RED: {
                coloredString = "§c" + coloredString + "§r";
                break;
            }
            case GREEN: {
                coloredString = "§a" + coloredString + "§r";
                break;
            }
            case LIGHT_PURPLE: {
                coloredString = "§d" + coloredString + "§r";
                break;
            }
            case YELLOW: {
                coloredString = "§e" + coloredString + "§r";
                break;
            }
        }
        return coloredString;
    }
    
    public static String cropMaxLengthMessage(final String s, final int i) {
        String output = "";
        if (s.length() >= 256 - i) {
            output = s.substring(0, 256 - i);
        }
        return output;
    }
    
    static {
        STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
        rand = new Random();
        TextUtill.shrug = "¯\\_(\u30c4)_/¯";
    }
    
    public enum Color
    {
        NONE, 
        WHITE, 
        BLACK, 
        DARK_BLUE, 
        DARK_GREEN, 
        DARK_AQUA, 
        DARK_RED, 
        DARK_PURPLE, 
        GOLD, 
        GRAY, 
        DARK_GRAY, 
        BLUE, 
        GREEN, 
        AQUA, 
        RED, 
        LIGHT_PURPLE, 
        YELLOW;
    }
}
