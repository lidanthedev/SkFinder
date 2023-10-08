package me.lidan.skfinder.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {
    public static String trans(String str) {
        return ChatColor.translateAlternativeColorCodes('&', str);
    }
}
