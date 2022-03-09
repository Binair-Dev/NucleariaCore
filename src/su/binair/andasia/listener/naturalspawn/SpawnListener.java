package su.binair.andasia.listener.naturalspawn;

import org.bukkit.event.entity.*;
import org.bukkit.event.*;

public class SpawnListener implements Listener
{
    @EventHandler
    public void onNaturalSpawn(final CreatureSpawnEvent event) {
        final CreatureSpawnEvent.SpawnReason raison = event.getSpawnReason();
        if (raison.equals((Object)CreatureSpawnEvent.SpawnReason.NATURAL)) {
            event.setCancelled(true);
        }
    }
}
