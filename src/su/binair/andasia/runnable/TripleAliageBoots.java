package su.binair.andasia.runnable;

import su.binair.andasia.*;
import org.bukkit.potion.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class TripleAliageBoots extends EffectRunnable
{
    public TripleAliageBoots(final Main plugin) {
        super(plugin, "TripleAliageBoots", new PotionEffect[] { new PotionEffect(PotionEffectType.FEATHER_FALLING, 1000000, 0, true) });
    }
    
    @Override
    protected boolean check(final Player player) {
        final ItemStack stack = player.getInventory().getBoots();
        return stack != null && stack.getType() == Material.TRIPLEALIAGE_BOOTS;
    }
}
