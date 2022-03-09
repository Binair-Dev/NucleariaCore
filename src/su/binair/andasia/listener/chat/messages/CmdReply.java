package su.binair.andasia.listener.chat.messages;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import su.binair.api.*;

public class CmdReply implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String msg, final String[] args) {
        final Player p = (Player)sender;
        final String playerx = CmdMessage.RMessage.get(p.getName().toString());
        if (playerx != null) {
            final Player cible = Bukkit.getPlayer(playerx);
            if (cible != null) {
                if (CmdMessage.msgToggle.contains(cible) && !p.isOp()) {
                    p.sendMessage(PrefixAPI.getPrefix() + " Ce joueur a d\u00e9sactivces messages priv\u00e9s!");
                }
                else {
                    final StringBuilder message = new StringBuilder("");
                    for (final String part : args) {
                        if (!message.toString().equals("")) {
                            message.append(" ");
                        }
                        message.append(part);
                    }
                    final String commande = message.toString().replace(p.getName(), "");
                    p.sendMessage("§b" + p.getName() + CmdMessage.messages + commande);
                    cible.sendMessage("§b" + p.getName() + CmdMessage.messages + commande);
                }
            }
            else {
                p.sendMessage(PrefixAPI.getPrefix() + " Tu n'as pas encore envoy\u00e9 de messages!");
            }
        }
        else {
            p.sendMessage(PrefixAPI.getPrefix() + " Tu n'as pas encore envoy\u00e9 de messages!");
        }
        return false;
    }
}
