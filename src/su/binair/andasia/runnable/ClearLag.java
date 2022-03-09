package su.binair.andasia.runnable;

import org.bukkit.scheduler.*;
import su.binair.utils.*;
import su.binair.andasia.*;
import su.binair.andasia.event.*;
import org.bukkit.event.*;
import su.binair.andasia.utils.clearlag.*;
import org.bukkit.plugin.*;

public class ClearLag extends BukkitRunnable
{
    public void run() {
        final String[] list = { ToolBox.getBeautyStart() + "§6Les entit\u00e9es au sol seronts", ToolBox.getBeautyStart() + "§6supprim\u00e9es dans §a§l1 §6§lminute!" };
        ToolBox.broadcast(list);
        Main.getInstance();
        Main.getPlugin().getServer().getPluginManager().callEvent((Event)new ClearLagAnnounceEvent(60));
        new BukkitRunnable() {
            public void run() {
                final int items = ClearlagUtils.removeAllItems();
                final String[] list = { ToolBox.getBeautyStart() + items + " §eEntit\u00e9es ont été supprim\u00e9es!" };
                ToolBox.broadcast(list);
            }
        }.runTaskLater((Plugin)Main.getInstance(), 7200L);
    }
}
