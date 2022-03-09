package su.binair.andasia.listener.chunkminer;

import org.bukkit.event.block.*;
import su.binair.api.*;
import su.binair.utils.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class ChunkMinerListener implements Listener
{
    @EventHandler
    public void onPlace(final BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        final Block b = e.getBlock();
        final Location l = b.getLocation();
        if (FactionAPI.canBuildFac(l, p) && b.getType() != null && b.getType() == Material.CHUNK_MINER) {
            if (e.getBlock().getLocation().getY() >= 15.0) {
                b.setType(Material.AIR);
                final Location loc = e.getBlock().getLocation();
                int mine = 0;
                final int recup = 0;
                final Location playerloc = p.getLocation();
                playerloc.setY(playerloc.getY() - 1.0);
                final Location location = b.getLocation();
                final World world = b.getWorld();
                final Location loctofill = new Location(world, location.getX(), location.getY() + 1.0, location.getZ());
                final Location loctofillx = new Location(world, location.getX() + 1.0, location.getY() + 1.0, location.getZ());
                final Block glass = playerloc.getBlock();
                glass.setType(Material.GLASS);
                final Block blockchest = loctofill.getBlock();
                blockchest.setType(Material.CHEST);
                final Block blockchestx = loctofillx.getBlock();
                blockchestx.setType(Material.CHEST);
                final int X = location.getChunk().getX() * 16;
                final int Z = location.getChunk().getZ() * 16;
                for (int x = 0; x < 16; ++x) {
                    for (int z = 0; z < 16; ++z) {
                        for (int y = 0; y < 256; ++y) {
                            final Block block = world.getBlockAt(X + x, y, Z + z);
                            ++mine;
                            block.getType();
                            final Material glass2 = Material.GLASS;
                            if (block.getType() != Material.CHEST && block.getType() != Material.GLASS) {
                                if (block.getType() == Material.STATIONARY_LAVA || block.getType() == Material.MOB_SPAWNER || block.getType() == Material.LAVA || block.getType() == Material.WATER || block.getType() == Material.GRASS || block.getType() == Material.DIRT || block.getType() == Material.WATER || block.getType() == Material.LAVA || block.getType() == Material.STONE) {
                                    block.setType(Material.getMaterial(0));
                                }
                                else if (block.getType() != Material.BEDROCK) {
                                    fillChest(loctofill, loctofillx, p, block);
                                    block.setType(Material.getMaterial(0));
                                }
                            }
                        }
                    }
                }
            }
            else {
                e.setCancelled(true);
                ToolBox.sendMessage(p, "§eVous ne pouvez pas poser le ChunkMineur en dessous de la couche §c15§e.");
            }
        }
    }
    
    public static void fillChest(final Location loc1, final Location loc2, final Player p, final Block items) {
        if (items.getType() != Material.BEDROCK) {
            try {
                final World w = Bukkit.getWorld(p.getWorld().getName());
                final Block b = w.getBlockAt(loc1);
                final Chest c = (Chest)b.getState();
                final Inventory inv = c.getBlockInventory();
                boolean hasEmptySlot1 = false;
                boolean hasEmptySlot2 = false;
                final boolean hasEmptySlot3 = false;
                final boolean hasEmptySlot4 = false;
                final boolean hasEmptySlot5 = false;
                final Block bx = w.getBlockAt(loc2);
                final Chest cx = (Chest)bx.getState();
                final Inventory invx = cx.getBlockInventory();
                ItemStack[] contents;
                for (int length = (contents = inv.getContents()).length, i = 0; i < length; ++i) {
                    final ItemStack stack = contents[i];
                    if (stack == null) {
                        hasEmptySlot1 = true;
                        break;
                    }
                }
                ItemStack[] contents2;
                for (int length2 = (contents2 = invx.getContents()).length, j = 0; j < length2; ++j) {
                    final ItemStack stack2 = contents2[j];
                    if (stack2 == null) {
                        hasEmptySlot2 = true;
                        break;
                    }
                }
                if (!hasEmptySlot1) {
                    final Chest cd = (Chest)bx.getState();
                    cd.getBlockInventory().addItem(new ItemStack[] { new ItemStack(items.getType()) });
                }
                else {
                    c.getBlockInventory().addItem(new ItemStack[] { new ItemStack(items.getType()) });
                    c.update();
                }
            }
            catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Coffre Introuvable");
            }
        }
    }
}
