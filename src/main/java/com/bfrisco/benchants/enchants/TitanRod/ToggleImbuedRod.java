package com.bfrisco.benchants.enchants.TitanRod;

import com.bfrisco.benchants.enchants.TitanShovel.ShovelInfo;
import com.bfrisco.benchants.utils.BEnchantEffects;
import com.bfrisco.benchants.utils.ItemInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ToggleImbuedRod implements Listener {
    public static Material rod = Material.FISHING_ROD;

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material coolDown = Material.JIGSAW;
        if (!event.getAction().isRightClick()) return;
        if (!player.isSneaking()) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != rod) return;
        if (!item.hasItemMeta()) return;
        if (!ItemInfo.isTitanTool(item)) return;
        if (!ItemInfo.isImbued(item)) return;
        if (player.hasCooldown(coolDown)) return;
        player.setCooldown(coolDown,25);
        if (!player.hasPermission("benchants.toggle")) return;
        event.setCancelled(true);
        toggleImbuedRodEnchant(item,player);
    }

    public static void toggleImbuedRodEnchant(ItemStack item, Player player){
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return;
        if (RodInfo.getImbuedState(item) == 1) {
            rod1ToRod2(item);
            new BEnchantEffects().disableEffect(player);
            player.sendActionBar("Rod set to Enchant2");
        } else if (RodInfo.getImbuedState(item) == 2) {
            rod2ToRod3(item);
            new BEnchantEffects().enableEffect(player);
            player.sendActionBar("Rod set to Enchant3");
        } else if (RodInfo.getImbuedState(item) == 3) {
            disableImbuedRod(item);
            new BEnchantEffects().enableEffect(player);
            player.sendActionBar("Rod set to dormant");
        } else if (ItemInfo.isDormantCharged(item)) {
            enableImbuedRod(item);
            new BEnchantEffects().enableEffect(player);
            player.sendActionBar("Rod set to Enchant1");
        }
    }

    public static void enableImbuedRod(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_ONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void disableImbuedRod(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }
    public static void rod1ToRod2(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_TWO);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }
    public static void rod2ToRod3(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_THREE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }



}
