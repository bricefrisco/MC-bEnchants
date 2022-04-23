package com.bfrisco.benchants.commands;

import com.bfrisco.benchants.BEnchants;
import com.bfrisco.benchants.enchants.Durability;
import com.bfrisco.benchants.enchants.Trench;
import com.bfrisco.benchants.utils.ItemInfo;
import com.bfrisco.benchants.utils.Toggle;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        ItemStack item = player.getInventory().getItemInMainHand();
/*        if (!ItemInfo.isTitanTool(item)) {
            player.sendMessage(ChatColor.RED + "You must be holding a Titan Tool!");
            return false;
        }*/
        if (!player.hasPermission("benchants.enchant")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length == 0) return false;
        if ("reload".equalsIgnoreCase(args[0])) {
            BEnchants.LOGGER.info("Reloading config...");
            BEnchants.PLUGIN.reloadConfig();
            Trench.loadConfig();
            Durability.loadConfig();
            player.sendMessage(ChatColor.GREEN + "Successfully reloaded config.");
            return true;
        }

        if (args.length != 2) return false;
        if (!"apply".equalsIgnoreCase(args[0]) && !"remove".equalsIgnoreCase(args[0])) return false;

        if ("trench".equalsIgnoreCase(args[1])) {
            if ("apply".equalsIgnoreCase(args[0])) {
                Trench.apply(item, player);
            }

            if ("remove".equalsIgnoreCase(args[0])) {
                Trench.remove(item, player);
            }

            return true;
        }

        if ("durability".equalsIgnoreCase(args[1])) {
            if ("apply".equalsIgnoreCase(args[0])) {
                Durability.apply(item, player);
            }

            if ("remove".equalsIgnoreCase(args[0])) {
                Durability.remove(item, player);
            }

            return true;
        }

        player.sendMessage(ChatColor.RED + "Unknown enchant '" + args[1] + "'. " +
                "Valid enchants are: 'durability', 'trench'");
        return true;
    }

}
