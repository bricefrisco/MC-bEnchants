package com.bfrisco.benchants.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PowerCrystalAdd implements Listener {

    @EventHandler
    public static void diamondBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        if (block.getType() != Material.DIAMOND_ORE && block.getType() != Material.DEEPSLATE_DIAMOND_ORE ) return;
        Player player = event.getPlayer();
        if (!player.hasPermission("benchants.powercrystaldrop")) return;
        if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_SHOVEL) return;
        if (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;
        Location loc = block.getLocation();
        Block newBlock = block.getRelative(0,-1,0);
        newBlock.setType(Material.JIGSAW);
        ItemStack powerCrystal = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add("§x§F§F§0§0§4§CAncient Charge");
        powerCrystal.setLore(lore);
        ItemMeta meta = powerCrystal.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Power Crystal");
        powerCrystal.setItemMeta(meta);
        player.getLocation().getWorld().dropItem(loc,powerCrystal);
    }


}
