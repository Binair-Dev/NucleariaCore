package su.binair.andasia.listener.reload;

import org.bukkit.event.player.*;
import su.binair.api.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class ReloadListener implements Listener
{
    @EventHandler
    public void onCmdReload(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String msg = e.getMessage();
        final String[] args = msg.split(" ");
        if ((args[0].equalsIgnoreCase("/reload") || args[0].equalsIgnoreCase("/stop") || args[0].equalsIgnoreCase("/rl") || args[0].equalsIgnoreCase("/restart")) && (!p.isOp() || !p.hasPermission("andasia.reload.bypass"))) {
            e.setCancelled(true);
            p.sendMessage(PrefixAPI.getPrefix() + " Reload interdit !");
            p.sendMessage(PrefixAPI.getPrefix() + " Si vous voulez faire un changement dans le plugin AndasiaCore, Veuillez red\u00e9marer le serveur !");
        }
    }
}
