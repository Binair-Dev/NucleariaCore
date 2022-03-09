package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.api.*;
import su.binair.andasia.utils.teleport.*;

public class RandomTPCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PrefixAPI.getPrefix() + " Un joueur est n\u00e9c\u00e9ssaire.");
            return true;
        }
        final Player player = (Player)sender;
        if (args.length > 0) {
            sender.sendMessage(PrefixAPI.getPrefix() + " Commande invalide! Fait /randomtp");
            return true;
        }
        TeleportUtils.randomTeleportPlayer(player);
        return true;
    }
}
