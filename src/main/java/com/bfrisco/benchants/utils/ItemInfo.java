package com.bfrisco.benchants.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;


public class ItemInfo {

    public static final String ANCIENT_POWER_ACTIVE = "§8Ancient Power §x§F§F§0§0§4§CΩ";
    public static final String ANCIENT_POWER_INACTIVE = "§8Ancient Power Ω";

    public static final String ANCIENT_RED = "§8Ancient Power§x§F§F§0§0§0§0 ♆";
    public static final String ANCIENT_YELLOW = "§8Ancient Power§x§F§F§E§C§2§7 ♆";
    public static final String ANCIENT_BLUE = "§8Ancient Power§x§6§D§5§E§F§F ♆";

    public static final String ANCIENT_CHARGE_ACTIVE = "§8Ancient Power §x§F§F§0§0§4§C♆";
    public static final String ANCIENT_CHARGE_INACTIVE = "§8Ancient Power ♆";
    public static final String ANCIENT_CHARGE = "§8Charge:§x§F§F§0§0§4§C";
    public static final String POWER_CRYSTAL = "§x§F§F§0§0§4§CAncient Charge";
    public static final String ANCIENT_DEPLETED = " ";

    public static final String RED = "§x§F§F§0§0§0§0";
    public static final String YELLOW = "§x§F§F§E§C§2§7";
    public static final String BLUE = "§x§6§D§5§E§F§F";

    public static final String SHOVEL_ONE_CHARGED = "§8Ancient Power §x§F§F§0§0§4§C1";
    public static final String SHOVEL_TWO_CHARGED = "§8Ancient Power §x§F§F§0§0§4§C2";
    public static final String SHOVEL_THREE_CHARGED = "§8Ancient Power §x§F§F§0§0§4§C3";

    public static final String SHOVEL_ONE_IMBUED = "§8Ancient Power §x§F§F§0§0§4§C1";
    public static final String SHOVEL_TWO_IMBUED = "§8Ancient Power §x§F§F§0§0§4§C2";
    public static final String SHOVEL_THREE_IMBUED = "§8Ancient Power §x§F§F§0§0§4§C3";

    public static final List<String> TITANLORE = new ArrayList<>(){
        {
            add(ANCIENT_POWER_ACTIVE);
            add(ANCIENT_POWER_INACTIVE);
            add(ANCIENT_RED);
            add(ANCIENT_YELLOW);
            add(ANCIENT_BLUE);
            add(ANCIENT_CHARGE_ACTIVE);
            add(ANCIENT_CHARGE_INACTIVE);

            add(SHOVEL_ONE_CHARGED);
            add(SHOVEL_TWO_CHARGED);
            add(SHOVEL_THREE_CHARGED);
            add(SHOVEL_ONE_IMBUED);
            add(SHOVEL_TWO_IMBUED);
            add(SHOVEL_THREE_IMBUED);
        }
    };

    public static final List<String> IMBUED_LORE = new ArrayList<>(){
        {
            add(ANCIENT_POWER_ACTIVE);
            add(ANCIENT_POWER_INACTIVE);

            add(SHOVEL_ONE_IMBUED);
            add(SHOVEL_TWO_IMBUED);
            add(SHOVEL_THREE_IMBUED);
        }
    };

    public static final List<String> UNIMBUED_LORE = new ArrayList<>(){
        {
            add(ANCIENT_RED);
            add(ANCIENT_YELLOW);
            add(ANCIENT_BLUE);
        }
    };

    public static final List<String> ANCIENT_CHARGE_LORE = new ArrayList<>(){
        {
            add(ANCIENT_CHARGE_ACTIVE);
            add(ANCIENT_CHARGE_INACTIVE);

            add(SHOVEL_ONE_CHARGED);
            add(SHOVEL_TWO_CHARGED);
            add(SHOVEL_THREE_CHARGED);
        }
    };

    public static boolean isTitanTool(ItemStack item){
        if (!item.hasItemMeta()) return false;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {

            if (TITANLORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isActive(ItemStack item){

        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (ANCIENT_POWER_ACTIVE.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientPowerActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isActiveCharge(ItemStack item){
        Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (ANCIENT_CHARGE_ACTIVE.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isImbued(ItemStack item) {

        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (IMBUED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPowerCrystal(ItemStack item) {
        if (!item.hasItemMeta()) return false;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        Material type = item.getType();
        Material powerCrystal = Material.AMETHYST_SHARD;
  /*      for (String lore : loreList) {
            if (POWER_CRYSTAL.equalsIgnoreCase(lore) && (type == powerCrystal)) {
                return true;
            }
        }*/
        Bukkit.getServer().getConsoleSender().sendMessage("inside isPowerCrystal");
        if (loreList.stream().anyMatch(lore -> lore.matches(POWER_CRYSTAL) && (type == powerCrystal))){
            Bukkit.getServer().getConsoleSender().sendMessage("passed isPowerCrystal check");
            return true;
        };

        return false;


    }

    public static boolean hasCharge(ItemStack item){
        if (!item.hasItemMeta()) return false;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        Material type = item.getType();
        Material fireCharge = Material.FIRE_CHARGE;
        for (String lore : loreList) {
            if (lore.matches("(.*)" + ANCIENT_CHARGE + "(.*)") && (type != fireCharge)) {
                return true;
            }
        }
        return false;
    }

    public static String isColor(ItemStack item){
        if (!item.hasItemMeta()) return null;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return null;
        for (String lore : loreList) {
            if (lore.matches("(.*)" + RED + "(.*)")) {
                return "RED";
            } else if (lore.matches("(.*)" + YELLOW + "(.*)")) {
                return "YELLOW";
            } else if (lore.matches("(.*)" + BLUE + "(.*)")) {
                return "BLUE";
            }
        }
        return null;
    }

    public static Integer getAncientPowerLoreIndex(List<String> loreList) {
        Bukkit.getServer().getConsoleSender().sendMessage("inside of getAncientPowerLoreIndex");
        for (int i = 0; i < loreList.size(); i++){
            if (IMBUED_LORE.contains(loreList.get(i)) ||
                    UNIMBUED_LORE.contains(loreList.get(i)) ||
                    ANCIENT_CHARGE_LORE.contains(loreList.get(i))) return i;
        }
        return null;
    }
}
