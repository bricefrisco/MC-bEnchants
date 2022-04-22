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

import java.util.ArrayList;
import java.util.List;

public class Toggle implements Listener {

    public static final String ANCIENT_POWER_ACTIVE = "§8Ancient Power §x§F§F§0§0§4§C♆";
    public static final String ANCIENT_POWER_INACTIVE = "§8Ancient Power ♆";
    public static final String ANCIENT_BLUE = "§8Ancient Power§x§6§D§5§E§F§F ♆";
    public static final String ANCIENT_RED = "§8Ancient Power§x§F§F§0§0§0§0 ♆";
    public static final String ANCIENT_YELLOW = "§8Ancient Power§x§F§F§E§C§2§7 ♆";
    private static final List<String> IMBUED_LORE = new ArrayList<>(){
        {
            add(ANCIENT_POWER_ACTIVE);
            add(ANCIENT_POWER_INACTIVE);
        }
    };
    private static final List<String> UNIMBUED_LORE = new ArrayList<>(){
        {
            add(ANCIENT_RED);
            add(ANCIENT_YELLOW);
            add(ANCIENT_BLUE);
        }
    };

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        if (!event.getAction().isRightClick()) return; //should reduce the amount of times the interactevent continues to use resources
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!isTitanTool(item,player)) return;
        if (!player.isSneaking()) return; //should reduce the amount of times the interactevent continues to use resources
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return;

        player.setCooldown(coolDown,40); //reduces the amount of times this can be spammed

        player.sendMessage(ChatColor.RED + "------Debug Start------");
        List<String> loreList = loreList(item,player);

        player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to !isImbued(loreList(p) check");
        if (!isImbued(player)){
            player.sendMessage(ChatColor.AQUA + "DebugSend: Item is not imbued");
            return;
        }

        if (!player.hasPermission("benchants.toggle")) {

            player.sendMessage("No permission.");
            return;
        }

        if (isActive(item,player)){
            player.sendMessage(ChatColor.AQUA + "Dbug2: Passed isActive(loreList(p)) test");
            player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to toggleActive(p,item,loreList(p)) method");
            toggleActive(player,item);
        } else {
            player.sendMessage( ChatColor.AQUA + "Dbug3: Did not pass isActive text");
            player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to toggleActive method");
            toggleActive(player,item);
        }

    }

    public static List<String> loreList(ItemStack item,Player player){

        ItemMeta meta = item.getItemMeta();
        List<String> loreList = meta.getLore();
        if (loreList == null) {
           player.sendMessage("List returned null");
           return null;
        }
        player.sendMessage("Dbug5: loreList obtained");
        return loreList;
    }

    public static boolean isTitanTool(ItemStack item,Player player){
        item = player.getInventory().getItemInMainHand();
        if (!(item.hasItemMeta())) return false; //return if air or other item without metadata
        List<String> loreList = loreList(item,player);
        if (loreList == null) return false;
        for (String lore : loreList) {
            //detects for any variant of ancient power color in titan tools
            //then returns true if any exist
            if (UNIMBUED_LORE.contains(lore)) {
                player.sendMessage("Dbug7: isTitanTool came back true due to red,yellow,blue,active lore detection");
                return true;
            } else if (IMBUED_LORE.contains(lore)) {
                player.sendMessage("Dbug8: isTitanTool came back true due to inactive lore detection");
                return true;
            }
        }
        return false;
    }

    public static boolean isActive(ItemStack item, Player player){


        List<String> loreList = loreList(item,player);
        for (String s : loreList) {
            //detects if ancient power is active or inactive on a titantool
            if (s.equalsIgnoreCase(ANCIENT_POWER_ACTIVE)) {
                player.sendMessage(player.getLocation() + " Ancient power is active!");
                return true;
            }
        } return false;
    }
    public static boolean isImbued(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        List<String> loreList = loreList(item,player);
        if (loreList == null) return false;
        for (String lore : loreList) {
            //detects for any variant of ancient power color in titan tools
            //then either "deactivates" or "activates"
            if (UNIMBUED_LORE.contains(lore)) {
                return false;
            } else if (IMBUED_LORE.contains(lore)) {
                return true;
            }
        } return false;
    }
    public static void toggleActive(Player player,ItemStack item){
        List<String> loreList = loreList(item,player);
        if (loreList == null) return;
        int index = getAncientPowerLoreIndex(loreList);
        if (isActive(item,player)) {
            removeEnchantment(item,player);
            return;
        }
        addEnchantment(item,player);

    }

    public static Integer getAncientPowerLoreIndex(List<String> loreList) {

        for (int i = 0; i < loreList.size(); i++){
            if (IMBUED_LORE.contains(loreList.get(i)) ||
                    UNIMBUED_LORE.contains(loreList.get(i))) return i;
        }
        return null;
    }

    public static void removeEnchantment(ItemStack item, Player player) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = getAncientPowerLoreIndex(loreList);
        loreList.set(index,ANCIENT_POWER_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
        new BEnchantEffects().addEffect(player);
    /*    Trench.remove(item,player);
        Durability.remove(item,player);*/
    }
    public static void addEnchantment (ItemStack item, Player player) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = getAncientPowerLoreIndex(loreList);
        loreList.set(index,ANCIENT_POWER_ACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
        new BEnchantEffects().removeEffect(player);
        /*Trench.apply(item, player);
        Durability.apply(item, player);*/
    }




}
