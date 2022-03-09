package su.binair.andasia.listener.freeze;

import su.binair.andasia.utils.staff.*;
import su.binair.api.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class FreezeListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStaffQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (StaffUtils.inFreeze.contains(player)) {
            player.getInventory().clear();
            player.kickPlayer(PrefixAPI.getPrefix() + "§cTu est maintenant bannis définitivement de §a§lAndasia §c!");
            player.setBanned(true);
        }
    }
    
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent e) {
        final Location from = e.getFrom();
        final Location to = e.getTo();
        final Player player = e.getPlayer();
        if (from.getBlockY() < to.getBlockY() && !player.isFlying() && StaffUtils.inFreeze.contains(player)) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onFreezeDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player)event.getEntity();
            if (StaffUtils.inFreeze.contains(player)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onFreezeCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if (StaffUtils.inFreeze.contains(player)) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onFreezeChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (StaffUtils.inFreeze.contains(player)) {
            event.setCancelled(true);
        }
    }
}
