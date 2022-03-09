package su.binair.andasia.listener.koth;

import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import su.binair.utils.*;
import su.binair.andasia.*;
import su.binair.andasia.utils.koth.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;

public class KothListener implements Listener
{
    @EventHandler
    public void onKothToolClick(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Action action = event.getAction();
        final Block block = event.getClickedBlock();
        final ItemStack item = player.getItemInHand();
        if (action.equals((Object)Action.RIGHT_CLICK_BLOCK) && block != null && item.getType() == Material.BLAZE_ROD && item.getItemMeta().getDisplayName().equalsIgnoreCase(ToolBox.format("§eOutil de §6KOTH"))) {
            Main.getInstance().getKothManager();
            KothManager.setPoint1(block.getLocation());
            ToolBox.sendMessage(player, "§eTu as bien positionle point §c1 §edu Koth a cette §aPosition§e.");
            Main.getInstance().getKothManager();
            KothManager.saveLocations(1, block.getLocation());
            event.setCancelled(true);
        }
        if (action.equals((Object)Action.LEFT_CLICK_BLOCK) && block != null && item.getType() == Material.BLAZE_ROD && item.getItemMeta().getDisplayName().equalsIgnoreCase(ToolBox.format("§eOutil de §6KOTH"))) {
            Main.getInstance().getKothManager();
            KothManager.setPoint2(block.getLocation());
            ToolBox.sendMessage(player, "§eTu as bien positionle point §c2 §edu Koth a cette §aPosition§e.");
            Main.getInstance().getKothManager();
            KothManager.saveLocations(2, block.getLocation());
            event.setCancelled(true);
        }
    }
}
