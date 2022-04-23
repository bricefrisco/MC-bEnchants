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
            ItemStack itemOnCursor = player.getItemOnCursor();
            ItemStack itemClicked = event.getCurrentItem();
            Integer numberOfCharge = itemOnCursor.getAmount();
            if (!ItemInfo.isAncientFragment(itemOnCursor)) return;
            if (event.getCurrentItem() == null) return;
            if (!ItemInfo.isTitanTool(itemClicked)) return;
            //player.sendMessage("itemOnCursor " + itemOnCursor);
            //player.sendMessage("Slot slot: " + event.getSlot());
            //player.sendMessage("Slot slot type: " + event.getSlotType());
            //player.sendMessage("Amount in hand: " + itemOnCursor.getAmount());
            addChargeLore(itemClicked,numberOfCharge);
            player.getItemOnCursor().setAmount(0);
            event.setCancelled(true);
        }

    }

    public static void addChargeLore(ItemStack item, Integer amount){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        Integer chargeIndex = index + 1;

        if (ItemInfo.hasCharge(item)) {
            Bukkit.getServer().getConsoleSender().sendMessage("Inside addChargeLore passed hasCharge test");
            String string = loreList.get(chargeIndex);
            String string1 = string.substring(30);
            int previousCharge = Integer.parseInt(string1);
            int newCharge = previousCharge + amount;
            Bukkit.getServer().getConsoleSender().sendMessage(String.valueOf(amount));
            Bukkit.getServer().getConsoleSender().sendMessage(String.valueOf(previousCharge));
            Bukkit.getServer().getConsoleSender().sendMessage(String.valueOf(newCharge));
            loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE + ": " + newCharge);
            ItemMeta meta = item.getItemMeta();
            meta.setLore(loreList);
            item.setItemMeta(meta);
            return;
        } else
        loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE + ": " + amount);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);

    }


}
