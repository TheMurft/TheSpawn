package com.themurft.thespawn;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MessageService {
    private static final Pattern HEX = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private final TheSpawnPlugin plugin;
    private String prefix;

    public MessageService(TheSpawnPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        prefix = color(plugin.getConfig().getString("messages.prefix", ""));
    }

    public void send(CommandSender target, String key) {
        send(target, key, Map.of());
    }

    public void send(CommandSender target, String key, Map<String, String> placeholders) {
        String raw = plugin.getConfig().getString("messages." + key);
        if (raw == null || raw.isBlank()) return;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            raw = raw.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        target.sendMessage(prefix + color(raw));
    }

    public static String color(String input) {
        if (input == null) return "";
        Matcher matcher = HEX.matcher(input);
        StringBuffer output = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder replacement = new StringBuilder("§x");
            for (char character : hex.toCharArray()) replacement.append('§').append(character);
            matcher.appendReplacement(output, Matcher.quoteReplacement(replacement.toString()));
        }
        matcher.appendTail(output);
        return ChatColor.translateAlternateColorCodes('&', output.toString());
    }
}
