package com.bfrisco.benchants.utils;

import com.bfrisco.benchants.enchants.Durability;
import com.bfrisco.benchants.enchants.Trench;
import org.bukkit.*;
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
        ItemStack item = p.getInventory().getItemInMainHand();
        if (!p.isSneaking()) return;
        if (!event.getAction().isRightClick()) return;
        p.sendMessage(ChatColor.RED + "------Debug Start------");
        if (!isTitanTool(p,loreList(p))) return;
        if (p.hasCooldown(coolDown)) return;



        if ((p.hasPermission("benchants.toggle") && event.getAction().isRightClick()) && (event.getPlayer().isSneaking())) {

            p.sendMessage(ChatColor.AQUA + "Dbug1: Passed perm check, rightclick sneaking check");

            p.setCooldown(coolDown,25);
            p.sendMessage(ChatColor.AQUA + "DebugSend: Sent to isActive check");
            if (isActive(p,loreList(p))){

                p.sendMessage(ChatColor.AQUA + "Dbug2: Passed isActive test");
                p.sendMessage(ChatColor.AQUA + "DebugSend: Sent to toggleActive method");
                toggleActive(p,item,loreList(p));

            } else if (!(isActive(p,loreList(p)))){

                p.sendMessage( ChatColor.AQUA + "Dbug3: Did not pass isActive text");
                p.sendMessage(ChatColor.AQUA + "DebugSend: Sent to toggleActive method");
                toggleActive(p,item,loreList(p));
            }

        }

    }

    public static List<String> loreList(Player p){
        ItemStack item = p.getInventory().getItemInMainHand();
        List<String> loreList;
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            loreList = meta.getLore();
            p.sendMessage("Dbug5: loreList returned");
            return loreList;
        } else if (!item.hasItemMeta()) {
            p.sendMessage("Dbug6: loreList returned null");
            return null;
        } return null;


    }

    public static boolean isTitanTool(Player p,List<String> lorelist){
        p.sendMessage(ChatColor.YELLOW + "Dbug4: inside isTitanTool Boolean");

        if (lorelist ==null) return false;
        for (int i = 0; i < lorelist.size(); i++) {
            //detects for any variant of ancient power color in titan tools
            //then returns true if any exist
            if (lorelist.get(i).equalsIgnoreCase(ANCIENT_RED) || lorelist.get(i).equalsIgnoreCase(ANCIENT_YELLOW)
                    || lorelist.get(i).equalsIgnoreCase(ANCIENT_BLUE) || lorelist.get(i).equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                p.sendMessage("Dbug7: isTitanTool came back true due to red,yellow,blue,active lore detection");
                return true;
            } else if (lorelist.get(i).equalsIgnoreCase(ANCIENT_POWER_INACTIVE)) {
                p.sendMessage("Dbug8: isTitanTool came back true due to inactive lore detection");
                return true;
            }
        } return false;
    }

    public static boolean isActive(Player p,List<String> loreList){

        if (loreList ==null) return false;
        for (int i = 0; i < loreList.size(); i++) {
            //detects if ancient power is active or inactive on a titantool
            if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                    return true;
            } else if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_INACTIVE)) {
                return false;
            }
        } return false;

    }
    public static boolean isImbued(List<String> loreList) {

        if (loreList ==null) return false;

        for (int i = 0; i < loreList.size(); i++) {
            //detects for any variant of ancient power color in titan tools
            //then either "deactivates" or "activates"
            if (loreList.get(i).equalsIgnoreCase(ANCIENT_RED) || loreList.get(i).equalsIgnoreCase(ANCIENT_YELLOW)
                    || loreList.get(i).equalsIgnoreCase(ANCIENT_BLUE)) {
                return false;
            } else if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_INACTIVE) || loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                return true;
            }
        }
        return false;

    }
    public static void toggleActive(Player p,ItemStack item,List<String> loreList){

        if (loreList ==null) return;
        boolean isImbued = false;
        for (int i = 0; i < loreList.size(); i++) {
        //detects for any variant of ancient power color in titan tools
        //then either "deactivates" or "activates"
        if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
            removeEnchantment(item, p, i);
            isImbued = true;

        } else if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_INACTIVE)) {
            addEnchantment(item, p, i);
            isImbued = true;
        }

        }
        if (!(isImbued)) {
            p.sendMessage(ChatColor.RED + "This tool cannot be activated without being imbued first!");
        }

    }

    //TODO: this is a fooking mess idk what to do here
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
