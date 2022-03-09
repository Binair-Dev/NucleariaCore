package su.binair.andasia.listener.obsidian;

import org.bukkit.event.entity.*;
import org.bukkit.block.*;
import org.bukkit.util.Vector;
import su.binair.andasia.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.util.*;
import java.util.*;
import su.binair.andasia.utils.obsidian.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import java.text.*;
import su.binair.utils.*;
import org.bukkit.entity.*;

public class ObsidianListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityExplode(final EntityExplodeEvent event) {
        final Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext()) {
            final Block block = it.next();
            if (block.getType() == Material.FACTION_CRISTAL || Main.getInstance().getConfig("obsidian").getConfig().getConfigurationSection("blocks").getKeys(false).contains(Integer.toString(block.getTypeId()))) {
                it.remove();
            }
        }
        final float unalteredRadius = (float)Main.getInstance().getConfig("obsidian").getConfig().getDouble("radius");
        final int radius = (int)Math.ceil(unalteredRadius);
        final Location detonatorLoc = event.getLocation();
        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    if (event.getEntity() != null) {
                        final Location targetLoc = new Location(detonatorLoc.getWorld(), detonatorLoc.getX() + x, detonatorLoc.getY() + y, detonatorLoc.getZ() + z);
                        if (detonatorLoc.distance(targetLoc) <= unalteredRadius) {
                            this.explodeBlock(targetLoc, detonatorLoc, event.getEntityType());
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        final StorageHandler storage = Main.getInstance().getStorage();
        final Location loc = event.getBlock().getLocation();
        storage.damage.remove(storage.generateHash(loc));
    }
    
    void explodeBlock(final Location loc, final Location source, final EntityType explosive) {
        final Block block = loc.getWorld().getBlockAt(loc);
        if (Main.getInstance().getStorage().isValidBlock(block)) {
            try {
                boolean isLiquid = false;
                final Vector v = new Vector(loc.getBlockX() - source.getBlockX(), loc.getBlockY() - source.getBlockY(), loc.getBlockZ() - source.getBlockZ());
                try {
                    final BlockIterator it = new BlockIterator(source.getWorld(), source.toVector(), v, 0.0, (int)Math.floor(source.distance(loc)));
                    while (it.hasNext()) {
                        if (it.next().isLiquid()) {
                            isLiquid = true;
                            break;
                        }
                    }
                }
                catch (Exception e) {
                    if (source.getBlock().isLiquid()) {
                        isLiquid = true;
                    }
                }
                final float liquidDivider = (float)Main.getInstance().getConfig("obsidian").getConfig().getDouble("liquid_multiplier");
                if (isLiquid && liquidDivider <= 0.0f) {
                    return;
                }
                final float rawDamage = (explosive == null) ? 1.0f : ((float)Main.getInstance().getConfig("obsidian").getConfig().getDouble("explosions." + explosive.toString()));
                if (Main.getInstance().getStorage().addDamage(block, isLiquid ? (rawDamage / liquidDivider) : rawDamage)) {
                    if (new Random().nextInt(100) + 1 >= Main.getInstance().getConfig("obsidian").getConfig().getInt("drop_chance")) {
                        block.setType(Material.AIR);
                    }
                    else {
                        block.breakNaturally();
                    }
                }
                else {
                    Main.getInstance().getStorage().renderCracks(block);
                }
            }
            catch (UnknownBlockTypeException ex) {}
        }
    }
    
    @EventHandler
    public void onPlayerInterect(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Player player = event.getPlayer();
            if (player.getItemInHand().getTypeId() == Main.getInstance().getConfig("obsidian").getConfig().getInt("durability_checker") && player.hasPermission("obsidianbreaker.test")) {
                try {
                    final Block block = event.getClickedBlock();
                    if (Main.getInstance().getStorage().getTotalDurability(block) >= 0.0f) {
                        final DecimalFormat format = new DecimalFormat("##.##");
                        final DecimalFormatSymbols symbol = new DecimalFormatSymbols();
                        symbol.setDecimalSeparator('.');
                        format.setDecimalFormatSymbols(symbol);
                        final String durability = format.format(Main.getInstance().getStorage().getTotalDurability(block));
                        final String durabilityLeft = format.format(Main.getInstance().getStorage().getRemainingDurability(block));
                        ToolBox.sendMessage(player, "§aIl reste: §6" + durabilityLeft + "§8/§6" + durability + "§a.");
                    }
                    else {
                        ToolBox.sendMessage(player, "§aCe block a une durabilit§6illimit§e§a.");
                    }
                }
                catch (UnknownBlockTypeException ex) {}
            }
        }
    }
}
