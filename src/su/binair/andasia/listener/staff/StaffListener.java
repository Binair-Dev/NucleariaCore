package su.binair.andasia.listener.staff;

import su.binair.andasia.utils.staff.*;
import org.bukkit.event.entity.*;
import su.binair.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

public class StaffListener implements Listener
{
    @EventHandler
    public void onStaffDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player)event.getEntity();
            if (StaffUtils.inStaff.contains(player)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onStaffFood(final FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player)event.getEntity();
            if (StaffUtils.inStaff.contains(player)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onStaffModeClickEntity(final PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getItemInHand();
        final Entity ent = event.getRightClicked();
        if (ent instanceof Player) {
            final Player target = (Player)ent;
            if (StaffUtils.inStaff.contains(player)) {
                event.setCancelled(true);
                switch (item.getType()) {
                    case BLAZE_ROD: {
                        if (target.hasPermission("andasia.staff")) {
                            ToolBox.sendMessage(player, "§cCette personne ne peut été re freeze !");
                            break;
                        }
                        if (!StaffUtils.inFreeze.contains(target)) {
                            StaffUtils.freezePlayer(target);
                            ToolBox.sendMessage(player, "§eTu viens de §aFreeze §ele joueur: §c" + target.getName());
                            break;
                        }
                        StaffUtils.freezePlayer(target);
                        ToolBox.sendMessage(player, "§eTu viens de §aD\u00e9Freeze §ele joueur: §c" + target.getName());
                        break;
                    }
                    case CHEST: {
                        if (!target.hasPermission("andasia.staff")) {
                            final ItemStack[] items = target.getInventory().getContents();
                            final ItemStack[] armors = target.getInventory().getArmorContents();
                            final List<ItemStack> toDraw = new ArrayList<ItemStack>();
                            for (final ItemStack it : items) {
                                if (it != null) {
                                    toDraw.add(item);
                                }
                            }
                            for (final ItemStack it : armors) {
                                if (it != null) {
                                    toDraw.add(item);
                                }
                            }
                            final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, ToolBox.format("§c§l" + target.getName()));
                            for (final ItemStack it2 : toDraw) {
                                if (it2 != null) {
                                    inv.addItem(new ItemStack[] { it2 });
                                }
                            }
                            player.openInventory(inv);
                            break;
                        }
                        ToolBox.sendMessage(player, "§cCette personne ne peut été re inspect§e !");
                        break;
                    }
                    case STRING: {
                        if (!target.hasPermission("andasia.staff")) {
                            target.setPassenger((Entity)player);
                            ToolBox.sendMessage(player, "§cVous suivez actuellement: " + target.getName());
                            break;
                        }
                        ToolBox.sendMessage(player, "§cCette personne ne peut été re suivie !");
                        break;
                    }
                }
                player.updateInventory();
            }
        }
    }
    
    @EventHandler
    public void onStaffMode(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();
        if (StaffUtils.inStaff.contains(player)) {
            event.setCancelled(true);
            switch (item.getType()) {
                case EYE_OF_ENDER: {
                    final Player target = StaffUtils.pickRandomPlayer();
                    player.teleport((Entity)target);
                    ToolBox.sendMessage(player, "§aTu viens d'été re t\u00e9l\u00e9porta: §c" + target.getName());
                    break;
                }
                case SKULL_ITEM: {
                    if (!StaffUtils.inVanish.contains(player)) {
                        StaffUtils.inVanish.add(player);
                        for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (p != null) {
                                p.hidePlayer(player);
                            }
                        }
                        ToolBox.sendMessage(player, "§aTu viens de te mettre en vanish");
                        break;
                    }
                    StaffUtils.inVanish.remove(player);
                    for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
                        if (p != null) {
                            p.showPlayer(player);
                        }
                    }
                    ToolBox.sendMessage(player, "§cTu viens de retirer ton vanish");
                    break;
                }
                case BEDROCK: {
                    final List<String> list = new ArrayList<String>();
                    list.add(ToolBox.getBeautyStart() + ToolBox.format("§e§lVoici la liste des Staffs connect\u00e9s!"));
                    for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
                        if (p2 != null && p2.hasPermission("andasia.staff")) {
                            list.add(ToolBox.getBeautyStart() + ToolBox.format("§a" + p2.getName()));
                        }
                    }
                    if (list.size() > 1) {
                        ToolBox.sendCustomComposedMessage(player, (List)list);
                        break;
                    }
                    break;
                }
            }
            player.updateInventory();
        }
    }
    
    public void onJoinIfVanish(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        for (final Player p : StaffUtils.inVanish) {
            if (p != null) {
                player.hidePlayer(p);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStaffQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (StaffUtils.inStaff.contains(player)) {
            player.getInventory().clear();
        }
        StaffUtils.inStaff.remove(player);
    }
    
    @EventHandler
    public void onStaffClick(final InventoryClickEvent event) {
        final Player player = (Player)event.getWhoClicked();
        if (StaffUtils.inStaff.contains(player)) {
            event.setCancelled(true);
            ToolBox.sendMessage(player, "§cTu ne peux pas d\u00e9placer d'items en §c§lStaffMode§c.");
        }
    }
    
    @EventHandler
    public void onStaffClick(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        if (StaffUtils.inStaff.contains(player)) {
            event.setCancelled(true);
            ToolBox.sendMessage(player, "§cTu ne peux pas dropper d'items en §c§lStaffMode§c.");
        }
    }
    
    @EventHandler
    public void onStaffClick(final PlayerPickupItemEvent event) {
        final Player player = event.getPlayer();
        if (StaffUtils.inStaff.contains(player)) {
            event.setCancelled(true);
        }
    }
}
