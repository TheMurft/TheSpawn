package com.themurft.thespawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class JoinQuitMessageListener implements Listener {
    private final TheSpawnPlugin plugin;

    public JoinQuitMessageListener(TheSpawnPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getConfig().getBoolean("join-quit-messages.hide-vanilla", true)) {
            event.setJoinMessage(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("join-quit-messages.hide-vanilla", true)) {
            event.setQuitMessage(null);
        }
    }
}
