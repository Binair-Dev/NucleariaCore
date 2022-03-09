package su.binair.andasia.runnable;

import su.binair.andasia.*;
import org.bukkit.potion.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class TripleAliageHelmet extends EffectRunnable
{
    public TripleAliageHelmet(final Main plugin) {
        super(plugin, "TripleAliageHelmet", new PotionEffect[] { new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 0, true) });
    }
    
    @Override
    protected boolean check(final Player player) {
        final ItemStack stack = player.getInventory().getHelmet();
        return stack != null && stack.getType() == Material.TRIPLEALIAGE_HELMET;
    }
}
