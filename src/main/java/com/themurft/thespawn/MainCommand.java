package com.themurft.thespawn;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class MainCommand implements CommandExecutor {
    private final TheSpawnPlugin plugin;

    public MainCommand(TheSpawnPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("thespawn.reload")) {
            plugin.messages().send(sender, "no-permission");
            return true;
        }
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            plugin.messages().send(sender, "usage-main");
            return true;
        }
        plugin.reloadPlugin();
        plugin.messages().send(sender, "reloaded");
        return true;
    }
}
