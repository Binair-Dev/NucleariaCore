package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class PoubelleCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (sender instanceof Player && label.equalsIgnoreCase("poubelle")) {
            if (args.length == 0) {
                this.Trash(p);
            }
            else {
                this.Trash(p);
            }
        }
        return true;
    }
    
    public void Trash(final Player p) {
        p.openInventory(PoubelleCommand.inv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.translateAlternateColorCodes('&', "§8Poubelle")));
    }
}
