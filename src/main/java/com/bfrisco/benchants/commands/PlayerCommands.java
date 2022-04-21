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
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PlayerCommands implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {


        if (!(sender instanceof Player player)) return false;
        player.sendMessage(ChatColor.RED + "------Debug------");
        if (!(Toggle.isTitanTool(player,Toggle.loreList(player)))) return false;
        if (Toggle.loreList(player) == null) return false;
        if (!(player.hasPermission("benchants.imbue"))) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return false;
        }
        if (!((Player) sender).getInventory().getItemInMainHand().hasItemMeta()) {
            //TODO: I DO NOT like this message popping up
            player.sendMessage(net.md_5.bungee.api.ChatColor.RED + "You must have an item in your hand!");
            return true;
        }
        if (args.length == 0) return false;

        ItemStack item = player.getInventory().getItemInMainHand();
        Material coolDown = Material.JIGSAW;
        if ("imbue".equalsIgnoreCase(args[0]) ){
            Bukkit.getServer().getConsoleSender().sendMessage("Successfully inside of imbue command");
            if (!(Toggle.isTitanTool(player,Toggle.loreList(player)))) return false;
            player.sendMessage("passed titan test");
            if (!(Toggle.isImbued(Toggle.loreList(player)))) {

                //TODO:Fix message sending twice after confirmation
                if (!player.hasCooldown(coolDown)) {
                    player.sendMessage("Are you sure you want to imbue this tool for $1,000,000?");
                    player.sendMessage("Retype the command to confirm");
                    player.setCooldown(coolDown, 200);
                    return false;
                }
                if ((player.hasCooldown(coolDown))) {

                    List<String> loreList = Toggle.loreList(player);
                    if (loreList == null) return false;
                    for (int i = 0; i < loreList.size(); i++) {
                        //detects for any variant of ancient power color in titan tools
                        //then either "deactivates" or "activates"
                        if (loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_RED) || loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_YELLOW)
                                || loreList.get(i).equalsIgnoreCase(Toggle.ANCIENT_BLUE)) {
                            Toggle.removeEnchantment(item, player, i);
                            Toggle.addEnchantment(item, player, i);
                            return true;
                        }
                    } return false;
                } return false;
            } else if (Toggle.isImbued(Toggle.loreList(player))){
                player.sendMessage(ChatColor.RED + "That item already has already been imbued!");
            } return false;
        } return true;
    }
}
