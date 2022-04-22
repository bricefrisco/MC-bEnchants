package com.bfrisco.benchants.commands;

import com.bfrisco.benchants.utils.BEnchantEffects;
import com.bfrisco.benchants.utils.ItemInfo;
import com.bfrisco.benchants.utils.Toggle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PlayerCommands implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (args.length == 0) return false;
        if (!(sender instanceof Player player)) return false;
        player.sendMessage(ChatColor.RED + "------Debug------");
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!ItemInfo.isTitanTool(item)) return false;
        //if (ItemInfo.loreList(item,player) == null) return false;
        if (!player.hasPermission("benchants.imbue")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return false;
        }

        Material coolDown = Material.JIGSAW;
        if ("imbue".equalsIgnoreCase(args[0]) ){
            if (ItemInfo.isImbued(item)) {
                player.sendMessage(ChatColor.GREEN + "That item is already imbued!");
                return false;
            }
            if (!player.hasCooldown(coolDown)) {
                player.sendMessage(ChatColor.GREEN + "Are you sure you want to imbue this tool for $1,000,000?");
                player.sendMessage(ChatColor.GREEN + "Retype the command to confirm");
                player.setCooldown(coolDown, 200);
                return false;
            }
            List<String> loreList = item.getItemMeta().getLore();
            if (loreList == null) return false;
            for (String lore : loreList) {
                //detects for any variant of ancient power color in titan tools
                //then either "deactivates" or "activates"
                if (ItemInfo.UNIMBUED_LORE.contains(lore)) {
                    Toggle.imbue(item);
                    new BEnchantEffects().toggleEffect(player);
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
