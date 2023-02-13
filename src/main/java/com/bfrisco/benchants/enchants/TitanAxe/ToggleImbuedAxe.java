package com.bfrisco.benchants.enchants.TitanAxe;

import com.bfrisco.benchants.enchants.TitanRod.RodInfo;
import com.bfrisco.benchants.utils.BEnchantEffects;
import com.bfrisco.benchants.utils.ItemInfo;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ToggleImbuedAxe implements Listener {
    public static Material pick = Material.DIAMOND_PICKAXE;

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material coolDown = Material.JIGSAW;
        if (!event.getAction().isRightClick()) return;
        if (!player.isSneaking()) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != pick) return;
        if (!item.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;
        if (!item.hasItemMeta()) return;
        if (!ItemInfo.isTitanTool(item)) return;
        if (!ItemInfo.isImbued(item)) return;
        if (player.hasCooldown(coolDown)) return;
        player.setCooldown(coolDown,25);
        if (!player.hasPermission("benchants.toggle")) return;
        event.setCancelled(true);
        toggleImbuedAxe(item,player);
    }

    public static void toggleImbuedAxe(ItemStack item, Player player){
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return;
        if (AxeInfo.getImbuedState(item) == 1) {
            Axe1ToAxe2(item);
            new BEnchantEffects().disableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Axe set to Enchant2");
        } else if (AxeInfo.getImbuedState(item) == 2) {
            axe2ToAxe3(item);
            new BEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Axe set to Enchant3");
        } else if (AxeInfo.getImbuedState(item) == 3) {
            disableImbuedAxe(item);
            new BEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Axe set to dormant");
        } else if (ItemInfo.isDormantCharged(item)) {
            enableImbuedAxe(item);
            new BEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Axe set to Enchant1");
        }
    }

    public static void enableImbuedAxe(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_ONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void disableImbuedAxe(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }
    public static void Axe1ToAxe2(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_TWO);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }
    public static void axe2ToAxe3(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_THREE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }



}
