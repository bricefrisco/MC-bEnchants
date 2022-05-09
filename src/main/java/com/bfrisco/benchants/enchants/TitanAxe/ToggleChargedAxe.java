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

public class ToggleChargedAxe implements Listener {

    public static Material axe = Material.DIAMOND_AXE;

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material coolDown = Material.JIGSAW;

        if (!event.getAction().isRightClick()) return;
        if (!player.isSneaking()) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != axe) return;
        if (!item.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;
        if (!item.hasItemMeta()) return;
        if (!ItemInfo.isTitanTool(item)) return;
        if (!ItemInfo.hasCharge(item)) return;
        if (player.hasCooldown(coolDown)) return;
        player.setCooldown(coolDown,25);
        if (!player.hasPermission("benchants.charge.toggle")) return;
        player.sendMessage("Passed activateClick checks");
        event.setCancelled(true);
        toggleChargedAxeEnchant(item,player);
    }

    public static void toggleChargedAxeEnchant(ItemStack item, Player player){
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return;
        if (AxeInfo.getChargedState(item) == 1) {
            axe1ToAxe2(item);
            new BEnchantEffects().disableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Axe set to Enchant2");
        } else if (AxeInfo.getChargedState(item) == 2) {
            axe2ToAxe3(item);
            new BEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Axe set to Enchant3");
        } else if (AxeInfo.getChargedState(item) == 3) {
            disableChargedItem(item);
            new BEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Axe set to dormant");
        } else if (ItemInfo.isDormantCharged(item)) {
            enableChargedItem(item);
            new BEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Axe set to Enchant1");
        }

    }

    public static void enableChargedItem(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_ONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void disableChargedItem(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }
    public static void axe1ToAxe2(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_TWO);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }
    public static void axe2ToAxe3(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_THREE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }



}
