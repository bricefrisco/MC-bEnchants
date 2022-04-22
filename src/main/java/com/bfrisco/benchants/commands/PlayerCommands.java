package com.bfrisco.benchants.commands;

import com.bfrisco.benchants.utils.ItemInfo;
import com.bfrisco.benchants.utils.Toggle;
import org.bukkit.Bukkit;
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
        if (!ItemInfo.isTitanTool(item,player)) return false;
        if (ItemInfo.loreList(item,player) == null) return false;
        if (!player.hasPermission("benchants.imbue")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return false;
        }

        Material coolDown = Material.JIGSAW;
        if ("imbue".equalsIgnoreCase(args[0]) ){
            Bukkit.getServer().getConsoleSender().sendMessage("Successfully inside of imbue command");
            if (ItemInfo.isImbued(player)) {
                player.sendMessage(ChatColor.GREEN + "That item is already imbued!");
                return false;
            }
            if (!player.hasCooldown(coolDown)) {
                player.sendMessage(ChatColor.GREEN + "Are you sure you want to imbue this tool for $1,000,000?");
                player.sendMessage(ChatColor.GREEN + "Retype the command to confirm");
                player.setCooldown(coolDown, 200);
                return false;
            }
            List<String> loreList = ItemInfo.loreList(item,player);
            if (loreList == null) return false;
            for (String s : loreList) {
                //detects for any variant of ancient power color in titan tools
                //then either "deactivates" or "activates"
                if (s.equalsIgnoreCase(ItemInfo.ANCIENT_RED) || s.equalsIgnoreCase(ItemInfo.ANCIENT_YELLOW)
                        || s.equalsIgnoreCase(ItemInfo.ANCIENT_BLUE)) {
                    Toggle.removeEnchantment(item, player);
                    Toggle.addEnchantment(item, player);
                    return true;
                }
            }
            return false;
        } return true;
    }
}
