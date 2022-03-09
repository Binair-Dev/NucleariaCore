package su.binair.andasia.listener.portal;

import org.bukkit.event.world.*;
import org.bukkit.event.*;

public class PortalListener implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPortalCreate(final PortalCreateEvent event) {
        event.setCancelled(true);
    }
}
