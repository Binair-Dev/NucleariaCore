package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.andasia.Main;
import su.binair.api.*;
import su.binair.andasia.utils.distributeur.*;
import su.binair.andasia.*;
import su.binair.utils.*;
import org.bukkit.*;

public class DistributeurCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PrefixAPI.getPrefix() + " Un joueur est n\u00e9c\u00e9ssaire.");
            return true;
        }
        final Player player = (Player)sender;
        if (args.length > 0) {
            return true;
        }
        DistributeurUtils.spawnDistributeur();
        final Location loc = DistributeurUtils.stringToLoc(Main.getInstance().getConfig("distributeur").getConfig().getString("location"));
        if (loc != null) {
            final String[] list = { "§eInformations: §a§lDistributeur", ToolBox.getBeautyStart() + "§aX§e: §2" + loc.getX(), ToolBox.getBeautyStart() + "§aY§e: §2" + loc.getY(), ToolBox.getBeautyStart() + "§aZ§e: §2" + loc.getZ() };
            ToolBox.sendCustomComposedMessage(player, list);
        }
        else {
            ToolBox.sendMessage(player, "Impossible de r\u00e9cup\u00e8rer les informations du &a&lDistributeur&c!");
        }
        return true;
    }
}
