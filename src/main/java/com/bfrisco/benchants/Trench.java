package com.bfrisco.benchants;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Trench extends JavaPlugin implements Listener, CommandExecutor {
    private final Set<Material> ALLOWED_ITEMS = new HashSet<>();
    private final Set<Material> ENCHANTABLE_ITEMS = new HashSet<>();
    private final Set<Location> IGNORE_LOCATIONS = new HashSet<>();

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        loadConfig();

        getCommand("benchants").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (IGNORE_LOCATIONS.contains(event.getBlock().getLocation())) {
            IGNORE_LOCATIONS.remove(event.getBlock().getLocation());
            return;
        }

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (!hasTrench(item)) return;

        for (Block block : getNearbyBlocks(event.getBlock().getLocation(), 1)) {
            if (block.getLocation().equals(event.getBlock().getLocation())) {
                continue;
            }

            if (ALLOWED_ITEMS.contains(block.getType())) {
                IGNORE_LOCATIONS.add(block.getLocation());
                BlockBreakEvent e = new BlockBreakEvent(block, event.getPlayer());
                Bukkit.getPluginManager().callEvent(e);
                if (!e.isCancelled()) {
                    if (!hasSilkTouch(item)) {
                        dropExperience(block);
                    }
                    block.breakNaturally(item);
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (!player.hasPermission("benchants.enchant")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (args.length == 0) return false;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (!ENCHANTABLE_ITEMS.contains(item.getType())) {
            player.sendMessage(ChatColor.RED + "Must be a valid item.");
            return true;
        }

        getLogger().info(Arrays.toString(args));

        if ("reload".equalsIgnoreCase(args[0])) {
            getLogger().info("Reloading config...");
            reloadConfig();
            loadConfig();
            player.sendMessage(ChatColor.GREEN + "Successfully reloaded config.");
            return true;
        }

        if ("apply".equalsIgnoreCase(args[0])) {
            if (args.length != 2) return false;
            if (!"trench".equalsIgnoreCase(args[1])) {
                player.sendMessage(ChatColor.RED + "Unknown enchant.");
                return true;
            }

            if (hasTrench(item)) {
                player.sendMessage(ChatColor.RED + "That item is already enchanted.");
                return true;
            }

            getLogger().info("Enchanting item with trench...");
            NBTItem nbti = new NBTItem(item);
            nbti.setBoolean("trench", Boolean.TRUE);
            nbti.applyNBT(item);
            player.sendMessage(ChatColor.GREEN + "Successfully enchanted item with Trench.");
            return true;
        }

        if ("remove".equalsIgnoreCase(args[0])) {
            if (args.length != 2) return false;
            if (!"trench".equalsIgnoreCase(args[1])) {
                player.sendMessage(ChatColor.RED + "Unknown enchant.");
                return true;
            }

            if (!hasTrench(item)) {
                player.sendMessage(ChatColor.RED + "That item does not have that enchantment.");
                return true;
            }

            getLogger().info("Removing trench enchantment from item...");
            NBTItem nbti = new NBTItem(item);
            nbti.setBoolean("trench", Boolean.FALSE);
            nbti.applyNBT(item);
            player.sendMessage(ChatColor.GREEN + "Successfully removed trench enchantment from item.");
            return true;
        }

        return false;
    }

    public void dropExperience(Block block) {
        int experience = switch (block.getType()) {
            case COAL_ORE -> getRandomNumber(0, 2);
            case NETHER_GOLD_ORE -> getRandomNumber(0, 1);
            case DIAMOND_ORE, EMERALD_ORE -> getRandomNumber(3, 7);
            case LAPIS_LAZULI, NETHER_QUARTZ_ORE -> getRandomNumber(2, 5);
            case REDSTONE_ORE -> getRandomNumber(1, 5);
            default -> 0;
        };

        getLogger().info("Experience for " + block.getType() + ": " + experience);

        if (experience > 0) {
            block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public boolean hasTrench(ItemStack item) {
        if (!ENCHANTABLE_ITEMS.contains(item.getType())) return false;
        NBTItem nbti = new NBTItem(item);
        if (!nbti.hasNBTData()) return false;
        return nbti.getBoolean("trench");
    }

    public boolean hasSilkTouch(ItemStack itemStack) {
        return itemStack.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
    }

    public List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }

    public void loadConfig() {
        ConfigurationSection trench = getConfig().getConfigurationSection("trench");
        if (trench == null) {
            getLogger().warning("Trench configuration not found!");
            return;
        }

        List<String> items = trench.getStringList("destroyable-items");

        if (items.size() == 0) {
            getLogger().warning("No destroyable-items found in config.");
        }

        for (String item : items) {
            try {
                ALLOWED_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                getLogger().warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }

        List<String> enchantableItems = trench.getStringList("enchantable-items");

        if (items.size() == 0) {
            getLogger().warning("No enchantable-items found in config.");
        }

        for (String item : enchantableItems) {
            try {
                ENCHANTABLE_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                getLogger().warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
    }
}
