package su.binair.andasia.listener.chat;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import su.binair.andasia.*;
import su.binair.utils.*;
import org.bukkit.event.*;

public class DeathListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKillMsg(final PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
            final String victim = e.getEntity().getName();
            final String killer = e.getEntity().getKiller().getName();
            e.setDeathMessage(ToolBox.getMessage(Main.getInstance().getConfig("deathmessage"), "dead_by").replace("%victim%", victim).replace("%killer%", killer));
        }
        else {
            final String victim = e.getEntity().getName();
            e.setDeathMessage(ToolBox.getMessage(Main.getInstance().getConfig("deathmessage"), "dead").replace("%victim%", victim));
        }
    }
}
