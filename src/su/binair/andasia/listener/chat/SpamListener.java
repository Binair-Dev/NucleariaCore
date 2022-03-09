package su.binair.andasia.listener.chat;

import java.util.*;
import org.bukkit.event.player.*;
import su.binair.andasia.*;
import su.binair.utils.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class SpamListener implements Listener
{
    public static HashMap<String, Long> chat;
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void antispam(final AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        if (!p.hasPermission("andasia.antispam.bypass") || !p.isOp()) {
            final Long atime = System.currentTimeMillis();
            if (SpamListener.chat.containsKey(p.getName())) {
                final Long now = SpamListener.chat.get(p.getName());
                if (now > atime) {
                    p.sendMessage(ToolBox.getMessage(Main.getInstance().getConfig("antispam"), "message").replace("%time%", ToolBox.getMessage(Main.getInstance().getConfig("antispam"), "time")));
                    e.setCancelled(true);
                }
                else {
                    SpamListener.chat.remove(p.getName());
                    SpamListener.chat.put(p.getName(), atime + Integer.valueOf(ToolBox.getMessage(Main.getInstance().getConfig("antispam"), "time")) * 1000);
                }
            }
            else {
                SpamListener.chat.put(p.getName(), atime + Integer.valueOf(ToolBox.getMessage(Main.getInstance().getConfig("antispam"), "time")) * 1000);
            }
        }
    }
    
    static {
        SpamListener.chat = new HashMap<String, Long>();
    }
}
