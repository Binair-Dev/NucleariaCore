package su.binair.andasia.listener;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_7_R4.EnumChatFormat;
import su.binair.utils.ToolBox;

public class BoneMealListener implements Listener
{
	 @EventHandler
	 public void onInteractPlant(final PlayerInteractEvent event) 
	 {
	    if(event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().getType() == Material.getMaterial(351)) 
	    {
	    	if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null)
	    	{
	    		if(event.getClickedBlock().getType() == Material.COURGETTE_BLOCK || event.getClickedBlock().getType() == Material.TOURNESOL_BLOCK || event.getClickedBlock().getType() == Material.RAISIN_BLOCK)
	    		{
	    			event.setCancelled(true);
	    			ToolBox.sendMessage(event.getPlayer(), EnumChatFormat.RED + "Tu ne peux pas utiliser de la poudre d'os sur cette plantation!");
	    		}
	    	}
	    }
	}
	 
	 @EventHandler
	 public void onPlantBreak(final BlockBreakEvent event) 
	 {
		 Block b = event.getBlock();
		 Location loc = b.getLocation();
		 World w = loc.getWorld();
		 
		 int drops = new Random().nextInt(3);
		 		 
		 if(b.getType() == Material.COURGETTE_BLOCK && b.getData() == 7)
		 {
			 event.setCancelled(true);
			 b.setType(Material.AIR);
			 w.dropItem(loc, new ItemStack(Material.COURGETTE, 1));
			 w.dropItem(loc, new ItemStack(Material.COURGETTE_SEED, drops));
		 }
		 else if(b.getType() == Material.RAISIN_BLOCK && b.getData() == 7)
		 {
			 event.setCancelled(true);
			 b.setType(Material.AIR);
			 w.dropItem(loc, new ItemStack(Material.RAISIN, 1));
			 w.dropItem(loc, new ItemStack(Material.RAISIN_SEED, drops));
		 }
		 else if(b.getType() == Material.TOURNESOL_BLOCK && b.getData() == 7)
		 {
			 event.setCancelled(true);
			 b.setType(Material.AIR);
			 w.dropItem(loc, new ItemStack(Material.TOURNESOL, 1));
			 w.dropItem(loc, new ItemStack(Material.TOURNESOL_SEED, drops));
		 }
	 }
}
