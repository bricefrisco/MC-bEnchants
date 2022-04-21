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

        if (args.length == 0) return false;
        if (!(sender instanceof Player player)) return false;
        player.sendMessage(ChatColor.RED + "------Debug------");
        if (!(Toggle.isTitanTool(player,Toggle.loreList(player)))) return false;
        if (Toggle.loreList(player) == null) return false;
        if (!(player.hasPermission("benchants.imbue"))) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return false;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        Material coolDown = Material.JIGSAW;
        if ("imbue".equalsIgnoreCase(args[0]) ){
            Bukkit.getServer().getConsoleSender().sendMessage("Successfully inside of imbue command");
            //if (!(Toggle.isTitanTool(player,Toggle.loreList(player)))) return false;
            if (Toggle.isImbued(Toggle.loreList(player))) {
                player.sendMessage(ChatColor.GREEN + "That item is already imbued!");
            }
            if (!(Toggle.isImbued(Toggle.loreList(player)))) {


                if (!player.hasCooldown(coolDown)) {
                    player.sendMessage(ChatColor.GREEN + "Are you sure you want to imbue this tool for $1,000,000?");
                    player.sendMessage(ChatColor.GREEN + "Retype the command to confirm");
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
                            Toggle.removeEnchantment(loreList, item, player, i);
                            Toggle.addEnchantment(loreList, item, player, i);
                            return true;
                        }
                    } return false;
                } return false;

            } return false;
        } return true;
    }
}
