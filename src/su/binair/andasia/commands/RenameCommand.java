package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.utils.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class RenameCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                final String[] list = { ToolBox.getBeautyStart() + "§cLa commande est: §a/rename <Nom de l'item>." };
                ToolBox.sendCustomComposedMessage(player, list);
            }
            else if (player.getItemInHand() == null) {
                ToolBox.sendMessage(player, "§cTu dois avoir un item dans la main !");
            }
            else {
                String name = "";
                final ItemStack item = player.getItemInHand();
                final ItemMeta meta = item.getItemMeta();
                for (final String s : args) {
                    if (!s.contains("rename")) {
                        name = name + s + " ";
                    }
                }
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
                item.setItemMeta(meta);
                player.setItemInHand(item);
                player.updateInventory();
            }
        }
        return true;
    }
}
