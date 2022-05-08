package com.bfrisco.benchants.enchants.TitanAxe;

import com.bfrisco.benchants.BEnchants;
import com.bfrisco.benchants.utils.ChargeManagement;
import com.bfrisco.benchants.utils.ItemInfo;
import org.bukkit.Bukkit;
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

import java.util.*;

public class Axe implements Listener {
    public static final Set<Material> ALLOWED_ITEMS = new HashSet<>();
    public static final Set<Material> ENCHANTABLE_ITEMS = new HashSet<>();
    public static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();
    public static final Material axe = Material.DIAMOND_AXE;
    public static final HashMap<Material, Material> treeMap = new HashMap<>();
    public static final Set<Material> TREE_TYPES = new HashSet<>();
    public static final Set<Material> SAPLINGS = new HashSet<>();
    HashMap<Material,Material> REPLANT_MAP = new HashMap<>();
    {
        REPLANT_MAP.putIfAbsent(Material.OAK_LOG,Material.OAK_SAPLING);
        REPLANT_MAP.putIfAbsent(Material.BIRCH_LOG,Material.BIRCH_SAPLING);
        REPLANT_MAP.putIfAbsent(Material.SPRUCE_LOG,Material.SPRUCE_SAPLING);
        REPLANT_MAP.putIfAbsent(Material.ACACIA_LOG,Material.ACACIA_SAPLING);
        REPLANT_MAP.putIfAbsent(Material.DARK_OAK_LOG,Material.DARK_OAK_SAPLING);
        REPLANT_MAP.putIfAbsent(Material.JUNGLE_LOG,Material.JUNGLE_SAPLING);
        REPLANT_MAP.putIfAbsent(Material.MUSHROOM_STEM,Material.RED_MUSHROOM);
        REPLANT_MAP.putIfAbsent(Material.CRIMSON_STEM,Material.CRIMSON_FUNGUS);
        REPLANT_MAP.putIfAbsent(Material.WARPED_STEM,Material.WARPED_FUNGUS);
    }

    public static final List<Material> DIRT_TYPES = new ArrayList<>(){
        {
            add(Material.DIRT);
            add(Material.WARPED_NYLIUM);
            add(Material.CRIMSON_NYLIUM);
            add(Material.PODZOL);
            add(Material.GRASS_BLOCK);
            add(Material.NETHERRACK);

        }
    };


    public Axe() {
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
        if (item.getType() != axe) return;
        if (!ItemInfo.isTitanTool(item)) return;
        if (!ItemInfo.isActiveImbued(item) && !ItemInfo.isActiveCharged(item)) return;

        Block blockBroken = event.getBlock();
        Location blockBrokenLocation = blockBroken.getLocation();

        for (Block blockLoop : generateSphere(blockBrokenLocation,5,false)) {
            if (blockLoop.getLocation().equals(blockBrokenLocation)) {
                continue;
            }
            if (DIRT_TYPES.contains(blockLoop.getRelative(0,-1,0).getType()) && REPLANT_MAP.containsKey(blockLoop.getType())){
                player.sendMessage("Key and V: " + REPLANT_MAP.get(blockBroken.getType()) );
                blockLoop.setType(REPLANT_MAP.get(blockLoop.getType()) );
            }
            if (ALLOWED_ITEMS.contains(blockLoop.getType())) {
                IGNORE_LOCATIONS.add(blockLoop.getLocation());

                BlockBreakEvent e = new BlockBreakEvent(blockLoop, player);
                Bukkit.getPluginManager().callEvent(e);
                if (!e.isCancelled()) {
                    if (!hasSilkTouch(item)) {
                        dropExperience(blockLoop);
                    }
                    blockLoop.breakNaturally(item);
                }
            }
        }
        ChargeManagement.decreaseChargeLore(item, player);
/*        if (blockBelow.getType() != blockBroken.getType()){
            if (blockBelow.getType() == Material.DIRT){
                blockBroken.setType(Material.OAK_SAPLING);
                event.setCancelled(true);
            }
            player.sendMessage("Block below broken is not the same");
        }*/
    }

    public static void loadConfig() {
        ENCHANTABLE_ITEMS.clear();
        ConfigurationSection TitanAxe = BEnchants.PLUGIN.getConfig().getConfigurationSection("titanAxe");
        if (TitanAxe == null) {
            BEnchants.LOGGER.warning("TitanAxe configuration not found!");
            return;
        }
        List<String> items = TitanAxe.getStringList("destroyable-items");
        if (items.size() == 0) {
            BEnchants.LOGGER.warning("No destroyable-items found in TitanAxe section of config.");
        }
        for (String item : items) {
            try {
                ALLOWED_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                BEnchants.LOGGER.warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
        List<String> enchantableItems = TitanAxe.getStringList("enchantable-items");
        if (items.size() == 0) {
            BEnchants.LOGGER.warning("No enchantable-items found in TitanAxe section of config.");
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
            case OAK_LOG -> getRandomNumber(0, 2);
            case BIRCH_LOG -> getRandomNumber(0, 1);
            case SPRUCE_LOG -> getRandomNumber(3, 7);
            case DARK_OAK_LOG -> getRandomNumber(2, 5);
            default -> 0;
        };
        if (experience > 0) {
            block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
        }
    }

    private boolean hasSilkTouch(ItemStack itemStack) {
        return itemStack.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
    }

    public static List<Block> generateSphere(Location location, int radius, boolean hollow) {
        List<Block> circleBlocks = new ArrayList<>();
        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                    if(distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
                        circleBlocks.add(location.getWorld().getBlockAt(x, y, z));
                    }
                }
            }
        }
        return circleBlocks;
    }


    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}

