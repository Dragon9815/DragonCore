package net.dragon9815.dragoncore.helpers;

import net.minecraft.util.StatCollector;

public class StringHelper {
    public static final String BLACK = "�0";
    public static final String BLUE = "�1";
    public static final String GREEN = "�2";
    public static final String TEAL = "�3";
    public static final String RED = "�4";
    public static final String PURPLE = "�5";
    public static final String ORANGE = "�6";
    public static final String LIGHT_GRAY = "�7";
    public static final String GRAY = "�8";
    public static final String LIGHT_BLUE = "�9";
    public static final String BRIGHT_GREEN = "�a";
    public static final String BRIGHT_BLUE = "�b";
    public static final String LIGHT_RED = "�c";
    public static final String PINK = "�d";
    public static final String YELLOW = "�e";
    public static final String WHITE = "�f";
    public static final String OBFUSCATED = "�k";
    public static final String BOLD = "�l";
    public static final String STRIKETHROUGH = "�m";
    public static final String UNDERLINE = "�n";
    public static final String ITALIC = "�o";
    public static final String END = "�r";

    public static String localize(String unlocalizedString) {
        return StatCollector.translateToLocal(unlocalizedString);
    }
}
