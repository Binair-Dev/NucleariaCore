package su.binair.andasia.runnable;

import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class ScoreboardClear extends BukkitRunnable
{
    public void run() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player != null) {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }
    }
}
