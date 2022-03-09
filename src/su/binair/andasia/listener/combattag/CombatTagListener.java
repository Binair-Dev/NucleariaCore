package su.binair.andasia.listener.combattag;

import su.binair.andasia.Main;
import su.binair.api.*;
import su.binair.andasia.utils.combattag.*;
import su.binair.utils.*;
import org.bukkit.entity.*;
import su.binair.andasia.*;
import org.apache.commons.lang.*;
import java.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;

public class CombatTagListener implements Listener
{
    @EventHandler
    public void onCombatTag(final EntityDamageByEntityEvent event) {
        final Entity entDamaged = event.getEntity();
        final Entity entDamager = event.getDamager();
        if (entDamaged instanceof Player && entDamager instanceof Player) {
            final Player player = (Player)entDamaged;
            final Player damager = (Player)entDamager;
            if (!FactionAPI.isInRegion(player.getLocation(), "spawn") && !FactionAPI.isInRegion(damager.getLocation(), "spawnest") && !FactionAPI.isInRegion(player.getLocation(), "sortienord") && !FactionAPI.isInRegion(damager.getLocation(), "spawnest") && !FactionAPI.isInRegion(player.getLocation(), "sortieouest") && !FactionAPI.isInRegion(damager.getLocation(), "spawnest") && !FactionAPI.isInRegion(player.getLocation(), "sortiesud") && !FactionAPI.isInRegion(damager.getLocation(), "spawnest") && !FactionAPI.isInRegion(player.getLocation(), "spawnest") && !FactionAPI.isInRegion(damager.getLocation(), "spawnest") && !FactionAPI.isSameFactionOrAlly(player, damager) && !FactionAPI.isNeutralAndCantFight(damager, player)) {
                if (!CombatUtils.tagged.containsKey(player)) {
                    ToolBox.sendMessage(player, "&eTu es maintenant en combat contre: &c" + damager.getName() + "&e!");
                }
                if (!CombatUtils.tagged.containsKey(damager)) {
                    ToolBox.sendMessage(damager, "&eTu es maintenant en combat contre: &c" + player.getName() + "&e!");
                }
                CombatUtils.addTagg(player);
                CombatUtils.addTagg(damager);
            }
        }
    }
    
    @EventHandler
    public void onCombatCommand(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String[] args = e.getMessage().split(" ");
        final List<String> allowed = (List<String>) Main.getInstance().getConfig("combat_tag").getConfig().getStringList("combat_allowed_cmds");
        if (CombatUtils.tagged.containsKey(p) && !StringUtils.containsIgnoreCase(allowed.toString(), args[0]) && !p.hasPermission("cwbasic.combattag.bypass")) {
            e.setCancelled(true);
            final String[] messages = { ToolBox.getBeautyStart() + "&cTu ne peux pas faire cette commande en combat!" };
            ToolBox.broadcast(messages);
        }
    }
    
    @EventHandler
    public void onCombatDie(final EntityDeathEvent e) {
        final Entity ent = (Entity)e.getEntity();
        if (ent instanceof Player) {
            final Player player = (Player)ent;
            if (CombatUtils.tagged.containsKey(player)) {
                CombatUtils.tagged.remove(player);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onCombatQuit(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        if (CombatUtils.tagged.containsKey(player)) {
            player.setHealth(0);
        }
    }
}
