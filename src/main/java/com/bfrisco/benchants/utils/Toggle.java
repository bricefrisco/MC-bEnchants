package com.bfrisco.benchants.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import static com.bfrisco.benchants.utils.ItemInfo.loreList;

public class Toggle implements Listener {



    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return;
        player.setCooldown(coolDown,40);
        if (!event.getAction().isRightClick()) return; //should reduce the amount of times the interactevent continues to use resources
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!ItemInfo.isTitanTool(item,player)) return;
        if (!player.isSneaking()) return; //should reduce the amount of times the interactevent continues to use resources

         //reduces the amount of times this can be spammed
        player.sendMessage(ChatColor.RED + "------Debug Start------");


        player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to !isImbued(loreList(p) check");
        if (!ItemInfo.isImbued(player)){
            player.sendMessage(ChatColor.AQUA + "DebugSend: Item is not imbued");
            return;
        }

        if (!player.hasPermission("benchants.toggle")) {

            player.sendMessage("No permission.");
            return;
        }

        if (ItemInfo.isActive(item,player)){
            player.sendMessage(ChatColor.AQUA + "Dbug2: Passed isActive(loreList(p)) test");
            player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to toggleActive(p,item,loreList(p)) method");
            toggleActive(player,item);
        } else {
            player.sendMessage( ChatColor.AQUA + "Dbug3: Did not pass isActive text");
            player.sendMessage(ChatColor.AQUA + "DebugSend: Sent to toggleActive method");
            toggleActive(player,item);
        }

    }

    public static void toggleActive(Player player,ItemStack item){
        List<String> loreList = loreList(item,player);
        if (loreList == null) return;
        if (ItemInfo.isActive(item,player)) {
            removeEnchantment(item,player);
            return;
        }
        addEnchantment(item,player);

    }

    public static void removeEnchantment(ItemStack item, Player player) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.ANCIENT_POWER_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
        new BEnchantEffects().addEffect(player);

    }
    public static void addEnchantment (ItemStack item, Player player) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.ANCIENT_POWER_ACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
        new BEnchantEffects().removeEffect(player);

    }

}
