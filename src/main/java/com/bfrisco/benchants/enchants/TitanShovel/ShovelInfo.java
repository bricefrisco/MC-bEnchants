package com.bfrisco.benchants.enchants.TitanShovel;

import com.bfrisco.benchants.utils.ItemInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class ShovelInfo {

    public static final String SHOVEL_ONE_CHARGED = "§8Ancient Power §x§F§F§0§0§4§C1";
    public static final String SHOVEL_TWO_CHARGED = "§8Ancient Power §x§F§F§0§0§4§C2";
    public static final String SHOVEL_THREE_CHARGED = "§8Ancient Power §x§F§F§0§0§4§C3";

    public static final String SHOVEL_ONE_IMBUED = "§8Ancient Power §x§F§F§0§0§4§C1";
    public static final String SHOVEL_TWO_IMBUED = "§8Ancient Power §x§F§F§0§0§4§C2";
    public static final String SHOVEL_THREE_IMBUED = "§8Ancient Power §x§F§F§0§0§4§C3";

    public static final List<String> SHOVEL_LORE = new ArrayList<>(){
        {
            add(SHOVEL_ONE_CHARGED);
            add(SHOVEL_TWO_CHARGED);
            add(SHOVEL_THREE_CHARGED);
            add(SHOVEL_ONE_IMBUED);
            add(SHOVEL_TWO_IMBUED);
            add(SHOVEL_THREE_IMBUED);

        }
    };

    public static final List<String> SHOVEL_IMBUED_LORE = new ArrayList<>(){
        {
            add(SHOVEL_ONE_IMBUED);
            add(SHOVEL_TWO_IMBUED);
            add(SHOVEL_THREE_IMBUED);
        }
    };

    public static final List<String> SHOVEL_CHARGED_LORE = new ArrayList<>(){
        {
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

            if (ItemInfo.TITANLORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isActive(ItemStack item){

        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (ItemInfo.ANCIENT_POWER_ACTIVE.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientPowerActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isChargedShovel1(ItemStack item){
        Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (SHOVEL_ONE_CHARGED.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isChargedShovel2(ItemStack item){
        Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (SHOVEL_TWO_CHARGED.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isChargedShovel3(ItemStack item){
        Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (SHOVEL_THREE_CHARGED.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isImbuedShovel1(ItemStack item){
        Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (SHOVEL_ONE_IMBUED.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isImbuedShovel2(ItemStack item){
        Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (SHOVEL_TWO_IMBUED.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isImbuedShovel3(ItemStack item){
        Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (SHOVEL_THREE_IMBUED.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");
                return true;
            }
        }
        return false;
    }


    public static boolean isActiveCharge(ItemStack item){
        Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (ItemInfo.ANCIENT_CHARGE_ACTIVE.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isDormantCharged(ItemStack item){
        Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (ItemInfo.ANCIENT_CHARGE_INACTIVE.equalsIgnoreCase(lore) || ItemInfo.ANCIENT_POWER_INACTIVE.equalsIgnoreCase(lore)) {
                Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");
                return true;
            }
        }
        return false;
    }

    public static boolean isImbuedShovel(ItemStack item) {

        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (SHOVEL_IMBUED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCharge(ItemStack item){
        if (!item.hasItemMeta()) return false;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        Material type = item.getType();
        Material fireCharge = Material.FIRE_CHARGE;
        for (String lore : loreList) {
            if (lore.matches("(.*)" + ItemInfo.ANCIENT_CHARGE + "(.*)") && (type != fireCharge)) {
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
            if (lore.matches("(.*)" + ItemInfo.RED + "(.*)")) {
                return "RED";
            } else if (lore.matches("(.*)" + ItemInfo.YELLOW + "(.*)")) {
                return "YELLOW";
            } else if (lore.matches("(.*)" + ItemInfo.BLUE + "(.*)")) {
                return "BLUE";
            }
        }
        return null;
    }

}
