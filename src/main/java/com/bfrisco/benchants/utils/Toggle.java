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
    public static final String ANCIENT_BLUE = "§8Ancient Power§x§6§D§5§E§F§F ♆";
    public static final String ANCIENT_RED = "§8Ancient Power§x§F§F§0§0§0§0 ♆";
    public static final String ANCIENT_YELLOW = "§8Ancient Power§x§F§F§E§C§2§7 ♆";

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Material coolDown = Material.JIGSAW;


        if ((p.hasPermission("benchants.toggle") && event.getAction().isRightClick()) && (event.getPlayer().isSneaking()) && isTitanTool(p) && !p.hasCooldown(coolDown) ) {

            p.sendMessage("You have a titan tool");

            p.setCooldown(coolDown,25);
            if (isActive(p)){

                p.sendMessage("was active");
                toggleActive(p);

            } else if (!(isActive(p))){

                p.sendMessage("was not active");
                toggleActive(p);
            }

        }

    }

    public static boolean isTitanTool(Player p){

        ItemStack item = p.getInventory().getItemInMainHand();
        List<String> loreList = null;
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            loreList = meta.getLore();
        } else if (!item.hasItemMeta()) {
            return false;
        }

        for (int i = 0; i < loreList.size(); i++) {
            //detects for any variant of ancient power color in titan tools
            //then either "deactivates" or "activates"
            if (loreList.get(i).equalsIgnoreCase(ANCIENT_RED) || loreList.get(i).equalsIgnoreCase(ANCIENT_YELLOW)
                    || loreList.get(i).equalsIgnoreCase(ANCIENT_BLUE) || loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                return true;
            } else if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_INACTIVE)) {
                return true;
            }

        }
        return false;
    }

    public static boolean isActive(Player p){

        ItemStack item = p.getInventory().getItemInMainHand();
        List<String> loreList = null;
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            loreList = meta.getLore();
        }

        for (int i = 0; i < loreList.size(); i++) {
            //detects for any variant of ancient power color in titan tools
            //then either "deactivates" or "activates"
            if (loreList.get(i).equalsIgnoreCase(ANCIENT_RED) || loreList.get(i).equalsIgnoreCase(ANCIENT_YELLOW)
                    || loreList.get(i).equalsIgnoreCase(ANCIENT_BLUE) || loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                return true;
            } else if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_INACTIVE)) {
                return false;
            }
        }
        return false;
    }
    public static void toggleActive(Player p){
        ItemStack item = p.getInventory().getItemInMainHand();
        List<String> loreList = null;
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            loreList = meta.getLore();
        }
        for (int i = 0; i < loreList.size(); i++) {
            //detects for any variant of ancient power color in titan tools
            //then either "deactivates" or "activates"
            if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                removeEnchantment(item,p,i);
                return;
            } else if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_INACTIVE)) {
                addEnchantment(item,p,i);
                return;
            }
        }
    }
    public static void removeEnchantment (ItemStack item, Player p, int i) {
        List<String> loreList = item.getItemMeta().getLore();
        ItemMeta meta = item.getItemMeta();
        loreList.set(i,ANCIENT_POWER_INACTIVE);
        meta.setLore(loreList);
        item.setItemMeta(meta);
        p.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,p.getEyeLocation(),100);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,10, 1);
        Trench.remove(item,p);
        Durability.remove(item,p);
    }
    public static void addEnchantment (ItemStack item, Player p, int i) {
        List<String> loreList = item.getItemMeta().getLore();
        ItemMeta meta = item.getItemMeta();
        loreList.set(i,ANCIENT_POWER_ACTIVE);
        meta.setLore(loreList);
        item.setItemMeta(meta);
        p.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,p.getEyeLocation(),100);
        p.getWorld().playSound(p.getLocation(),Sound.BLOCK_BEACON_ACTIVATE,10, 1);
        Trench.apply(item, p);
        Durability.apply(item, p);
    }




}
