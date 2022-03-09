package su.binair.andasia.listener.player;

import org.bukkit.event.player.*;
import org.bukkit.potion.*;
import su.binair.utils.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class VisionListener implements Listener
{
    @EventHandler
    public void on(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/vision")) {
            e.setCancelled(true);
            if (!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 0));
                ToolBox.sendMessage(p, "Tu viens d'activer la vision nocturne.");
            }
            else {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                ToolBox.sendMessage(p, "Tu viens de d\u00e9sactiver la vision nocturne.");
            }
        }
    }
}
