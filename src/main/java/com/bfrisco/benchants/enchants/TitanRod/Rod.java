package com.bfrisco.benchants.enchants.TitanRod;

import com.bfrisco.benchants.utils.ChargeManagement;
import com.bfrisco.benchants.utils.ItemInfo;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;


public class Rod implements Listener {

    public static final String TROPICALFISH = "Tropical Fish";
    public static final String PUFFERFISH = "Pufferfish";
    public static final String RAWCOD = "Raw Cod";
    public static final String RAWSALMON = "Raw Salmon";

    public static final ItemStack TropicalFish = new ItemStack(Material.TROPICAL_FISH);
    public static final ItemStack PufferFish = new ItemStack(Material.PUFFERFISH);
    public static final ItemStack Raw_Cod = new ItemStack(Material.COD);
    public static final ItemStack Raw_Salmon = new ItemStack(Material.SALMON);

    public static final ItemStack Raw_Cod2 = new ItemStack(Material.COD,2);
    public static final ItemStack Raw_Salmon2 = new ItemStack(Material.SALMON,2);

    public static final ItemStack Raw_Cod3 = new ItemStack(Material.COD,3);
    public static final ItemStack Raw_Salmon3 = new ItemStack(Material.SALMON,3);

    @EventHandler
    public void onFishEvent(PlayerFishEvent event){
        Player player = event.getPlayer();
        Location location = player.getEyeLocation();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!ItemInfo.isTitanTool(item)) return;
        if (!ItemInfo.isActiveImbued(item) && !ItemInfo.isActiveCharged(item)) return;
        if (!event.getState().toString().equalsIgnoreCase("CAUGHT_FISH")) return;
        player.sendMessage(event.getState().toString());
        player.sendMessage("Succeeded case");

        if (ItemInfo.isLevelOne(item)) {
            if (event.getCaught().getName().equalsIgnoreCase(TROPICALFISH)) {
                event.getPlayer().getWorld().dropItem(location, TropicalFish);
                ChargeManagement.decreaseChargeLore(item, player);
            }
            if (event.getCaught().getName().equalsIgnoreCase(PUFFERFISH)) {
                event.getPlayer().getWorld().dropItem(location, PufferFish);
                ChargeManagement.decreaseChargeLore(item, player);
            }
            if (event.getCaught().getName().equalsIgnoreCase(RAWCOD)) {
                event.getPlayer().getWorld().dropItem(location, Raw_Cod);
                ChargeManagement.decreaseChargeLore(item, player);
            }
            if (event.getCaught().getName().equalsIgnoreCase(RAWSALMON)) {
                event.getPlayer().getWorld().dropItem(location, Raw_Salmon);
                ChargeManagement.decreaseChargeLore(item, player);
            }

        } else if (ItemInfo.isLevelTwo(item)) {
            if (event.getCaught().getName().equalsIgnoreCase(TROPICALFISH)) {
                event.getPlayer().getWorld().dropItem(location, TropicalFish);
                ChargeManagement.decreaseChargeLore2(item,player);
            }
            if (event.getCaught().getName().equalsIgnoreCase(PUFFERFISH)) {
                event.getPlayer().getWorld().dropItem(location, PufferFish);
                ChargeManagement.decreaseChargeLore2(item,player);
            }
            if (event.getCaught().getName().equalsIgnoreCase(RAWCOD)) {
                event.getPlayer().getWorld().dropItem(location, Raw_Cod2);
                ChargeManagement.decreaseChargeLore2(item,player);
            }
            if (event.getCaught().getName().equalsIgnoreCase(RAWSALMON)) {
                event.getPlayer().getWorld().dropItem(location, Raw_Salmon2);
                ChargeManagement.decreaseChargeLore2(item,player);
            }
        } else if (ItemInfo.isLevelThree(item)) {
            if (event.getCaught().getName().equalsIgnoreCase(TROPICALFISH)) {
                event.getPlayer().getWorld().dropItem(location, TropicalFish);
                ChargeManagement.decreaseChargeLore3(item,player);
            }
            if (event.getCaught().getName().equalsIgnoreCase(PUFFERFISH)) {
                event.getPlayer().getWorld().dropItem(location, PufferFish);
                ChargeManagement.decreaseChargeLore3(item,player);
            }
            if (event.getCaught().getName().equalsIgnoreCase(RAWCOD)) {
                event.getPlayer().getWorld().dropItem(location, Raw_Cod3);
                ChargeManagement.decreaseChargeLore3(item,player);
            }
            if (event.getCaught().getName().equalsIgnoreCase(RAWSALMON)) {
                event.getPlayer().getWorld().dropItem(location, Raw_Salmon3);
                ChargeManagement.decreaseChargeLore3(item,player);
            }
        }
    }
}
