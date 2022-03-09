package su.binair.andasia.utils.clearlag;

import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;

public class ClearlagUtils
{
    public static int removeAllItems() {
        int amount = 0;
        for (final World w : Bukkit.getWorlds()) {
            for (final Entity entities : w.getEntities()) {
                if (!(entities instanceof Player) && !(entities instanceof Villager)) {
                    if (entities instanceof LivingEntity) {
                        final LivingEntity entity = (LivingEntity)entities;
                        if (entity.getType() == EntityType.TURRET || entity.getType() == EntityType.VILLAGER || entity.getType() == EntityType.INDIGENE || entity.getType() == EntityType.SCIENTIST || entity.getType() == EntityType.MUTATEDENDERMAN || entity.getType() == EntityType.MUTATEDZOMBIE || entity.getType() == EntityType.MINECART_CHEST || entity.getCustomName() != null) {
                            continue;
                        }
                        ++amount;
                        entities.remove();
                    }
                    else {
                        if (!(entities instanceof Item)) {
                            continue;
                        }
                        ++amount;
                        entities.remove();
                    }
                }
            }
        }
        return amount;
    }
}
