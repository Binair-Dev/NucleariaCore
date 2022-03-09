package su.binair.andasia.listener.combat;

import org.bukkit.entity.*;
import org.bukkit.potion.*;
import su.binair.api.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class InvisibilityListener implements Listener
{
    @EventHandler
    public void onAttack(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player p = (Player)e.getDamager();
            if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
                p.sendMessage(PrefixAPI.getPrefix() + " Vous n'été es plus invisible car vous avez attaququelqu'un.");
            }
        }
    }
    
    @EventHandler
    public void onPotionSplash(final PotionSplashEvent e) {
        if (!e.getAffectedEntities().isEmpty() && e.getPotion().getShooter() instanceof Player) {
            final Player p = (Player)e.getPotion().getShooter();
            if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
                p.sendMessage(PrefixAPI.getPrefix() + " Vous n'été es plus invisible car vous avez attaququelqu'un.");
            }
        }
    }
}
