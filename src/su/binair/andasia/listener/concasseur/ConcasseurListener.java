package su.binair.andasia.listener.concasseur;

import org.bukkit.event.player.*;
import su.binair.andasia.utils.item.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.event.block.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class ConcasseurListener implements Listener
{
    @EventHandler
    public void onConcasseurClick(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Action action = event.getAction();
        final ItemStack item = player.getItemInHand();
        final Block block = event.getClickedBlock();
        if (item == null) {
            return;
        }
        if (block == null) {
            return;
        }
        final World world = block.getWorld();
        final Location location = block.getLocation();
        if (item.getType() == Material.CONCASSEUR) {
            ItemUtils.damageItemBy(10, 1, player);
            if (block.getType() == Material.RADIOACTIVE_DIRT || block.getType() == Material.RADIOACTIVE_STONE) {
                final int random = new Random().nextInt(100);
                if (random > 0 && random < 30) {
                    block.setType(Material.AIR);
                    world.dropItem(location, new ItemStack(Material.CAGE_NUGGET, 1));
                }
            }
        }
    }
}
