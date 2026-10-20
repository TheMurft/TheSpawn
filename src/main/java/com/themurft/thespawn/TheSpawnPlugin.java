package com.themurft.thespawn;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheSpawnPlugin extends JavaPlugin {
    private SpawnManager spawnManager;
    private MessageService messages;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        messages = new MessageService(this);
        spawnManager = new SpawnManager(this);

        registerCommand("spawn", new SpawnCommand(this));
        registerCommand("setspawn", new SetSpawnCommand(this));
        registerCommand("thespawn", new MainCommand(this));
        getServer().getPluginManager().registerEvents(spawnManager, this);
        getServer().getPluginManager().registerEvents(new JoinQuitMessageListener(this), this);
    }

    private void registerCommand(String name, org.bukkit.command.CommandExecutor executor) {
        PluginCommand command = getCommand(name);
        if (command == null) {
            throw new IllegalStateException("Command not declared in plugin.yml: " + name);
        }
        command.setExecutor(executor);
    }

    public void reloadPlugin() {
        spawnManager.cancelAll();
        reloadConfig();
        messages.reload();
        spawnManager.reload();
    }

    @Override
    public void onDisable() {
        if (spawnManager != null) spawnManager.cancelAll();
    }

    public SpawnManager spawnManager() {
        return spawnManager;
    }

    public MessageService messages() {
        return messages;
    }
}
