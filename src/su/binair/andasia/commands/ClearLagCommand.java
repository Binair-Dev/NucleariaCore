package su.binair.andasia.commands;

import org.bukkit.inventory.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.utils.*;
import su.binair.andasia.utils.clearlag.*;
import su.binair.andasia.utils.stacker.*;

public class ClearLagCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                final String[] list = { ToolBox.getBeautyStart() + "§cLa commande est: §a/clearlag clear§c." };
                ToolBox.sendCustomComposedMessage(player, list);
            }
            else if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
                final int items = ClearlagUtils.removeAllItems();
                final int entities = StackerUtils.killStackedMobs();
                final int total = items + entities;
                final String[] list2 = { ToolBox.getBeautyStart() + total + " §eEntit\u00e9es ont été \u00e9 supprim\u00e9es!" };
                ToolBox.sendCustomComposedMessage(player, list2);
            }
            else {
                final String[] list = { ToolBox.getBeautyStart() + "§cLa commande est: §a/clearlag clear§c." };
                ToolBox.sendCustomComposedMessage(player, list);
            }
        }
        return true;
    }
}
