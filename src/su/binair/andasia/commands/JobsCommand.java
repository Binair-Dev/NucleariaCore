package su.binair.andasia.commands;

import org.bukkit.inventory.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.andasia.utils.jobs.*;
import su.binair.utils.*;
import su.binair.andasia.*;
import su.binair.andasia.object.jobs.*;
import org.apache.commons.lang.*;
import java.util.*;

public class JobsCommand implements CommandExecutor
{
    public static Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                JobsUtils.sendPacket(player);
            }
            else if (sender.hasPermission("andasia.jobs")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("jobs") && args[1].equalsIgnoreCase("list")) {
                        ToolBox.sendMessage(player, "§eListe des §6Jobs:");
                        for (final String s : Main.getInstance().getConfig("jobs").getConfig().getStringList("Jobs")) {
                            ToolBox.sendMessage(player, "§2§l" + s);
                        }
                    }
                }
                else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("create")) {
                        ToolBox.sendMessage(player, "§eF\u00e9licitaton, tu viens de cr\u00e9er un Jobs nomm\u00e9: §a" + args[1] + "§e.");
                        JobsUtils.addNewJob(args[1], Integer.valueOf(args[2]));
                    }
                    if (args[0].equalsIgnoreCase("action") && args[1].equalsIgnoreCase("list") && args[2] != null) {
                        ToolBox.sendMessage(player, "§eListe des §6Actions:");
                        for (final Map.Entry<String, JobsAction> map : JobsUtils.jobsAction.entrySet()) {
                            if (map != null && map.getValue().getJobId().equalsIgnoreCase(args[2]) && map.getValue().getDescription() != null && map.getValue().getDescription().length() > 0) {
                                final JobsAction action = map.getValue();
                                ToolBox.sendMessage(player, "§eNom: " + action.getDescription());
                                ToolBox.sendMessage(player, "§eExp\u00e9rience: " + action.getExperienceAmount());
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("reward") && args[1] != null && args[2] != null) {
                        if (JobsUtils.jobsName.contains(args[1])) {
                            if (Integer.valueOf(args[2]) != 0 && Integer.valueOf(args[2]) <= 20) {
                                JobsUtils.inRewardAwaiting.put(player, args[1] + "-" + args[2]);
                                ToolBox.sendMessage(player, "§aVeuillez entrer le nom de la commande de r\u00e9compense pour le niveau §c" + args[2] + " §adu mété ier de: §c" + args[1] + "§a.");
                                ToolBox.sendMessage(player, "§cRemplace le nom du joueur par: §a@player§c.");
                            }
                            else {
                                ToolBox.sendMessage(player, "§cCe niveau n'existe pas!");
                            }
                        }
                        else {
                            ToolBox.sendMessage(player, "§cCe mété ier n'existe pas!");
                        }
                    }
                    if (args[0].equalsIgnoreCase("craft") && args[1] != null && args[2] != null) {
                        if (JobsUtils.jobsName.contains(args[1])) {
                            if (Integer.valueOf(args[2]) != 0 && Integer.valueOf(args[2]) <= 20) {
                                JobsUtils.createOrLoadJobsLockedCraft(new JobsLockedCraft(player.getItemInHand().getType(), args[1], Integer.valueOf(args[2])));
                                ToolBox.sendMessage(player, "§a§lF\u00e9licitation! §eTu viens de bloquer le craft de: §c" + player.getItemInHand().getType().toString() + " §eau mété ier de: §c" + args[1] + " §ea partir du niveau: §c" + args[2] + "§e.");
                            }
                            else {
                                ToolBox.sendMessage(player, "§cCe niveau n'existe pas!");
                            }
                        }
                        else {
                            ToolBox.sendMessage(player, "§cCe mété ier n'existe pas!");
                        }
                    }
                }
                else if (args.length == 4) {
                    final String jobs = args[0];
                    final String type = args[1];
                    final String id = args[2];
                    final String expAmount = args[3];
                    final JobsAction action2 = new JobsAction(jobs, type, Integer.valueOf(id), new Double(expAmount));
                    JobsUtils.createOrLoadJobsAction(action2);
                    final String[] list = { ToolBox.getBeautyStart() + "§a§lF\u00e9licitation! §eTu viens de cr\u00e9er une nouvelle mété hode", ToolBox.getBeautyStart() + "§epour recevoir de §al'exp\u00e9rience §een temps que §a§l" + jobs, ToolBox.getBeautyStart() + "§6§lDescription: ", ToolBox.getBeautyStart() + "§eTu dois §a" + type + " §ele block avec l'§6§lID §e" + id, ToolBox.getBeautyStart() + "§eet tu gagnera §a§l" + expAmount + " §ad'§a§lExp\u00e9rience§e.", ToolBox.getBeautyStart() + "§cMet une description a la mission:", ToolBox.getBeautyStart() + "§a§l: §e/jobs description " + action2.getName() + " <votre description>" };
                    ToolBox.sendCustomComposedMessage(player, list);
                }
                else if (args[0].equalsIgnoreCase("description") && args[1] != null && args[2] != null) {
                    final String fullArgs = StringUtils.join((Object[])args, " ").replace(args[0], "").replace(args[1], "");
                    final JobsAction action3 = JobsUtils.jobsAction.get(args[1]);
                    if (action3 != null) {
                        action3.setDescription(fullArgs);
                        ToolBox.sendMessage(player, "§eDescription de la §6mission §a§l" + action3.getName());
                        ToolBox.sendMessage(player, "§een: §6§l" + action3.getDescription());
                        JobsUtils.createOrLoadJobsAction(action3);
                    }
                    else {
                        ToolBox.sendMessage(player, "§cImpossible de trouver la §6mission §c!");
                    }
                }
                else {
                    final String[] list2 = { ToolBox.getBeautyStart() + "§a/jobs create <name> <logoId>§c. §6- permet de cr\u00e9er un mété ier!", ToolBox.getBeautyStart() + "§a/jobs <Jobs> <TYPE> <ID du block/Entity> <Nombre d'XP> pour cr\u00e9er un moyen de gagner de l'XP sur un Mété ier!", ToolBox.getBeautyStart() + "§a/jobs description <name> <description de la mission>§c. §6- permet de changer la description de la mission!", ToolBox.getBeautyStart() + "§a/jobs action list <mété ier>§c. §6- permet de voir la liste des actions pour un mété ier!", ToolBox.getBeautyStart() + "§a/jobs reward <job> <level>§c. §6- permet de modifier les r\u00e9compenses de chaque mété ier par niveau!", ToolBox.getBeautyStart() + "§a/jobs craft <job> <level>§c. §6- permet de bloquer certains craft en fonction du niveau de certains mété iers!" };
                    ToolBox.sendCustomComposedMessage(player, list2);
                }
            }
            else {
                ToolBox.sendMessage(player, "§cTu n'a pas la permission d'\u00e9x\u00e9cuter cette commande!");
            }
        }
        return true;
    }
}
