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

        Player player = event.getPlayer();
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return;
        if (!player.isSneaking()) return; //should reduce the amount of times the interactevent continues to use resources
        if (!event.getAction().isRightClick()) return; //should reduce the amount of times the interactevent continues to use resources
        player.setCooldown(coolDown,40); //reduces the amount of times this can be spammed
        ItemStack item = player.getInventory().getItemInMainHand();
        List<String> loreList = loreList(player);
        player.sendMessage(ChatColor.RED + "------Debug Start------");
        if (!isTitanTool(player,loreList)) return;
        player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to !isImbued(loreList(p) check");
        if (!isImbued(loreList)){
            player.sendMessage(ChatColor.AQUA + "DebugSend: Item is not imbued");
            return;
        }


        if ((player.hasPermission("benchants.toggle"))) {

            player.sendMessage(ChatColor.AQUA + "Dbug1: Passed perm check");
            player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to isActive(loreList(p)) check");
            if (isActive(loreList,player)){
                player.sendMessage(ChatColor.AQUA + "Dbug2: Passed isActive(loreList(p)) test");
                player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to toggleActive(p,item,loreList(p)) method");
                toggleActive(player,item);
            } else if (!(isActive(loreList,player))){
                player.sendMessage( ChatColor.AQUA + "Dbug3: Did not pass isActive text");
                player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to toggleActive method");
                toggleActive(player,item);
            }
        }
    }

    public static List<String> loreList(Player player){
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        List<String> loreList = meta.getLore();
        player.sendMessage("Dbug5: loreList verified for condition");
        return loreList;
    }

    public static boolean isTitanTool(Player player,List<String> lorelist){
        player.sendMessage(ChatColor.YELLOW + "Dbug4: inside isTitanTool Boolean");
        if (lorelist == null) return false;
        for (String s : lorelist) {
            //detects for any variant of ancient power color in titan tools
            //then returns true if any exist
            if (s.equalsIgnoreCase(ANCIENT_RED) || s.equalsIgnoreCase(ANCIENT_YELLOW)
                    || s.equalsIgnoreCase(ANCIENT_BLUE) || s.equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                player.sendMessage("Dbug7: isTitanTool came back true due to red,yellow,blue,active lore detection");
                return true;
            } else if (s.equalsIgnoreCase(ANCIENT_POWER_INACTIVE)) {
                player.sendMessage("Dbug8: isTitanTool came back true due to inactive lore detection");
                return true;
            }
        }
        return false;
    }

    public static boolean isActive(List<String> loreList, Player player){

        for (String s : loreList) {
            //detects if ancient power is active or inactive on a titantool
            if (s.equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                player.sendMessage("Ancient power is active!");
                return true;
            } else if (s.equalsIgnoreCase(ANCIENT_POWER_INACTIVE)) {
                player.sendMessage("Ancient power is not active!");
                return false;
            }
        } return false;
    }
    public static boolean isImbued(List<String> loreList) {

        for (String s : loreList) {
            //detects for any variant of ancient power color in titan tools
            //then either "deactivates" or "activates"
            if (s.equalsIgnoreCase(ANCIENT_RED) || s.equalsIgnoreCase(ANCIENT_YELLOW)
                    || s.equalsIgnoreCase(ANCIENT_BLUE)) {
                return false;
            } else if (s.equalsIgnoreCase(ANCIENT_POWER_INACTIVE) || s.equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                return true;
            }
        } return false;
    }
    public static void toggleActive(Player player,ItemStack item){

        List<String> loreList = loreList(player);
        boolean isImbued = false;
        assert loreList != null;
        for (int i = 0; i < loreList.size(); i++) {
            //detects for any variant of ancient power color in titan tools
            //then either "deactivates" or "activates"
            if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                removeEnchantment(loreList,item, player, i);
                isImbued = true;
            } else if (loreList.get(i).equalsIgnoreCase(ANCIENT_POWER_INACTIVE)) {
                addEnchantment(loreList, item, player, i);
                isImbued = true;
            }
        }
        if (!(isImbued)) {
            player.sendMessage(ChatColor.RED + "This tool cannot be activated without being imbued first!");
        }

    }

    //TODO: this is a fooking mess idk what to do here
    public static void removeEnchantment (List<String> loreList, ItemStack item, Player player, int i) {

        loreList.set(i,ANCIENT_POWER_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
        new BEnchantEffects().addEffect(player);
        Trench.remove(item,player);
        Durability.remove(item,player);
    }
    public static void addEnchantment (List<String> loreList, ItemStack item, Player player, int i) {
        loreList.set(i,ANCIENT_POWER_ACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
        new BEnchantEffects().removeEffect(player);
        Trench.apply(item, player);
        Durability.apply(item, player);
    }




}
