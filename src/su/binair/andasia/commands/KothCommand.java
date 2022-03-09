package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import su.binair.utils.*;
import su.binair.andasia.*;
import su.binair.andasia.utils.koth.*;
import org.bukkit.inventory.*;

public class KothCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                final String[] list = { ToolBox.getBeautyStart() + "§eFait /koth zone §6- §eTe donne l'outil de cr\u00e9ation de la zone du §6KOTH", ToolBox.getBeautyStart() + "§eFait /koth create §6- §eForce la cr\u00e9ation du totem", ToolBox.getBeautyStart() + "§eFait /koth now §6- §eCr§e le totem sans compte \u00e0 rebours", ToolBox.getBeautyStart() + "§eFait /koth stop §6- \u00e9esupprime et annule le totem en cours" };
                ToolBox.sendCustomComposedMessage(player, list);
            }
            else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("zone")) {
                    final ItemStack zoneTool = ItemBuilder.getCreatedItem(Material.BLAZE_ROD, 1, ToolBox.format("§eOutil de §6KOTH"));
                    player.getInventory().setItemInHand(zoneTool);
                    ToolBox.sendMessage(player, "§aTu viens de recevoir l'item de d\u00e9limitation de zone de §6KOTH§a.");
                }
                if (args[0].equalsIgnoreCase("create")) {
                    Main.getInstance().getKothManager();
                    if (KothManager.isRunning()) {
                        ToolBox.sendMessage(player, "§eLe §6KOTH §eest déjà lancé!");
                    }
                    else {
                        ToolBox.sendMessage(player, "§eLe §6KOTH §ebien été lancé!");
                        Main.getInstance().getKothManager().startNormalKoth();
                    }
                }
                if (args[0].equalsIgnoreCase("now")) {
                    Main.getInstance().getKothManager();
                    if (KothManager.isRunning()) {
                        ToolBox.sendMessage(player, "§eLe §6KOTH §eest déjà lancé!");
                    }
                    else {
                        ToolBox.sendMessage(player, "§eLe §6KOTH §ebien été lancé!");
                        Main.getInstance().getKothManager().startInstantKoth();
                    }
                }
                if (args[0].equalsIgnoreCase("stop")) {
                    Main.getInstance().getKothManager();
                    if (KothManager.isRunning()) {
                        Main.getInstance().getKothManager().stopKoth();
                    }
                    else {
                        ToolBox.sendMessage(player, "§cIl n'y a aucun KOTH en cours!");
                    }
                }
            }
            else {
                final String[] list = { ToolBox.getBeautyStart() + "§eFait /koth zone §6- §eTe donne l'outil de cr\u00e9ation de la zone du §6KOTH", ToolBox.getBeautyStart() + "§eFait /koth create §6- §eForce la cr\u00e9ation du totem", ToolBox.getBeautyStart() + "§eFait /koth now §6- §eCr§e le totem sans compte \u00e0 rebours", ToolBox.getBeautyStart() + "§eFait /koth stop §6- \u00e9esupprime et annule le totem en cours" };
                ToolBox.sendCustomComposedMessage(player, list);
            }
        }
        return true;
    }
}
