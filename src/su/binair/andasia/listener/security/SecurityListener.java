package su.binair.andasia.listener.security;

import su.binair.andasia.Main;
import su.binair.api.*;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.util.org.apache.commons.lang3.*;
import su.binair.andasia.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.*;

public class SecurityListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreCommand(final PlayerCommandPreprocessEvent event) {
        final Player p = event.getPlayer();
        if (event.getMessage().contains(":") && !p.hasPermission("andasia.chat.bypass")) {
            event.setCancelled(true);
            p.sendMessage(PrefixAPI.getPrefix() + EnumChatFormat.RED + " Tu ne peux pas mettre de ':' dans cette commande.");
        }
        if (StringUtils.startsWithIgnoreCase((CharSequence)event.getMessage(), (CharSequence)"/epay") && event.getMessage().contains(".")) {
            event.setCancelled(true);
            p.sendMessage(PrefixAPI.getPrefix() + EnumChatFormat.RED + " Tu ne peux pas mettre de '.' dans cette commande.");
        }
        if (StringUtils.startsWithIgnoreCase((CharSequence)event.getMessage(), (CharSequence)"/pay") && event.getMessage().contains(".")) {
            event.setCancelled(true);
            p.sendMessage(PrefixAPI.getPrefix() + EnumChatFormat.RED + " Tu ne peux pas mettre de '.' dans cette commande.");
        }
        if ((!Main.getInstance().getConfig("stafflist").getConfig().getList("staffs").contains(p.getName()) && p.isOp()) || (!Main.getInstance().getConfig("stafflist").getConfig().getList("staffs").contains(p.getName()) && p.hasPermission("*"))) {
            event.setCancelled(true);
            p.setBanned(true);
            p.kickPlayer(PrefixAPI.getPrefix() + " Tentative de Hack!");
        }
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if ((!Main.getInstance().getConfig("stafflist").getConfig().getList("staffs").contains(p.getName()) && p.isOp()) || (!Main.getInstance().getConfig("stafflist").getConfig().getList("staffs").contains(p.getName()) && p.hasPermission("*"))) {
            e.setCancelled(true);
            p.setBanned(true);
            p.kickPlayer(PrefixAPI.getPrefix() + " Tentative de Hack!");
        }
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if ((!Main.getInstance().getConfig("stafflist").getConfig().getList("staffs").contains(p.getName()) && p.isOp()) || (!Main.getInstance().getConfig("stafflist").getConfig().getList("staffs").contains(p.getName()) && p.hasPermission("*")) || (!Main.getInstance().getConfig("stafflist").getConfig().getList("staffs").contains(p.getName()) && p.getGameMode() == GameMode.CREATIVE)) {
            p.setBanned(true);
            p.kickPlayer(PrefixAPI.getPrefix() + " Tentative de Hack!");
        }
    }
}
