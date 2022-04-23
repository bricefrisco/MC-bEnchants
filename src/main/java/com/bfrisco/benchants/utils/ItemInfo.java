package com.bfrisco.benchants.utils;

import org.bukkit.inventory.ItemStack;
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

    public static boolean isTitanTool(ItemStack item){
        if (item == null) return false;
        if (!item.hasItemMeta()) return false;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {

            if (ItemInfo.UNIMBUED_LORE.contains(lore)) {
                return true;
            } else if (ItemInfo.IMBUED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isActive(ItemStack item){

        List<String> loreList = item.getItemMeta().getLore();
        for (String s : loreList) {
            if (s.equalsIgnoreCase(ItemInfo.ANCIENT_POWER_ACTIVE)) {
                return true;
            } else if (!ItemInfo.isImbued(item)) return false;
        }
        return false;
    }

    public static boolean isImbued(ItemStack item) {

        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (ItemInfo.IMBUED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static Integer getAncientPowerLoreIndex(List<String> loreList) {

        for (int i = 0; i < loreList.size(); i++){
            if (ItemInfo.IMBUED_LORE.contains(loreList.get(i)) ||
                    ItemInfo.UNIMBUED_LORE.contains(loreList.get(i))) return i;
        }
        return null;
    }
}
