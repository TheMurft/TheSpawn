package com.themurft.thespawn;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class SpawnCommand implements CommandExecutor {
    private final TheSpawnPlugin plugin;

    public SpawnCommand(TheSpawnPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("thespawn.spawn")) {
            plugin.messages().send(sender, "no-permission");
            return true;
        }

        if (!(sender instanceof Player player)) {
            plugin.messages().send(sender, "players-only");
            return true;
        }
        if (!plugin.spawnManager().startTeleport(player)) {
            plugin.messages().send(sender, "spawn-not-set");
        }
        return true;
    }
}
