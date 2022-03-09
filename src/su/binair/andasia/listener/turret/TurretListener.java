package su.binair.andasia.listener.turret;

import java.util.*;
import org.bukkit.entity.*;
import su.binair.main.entity.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.spigotmc.event.entity.EntityDismountEvent;

import com.massivecraft.factions.FPlayer;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_7_R4.AttributeModifier;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EnumChatFormat;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import su.binair.andasia.utils.turrets.*;
import su.binair.api.FactionAPI;
import su.binair.utils.*;
import sun.text.CollatorUtilities;

import org.bukkit.event.player.*;
import org.bukkit.event.world.ChunkUnloadEvent;

public class TurretListener implements Listener
{
    HashMap<Player, LivingEntity> clickedEntity;
    List<Player> addingWhitelist;
    List<Player> remWhitelist;
    
    public TurretListener() {
        this.clickedEntity = new HashMap<Player, LivingEntity>();
        this.addingWhitelist = new ArrayList<Player>();
        this.remWhitelist = new ArrayList<Player>();
    }
    
    @EventHandler
    public void onTurretRightClick(final PlayerInteractEntityEvent event) 
    {
        if (event.getRightClicked() != null && event.getRightClicked().getType().equals((Object)EntityType.TURRET) && event.getPlayer().getItemInHand().getType() != Material.WRENCH) 
        {
        	LivingEntity livingTurret = (LivingEntity)event.getRightClicked();
        	
        	if(livingTurret.getCustomName() != null && livingTurret.getCustomName().contains(event.getPlayer().getName()))
        	{
                final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity((LivingEntity)event.getRightClicked());
        		final Inventory turretInventory = Bukkit.createInventory((InventoryHolder)null, 27, ToolBox.format("§cTourelle"));
                final String wl = turret.getWhitelist().replaceAll(",", "");
                turretInventory.setItem(13, ItemBuilder.getCreatedSkullPlayerWithName("B_nair", ToolBox.format("§eWhitelist: §c" + wl)));
                final List<String> lore = new ArrayList<String>();
                lore.add(ToolBox.format(ToolBox.getBeautyStart() + "§eClique Gauche:"));
                lore.add(ToolBox.format("            §ePour ajouter des une munition."));
                lore.add(ToolBox.format(ToolBox.getBeautyStart() + "§eClique Droit:"));
                lore.add(ToolBox.format("            §ePour ajouter des 64 munitions."));
                lore.add(ToolBox.format(ToolBox.getBeautyStart() + "§c§lInformation:"));
                lore.add(ToolBox.format("            §5Munition: §efl\u00e9che"));
                turretInventory.setItem(14, ItemBuilder.getCreatedItemWithLore(Material.LEVER, 1, ToolBox.format("§eMunitions: §c" + turret.getAmmo()), (List)lore));
                turretInventory.setItem(12, ItemBuilder.getCreatedItem(Material.TRIPLEALIAGE_SWORD, 1, ToolBox.format("§eD\u00e9gats: §c" + turret.getDamage())));
                turretInventory.setItem(4, ItemBuilder.getCreatedItem(Material.ARROW, 1, ToolBox.format("§eRange: §c" + turret.getRange())));
                turretInventory.setItem(22, ItemBuilder.getCreatedItem(Material.BEACON, 1, ToolBox.format("§eA venir.")));
                this.clickedEntity.put(event.getPlayer(), (LivingEntity)event.getRightClicked());
                event.getPlayer().openInventory(turretInventory);
        	}
        	else
        		ToolBox.sendMessage(event.getPlayer(), EnumChatFormat.RED + "Cette tourelle ne t'appartient pas!");
        }
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getView().getTitle().contains("Tourelle")) {
            event.setCancelled(true);
            final Player player = (Player)event.getWhoClicked();
            switch (event.getCurrentItem().getType()) {
                case SKULL_ITEM: {
                    final Inventory turretInventory = Bukkit.createInventory((InventoryHolder)null, 27, ToolBox.format("§cTourelle"));
                    turretInventory.setItem(14, ItemBuilder.getCreatedItem(Material.NAME_TAG, 1, ToolBox.format("§aAjouter un joueur")));
                    turretInventory.setItem(12, ItemBuilder.getCreatedItem(Material.COMPASS, 1, ToolBox.format("§cRetirer un joueur")));
                    player.openInventory(turretInventory);
                    break;
                }
                case NAME_TAG: {
                    this.addingWhitelist.add(player);
                    ToolBox.sendMessage(player, "§aVeuillez entrer le nom du §cjoueur §aa ajouter a la §ewhitelist§a!");
                    player.closeInventory();
                    break;
                }
                case COMPASS: {
                    this.remWhitelist.add(player);
                    ToolBox.sendMessage(player, "§aVeuillez entrer le nom du §cjoueur §aa retirer de la §ewhitelist§a!");
                    player.closeInventory();
                    break;
                }
                case LEVER: {
                    if (event.isLeftClick()) {
                        if (player.getInventory().containsAtLeast(new ItemStack(Material.ARROW), 1)) {
                            TurretUtils.addAmmo(this.clickedEntity.get(player), 1);
                            ToolBox.sendMessage(player, "§8[§c§l-§8] §cFl\u00e9che!");
                            InventoryUtils.removeItem(player, Material.ARROW, 1);
                            player.updateInventory();
                            break;
                        }
                        player.closeInventory();
                        ToolBox.sendMessage(player, "§cTon inventaire ne contiens §c§laucunes §cmunitions! §8(§eFl\u00e9ches§8)");
                        break;
                    }
                    else {
                        if (player.getInventory().containsAtLeast(new ItemStack(Material.ARROW), 64)) {
                            TurretUtils.addAmmo(this.clickedEntity.get(player), 64);
                            ToolBox.sendMessage(player, "§8[§c§l-§8] §cFl\u00e9che!");
                            InventoryUtils.removeItem(player, Material.ARROW, 64);
                            player.updateInventory();
                            break;
                        }
                        player.closeInventory();
                        ToolBox.sendMessage(player, "§cTon inventaire ne contiens §c§laucunes §cmunitions! §8(§eFl\u00e9ches§8)");
                        break;
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onDisconnect(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (this.addingWhitelist.contains(player)) {
            this.addingWhitelist.remove(player);
        }
        if (this.remWhitelist.contains(player)) {
            this.remWhitelist.remove(player);
        }
    }
    
    @EventHandler
    public void chatEvent(final AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final String[] args = e.getMessage().split(" ");
        if (this.addingWhitelist.contains(player) && args[0] != null) {
            e.setCancelled(true);
            TurretUtils.addWhitelist(player, this.clickedEntity.get(player), args[0]);
            this.addingWhitelist.remove(player);
        }
        if (this.remWhitelist.contains(player) && args[0] != null) {
            e.setCancelled(true);
            TurretUtils.remWhitelist(player, this.clickedEntity.get(player), args[0]);
            this.remWhitelist.remove(player);
        }
    }
    
    @EventHandler
    public void onRightClick(final PlayerInteractEvent event) 
    {
    	if(event.getItem() != null && event.getItem().getType() == Material.getMaterial(499))
    	{
    		event.setCancelled(true);
        	if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        	{
        		World world = event.getPlayer().getWorld();
        		Location loc = event.getClickedBlock().getLocation();
        		ItemStack item = event.getItem();
        		Player player = event.getPlayer();
        		
        		if(item.getItemMeta().hasLore())
        		{
        			Entity turret = world.spawnEntity(event.getPlayer().getLocation(), EntityType.TURRET);
        			LivingEntity livingTurret = (LivingEntity)turret;
            		EntityTurret turretNms = (EntityTurret)EntityUtils.getNMSEntity((LivingEntity)turret);
            		Set<FPlayer> factionPlayers = FactionAPI.getFactionByPlayer(player).getFPlayers();
            		if(!FactionAPI.getFactionByPlayer(player).getTag().contains("Wilderness"))
            		{
                		for(FPlayer fp : factionPlayers)
                		{
                			turretNms.addWhitelist(fp.getName());
                		}
            		}
            		else
            			turretNms.addWhitelist(player.getName());
            		
            	    List<String> lores = item.getItemMeta().getLore();
            	    String loreText = ChatColor.stripColor(lores.get(0));
            	    
            	    if(loreText.contains("1"))
            	    {
            	    	TurretUtils.setRange((LivingEntity)turret, player.getName(), 8);
            	    	TurretUtils.setDamage((LivingEntity)turret, player.getName(), 1);
            	    	livingTurret.setMaxHealth(new Double(450));
            	    	livingTurret.setHealth(new Double(450));
            	    }
            	    else if(loreText.contains("2"))
            	    {
            	    	TurretUtils.setRange((LivingEntity)turret, player.getName(), 12);
            	    	TurretUtils.setDamage((LivingEntity)turret, player.getName(), 2);
            	    	livingTurret.setMaxHealth(new Double(900));
            	    	livingTurret.setHealth(new Double(900));
            	    }
            	    else if(loreText.contains("3"))
            	    {
            	    	TurretUtils.setRange((LivingEntity)turret, player.getName(), 16);
            	    	TurretUtils.setDamage((LivingEntity)turret, player.getName(), 3);
            	    	livingTurret.setMaxHealth(new Double(1500));
            	    	livingTurret.setHealth(new Double(1500));
            	    }
            	    else
            	    {
            	    	TurretUtils.setRange((LivingEntity)turret, player.getName(), 8);
            	    	TurretUtils.setDamage((LivingEntity)turret, player.getName(), 1);
            	    	livingTurret.setMaxHealth(new Double(450));
            	    	livingTurret.setHealth(new Double(450));
            	    }
            	    livingTurret.setCustomName(EnumChatFormat.RED + "Propriétaire: " + EnumChatFormat.GOLD + player.getName());
            	    livingTurret.setCustomNameVisible(true);
            	    livingTurret.setRemoveWhenFarAway(false);
        		}
        		else
        		{
        			Entity turret = world.spawnEntity(event.getPlayer().getLocation(), EntityType.TURRET);
        			LivingEntity livingTurret = (LivingEntity)turret;
            		EntityTurret turretNms = (EntityTurret)EntityUtils.getNMSEntity((LivingEntity)turret);
            		Set<FPlayer> factionPlayers = FactionAPI.getFactionByPlayer(player).getFPlayers();
            		if(!FactionAPI.getFactionByPlayer(player).getTag().contains("Wilderness"))
            		{
                		for(FPlayer fp : factionPlayers)
                		{
                			turretNms.addWhitelist(fp.getName());
                		}
            		}
            		else
            			turretNms.addWhitelist(player.getName());
            		
            		TurretUtils.setRange((LivingEntity)turret, player.getName(), 8);
        	    	TurretUtils.setDamage((LivingEntity)turret, player.getName(), 1);
        	    	livingTurret.setMaxHealth(new Double(450));
        	    	livingTurret.setHealth(new Double(450));
            	    livingTurret.setCustomName(EnumChatFormat.RED + "Propriétaire: " + EnumChatFormat.GOLD + player.getName());
            	    livingTurret.setCustomNameVisible(true);
            	    livingTurret.setRemoveWhenFarAway(false);
            		
            	}
        		
        		ItemStack stack = event.getItem().clone();
        		stack.setAmount(1);
        		player.getInventory().removeItem(new ItemStack[] { stack });
        		player.updateInventory();
        	}
    	}
    }
    
    @EventHandler
    public void onTurretWrench(final PlayerInteractEntityEvent event) 
    {
    	if(event.getRightClicked() != null && event.getRightClicked().getType().equals((Object)EntityType.TURRET) && event.getPlayer().getItemInHand().getType() == Material.WRENCH) 
    	{
			LivingEntity livingTurret = (LivingEntity)event.getRightClicked();
    		
			if(livingTurret.getCustomName() != null && livingTurret.getCustomName().contains(event.getPlayer().getName()))
			{
				ItemStack item = new ItemStack(Material.getMaterial(499));
	    		String level = "1";
	    		
	    		if(TurretUtils.getLevel(livingTurret) != 0.0)
	    		{
	    			if(TurretUtils.getLevel(livingTurret) == 1)
	    				level = "1";
	    			if(TurretUtils.getLevel(livingTurret) == 2)
	    				level = "2";
	    			if(TurretUtils.getLevel(livingTurret) == 3)
	    				level = "3";
	    		}
	    		
	    		ItemMeta meta = item.getItemMeta();
	    		List<String> lore = new ArrayList<String>();
	    		lore.add(EnumChatFormat.GOLD + "Niveau: " + EnumChatFormat.GREEN + level);
	    		meta.setLore(lore);
	    		item.setItemMeta(meta);
	    		event.getRightClicked().remove();
	    		event.getPlayer().getInventory().addItem(item);
			}
			else
        		ToolBox.sendMessage(event.getPlayer(), EnumChatFormat.RED + "Cette tourelle ne t'appartient pas!");
    	}
    }
}
