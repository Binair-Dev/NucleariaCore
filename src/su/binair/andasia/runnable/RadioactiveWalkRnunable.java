package su.binair.andasia.runnable;

import org.bukkit.scheduler.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.potion.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class RadioactiveWalkRnunable extends BukkitRunnable
{
    public void run() {
        for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!this.isWearingRunnedHelmet(player) && (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.RADIOACTIVE_DIRT || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.RADIOACTIVE_STONE)) {
                if (player.hasPotionEffect(PotionEffectType.CONFUSION)) {
                    player.removePotionEffect(PotionEffectType.CONFUSION);
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 0));
                player.damage(1.5);
            }
        }
    }
    
    public boolean isWearingRunnedHelmet(final Player player) {
        final ItemStack helmet = player.getInventory().getHelmet();
        return helmet != null && helmet.getEnchantments().toString().contains("65,");
    }
}
