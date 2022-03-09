package su.binair.andasia.listener.misc;

import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import su.binair.andasia.*;
import su.binair.utils.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;

public class BottleListener implements Listener
{
    @EventHandler
    public void onBottleUse(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final ItemStack is = e.getItem();
            if (is == null) {
                return;
            }
            if (e.getItem().getType() == Material.EXP_BOTTLE && e.getItem().getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().startsWith(ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottlename"))) {
                e.setCancelled(true);
                final String str = e.getItem().getItemMeta().getDisplayName().replace(ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottlename"), "");
                final int value = Integer.parseInt(str);
                if (value <= 0) {
                    return;
                }
                e.getPlayer().setLevel(e.getPlayer().getLevel() + value);
                if (e.getPlayer().getItemInHand().getAmount() > 1) {
                    e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
                    e.getPlayer().setItemInHand(e.getPlayer().getItemInHand());
                }
                else {
                    e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                }
            }
        }
    }
}
