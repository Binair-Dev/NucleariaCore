package su.binair.andasia.listener.security;

import org.bukkit.event.player.*;
import su.binair.api.*;
import org.bukkit.*;
import org.bukkit.command.*;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class CrashListener implements Listener
{
    @EventHandler
    public void onPreCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if (event.getMessage().startsWith("/worldedit:/calc") || event.getMessage().startsWith("/worldedit:/eval") || event.getMessage().startsWith("/worldedit:/solve") || event.getMessage().startsWith("//calc") || event.getMessage().startsWith("//eval") || event.getMessage().startsWith("//solve")) {
            event.setCancelled(true);
            if (player.isOp()) {
                player.sendMessage(PrefixAPI.getPrefix() + " Tu ne peux pas faire cette commande !");
            }
            else {
                player.sendMessage(PrefixAPI.getPrefix() + " Tu ne peux pas faire cette commande !");
                Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "tempban " + player.getName() + " 10m Bien essay\u00e9, reviens plus tard !");
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isOp()) {
                        player.sendMessage(PrefixAPI.getPrefix() + " " + player.getName() + " vient de ce faire bannir 10 minutes pour tentative de" + EnumChatFormat.RED + " //calc" + EnumChatFormat.GRAY + " !");
                    }
                }
            }
        }
    }
}
