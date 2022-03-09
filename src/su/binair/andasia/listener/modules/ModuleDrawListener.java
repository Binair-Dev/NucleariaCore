package su.binair.andasia.listener.modules;

import su.binair.andasia.event.*;
import su.binair.packets.network.*;
import org.bukkit.*;
import su.binair.packets.*;
import org.bukkit.plugin.messaging.*;
import org.bukkit.event.*;

public class ModuleDrawListener implements Listener
{
    @EventHandler
    public void onClearLagAnnounce(final ClearLagAnnounceEvent event) {
        final int amount = event.getDelay();
        CustomPacketHandler.dispatch((CustomPacket)new ModuleDrawPacket(amount, "clearlag"), (PluginMessageRecipient[])Bukkit.getOnlinePlayers());
    }
}
