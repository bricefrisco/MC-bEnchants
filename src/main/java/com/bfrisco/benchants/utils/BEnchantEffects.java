package com.bfrisco.benchants.utils;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class BEnchantEffects {

    public void addEffect(Player player) {

        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,player.getEyeLocation(),100);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,10, 1);


    }

    public void removeEffect(Player player) {

        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,player.getEyeLocation(),100);
        player.getWorld().playSound(player.getLocation(),Sound.BLOCK_BEACON_ACTIVATE,10, 1);

    }

}
