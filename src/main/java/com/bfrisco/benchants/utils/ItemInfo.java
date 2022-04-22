package com.bfrisco.benchants.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;


public class ItemInfo {

public static final String ANCIENT_POWER_ACTIVE = "§8Ancient Power §x§F§F§0§0§4§C♆";
public static final String ANCIENT_POWER_INACTIVE = "§8Ancient Power ♆";
public static final String ANCIENT_BLUE = "§8Ancient Power§x§6§D§5§E§F§F ♆";
public static final String ANCIENT_RED = "§8Ancient Power§x§F§F§0§0§0§0 ♆";
public static final String ANCIENT_YELLOW = "§8Ancient Power§x§F§F§E§C§2§7 ♆";

    public static final List<String> IMBUED_LORE = new ArrayList<>(){
        {
        add(ANCIENT_POWER_ACTIVE);
        add(ANCIENT_POWER_INACTIVE);
        }
    };
    public static final List<String> UNIMBUED_LORE = new ArrayList<>(){
        {
        add(ANCIENT_RED);
        add(ANCIENT_YELLOW);
        add(ANCIENT_BLUE);
        }
    };

    public static boolean isTitanTool(ItemStack item,Player player){
        item = player.getInventory().getItemInMainHand();
        if (!(item.hasItemMeta())) return false; //return if air or other item without metadata
        List<String> loreList = loreList(item,player);
        if (loreList == null) return false;
        for (String lore : loreList) {
            //detects for any variant of ancient power color in titan tools
            //then returns true if any exist
            if (ItemInfo.UNIMBUED_LORE.contains(lore)) {
                player.sendMessage("Dbug7: isTitanTool came back true due to red,yellow,blue,active lore detection");
                return true;
            } else if (ItemInfo.IMBUED_LORE.contains(lore)) {
                player.sendMessage("Dbug8: isTitanTool came back true due to inactive lore detection");
                return true;
            }
        }
        return false;
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
    public static boolean isActive(ItemStack item, Player player){


        List<String> loreList = loreList(item,player);
        for (String s : loreList) {
            //detects if ancient power is active or inactive on a titantool
            if (s.equalsIgnoreCase(ItemInfo.ANCIENT_POWER_ACTIVE)) {
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
            if (ItemInfo.UNIMBUED_LORE.contains(lore)) {
                return false;
            } else if (ItemInfo.IMBUED_LORE.contains(lore)) {
                return true;
            }
        } return false;
    }
    public static Integer getAncientPowerLoreIndex(List<String> loreList) {

        for (int i = 0; i < loreList.size(); i++){
            if (ItemInfo.IMBUED_LORE.contains(loreList.get(i)) ||
                    ItemInfo.UNIMBUED_LORE.contains(loreList.get(i))) return i;
        }
        return null;
    }
}
