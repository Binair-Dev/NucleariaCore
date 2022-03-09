package su.binair.andasia.listener.food;

import org.bukkit.event.player.*;
import org.bukkit.potion.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class FoodListener implements Listener
{
    @EventHandler
    public void onItemClick(final PlayerItemConsumeEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getItemInHand();
        if (item.getTypeId() == 491) {
            event.setCancelled(true);
            if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 3600, 0));
            player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.getMaterial(491), 1) });
            if (player.getFoodLevel() < 20) {
                player.setFoodLevel(player.getFoodLevel() + 4);
            }
        }
        if (item.getTypeId() == 493) {
            event.setCancelled(true);
            if (player.hasPotionEffect(PotionEffectType.SPEED)) {
                player.removePotionEffect(PotionEffectType.SPEED);
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3600, 1));
            player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.getMaterial(493), 1) });
            if (player.getFoodLevel() < 20) {
                player.setFoodLevel(player.getFoodLevel() + 4);
            }
        }
        if (item.getTypeId() == 495) {
            event.setCancelled(true);
            if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3600, 0));
            player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.getMaterial(495), 1) });
            if (player.getFoodLevel() < 20) {
                player.setFoodLevel(player.getFoodLevel() + 4);
            }
        }
        if (item.getTypeId() == 496) {
            event.setCancelled(true);
            if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 9600, 0));
            player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.getMaterial(496), 1) });
            if (player.getFoodLevel() < 20) {
                player.setFoodLevel(player.getFoodLevel() + 4);
            }
        }
        if (item.getTypeId() == 497) {
            event.setCancelled(true);
            if (player.hasPotionEffect(PotionEffectType.SPEED)) {
                player.removePotionEffect(PotionEffectType.SPEED);
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9600, 1));
            player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.getMaterial(497), 1) });
            if (player.getFoodLevel() < 20) {
                player.setFoodLevel(player.getFoodLevel() + 4);
            }
        }
        if (item.getTypeId() == 498) {
            event.setCancelled(true);
            if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9600, 0));
            player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.getMaterial(498), 1) });
            if (player.getFoodLevel() < 20) {
                player.setFoodLevel(player.getFoodLevel() + 4);
            }
        }
    }
}
