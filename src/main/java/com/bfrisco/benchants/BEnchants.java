package com.bfrisco.benchants;

import com.bfrisco.benchants.commands.Commands;
import com.bfrisco.benchants.commands.PlayerCommands;

import com.bfrisco.benchants.enchants.*;
import com.bfrisco.benchants.enchants.TitanAxe.Axe;
import com.bfrisco.benchants.enchants.TitanPickSilk.PickSilk;
import com.bfrisco.benchants.enchants.TitanPickSilk.ToggleChargedPickSilk;
import com.bfrisco.benchants.enchants.TitanPickSilk.ToggleImbuedPickSilk;
import com.bfrisco.benchants.enchants.TitanRod.Rod;
import com.bfrisco.benchants.enchants.TitanRod.ToggleChargedRod;
import com.bfrisco.benchants.enchants.TitanRod.ToggleImbuedRod;
import com.bfrisco.benchants.enchants.TitanShovel.Shovel;
import com.bfrisco.benchants.enchants.TitanShovel.ToggleChargedShovel;
import com.bfrisco.benchants.enchants.TitanShovel.ToggleImbuedShovel;
import com.bfrisco.benchants.misc.SuperMan;
import com.bfrisco.benchants.utils.ChargeManagement;
import com.bfrisco.benchants.utils.PowerCrystalAdd;
import com.bfrisco.benchants.utils.ToggleImbuedItem;
import com.bfrisco.benchants.utils.ToggleChargedItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
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

    public static BEnchants getInstance() {
        return getPlugin(BEnchants.class);
    }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "bEnchants  E N A B L E D  with emphasis!!!!!!");
        Bukkit.getPluginManager().registerEvents(new Trench(), this);
        Bukkit.getPluginManager().registerEvents(new Axe(), this);

        Bukkit.getPluginManager().registerEvents(new PickSilk(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleChargedPickSilk(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleImbuedPickSilk(), this);

        Bukkit.getPluginManager().registerEvents(new Shovel(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleChargedShovel(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleImbuedShovel(), this);

        Bukkit.getPluginManager().registerEvents(new Durability(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleImbuedItem(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleChargedItem(), this);

        Bukkit.getPluginManager().registerEvents(new Rod(),this);
        Bukkit.getPluginManager().registerEvents(new ToggleChargedRod(),this);
        Bukkit.getPluginManager().registerEvents(new ToggleImbuedRod(),this);

        Bukkit.getPluginManager().registerEvents(new ChargeManagement(),this);
        Bukkit.getPluginManager().registerEvents(new SuperMan(),this);

        Bukkit.getPluginManager().registerEvents(new PowerCrystalAdd(),this);

/*        Bukkit.getPluginManager().registerEvents(new HoverBoots(),this);*/

        Objects.requireNonNull(getCommand("benchants")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("ancient")).setExecutor(new PlayerCommands());


    }
}
