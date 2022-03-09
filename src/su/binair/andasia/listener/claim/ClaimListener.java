package su.binair.andasia.listener.claim;

import org.bukkit.event.player.*;
import su.binair.api.*;
import su.binair.packets.network.*;
import org.bukkit.plugin.messaging.*;
import su.binair.packets.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class ClaimListener implements Listener
{
    @EventHandler
    public void onClaimChange(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final DrawPacket packet = new DrawPacket("claim", FactionAPI.getFactionOfPlayersLocation(player));
        CustomPacketHandler.dispatch((CustomPacket)packet, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
    }
}
