package su.binair.andasia.effects;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import su.binair.api.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;

public class FeatherFallingListener implements Listener
{
    @EventHandler
    public void onPlayerDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player)e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                final double x = p.getLocation().getX();
                final double y = p.getLocation().getY() - 1.0;
                final double z = p.getLocation().getZ();
                final World w = p.getWorld();
                final Location loc = new Location(w, x, y, z);
                final Block b = w.getBlockAt(loc);
                if (p.hasPotionEffect(PotionEffectType.FEATHER_FALLING)) {
                    e.setCancelled(true);
                }
                if (FactionAPI.isWarzone(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
