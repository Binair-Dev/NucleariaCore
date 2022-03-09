package su.binair.andasia.listener.stacker;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import su.binair.andasia.utils.stacker.*;
import org.bukkit.event.*;

public class StackerListener implements Listener
{
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDeath(final EntityDeathEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        final LivingEntity entity = event.getEntity();
        if (entity.getType() != EntityType.PLAYER) {
            StackerUtils.attemptUnstackOne(entity);
        }
    }
}
