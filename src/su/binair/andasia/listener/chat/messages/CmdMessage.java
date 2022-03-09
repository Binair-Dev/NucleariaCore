package su.binair.andasia.listener.chat.messages;

import org.bukkit.entity.*;
import org.bukkit.command.*;
import su.binair.api.*;
import org.bukkit.*;
import java.util.*;

public class CmdMessage implements CommandExecutor
{
    public static String messages;
    public static List<Player> msgToggle;
    public static HashMap<String, String> RMessage;
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String msg, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (args.length == 0) {
                p.sendMessage(PrefixAPI.getPrefix() + " Tu dois sp\u00e9cifier un joueur a qui parler!");
            }
            else if (args[0] != null) {
                if (args[0].equalsIgnoreCase("toggle")) {
                    if (p.hasPermission("msg.toggle")) {
                        if (CmdMessage.msgToggle.contains(p)) {
                            p.sendMessage(PrefixAPI.getPrefix() + " Tu viens de r\u00e9activer tes messages priv\u00e9s!");
                            CmdMessage.msgToggle.remove(p);
                        }
                        else {
                            p.sendMessage(PrefixAPI.getPrefix() + " Tu viens de d\u00e9sactiver tes messages priv\u00e9s!");
                            CmdMessage.msgToggle.add(p);
                        }
                    }
                }
                else if (args[0].toString().equalsIgnoreCase(p.getName())) {
                    p.sendMessage(PrefixAPI.getPrefix() + " Tu ne peux pas t'envoyer de messages !");
                }
                else {
                    final Player cible = Bukkit.getPlayer(args[0].toString());
                    if (cible != null) {
                        if (CmdMessage.msgToggle.contains(cible) && !p.isOp()) {
                            p.sendMessage(PrefixAPI.getPrefix() + " Ce joueur a d\u00e9sactiv\u00e9 ces messages priv\u00e9s!");
                        }
                        else {
                            final boolean isUser = true;
                            final StringBuilder message = new StringBuilder("");
                            for (final String part : args) {
                                if (!message.toString().equals("") && !isUser) {
                                    message.append(": ");
                                }
                                else {
                                    message.append(" ");
                                }
                                message.append(part);
                            }
                            final String commande = message.toString().replace(p.getName(), "");
                            p.sendMessage("§b" + p.getName() + CmdMessage.messages + commande);
                            cible.sendMessage("§b" + p.getName() + CmdMessage.messages + commande);
                            CmdMessage.RMessage.put(p.getName(), cible.getName());
                            CmdMessage.RMessage.put(cible.getName(), p.getName());
                        }
                    }
                    else {
                        p.sendMessage(PrefixAPI.getPrefix() + " Ce joueur n'est pas connect\u00e9 ou n'existe pas!");
                    }
                }
            }
        }
        return false;
    }
    
    static {
        CmdMessage.messages = " §6§ §a";
        CmdMessage.msgToggle = new ArrayList<Player>();
        CmdMessage.RMessage = new HashMap<String, String>();
    }
}
