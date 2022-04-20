package com.bfrisco.benchants.utils;

import com.bfrisco.benchants.enchants.Durability;
import com.bfrisco.benchants.enchants.Trench;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class Toggle implements Listener {


    public static final String ANCIENT_POWER_ACTIVE = "§8Ancient Power §x§F§F§0§0§4§C♆";
    public static final String ANCIENT_POWER_INACTIVE = "§8Ancient Power ♆";
    public static final String ancientBlue = "§8Ancient Power§x§6§D§5§E§F§F ♆";
    public static final String ancientRed = "§8Ancient Power§x§F§F§0§0§0§0 ♆";
    public static final String ancientYellow = "§8Ancient Power§x§F§F§E§C§2§7 ♆";
    public static final String ancientPowerActive = "§8Ancient Power §x§F§F§0§0§4§C♆";
    public static final String ancientGray = "§8Ancient Power ♆";
    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        List<String> loreList = null;
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            loreList = meta.getLore();
        }
        Material coolDown = Material.JIGSAW;


        if ((p.hasPermission("benchants.toggle") && event.getAction().isRightClick()) && (event.getPlayer().isSneaking()) && item.hasItemMeta() && !p.hasCooldown(coolDown)) {

            boolean hadAncientPower = false;

            Bukkit.getServer().getConsoleSender().sendMessage("Successfully inside of toggle event");
            if (!(p.getInventory().getItemInMainHand().hasItemMeta())) {
                p.sendMessage(net.md_5.bungee.api.ChatColor.RED + "You must have an item in your hand!");
                return;
            }
            for (int i = 0; i < loreList.size(); i++) {
                //detects for any variant of ancient power color in titan tools
                //then either "deactivates" or "activates"

                if (loreList.get(i).equalsIgnoreCase(ancientRed) || loreList.get(i).equalsIgnoreCase(ancientYellow)
                        || loreList.get(i).equalsIgnoreCase(ancientBlue) || loreList.get(i).equalsIgnoreCase(ancientPowerActive)) {
                    removeEnchantment(item, p, i);
                    hadAncientPower = true;
                    p.setCooldown(coolDown,25);
                } else if (loreList.get(i).equalsIgnoreCase(ancientGray)) {
                    addEnchantment(item, p, i);
                    hadAncientPower = true;
                    p.setCooldown(coolDown,25);
                }

            }
            if (!hadAncientPower) p.sendMessage("You are not holding a titan tool");
        }

    }

    public static void removeEnchantment (ItemStack item, Player p, int i) {
        List<String> loreList = item.getItemMeta().getLore();
        ItemMeta meta = item.getItemMeta();
        loreList.set(i,ANCIENT_POWER_INACTIVE);
        Bukkit.getServer().getConsoleSender().sendMessage(loreList.get(i));
        meta.setLore(loreList);
        item.setItemMeta(meta);
        p.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,p.getEyeLocation(),100);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,10, 1);
        Trench.remove(item,p);
        Durability.remove(item,p);
        p.sendMessage("Inside active ancient");

    }
    public static void addEnchantment (ItemStack item, Player p, int i) {
        List<String> loreList = item.getItemMeta().getLore();
        ItemMeta meta = item.getItemMeta();
        loreList.set(i,ANCIENT_POWER_ACTIVE);
        Bukkit.getServer().getConsoleSender().sendMessage(loreList.get(i));
        meta.setLore(loreList);
        item.setItemMeta(meta);
        p.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,p.getEyeLocation(),100);
        p.getWorld().playSound(p.getLocation(),Sound.BLOCK_BEACON_ACTIVATE,10, 1);
        Trench.apply(item, p);
        Durability.apply(item, p);
        p.sendMessage("inside inactiveancient");


    }




}
