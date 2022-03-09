package su.binair.andasia.listener.uuid;

import org.bukkit.event.player.*;
import su.binair.andasia.utils.uuid.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class UUIDListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(final PlayerLoginEvent event) {
        final Player player = event.getPlayer();
        final String uuid = player.getUniqueId().toString();
        final Fetcher fetcher = new Fetcher();
        if (!fetcher.fetchUUID(player.getName(), UUIDUtils.onlineMode()).equals(uuid)) {
            UUIDUtils.kickSpoofed(player, event, uuid);
        }
    }
}
