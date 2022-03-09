package su.binair.andasia.listener.tool;

import org.bukkit.event.enchantment.*;
import org.bukkit.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.inventory.meta.*;
import org.apache.commons.lang.*;
import org.bukkit.event.inventory.*;
import org.bukkit.entity.*;
import su.binair.andasia.*;
import su.binair.andasia.Main;
import su.binair.utils.*;
import org.bukkit.inventory.*;
import org.bukkit.event.block.*;
import su.binair.api.*;
import org.bukkit.block.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.event.player.*;

public class PickaxeListener implements Listener
{
    public static HashMap<String, Integer> stop;
    public static HashMap<String, Integer> blockFace;
    
    @EventHandler
    public void fortuneEnchantmentLocker(final EnchantItemEvent e) {
        if (e.getEnchantsToAdd().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
            e.setCancelled(true);
            e.getEnchantsToAdd().remove(Enchantment.LOOT_BONUS_BLOCKS);
            e.getItem().addEnchantments(e.getEnchantsToAdd());
        }
    }
    
    @EventHandler
    public void click(final InventoryClickEvent e) {
        final ItemStack item = e.getCursor();
        final ItemStack current = e.getCurrentItem();
        if (e.getAction().equals((Object)InventoryAction.MOVE_TO_OTHER_INVENTORY) && current.getType() == Material.ENCHANTED_BOOK) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta)current.getItemMeta();
            if (StringUtils.containsIgnoreCase(meta.getStoredEnchants().toString(), "LOOT_BONUS_BLOCKS") && e.getInventory().getType().equals((Object)InventoryType.ANVIL)) {
                e.setCancelled(true);
                if (e.getWhoClicked() instanceof Player) {
                    final Player p = (Player)e.getWhoClicked();
                    p.closeInventory();
                    p.sendMessage(ToolBox.getMessage(Main.getInstance().getConfig("fortune"), "fortune_prevent"));
                }
            }
        }
        if (item.getType() == Material.ENCHANTED_BOOK) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta)item.getItemMeta();
            if (StringUtils.containsIgnoreCase(meta.getStoredEnchants().toString(), "LOOT_BONUS_BLOCKS") && e.getInventory().getType().equals((Object)InventoryType.ANVIL)) {
                e.setCancelled(true);
                if (e.getWhoClicked() instanceof Player) {
                    final Player p = (Player)e.getWhoClicked();
                    p.closeInventory();
                    p.sendMessage(ToolBox.getMessage(Main.getInstance().getConfig("fortune"), "fortune_prevent"));
                }
            }
        }
    }
    
    @EventHandler
    public void blockBreakEvent(final BlockBreakEvent e) {
        final Material main = e.getPlayer().getItemInHand().getType();
        final Block b = e.getBlock();
        final World w = b.getWorld();
        final Player p = e.getPlayer();
        if (FactionAPI.isUnclaimedLocation(b.getLocation())) {
            if (main == Material.MULTIPLE_PICKAXE) {
                int total = 0;
                final ArrayList<Block> blocks = new ArrayList<Block>();
                if (PickaxeListener.blockFace.get(p.getName()) == 1) {
                    blocks.add(b.getRelative(BlockFace.NORTH_WEST));
                    blocks.add(b.getRelative(BlockFace.NORTH));
                    blocks.add(b.getRelative(BlockFace.NORTH_EAST));
                    blocks.add(b.getRelative(BlockFace.WEST));
                    blocks.add(b.getRelative(BlockFace.EAST));
                    blocks.add(b.getRelative(BlockFace.SOUTH_WEST));
                    blocks.add(b.getRelative(BlockFace.SOUTH));
                    blocks.add(b.getRelative(BlockFace.SOUTH_EAST));
                }
                if (PickaxeListener.blockFace.get(p.getName()) == 2) {
                    blocks.add(b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST));
                    blocks.add(b.getRelative(BlockFace.UP));
                    blocks.add(b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST));
                    blocks.add(b.getRelative(BlockFace.WEST));
                    blocks.add(b.getRelative(BlockFace.EAST));
                    blocks.add(b.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST));
                    blocks.add(b.getRelative(BlockFace.DOWN));
                    blocks.add(b.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST));
                }
                if (PickaxeListener.blockFace.get(p.getName()) == 3) {
                    blocks.add(b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH));
                    blocks.add(b.getRelative(BlockFace.UP));
                    blocks.add(b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH));
                    blocks.add(b.getRelative(BlockFace.NORTH));
                    blocks.add(b.getRelative(BlockFace.SOUTH));
                    blocks.add(b.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH));
                    blocks.add(b.getRelative(BlockFace.DOWN));
                    blocks.add(b.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH));
                }
                for (final Block block : blocks) {
                    final byte data = block.getData();
                    final Material drop = block.getType();
                    if (FactionAPI.canBuildFac(block.getLocation(), p) && ((block.getType() != Material.BEDROCK && block.getType() != Material.OBSIDIAN) || block.getType() != Material.POUSSE)) {
                        block.breakNaturally();
                    }
                    ++total;
                }
            }
            if (main == Material.LEGENDARY_PICKAXE && (b.getType() == Material.IRON_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.COBALT_ORE || b.getType() == Material.SCANDIUM_ORE || b.getType() == Material.URANIUM_ORE || b.getType() == Material.TRIPLEALIAGE_ORE)) {
                e.setCancelled(true);
                if (b.getType() == Material.IRON_ORE) {
                    w.dropItem(b.getLocation(), new ItemStack(Material.IRON_INGOT));
                    w.dropItem(b.getLocation(), new ItemStack(Material.IRON_INGOT));
                }
                if (b.getType() == Material.GOLD_ORE) {
                    w.dropItem(b.getLocation(), new ItemStack(Material.GOLD_INGOT));
                    w.dropItem(b.getLocation(), new ItemStack(Material.GOLD_INGOT));
                }
                if (b.getType() == Material.COBALT_ORE) {
                    w.dropItem(b.getLocation(), new ItemStack(Material.COBALT));
                    w.dropItem(b.getLocation(), new ItemStack(Material.COBALT));
                }
                if (b.getType() == Material.SCANDIUM_ORE) {
                    w.dropItem(b.getLocation(), new ItemStack(Material.SCANDIUM));
                    w.dropItem(b.getLocation(), new ItemStack(Material.SCANDIUM));
                }
                if (b.getType() == Material.URANIUM_ORE) {
                    w.dropItem(b.getLocation(), new ItemStack(Material.URANIUM));
                    w.dropItem(b.getLocation(), new ItemStack(Material.URANIUM));
                }
                if (b.getType() == Material.TRIPLEALIAGE_ORE) {
                    w.dropItem(b.getLocation(), new ItemStack(Material.TRIPLEALIAGE));
                    w.dropItem(b.getLocation(), new ItemStack(Material.TRIPLEALIAGE));
                }
                b.setType(Material.AIR);
            }
        }
    }
    
    @EventHandler
    public void getBlockFace(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemInHand = player.getItemInHand();
        final BlockFace bFace = event.getBlockFace();
        if (itemInHand.getType() == Material.MULTIPLE_PICKAXE) {
            if (bFace == BlockFace.UP || bFace == BlockFace.DOWN) {
                PickaxeListener.blockFace.put(player.getName(), 1);
            }
            if (bFace == BlockFace.NORTH || bFace == BlockFace.SOUTH) {
                PickaxeListener.blockFace.put(player.getName(), 2);
            }
            if (bFace == BlockFace.WEST || bFace == BlockFace.EAST) {
                PickaxeListener.blockFace.put(player.getName(), 3);
            }
        }
    }
    
    static {
        PickaxeListener.stop = new HashMap<String, Integer>();
        PickaxeListener.blockFace = new HashMap<String, Integer>();
    }
}
