package su.binair.andasia.commands;

import org.bukkit.inventory.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.utils.*;
import org.bukkit.*;
import su.binair.andasia.utils.staff.*;

public class FreezeCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                final String[] list = { ToolBox.getBeautyStart() + "§cLa commande est: §a/freeze <pseudo>§c." };
                ToolBox.sendCustomComposedMessage(player, list);
            }
            else if (args.length == 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                StaffUtils.freezePlayer(target);
            }
            else {
                final String[] list = { ToolBox.getBeautyStart() + "§cLa commande est: §a/freeze <pseudo>§c." };
                ToolBox.sendCustomComposedMessage(player, list);
            }
        }
        return true;
    }
}
