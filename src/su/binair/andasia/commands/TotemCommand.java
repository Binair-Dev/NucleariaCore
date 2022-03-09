package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.utils.*;
import su.binair.andasia.*;
import su.binair.andasia.utils.totem.*;
import org.bukkit.scheduler.*;

public class TotemCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                final String[] list = { ToolBox.getBeautyStart() + "§eFait /totem setpoint §6- §edéfinit le point de spawn du totem", ToolBox.getBeautyStart() + "§eFait /totem create §6- §eForce la cr\u00e9ation du totem", ToolBox.getBeautyStart() + "§eFait /totem now §6- §eCr§e le totem sans compte \u00e0 rebours", ToolBox.getBeautyStart() + "§eFait /totem stop §6- \u00e9esupprime et annule le totem en cours" };
                ToolBox.sendCustomComposedMessage(player, list);
            }
            else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("create") && !TotemUtils.isCreated) {
                    if (!TotemTask.countdown) {
                        ToolBox.sendMessage(player, "§aTu viens de lancer le §6Totem§a.");
                        Main.getInstance();
                        final BukkitScheduler scheduler = Main.getPlugin().getServer().getScheduler();
                        Main.getInstance();
                        scheduler.runTask(Main.getPlugin(), (BukkitRunnable)new TotemTask());
                    }
                    else {
                        ToolBox.sendMessage(player, "§cLe compte a rebours du §6Totem §cest déjà lancé.");
                    }
                }
                if (args[0].equalsIgnoreCase("setpoint")) {
                    Main.getInstance().getTotemManager();
                    TotemManager.setLocation(player.getLocation());
                    ToolBox.sendMessage(player, "§eLe point de §6spawn §edu §6totem §ebien été définit.");
                }
                if (args[0].equalsIgnoreCase("now")) {
                    if (!TotemUtils.isCreated) {
                        final String[] list = { ToolBox.getBeautyStart() + "§eLe §6Totem §evient de démarrer!" };
                        ToolBox.sendCustomComposedMessage(player, list);
                        TotemUtils.create();
                        ToolBox.sendMessage(player, "§eLe §aTotem §ea bien été §clancé§e!");
                    }
                    else {
                        ToolBox.sendMessage(player, "§eLe §aTotem §eest déjà §clancé§e!");
                    }
                }
                if (args[0].equalsIgnoreCase("stop")) {
                    if (!TotemUtils.isCreated) {
                        Main.getInstance().getTotemManager();
                        if (TotemManager.getTaskId() == 0) {
                            ToolBox.sendMessage(player, "§eLe §6Totem §en'est pas §clancé.");
                            return true;
                        }
                    }
                    ToolBox.sendMessage(player, "§eLe §6Totem §ea bien été §cstoppée.");
                    TotemUtils.delete();
                }
            }
            else {
                final String[] list = { ToolBox.getBeautyStart() + "§eFait /totem setpoint §6- §edéfinit le point de spawn du totem", ToolBox.getBeautyStart() + "§eFait /totem create §6- §eForce la cr\u00e9ation du totem", ToolBox.getBeautyStart() + "§eFait /totem now §6- §eCr§e le totem sans compte \u00e0 rebours", ToolBox.getBeautyStart() + "§eFait /totem stop §6- §esupprime et annule le totem en cours" };
                ToolBox.sendCustomComposedMessage(player, list);
            }
        }
        return true;
    }
}
