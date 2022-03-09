package su.binair.andasia.utils.item;

import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class ItemUtils
{
    public static void damageItemBy(final int maxDamage, final int damage, final Player p) {
        if (p.getInventory().getItemInHand().getDurability() <= maxDamage) {
            p.getInventory().getItemInHand().setDurability((short)(p.getInventory().getItemInHand().getDurability() + damage));
        }
        else {
            p.getInventory().setItemInHand(new ItemStack(Material.AIR));
        }
    }
}
