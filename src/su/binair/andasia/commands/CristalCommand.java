package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.utils.*;
import su.binair.andasia.utils.cristalFaction.*;
import su.binair.andasia.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;

public class CristalCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                this.sendInformations(player);
            }
            else if (args.length == 1 && args[0].equalsIgnoreCase("create")) {
                if (player.getItemInHand() == null) {
                    ToolBox.sendMessage(player, "§cTu dois avoir un item dans la main!");
                }
                if (player.getItemInHand() != null) {
                    final ItemStack item = player.getItemInHand();
                    CristalUtils.crystalRecompense.add(item);
                    ToolBox.sendMessage(player, "§eTu viens d'ajouter un(e) §6" + item.getType() + " §edans la liste des r\u00e9compense de §6Cristal de Faction§e.");
                    Main.getInstance().getConfig("cristal").getConfig().set("recompenses", (Object)CristalUtils.crystalRecompense);
                    if (Main.getInstance().getConfig("cristal").save()) {
                        ToolBox.sendMessage(player, "§aSauvegarde r\u00e9ussie!");
                    }
                }
            }
            else if (args.length == 1 && args[0].equalsIgnoreCase("open")) {
                final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, ToolBox.format("§6R\u00e9compense Cristal"));
                for (final ItemStack item2 : CristalUtils.crystalRecompense) {
                    inv.addItem(new ItemStack[] { item2 });
                }
                player.openInventory(inv);
            }
            else {
                this.sendInformations(player);
            }
        }
        return true;
    }
    
    public void sendInformations(final Player player) {
        final String[] list = { ToolBox.getBeautyStart() + "§cLa commande est: §a/cristal create §6- §aAjoute l'item que vous avez dans la main dans les r\u00e9compenses§c.", ToolBox.getBeautyStart() + "§cLa commande est: §a/cristal open §6- §aouvre le menu pour voir les r\u00e9compense, et permet aussi de les supprimer§c." };
        ToolBox.sendCustomComposedMessage(player, list);
    }
}
