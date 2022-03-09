package su.binair.andasia.runnable;

import su.binair.andasia.*;
import org.bukkit.potion.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.metadata.*;

public abstract class EffectRunnable implements Runnable
{
    private final Main plugin;
    private final String metadata;
    private final List<PotionEffect> effects;
    
    protected EffectRunnable(final Main plugin, final String metadata, final PotionEffect... effects) {
        this.plugin = plugin;
        this.metadata = metadata;
        this.effects = Arrays.asList(effects);
    }
    
    @Override
    public void run() {
        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            if (this.check(player)) {
                for (final PotionEffect effect : this.effects) {
                    player.addPotionEffect(effect, true);
                }
                player.setMetadata(this.metadata, (MetadataValue)new HasEffectMeta());
            }
            else if (player.hasMetadata(this.metadata)) {
                for (final PotionEffect effect : this.effects) {
                    player.removePotionEffect(effect.getType());
                }
                player.removeMetadata(this.metadata, (Plugin)this.plugin);
            }
        }
    }
    
    protected abstract boolean check(final Player p0);
    
    private class HasEffectMeta extends MetadataValueAdapter
    {
        private HasEffectMeta() {
            super((Plugin)EffectRunnable.this.plugin);
        }
        
        public Object value() {
            return Boolean.TRUE;
        }
        
        public void invalidate() {
        }
    }
}
