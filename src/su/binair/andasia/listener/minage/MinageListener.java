package su.binair.andasia.listener.minage;

import org.bukkit.scheduler.*;
import su.binair.database.*;
import su.binair.andasia.*;
import su.binair.utils.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.inventory.*;
import org.bukkit.*;

public class MinageListener implements Listener
{
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        new BukkitRunnable() {
            public void run() {
                final Player p = e.getPlayer();
                if (DatabaseUtils.hasAccount(p)) {
                    DatabaseUtils.createAccount(p);
                    Main.getInstance();
                    Main.reserve.put(p, InventoryUtils.getFormattedInventory());
                }
                else {
                    final Inventory inv = InventoryUtils.getFormattedInventory();
                    for (final ItemStack item : DatabaseUtils.getInventory(p)) {
                        inv.addItem(new ItemStack[] { item });
                    }
                    Main.getInstance();
                    Main.reserve.put(p, inv);
                }
            }
        }.runTaskLaterAsynchronously((Plugin)Main.getInstance(), 10L);
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (DatabaseUtils.hasAccount(p)) {
            Main.getInstance();
            final Inventory inv = Main.reserve.get(p);
            DatabaseUtils.saveInventory(p, inv);
            Main.getInstance();
            Main.reserve.remove(p);
        }
    }
    
    @EventHandler
    public void onInvClick(final InventoryClickEvent event) {
        final Inventory top = event.getView().getTopInventory();
        final Inventory bottom = event.getView().getBottomInventory();
        final String title = top.getTitle();
        final Inventory clickInv = event.getClickedInventory();
        final Player p = (Player)event.getWhoClicked();
        if (title.contains("Sac de ressource")) {
            if (event.getCurrentItem().getType() == Material.ENCHANTED_BOOK || event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                event.setCancelled(true);
            }
            else {
                event.setCancelled(true);
                if (clickInv == top) {
                    final ItemStack item = event.getCurrentItem();
                    top.remove(item);
                    bottom.addItem(new ItemStack[] { item });
                }
            }
        }
    }
}
