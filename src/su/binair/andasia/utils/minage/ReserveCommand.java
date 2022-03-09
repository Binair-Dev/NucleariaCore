package su.binair.andasia.utils.minage;

import org.bukkit.event.player.*;
import su.binair.andasia.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class ReserveCommand implements Listener
{
    @EventHandler
    public void test(final PlayerCommandPreprocessEvent e) {
        final Player player = e.getPlayer();
        final String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/reserve")) {
            e.setCancelled(true);
            final Player player2 = player;
            Main.getInstance();
            player2.openInventory((Inventory)Main.getReserve().get(player));
        }
    }
}
