package com.bfrisco.benchants.commands;

import com.bfrisco.benchants.enchants.Durability;
import com.bfrisco.benchants.enchants.Trench;
import com.bfrisco.benchants.utils.Toggle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.EventListener;
import java.util.List;

public class PlayerCommands implements CommandExecutor, EventListener {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
        //HEX codes convert to capital letters when for whatever reason
        //Using spigots api for lore, paper's is fucking weird




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

        if ("toggle".equalsIgnoreCase(args[0])){
            Bukkit.getServer().getConsoleSender().sendMessage("Successfully inside of toggle command");
                boolean hadAncientPower = false;
                for (int i = 0; i < loreList.size(); i++) {
                    //detects for any variant of ancient power color in titan tools
                    //then either "deactivates" or "activates"

                    if (loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_RED) || loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_YELLOW)
                            || loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_BLUE) || loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_POWER_ACTIVE)) {
                        Toggle.removeEnchantment(item, player, i);
                        hadAncientPower = true;
                    } else if (loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_POWER_INACTIVE)) {
                        Toggle.addEnchantment(item, player, i);
                        hadAncientPower = true;
                    }

                }
            if (!hadAncientPower) player.sendMessage("You are not holding a titan tool");
        }



        return true;
    }
}
