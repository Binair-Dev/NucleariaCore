package su.binair.andasia.runnable;

import org.bukkit.scheduler.*;
import org.bukkit.*;
import su.binair.andasia.utils.jobs.*;
import org.bukkit.entity.*;

public class JobsSaveTask extends BukkitRunnable
{
    public void run() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            JobsUtils.saveJobsForPlayer(player);
        }
    }
}
