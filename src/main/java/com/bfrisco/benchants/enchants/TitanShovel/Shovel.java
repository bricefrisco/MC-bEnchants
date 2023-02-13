package com.bfrisco.benchants.enchants.TitanShovel;

import com.bfrisco.benchants.BEnchants;
import com.bfrisco.benchants.utils.ChargeManagement;
import com.bfrisco.benchants.utils.ItemInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Shovel implements Listener {
    public static final Set<Material> DISALLOWED_ITEMS = new HashSet<>();
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();

    public Shovel() {
        loadConfig();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void titanShovelBreakBlock(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (!event.getAction().isLeftClick()) return;
        if (!ShovelInfo.isRunnableShovel(event.getPlayer().getInventory().getItemInMainHand())) return;
        IGNORE_LOCATIONS.clear(); //Strange bug would occur with sand and gravel if IGNORE_LOCATIONS wasn't cleared
        Player player = event.getPlayer();
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        player.sendMessage("Inside shovel enchant");
        if (IGNORE_LOCATIONS.contains(event.getClickedBlock().getLocation())) {
            IGNORE_LOCATIONS.remove(event.getClickedBlock().getLocation());
            return;
        }
        Block clickedBlock = event.getClickedBlock();
        if (ItemInfo.isLevelOne(item)) {
            if (clickedBlock.getType() == Material.BEDROCK) return;
            if (clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.SHULKER_BOX) {
                clickedBlock.breakNaturally(item);
                ChargeManagement.decreaseChargeLore(item, player);
                return;
            }
            if (DISALLOWED_ITEMS.contains(clickedBlock.getType())) return;
            BlockBreakEvent e = new BlockBreakEvent(clickedBlock, event.getPlayer());
            Bukkit.getPluginManager().callEvent(e);
            if (!e.isCancelled()) {
                clickedBlock.setType(Material.AIR);
                ChargeManagement.decreaseChargeLore(item, player);
            }


        } else if (ItemInfo.isLevelTwo(item)){
            BlockFace blockFace = event.getBlockFace();
            if (clickedBlock.getType() == Material.BEDROCK) return;
            if (clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.SHULKER_BOX) {
                clickedBlock.breakNaturally(item);
            }
            BlockBreakEvent e = new BlockBreakEvent(clickedBlock, event.getPlayer());
            Bukkit.getPluginManager().callEvent(e);
            if (e.isCancelled()) return;
            if (!e.isCancelled()) {
                clickedBlock.setType(Material.AIR);
            }
            ChargeManagement.decreaseChargeLore(item, player);
            for (Block blockLoop : getNearbyBlocks2(clickedBlock.getLocation(), blockFace)) {
                if (blockLoop.getLocation().equals(clickedBlock.getLocation())) {
                    continue;
                }
                if (!DISALLOWED_ITEMS.contains(blockLoop.getType())) {
                    IGNORE_LOCATIONS.add(blockLoop.getLocation());
                    e = new BlockBreakEvent(blockLoop, event.getPlayer());
                    Bukkit.getPluginManager().callEvent(e);
                    if (!e.isCancelled()) {
                        blockLoop.setType(Material.AIR);

                    }
                }
            }

        } else if (ItemInfo.isLevelThree(item)){
            BlockFace blockFace = event.getBlockFace();
            if (clickedBlock.getType() == Material.BEDROCK) return;
            if (clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.SHULKER_BOX) {
                clickedBlock.breakNaturally(item);
            }
            BlockBreakEvent e = new BlockBreakEvent(clickedBlock, event.getPlayer());
            Bukkit.getPluginManager().callEvent(e);
            if (e.isCancelled()) return;
            if (!e.isCancelled()) {
                clickedBlock.setType(Material.AIR);
            }
            ChargeManagement.decreaseChargeLore2(item, player);
            for (Block blockLoop : getNearbyBlocks3(clickedBlock.getLocation(), blockFace)) {
                if (blockLoop.getLocation().equals(clickedBlock.getLocation())) {
                    continue;
                }
                if (!DISALLOWED_ITEMS.contains(blockLoop.getType())) {
                    IGNORE_LOCATIONS.add(blockLoop.getLocation());
                    e = new BlockBreakEvent(blockLoop, event.getPlayer());
                    Bukkit.getPluginManager().callEvent(e);
                    if (!e.isCancelled()) {
                        blockLoop.setType(Material.AIR);

                    }
                }
            }
        }
    }
    public static void loadConfig() {
        ConfigurationSection titanShovel = BEnchants.PLUGIN.getConfig().getConfigurationSection("titanShovel");
        if (titanShovel == null) {
            BEnchants.LOGGER.warning("TitanShovel configuration not found!");
            return;
        }
        List<String> items = titanShovel.getStringList("non-destroyable-items");
        if (items.size() == 0) {
            BEnchants.LOGGER.warning("No destroyable-items found in titanShovel section of config.");
        }
        for (String item : items) {
            try {
                DISALLOWED_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                BEnchants.LOGGER.warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
    }

    private static List<Block> getNearbyBlocks2(Location location,BlockFace blockFace) {
        List<Block> blocks = new ArrayList<>();

        if (blockFace.getModY() != 0){
            int y = location.getBlockY();
            for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModX() !=0){
            int x = location.getBlockX();
            for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModZ() !=0){
            int z = location.getBlockZ();
            for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
                for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    private static List<Block> getNearbyBlocks3(Location location,BlockFace blockFace) {
        List<Block> blocks = new ArrayList<>();
        if (blockFace.getModY() != 0){
            int y = location.getBlockY();
            for (int x = location.getBlockX() - 2; x <= location.getBlockX() + 2; x++) {
                for (int z = location.getBlockZ() - 2; z <= location.getBlockZ() + 2; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModX() !=0){
            int x = location.getBlockX();
            for (int y = location.getBlockY() - 2; y <= location.getBlockY() + 2; y++) {
                for (int z = location.getBlockZ() - 2; z <= location.getBlockZ() + 2; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModZ() !=0){
            int z = location.getBlockZ();
            for (int x = location.getBlockX() - 2; x <= location.getBlockX() + 2; x++) {
                for (int y = location.getBlockY() - 2; y <= location.getBlockY() + 2; y++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}
