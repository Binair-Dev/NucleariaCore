package su.binair.andasia.runnable;

import su.binair.andasia.*;
import org.bukkit.potion.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class TripleAliageChestplate extends EffectRunnable
{
    public TripleAliageChestplate(final Main m) {
        super(m, "TripleAliageChestplate", new PotionEffect[] { new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 0, true) });
    }
    
    @Override
    protected boolean check(final Player player) {
        final ItemStack stack = player.getInventory().getChestplate();
        return stack != null && stack.getType() == Material.TRIPLEALIAGE_CHESTPLATE;
    }
}
