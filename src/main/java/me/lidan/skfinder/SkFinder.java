package me.lidan.skfinder;

import me.lidan.skfinder.commands.SkFindCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import me.lidan.skfinder.utils.ColorUtils;

public final class SkFinder extends JavaPlugin {

    public static final String PREFIX = ColorUtils.trans("&7[&6SkFinder&7]&r");
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        commandHandler = BukkitCommandHandler.create(this);
        commandHandler.register(new SkFindCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SkFinder getInstance(){
        return SkFinder.getPlugin(SkFinder.class);
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
