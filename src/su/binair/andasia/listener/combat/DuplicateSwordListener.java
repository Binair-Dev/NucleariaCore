package su.binair.andasia.listener.combat;

import org.bukkit.event.inventory.*;
import org.bukkit.*;
import su.binair.api.*;
import su.binair.utils.*;
import org.bukkit.inventory.*;
import fr.andasia.core.shops.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import java.util.*;

public class DuplicateSwordListener implements Listener
{
    @EventHandler
    public void onItemPickup(final InventoryOpenEvent e) {
        final Player p = (Player)e.getPlayer();
        final ItemStack main = p.getInventory().getItemInHand();
        if (main != null && main.getType() == Material.DUPLICABLE_SWORD) {
            final ItemStack[] chestInventory = e.getInventory().getContents();
            final Shop shop = ShopManager.getInstance().getShop("Farms");
            if (shop != null) {
                for (final ItemStack items : chestInventory) {
                    if (items != null) {
                        final ItemStack item = items;
                        for (final ShopItem shopItem : shop.getItems()) {
                            if (shopItem != null && shopItem.getItem().getType() == item.getType() && shopItem.isSellable()) {
                                e.getInventory().remove(item);
                                final int quantity = item.getAmount();
                                final double calculMoney = quantity * shopItem.getSellPrice();
                                VaultAPI.depositMoney(p, calculMoney);
                                ToolBox.sendMessage(p, "§eTu viens de vendre §a" + quantity + " " + ShopUtils.getFormatedItemName(item).toLowerCase() + " §epour §a" + calculMoney + "§6§l$§e!");
                            }
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(final EntityDeathEvent e) {
        if (e.getEntity().getKiller() instanceof Player) {
            final LivingEntity victim = e.getEntity();
            final Player p = victim.getKiller();
            final ItemStack main = p.getItemInHand();
            if (main.getType() != null && main.getType() == Material.DUPLICABLE_SWORD && !(victim instanceof Player)) {
                final List<ItemStack> drops = (List<ItemStack>)e.getDrops();
                for (final ItemStack stacks : drops) {
                    if (stacks.getType() == Material.GOLD_NUGGET || stacks.getType() == Material.IRON_INGOT || stacks.getType() == Material.ROTTEN_FLESH || stacks.getType() == Material.BONE || stacks.getType() == Material.ARROW) {
                        e.getEntity().getWorld().dropItemNaturally(victim.getLocation(), stacks);
                        e.getEntity().getWorld().dropItemNaturally(victim.getLocation(), stacks);
                    }
                }
                e.setDroppedExp(e.getDroppedExp() * 2);
            }
        }
    }
}
