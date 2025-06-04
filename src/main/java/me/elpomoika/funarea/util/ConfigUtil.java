package me.elpomoika.funarea.util;

import me.elpomoika.funarea.FunArea;
import org.bukkit.ChatColor;

public class ConfigUtil {
    private final FunArea plugin;

    public ConfigUtil(FunArea plugin) {
        this.plugin = plugin;
    }

    public String getCommand() {
        return plugin.getConfig().getString("menu.sword.command");
    }

    public String getNonFullNetheriteMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.non-full-netherite-armor"));
    }
}
