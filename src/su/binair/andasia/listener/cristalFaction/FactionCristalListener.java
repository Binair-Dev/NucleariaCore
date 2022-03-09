package su.binair.andasia.listener.cristalFaction;

import org.bukkit.event.*;
import org.bukkit.event.player.*;
import com.massivecraft.factions.*;
import org.bukkit.event.block.*;
import su.binair.andasia.Main;
import su.binair.api.*;
import su.binair.utils.*;
import com.massivecraft.factions.struct.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;
import su.binair.andasia.utils.cristalFaction.*;
import su.binair.andasia.*;
import org.bukkit.inventory.*;

public class FactionCristalListener implements Listener
{
    @EventHandler
    public void onCristalBlockBreak(final BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.FACTION_CRISTAL) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onCristalBlockInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final FPlayer fplayer = (FPlayer)FPlayers.i.get(player);
        final Action action = event.getAction();
        final Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        final World world = block.getWorld();
        if (!action.equals((Object)Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (block.getType() == Material.FACTION_CRISTAL) {
            if (!FactionAPI.canBuildFac(block.getLocation(), player)) {
                event.setCancelled(true);
                ToolBox.sendMessage(player, "§cVous ne faites pas partie de cette Faction!");
                return;
            }
            if (fplayer.getRole() == Role.NORMAL) {
                event.setCancelled(true);
                ToolBox.sendMessage(player, "§aSeul les §emod\u00e9rateurs§a/§eadministrateurs §ade Faction peuvent ouvrir le §e§lCristal de Faction§a!");
            }
        }
    }
    
    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        final Player player = (Player)event.getWhoClicked();
        final ItemStack item = event.getCurrentItem();
        final String name = event.getView().getTitle();
        if (item != null && name.contains("R\u00e9compense Cristal")) {
            event.setCancelled(true);
            player.openInventory(CristalUtils.openChoiceInventory(item));
        }
        if (name.contains("Cristal Choix")) {
            if (item.getData().getData() == 14) {
                CristalUtils.crystalRecompense.remove(event.getInventory().getItem(13));
                ToolBox.sendMessage(player, "§cTu viens de retirer cet item des r\u00e9compenses!");
                player.closeInventory();
                Main.getInstance().getConfig("cristal").getConfig().set("recompenses", (Object)CristalUtils.crystalRecompense);
                Main.getInstance().getConfig("cristal").save();
            }
            else if (item.getData().getData() == 5) {
                player.closeInventory();
            }
            else {
                event.setCancelled(true);
            }
        }
    }
}
