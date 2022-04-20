package com.bfrisco.benchants.commands;

import com.bfrisco.benchants.utils.Toggle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerCommands implements CommandExecutor{


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (!player.hasPermission("benchants.toggle")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (!(((Player) sender).getInventory().getItemInMainHand().hasItemMeta())) {
            player.sendMessage(net.md_5.bungee.api.ChatColor.RED + "You must have an item in your hand!");
            return true;
        }
        if (args.length == 0) return false;

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        List<String> loreList = meta.getLore();
        Material coolDown = Material.JIGSAW;
        if ("imbue".equalsIgnoreCase(args[0]) ){
            Bukkit.getServer().getConsoleSender().sendMessage("Successfully inside of imbue command");
            if (!Toggle.isTitanTool(player)) return false;
            player.sendMessage("passed titan test");
            if (!(Toggle.isImbued(player))) {
                player.sendMessage("Are you sure you want to imbue this tool for $1,000,000?");
                player.sendMessage("Retype the command to confirm");
                if (player.hasCooldown(coolDown)) {
                    for (int i = 0; i < loreList.size(); i++) {
                        //detects for any variant of ancient power color in titan tools
                        //then either "deactivates" or "activates"

                        if (loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_RED) || loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_YELLOW)
                                || loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_BLUE)) {
                            Toggle.removeEnchantment(item, player, i);
                            Toggle.addEnchantment(item, player, i);
                        }

                    }
                }
                player.setCooldown(coolDown, 250);
            } else if (Toggle.isImbued(player)){
                player.sendMessage("That item already has already been imbued!");
            }
        }



        return true;
    }
}
