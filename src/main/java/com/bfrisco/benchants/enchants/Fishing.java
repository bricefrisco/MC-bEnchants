package com.bfrisco.benchants.enchants;

import com.bfrisco.benchants.utils.ItemInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class Fishing implements Listener {

    @EventHandler
    public void onFishEvent(PlayerFishEvent event){

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Inventory inv = player.getInventory();
        if (!ItemInfo.isTitanTool(item)) return;

        ItemStack puffer = new ItemStack(Material.PUFFERFISH);
        player.sendMessage(event.getState().toString());
        if (event.getState().toString().equalsIgnoreCase("CAUGHT_FISH")){
            player.sendMessage(event.getState().toString());
            player.sendMessage("Succeeded case");
            inv.addItem(puffer);

        }



    }




}
