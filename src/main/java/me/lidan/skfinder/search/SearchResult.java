package me.lidan.skfinder.search;

import net.md_5.bungee.api.ChatColor;
import me.lidan.skfinder.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private String line;
    private int lineNumber;
    private String filePath;

    public SearchResult(String line, int lineNumber, String filePath) {
        this.line = line;
        this.lineNumber = lineNumber;
        this.filePath = filePath;
    }

    public List<String> format(){
        List<String> list = new ArrayList<>();
        list.add(ColorUtils.trans("&6&lLine " + lineNumber + ": " + "&7(" + filePath + ")"));
        list.add(ColorUtils.trans("&6Line" + line + ": &7" + line));
        return list;
    }
}
