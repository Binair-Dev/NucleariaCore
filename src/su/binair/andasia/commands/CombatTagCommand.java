package su.binair.andasia.commands;

import org.bukkit.inventory.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.andasia.utils.combattag.*;
import su.binair.andasia.utils.time.*;
import su.binair.utils.*;

public class CombatTagCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (CombatUtils.tagged.containsKey(player)) {
                ToolBox.sendMessage(player, "§cTu es en combat encore pendant: §6" + TimeUtils.secondToMinSec(CombatUtils.tagged.get(player)) + "§c.");
            }
            else {
                ToolBox.sendMessage(player, "§aTu n'es pas en combat !");
            }
        }
        return true;
    }
}
