package com.bfrisco.benchants.enchants;

import com.bfrisco.benchants.BEnchants;
import com.bfrisco.benchants.utils.ChargeManagement;
import com.bfrisco.benchants.utils.ItemInfo;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Tree implements Listener {
    public static final Set<Material> ALLOWED_ITEMS = new HashSet<>();
    public static final Set<Material> ENCHANTABLE_ITEMS = new HashSet<>();
    public static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();
    public static final Material axe = Material.DIAMOND_AXE;

    public Tree() {
        loadConfig();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (IGNORE_LOCATIONS.contains(event.getBlock().getLocation())) {
            IGNORE_LOCATIONS.remove(event.getBlock().getLocation());
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (!ItemInfo.isTitanTool(item)) return;
        Bukkit.getServer().getConsoleSender().sendMessage("Is a titantool " + ItemInfo.isTitanTool(item));
        Bukkit.getServer().getConsoleSender().sendMessage("Is active " + ItemInfo.isActive(item));
        Bukkit.getServer().getConsoleSender().sendMessage("Is activeCharge " + ItemInfo.isActiveCharge(item));
        Bukkit.getServer().getConsoleSender().sendMessage("Is hasCharge " + ItemInfo.hasCharge(item));

        if (!ItemInfo.isActive(item) && !ItemInfo.isActiveCharge(item)) return;
        if (item.getType() != axe) return;
        ChargeManagement.decreaseChargeLore(item,player);




        for (Block block : getNearbyBlocks(event.getBlock().getLocation())) {
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



    public static void apply(ItemStack item, Player player) {
        if (!ENCHANTABLE_ITEMS.contains(item.getType())) {
            player.sendMessage(ChatColor.RED + "That item cannot be enchanted with Tree.");
            return;
        }

        if (hasTree(item)) {
            player.sendMessage(ChatColor.RED + "That item is already enchanted with Tree.");
            return;
        }

        BEnchants.LOGGER.info(player.getName() + " has enchanted item with Tree...");
        /*NBTItem nbti = new NBTItem(item);
        nbti.setBoolean("Tree", Boolean.TRUE);
        nbti.applyNBT(item);*/
        player.sendMessage(ChatColor.GREEN + "Successfully enchanted item with Tree.");
    }

    public static void remove(ItemStack item, Player player) {
        if (!hasTree(item)) {
            player.sendMessage(ChatColor.RED + "That item is not enchanted with Tree.");
            return;
        }

        BEnchants.LOGGER.info(player + "has removed Tree enchantment from item...");
/*        NBTItem nbti = new NBTItem(item);
        nbti.setBoolean("Tree", Boolean.FALSE);
        nbti.applyNBT(item);*/
        player.sendMessage(ChatColor.GREEN + "Successfully removed Tree enchantment from item.");
    }

    public static boolean hasTree(ItemStack item) {
        if (!ENCHANTABLE_ITEMS.contains(item.getType())) return false;
        NBTItem nbti = new NBTItem(item);
        if (!nbti.hasNBTData()) return false;
        return nbti.getBoolean("Tree");
    }

    public static void loadConfig() {
        ENCHANTABLE_ITEMS.clear();

        ConfigurationSection Tree = BEnchants.PLUGIN.getConfig().getConfigurationSection("tree");
        if (Tree == null) {
            BEnchants.LOGGER.warning("Tree configuration not found!");
            return;
        }

        List<String> items = Tree.getStringList("destroyable-items");

        if (items.size() == 0) {
            BEnchants.LOGGER.warning("No destroyable-items found in Tree section of config.");
        }

        for (String item : items) {
            try {
                ALLOWED_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                BEnchants.LOGGER.warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }

        List<String> enchantableItems = Tree.getStringList("enchantable-items");

        if (items.size() == 0) {
            BEnchants.LOGGER.warning("No enchantable-items found in Tree section of config.");
        }

        for (String item : enchantableItems) {
            try {
                ENCHANTABLE_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                BEnchants.LOGGER.warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
    }

    private void dropExperience(Block block) {
        int experience = switch (block.getType()) {
            case COAL_ORE -> getRandomNumber(0, 2);
            case NETHER_GOLD_ORE -> getRandomNumber(0, 1);
            case DIAMOND_ORE, EMERALD_ORE -> getRandomNumber(3, 7);
            case LAPIS_LAZULI, NETHER_QUARTZ_ORE -> getRandomNumber(2, 5);
            case REDSTONE_ORE -> getRandomNumber(1, 5);
            default -> 0;
        };

        if (experience > 0) {
            block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
        }
    }

    private boolean hasSilkTouch(ItemStack itemStack) {
        return itemStack.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
    }

    private static List<Block> getNearbyBlocks(Location location) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - 5; x <= location.getBlockX() + 5; x++) {
            for (int y = location.getBlockY() - 5; y <= location.getBlockY() + 5; y++) {
                for (int z = location.getBlockZ() - 5; z <= location.getBlockZ() + 5; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}

