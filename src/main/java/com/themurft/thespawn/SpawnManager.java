package com.themurft.thespawn;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public final class SpawnManager implements Listener {
    private final TheSpawnPlugin plugin;
    private final File file;
    private YamlConfiguration data;
    private final Map<UUID, Warmup> warmups = new HashMap<>();

    public SpawnManager(TheSpawnPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "spawn.yml");
        reload();
    }

    public void reload() {
        data = YamlConfiguration.loadConfiguration(file);
    }

    public void setSpawn(Location location) {
        data.set("spawn", location);
        try {
            data.save(file);
        } catch (IOException exception) {
            plugin.getLogger().log(Level.SEVERE, "Could not save spawn.yml", exception);
        }
    }

    public boolean startTeleport(Player player) {
        Location spawn = data.getLocation("spawn");
        if (spawn == null || spawn.getWorld() == null) return false;

        cancel(player, false);
        int seconds = Math.max(0, plugin.getConfig().getInt("settings.teleport-delay-seconds", 3));
        if (seconds == 0) {
            player.teleportAsync(spawn).thenAccept(success -> {
                if (success && player.isOnline()) plugin.messages().send(player, "teleported");
            });
            return true;
        }

        plugin.messages().send(player, "warmup", Map.of("seconds", String.valueOf(seconds)));
        Location origin = player.getLocation().clone();
        BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            warmups.remove(player.getUniqueId());
            if (!player.isOnline()) return;
            player.teleportAsync(spawn).thenAccept(success -> {
                if (success && player.isOnline()) plugin.messages().send(player, "teleported");
            });
        }, seconds * 20L);
        warmups.put(player.getUniqueId(), new Warmup(origin, task));
        return true;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo() == null) return;
        Warmup warmup = warmups.get(event.getPlayer().getUniqueId());
        if (warmup == null || !warmup.origin.getWorld().equals(event.getTo().getWorld())) return;
        double tolerance = Math.max(0, plugin.getConfig().getDouble("settings.movement-tolerance", 0.2));
        if (warmup.origin.distanceSquared(event.getTo()) > tolerance * tolerance) cancel(event.getPlayer(), true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) cancel(player, true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        cancel(event.getPlayer(), false);
    }

    public void cancelAll() {
        warmups.values().forEach(warmup -> warmup.task.cancel());
        warmups.clear();
    }

    private void cancel(Player player, boolean notify) {
        Warmup warmup = warmups.remove(player.getUniqueId());
        if (warmup == null) return;
        warmup.task.cancel();
        if (notify) plugin.messages().send(player, "cancelled");
    }

    private record Warmup(Location origin, BukkitTask task) {}
}
