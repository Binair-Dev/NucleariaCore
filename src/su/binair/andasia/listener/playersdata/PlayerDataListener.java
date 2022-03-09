package su.binair.andasia.listener.playersdata;

import org.bukkit.event.player.*;
import su.binair.andasia.*;
import org.bukkit.*;
import su.binair.andasia.Main;
import su.binair.api.*;
import su.binair.utils.*;
import su.binair.andasia.utils.playersdata.*;
import org.bukkit.event.*;

public class PlayerDataListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerLogin(final PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        final RealPlayersStorage storage = Main.getInstance().getRealPlayersStorage();
        if (!storage.hasRealPlayer((OfflinePlayer)event.getPlayer())) {
            storage.addRealPlayer((OfflinePlayer)event.getPlayer());
            return;
        }
        if (!storage.isPlayerReal((OfflinePlayer)event.getPlayer())) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, PrefixAPI.getPrefix() + ToolBox.getBeautyStart() + "§cUn joueur avec ce pseudo est déjà existant !");
        }
    }
}
