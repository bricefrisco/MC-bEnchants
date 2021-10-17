package com.bfrisco.benchants;

import com.bfrisco.benchants.commands.Commands;
import com.bfrisco.benchants.enchants.Durability;
import com.bfrisco.benchants.enchants.Trench;
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

        Bukkit.getPluginManager().registerEvents(new Trench(), this);
        Bukkit.getPluginManager().registerEvents(new Durability(), this);

        Objects.requireNonNull(getCommand("benchants")).setExecutor(new Commands());
    }
}
