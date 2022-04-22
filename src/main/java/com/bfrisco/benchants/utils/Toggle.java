package com.bfrisco.benchants.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class Toggle implements Listener {



    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Material coolDown = Material.JIGSAW;
        if (!event.getAction().isRightClick()) return; //should reduce the amount of times the interactevent continues to use resources
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!ItemInfo.isTitanTool(item)) return;
        if (!ItemInfo.isImbued(item)) return;
        if (player.hasCooldown(coolDown)) return;
        player.setCooldown(coolDown,25);
        player.sendMessage("Made it past isImbued check in toggle event");

        if (!player.isSneaking()) return; //should reduce the amount of times the interactevent continues to use resources

        if (!player.hasPermission("benchants.toggle")) return;

        toggleAncientPower(item,player);
        new BEnchantEffects().toggleEffect(player);
    }

    public static void toggleAncientPower(ItemStack item, Player player){
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return;
        if (ItemInfo.isActive(item)) {
            disableAncientPower(item);

        } else
        enableAncientPower(item);

    }

    public static void imbue(ItemStack item){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.ANCIENT_POWER_ACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);

    }
    public static void enableAncientPower(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.ANCIENT_POWER_ACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);

    }
    public static void disableAncientPower(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.ANCIENT_POWER_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);

    }


}
