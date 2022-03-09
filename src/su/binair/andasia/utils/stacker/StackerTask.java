package su.binair.andasia.utils.stacker;

import org.bukkit.scheduler.*;
import su.binair.andasia.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class StackerTask extends BukkitRunnable
{
    public static Set<EntityType> mobTypes;
    
    public StackerTask() {
        StackerUtils.compileEntityTypesList(Main.getInstance().getConfig("stacker").getConfig().getStringList("MobTypes"));
    }
    
    public void run() {
        final int radius = Main.getInstance().getConfig("stacker").getConfig().getInt("StackRadius");
        final Set<EntityType> entityTypes = StackerTask.mobTypes;
        for (final World world : Bukkit.getServer().getWorlds()) {
            for (final LivingEntity entity : world.getLivingEntities()) {
                if (entityTypes.contains(entity.getType())) {
                    if (!entity.isValid()) {
                        continue;
                    }
                    for (final Entity nearby : entity.getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                        if (nearby instanceof LivingEntity) {
                            final LivingEntity tnearby = (LivingEntity)nearby;
                            if (!nearby.isValid()) {
                                continue;
                            }
                            if (!entityTypes.contains(nearby.getType())) {
                                continue;
                            }
                            if (entity.getCustomName() != null && (entity.getCustomName() == null || entity.getCustomName().equals(ChatColor.stripColor(entity.getCustomName())))) {
                                continue;
                            }
                            StackerUtils.stack(entity, tnearby);
                        }
                    }
                }
            }
        }
    }
    
    static {
        StackerTask.mobTypes = new HashSet<EntityType>();
    }
}
