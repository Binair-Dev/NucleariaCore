package su.binair.andasia.commands;

import org.bukkit.inventory.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.utils.*;
import org.apache.commons.lang.*;
import su.binair.andasia.utils.generations.*;
import su.binair.andasia.listener.structure.*;
import su.binair.andasia.object.structure.*;
import java.util.*;

public class StructureCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("structure") && args.length == 0) {
                final String[] messages = { "§aPour cr\u00e9er une structure: ", "§cFait §e/structure create [§anom§e] [§aschematic§e] [§araret/ 200§e]", "§aPour supprimer une structure: ", "§cFait §e/structure delete [§anom§e]", "§aPour voir la liste des structures: ", "§cFait §e/structure list" };
                ToolBox.sendCustomComposedMessage(p, messages);
            }
            else {
                if (args[0] != null && args[0].equalsIgnoreCase("create")) {
                    if (args[1] != null && args[2] != null && args[3] != null) {
                        if (NumberUtils.isNumber(args[3])) {
                            final double rarity = new Double(args[3]);
                            StructureUtils.createNewStructure(args[1], args[2], rarity);
                            final String[] messages2 = { "§c§lLa structure a bien été \u00e9 cr\u00e9\u00e9e!" };
                            ToolBox.sendCustomComposedMessage(p, messages2);
                        }
                        else {
                            final String[] messages = { "§a§lLa raret\u00e9 n'est pas bonne!" };
                            ToolBox.sendCustomComposedMessage(p, messages);
                        }
                    }
                    else {
                        final String[] messages = { "§aPour cr\u00e9er une structure: ", "§cFait §e/structure create [§anom§e] [§aschematic§e] [§araret/ 200§e]" };
                        ToolBox.sendCustomComposedMessage(p, messages);
                    }
                }
                if (args[0] != null && args[0].equalsIgnoreCase("delete")) {
                    if (args[1] != null) {
                        final boolean isRemoved = StructureUtils.removeStructure(args[1]);
                        if (isRemoved) {
                            final String[] messages3 = { "§a§lLa structure a bien été \u00e9 supprim\u00e9e!" };
                            ToolBox.sendCustomComposedMessage(p, messages3);
                        }
                        else {
                            final String[] messages3 = { "§c§lLa structure n'a pas pu été re supprim\u00e9e!" };
                            ToolBox.sendCustomComposedMessage(p, messages3);
                        }
                    }
                    else {
                        final String[] messages = { "§aPour supprimer une structure: ", "§cFait §e/structure delete [§anom§e]" };
                        ToolBox.sendCustomComposedMessage(p, messages);
                    }
                }
                if (args[0] != null && args[0].equalsIgnoreCase("list")) {
                    ToolBox.sendMessage(p, "§aVoici la liste des g\u00e9n\u00e9rations:");
                    for (final Map.Entry<String, CustomPopulator> s : StructureChunkListener.pops.entrySet()) {
                        ToolBox.sendMessage(p, " " + s.getKey());
                    }
                }
            }
        }
        return true;
    }
}
