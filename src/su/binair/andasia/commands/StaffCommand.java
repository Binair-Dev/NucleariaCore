package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.andasia.utils.staff.*;
import org.bukkit.*;
import su.binair.utils.*;
import org.bukkit.inventory.*;

public class StaffCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                if (StaffUtils.inStaff.contains(player)) {
                    player.getInventory().clear();
                    StaffUtils.inStaff.remove(player);
                    ToolBox.sendMessage(player, "§cTu viens de quitter le §c§lStaffMode§c.");
                }
                else {
                    player.getInventory().clear();
                    StaffUtils.inStaff.add(player);
                    ToolBox.sendMessage(player, "§aTu viens de rejoindre le §a§lStaffMode§a.");
                    final ItemStack randomTeleport = ItemBuilder.getCreatedItem(Material.EYE_OF_ENDER, 1, ToolBox.format("§aT\u00e9l\u00e9portation al\u00e9atoire"));
                    final ItemStack vanish = ItemBuilder.getCreatedItem(Material.SKULL_ITEM, 1, ToolBox.format("§aVanish"));
                    final ItemStack staffList = ItemBuilder.getCreatedItem(Material.BEDROCK, 1, ToolBox.format("§aListe du §cStaff"));
                    final ItemStack freezeRod = ItemBuilder.getCreatedItem(Material.BLAZE_ROD, 1, ToolBox.format("§aFreeze"));
                    final ItemStack inventorySee = ItemBuilder.getCreatedItem(Material.CHEST, 1, ToolBox.format("§aVoir l'inventaire"));
                    final ItemStack summon = ItemBuilder.getCreatedItem(Material.STRING, 1, ToolBox.format("§aSuivre"));
                    player.getInventory().setItem(0, randomTeleport);
                    player.getInventory().setItem(1, vanish);
                    player.getInventory().setItem(3, staffList);
                    player.getInventory().setItem(5, freezeRod);
                    player.getInventory().setItem(7, inventorySee);
                    player.getInventory().setItem(8, summon);
                }
            }
            else {
                final String[] list = { ToolBox.getBeautyStart() + "§cLa commande est: §a/staff§c." };
                ToolBox.sendCustomComposedMessage(player, list);
            }
        }
        return true;
    }
}
