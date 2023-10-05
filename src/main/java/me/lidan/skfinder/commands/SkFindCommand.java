package me.lidan.skfinder.commands;

import me.lidan.skfinder.SkFinder;
import net.md_5.bungee.api.ChatColor;
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

    @Subcommand("search")
    public void searchFiles(CommandSender sender, String query){
        searchFiles(sender, skriptFolder, query, false);
    }

    public void searchFiles(CommandSender sender, File folder, String query, boolean caseSensitive) {
        if (!folder.exists() || !folder.isDirectory()){
            sender.sendMessage(String.format("Folder %s not", folder.getPath()));
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {

                try {
                    sender.sendMessage(String.format("[FileFind] Searching for %s in folder %s", query, folder.getPath()));
                    int count = 0;
                    File[] files = folder.listFiles();
                    assert files != null;
                    for (final File fileEntry : files) {
                        BufferedReader reader = new BufferedReader(new FileReader(fileEntry.getAbsolutePath()));
                        int lines = 1;
                        String s;
                        while ((s = reader.readLine()) != null) {
                            if (!caseSensitive) s = s.toLowerCase();
                            if (s.contains(query)) {
                                s = s.replace(query, ChatColor.RED + query + ChatColor.RESET);
                                sender.sendMessage("[FileFind] " + fileEntry.getName() + " " + s + " at line " + lines);
                                plugin.getLogger().info("[FileFind] " + fileEntry.getName() + " " + s + " at line " + lines);
                                count++;
                            }
                            lines++;
                        }
                        reader.close();
                    }
                    if (count == 0) {
                        sender.sendMessage("[FileFind] couldn't find " + query);
                    }
                    else{
                        sender.sendMessage("[FileFind] Found " + query + " " + count + " times");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
}
