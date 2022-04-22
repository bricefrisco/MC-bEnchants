package com.bfrisco.benchants.enchants;

import com.bfrisco.benchants.BEnchants;
import com.bfrisco.benchants.utils.ItemInfo;
import com.bfrisco.benchants.utils.Toggle;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Durability implements Listener {
    public static final Set<Material> ENCHANTABLE_ITEMS = new HashSet<>();

    public Durability() {
        loadConfig();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
//*****************Proposed new check, removes nbtapi dependency*********************************
        if (!ItemInfo.isTitanTool(item,player)) return;
        if (!ItemInfo.isActive(item,player)) return;
//*****************Proposed new check, removes nbtapi dependency*********************************

       //if (!hasDurability(event.getItem())) return;

        event.setCancelled(Boolean.TRUE);
    }

    public static void apply(ItemStack item, Player player) {
        if (!ENCHANTABLE_ITEMS.contains(item.getType())) {
            player.sendMessage(ChatColor.RED + "That item cannot be enchanted with Durability.");
            return;
        }

        if (hasDurability(item)) {
            player.sendMessage(ChatColor.RED + "That item is already enchanted with Durability.");
            return;
        }

        BEnchants.LOGGER.info(player.getName() + " enchanted item with durability...");
        NBTItem nbti = new NBTItem(item);
        nbti.setBoolean("durability", Boolean.TRUE);
        nbti.applyNBT(item);
        player.sendMessage(ChatColor.GREEN + "Successfully enchanted item with Durability.");
    }

    public static void remove(ItemStack item, Player player) {
        if (!hasDurability(item)) {
            player.sendMessage(ChatColor.RED + "That item is not enchanted with Durability.");
            return;
        }

        BEnchants.LOGGER.info(player + " removed durability enchantment from item...");
        NBTItem nbti = new NBTItem(item);
        nbti.setBoolean("durability", Boolean.FALSE);
        nbti.applyNBT(item);
        player.sendMessage(ChatColor.GREEN + "Successfully removed durability enchantment from item.");
    }

    public static boolean hasDurability(ItemStack item) {
        if (!ENCHANTABLE_ITEMS.contains(item.getType())) return false;
        NBTItem nbti = new NBTItem(item);
        if (!nbti.hasNBTData()) return false;
        return nbti.getBoolean("durability");
    }

    public static void loadConfig() {
        ENCHANTABLE_ITEMS.clear();

        ConfigurationSection durability = BEnchants.PLUGIN.getConfig().getConfigurationSection("durability");
        if (durability == null) {
            BEnchants.LOGGER.warning("Durability configuration not found!");
            return;
        }

        List<String> items = durability.getStringList("enchantable-items");

        if (items.size() == 0) {
            BEnchants.LOGGER.warning("No enchantable-items found in durability section of config.");
        }

        for (String item : items) {
            try {
                ENCHANTABLE_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                BEnchants.LOGGER.warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
    }

}
