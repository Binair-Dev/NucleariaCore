package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import su.binair.packets.network.*;
import org.bukkit.plugin.messaging.*;
import su.binair.packets.*;
import su.binair.andasia.*;
import su.binair.andasia.utils.quest.*;
import su.binair.utils.*;
import org.bukkit.*;
import java.util.*;

public class QuestCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (args.length == 0) {
                CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("clear"), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                for (final Map.Entry<Integer, Quest> map : Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName()).getQuests().entrySet()) {
                    final Quest q = map.getValue();
                    if (q != null) {
                        final QuestPacket load = new QuestPacket("quest", q.getId(), q.getName(), q.getDescription(), q.getType(), q.getRecompenseName(), q.getRecompense(), q.getAmount(), q.getEntityBlockId(), q.isStatus(), q.isSelectable());
                        CustomPacketHandler.dispatch((CustomPacket)load, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                    }
                }
                CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("open"), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
            }
            else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("open")) {
                    CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("clear"), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                    for (final Map.Entry<Integer, Quest> map : Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName()).getQuests().entrySet()) {
                        final Quest q = map.getValue();
                        if (q != null) {
                            final QuestPacket load = new QuestPacket("quest", q.getId(), q.getName(), q.getDescription(), q.getType(), q.getRecompenseName(), q.getRecompense(), q.getAmount(), q.getEntityBlockId(), q.isStatus(), q.isSelectable());
                            CustomPacketHandler.dispatch((CustomPacket)load, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                        }
                    }
                    CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("open"), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                }
                else if (args[0].equalsIgnoreCase("create") && player.hasPermission("andasia.quest")) {
                    Main.getInstance().getQuestManager().getQuesUtils().questCreate.put(player.getName(), new Quest(Main.getInstance().getQuestManager().getQuesUtils().generateNewQuestID()));
                    ToolBox.sendMessage(player, "§e§lID de la quété e: " + Main.getInstance().getQuestManager().getQuesUtils().questCreate.get(player.getName()).getId());
                    ToolBox.sendMessage(player, "§eVeuillez entrer le §6Type §ede la §6quété e §e!");
                }
            }
            else {
                CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("clear"), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                for (final Map.Entry<Integer, Quest> map : Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName()).getQuests().entrySet()) {
                    final Quest q = map.getValue();
                    if (q != null) {
                        final QuestPacket load = new QuestPacket("quest", q.getId(), q.getName(), q.getDescription(), q.getType(), q.getRecompenseName(), q.getRecompense(), q.getAmount(), q.getEntityBlockId(), q.isStatus(), q.isSelectable());
                        CustomPacketHandler.dispatch((CustomPacket)load, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                    }
                }
                CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("open"), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
            }
        }
        else if (args[0].equalsIgnoreCase("setquest") && args[1] != null && args[2] != null) {
            CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("active", args[1], (int)Integer.valueOf(args[2])), new PluginMessageRecipient[] { (PluginMessageRecipient)Bukkit.getPlayer(args[1]) });
        }
        return true;
    }
}
