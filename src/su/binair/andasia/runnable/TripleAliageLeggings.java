package su.binair.andasia.runnable;

import su.binair.andasia.*;
import org.bukkit.potion.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class TripleAliageLeggings extends EffectRunnable
{
    public TripleAliageLeggings(final Main plugin) {
        super(plugin, "TripleAliageLeggings", new PotionEffect[] { new PotionEffect(PotionEffectType.SPEED, 1000000, 1, true) });
    }
    
    @Override
    protected boolean check(final Player player) {
        final ItemStack stack = player.getInventory().getLeggings();
        return stack != null && stack.getType() == Material.TRIPLEALIAGE_LEGGINGS;
    }
}
