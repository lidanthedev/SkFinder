package me.lidan.skfinder.commands;

import me.lidan.skfinder.SkFinder;
import me.lidan.skfinder.search.SearchResult;
import me.lidan.skfinder.search.Searcher;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import me.lidan.skfinder.utils.ColorUtils;

import java.io.File;
import java.io.IOException;

@Command({"skfind","skriptfind", "filefind", "skfinder"})
@CommandPermission("skript.admin")
public class SkFindCommand {

    private final SkFinder plugin = SkFinder.getInstance();

    @Subcommand("search")
    public void searchFiles(CommandSender sender, String query){
        Searcher searcher = new Searcher(query, false);
        if (!searcher.dirExists()){
            message(sender, ChatColor.RED + String.format("Folder %s doesn't exists!", searcher.getBaseDir().getPath()));
            return;
        }
        message(sender, ColorUtils.trans("Searching for &6" + query + "&f..."));
        try {
            searcher.search(searchResults -> {
                if (searchResults.isEmpty()) {
                    message(sender, ColorUtils.trans("&4Failed to find &6" + query);
                    return;
                }
                for (SearchResult searchResult : searchResults) {
                    searchResult.format().forEach(sender::sendMessage);
                }
                message(sender, ColorUtils.trans(("Successfully found &6" + query + "&a" + searchResults.size() + " &rtimes");
            });
        }
        catch (RuntimeException e){
            message(sender, ColorUtils.trans("&4Encountered ERROR while searching! see console for logs."));
            e.printStackTrace();
        }
    }

    public void message(CommandSender sender, String msg){
        sender.sendMessage(SkFinder.PREFIX + msg);
    }
}
