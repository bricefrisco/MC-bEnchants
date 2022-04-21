package com.bfrisco.benchants;

import com.bfrisco.benchants.commands.Commands;
import com.bfrisco.benchants.commands.PlayerCommands;

import com.bfrisco.benchants.enchants.Durability;
import com.bfrisco.benchants.enchants.Trench;
import com.bfrisco.benchants.utils.Toggle;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class BEnchants extends JavaPlugin {
    public static Logger LOGGER;
    public static FileConfiguration CONFIG;
    public static Plugin PLUGIN;

    public BEnchants() {
        PLUGIN = this;
        LOGGER = getLogger();
        CONFIG = getConfig();
    }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "ITS WORKINGGG ITTTS WOOOORKINNNGGG");
        Bukkit.getPluginManager().registerEvents(new Trench(), this);
        Bukkit.getPluginManager().registerEvents(new Durability(), this);
        Bukkit.getPluginManager().registerEvents(new Toggle(), this);

        Objects.requireNonNull(getCommand("benchants")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("ancient")).setExecutor(new PlayerCommands());

    }
}
