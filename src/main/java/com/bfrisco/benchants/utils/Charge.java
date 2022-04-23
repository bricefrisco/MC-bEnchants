package com.bfrisco.benchants.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


public class Charge implements Listener {

    public static final String ANCIENT_CHARGE = "§x§F§F§0§0§4§CAncient Charge ♆";


    //PLEASE EXCUSE THE MESS, I AM BUILDING STILL LOL
    //PLEASE EXCUSE THE MESS, I AM BUILDING STILL LOL
    //PLEASE EXCUSE THE MESS, I AM BUILDING STILL LOL
    //PLEASE EXCUSE THE MESS, I AM BUILDING STILL LOL
    //PLEASE EXCUSE THE MESS, I AM BUILDING STILL LOL
    //PLEASE EXCUSE THE MESS, I AM BUILDING STILL LOL

    @EventHandler
    public static void applyCharge(InventoryClickEvent event){


        if (event.isLeftClick()) {
            Player player = (Player) event.getWhoClicked();
            ItemStack ancientFragement = player.getItemOnCursor();
            if (!ItemInfo.isTitanTool(event.getCurrentItem())) return;
            if (!isAncientFragment(ancientFragement)) return;
            player.sendMessage("Slot slot: " + event.getSlot());
            player.sendMessage("Slot slot type: " + event.getSlotType());
            Inventory inv = player.getInventory();
            Material charge = Material.FIRE_CHARGE;
            int taken = 0;
            addChargeLore(event.getCurrentItem(),ancientFragement.getAmount());

            player.sendMessage("Amount in hand: " + ancientFragement.getAmount());
            player.getItemOnCursor().setAmount(0);
            event.setCancelled(true);

            /*if (inv.contains(charge)) {
                for (int i = 0; i < inv.getSize(); i++) {
                    ItemStack itemSlot = player.getInventory().getItem(i);
                    if (itemSlot != null && itemSlot.getType().equals(charge)) {

                        int amount = itemSlot.getAmount();
                        int remainder = amount;
                        itemSlot.setAmount(remainder);
                        player.getInventory().setItem(i, remainder > 0 ? itemSlot: null);
                        player.updateInventory();
                        i = inv.getSize();
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Amount left in stack taken from: "
                                + ChatColor.YELLOW + remainder );
                    }
                }

            }*/
        }

    }

    public static boolean isAncientFragment(ItemStack item){
        if (!item.hasItemMeta()) return false;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {

            if (ANCIENT_CHARGE.contains(lore)) {
                return true;
            }
        }
        return false;
    }



    public static void addChargeLore(ItemStack item, Integer amount){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        Integer chargeIndex = index + 1;
        loreList.set(chargeIndex,ANCIENT_CHARGE + " : " + amount);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);

    }


}
