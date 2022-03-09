package su.binair.andasia.commands;

import org.bukkit.inventory.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.utils.*;
import su.binair.andasia.utils.stacker.*;

public class StackerCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                final String[] list = { ToolBox.getBeautyStart() + "§cLa commande est: §a/stacker kill§c." };
                ToolBox.sendCustomComposedMessage(player, list);
            }
            else if (args.length == 1 && args[0].equalsIgnoreCase("kill")) {
                ToolBox.sendMessage(player, "§e" + StackerUtils.killStackedMobs() + " §astacks de mobs ont été supprim\u00e9s!");
            }
            else {
                final String[] list = { ToolBox.getBeautyStart() + "§cLa commande est: §a/stacker kill§c." };
                ToolBox.sendCustomComposedMessage(player, list);
            }
        }
        return true;
    }
}
