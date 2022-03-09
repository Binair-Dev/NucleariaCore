package su.binair.andasia.runnable;

import org.bukkit.scheduler.*;
import su.binair.andasia.utils.messages.*;

public class AutoMessage extends BukkitRunnable
{
    public void run() {
        AutomessageUtils.sendRandomMessage();
    }
}
