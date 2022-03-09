package su.binair.andasia.listener.drops;

import org.bukkit.event.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.*;

import java.util.Random;

public class EntityDropListener implements Listener
{
    @EventHandler
    public void onEDeath(final EntityDeathEvent event)
    {
        final ItemStack item = this.getAdditionalDrop(event.getEntity().getType());
        if (item != null)
        {
            event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), item);
        }
    }
    
    public ItemStack getAdditionalDrop(final EntityType entity)
    {
        final int pourcentage = new Random().nextInt(100);
        if (entity.equals((Object)EntityType.INDIGENE) && pourcentage < 6)
        {
            return new ItemStack(Material.CAGE_NUGGET, 1);
        }
        if (entity.equals((Object)EntityType.SCIENTIST) && pourcentage < 8)
        {
            return new ItemStack(Material.CRISTAL, 1);
        }
        if (entity.equals((Object)EntityType.MUTATEDENDERMAN) && pourcentage < 4)
        {
            return new ItemStack(Material.BROKEN_LEGENDARY_STICK, 1);
        }
        if (entity.equals((Object)EntityType.MUTATEDZOMBIE) && pourcentage < 4)
        {
            return new ItemStack(Material.BROKEN_LEGENDARY_STICK, 1);
        }
        return null;
    }
}
