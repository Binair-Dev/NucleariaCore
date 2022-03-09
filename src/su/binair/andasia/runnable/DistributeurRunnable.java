package su.binair.andasia.runnable;

import org.bukkit.scheduler.*;
import su.binair.andasia.utils.distributeur.*;

public class DistributeurRunnable extends BukkitRunnable
{
    public void run() {
        DistributeurUtils.spawnDistributeur();
    }
}
