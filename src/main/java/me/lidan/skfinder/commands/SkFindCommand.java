package me.lidan.skfinder.commands;

import me.lidan.skfinder.SkFinder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Command({"skfind","skriptfind", "filefind", "skfinder"})
@CommandPermission("skript.admin")
public class SkFindCommand {

    private final SkFinder plugin = SkFinder.getInstance();
    private final File skriptFolder = new File(plugin.getDataFolder().getParentFile(), "Skript/scripts");

    private final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + "SkFinder" + ChatColor.GRAY + "] " + ChatColor.RESET;

    @Subcommand("search")
    public void searchFiles(CommandSender sender, String query){
        searchFiles(sender, skriptFolder, query, false);
    }

    public void searchFiles(CommandSender sender, File folder, String query, boolean caseSensitive) {
        if (!folder.exists() || !folder.isDirectory()){
            sender.sendMessage(String.format("Folder %s not", folder.getPath()));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                sender.sendMessage(PREFIX + ChatColor.WHITE + "Searching for " + ChatColor.GOLD + query + ChatColor.WHITE + "...");
                int count = 0;
                File[] files = folder.listFiles();
                assert files != null;
                for (final File file : files) {
                    if (!file.isFile()) continue;
                    BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                    int lineNumber = 1;
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!caseSensitive) line = line.toLowerCase();
                        if (line.contains(query)) {
                            line = line.trim();
                            line = line.replace(query, ChatColor.YELLOW + query + ChatColor.GRAY);
                            sender.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Line " + lineNumber + ": " + ChatColor.GRAY + "(" + file.getName() + ")");
                            sender.sendMessage(ChatColor.GOLD + "    Line: " + ChatColor.GRAY + line);
                            count++;
                        }
                        lineNumber++;
                    }
                    reader.close();
                }
                if (count == 0) {
                    sender.sendMessage(PREFIX + ChatColor.RED + "Failed to find " + ChatColor.GOLD + query);
                }
                else{
                    sender.sendMessage(PREFIX + "Successfully found " + ChatColor.GOLD + query + ChatColor.GREEN + " " + count + ChatColor.RESET + " times");
                }
            } catch (IOException e) {
                sender.sendMessage(PREFIX + "Encountered ERROR while searching! see console for logs.");
                e.printStackTrace();
            }
        });
    }
}
