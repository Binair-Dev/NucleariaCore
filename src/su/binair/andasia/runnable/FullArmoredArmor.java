package su.binair.andasia.runnable;

import su.binair.andasia.*;
import org.bukkit.potion.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class FullArmoredArmor extends EffectRunnable
{
    public FullArmoredArmor(final Main plugin) {
        super(plugin, "FullArmored", new PotionEffect[] { new PotionEffect(PotionEffectType.HEALTH_BOOST, 1000000, 2, true), new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 1, true), new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 0, true), new PotionEffect(PotionEffectType.SLOW, 1000000, 1, true) });
    }
    
    @Override
    protected boolean check(final Player player) {
        final PlayerInventory pi = player.getInventory();
        return pi.getHelmet() != null && pi.getHelmet().getType() == Material.ARMORED_HELMET && pi.getChestplate() != null && pi.getChestplate().getType() == Material.ARMORED_CHESTPLATE && pi.getLeggings() != null && pi.getLeggings().getType() == Material.ARMORED_LEGGINGS && pi.getBoots() != null && pi.getBoots().getType() == Material.ARMORED_BOOTS;
    }
}
