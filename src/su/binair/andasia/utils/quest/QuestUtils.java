package su.binair.andasia.utils.quest;

import java.util.*;
import org.bukkit.entity.*;
import su.binair.andasia.*;
import java.io.*;
import su.binair.utils.*;
import su.binair.packets.network.*;
import org.bukkit.plugin.messaging.*;
import su.binair.packets.*;
import org.bukkit.*;
import org.bukkit.command.*;

public class QuestUtils
{
    private int questId;
    public HashMap<Integer, Quest> quest;
    public HashMap<String, QuestPlayer> questPlayer;
    public HashMap<String, Quest> questCreate;
    
    public QuestUtils() {
        this.questId = 0;
        this.quest = new HashMap<Integer, Quest>();
        this.questPlayer = new HashMap<String, QuestPlayer>();
        this.questCreate = new HashMap<String, Quest>();
    }
    
    public int getQuestId() {
        return this.questId;
    }
    
    public int generateNewQuestID() {
        return ++this.questId;
    }
    
    public HashMap<Integer, Quest> getQuest() {
        return this.quest;
    }
    
    public HashMap<String, QuestPlayer> getQuestPlayer() {
        return this.questPlayer;
    }
    
    public Quest getQuestById(final int id) {
        for (final Map.Entry<Integer, Quest> map : this.getQuest().entrySet()) {
            if (map != null && map.getValue().getId() == id) {
                return map.getValue();
            }
        }
        return null;
    }
    
    public void createOrLoadQuestPlayer(final Player player) {
        final File file = new File(Main.getInstance().getQuestManager().getQuestPlayerFolder(), player.getName() + ".json");
        if (file.exists()) {
            final QuestManager manager = Main.getInstance().getQuestManager();
            final String json = Main.getInstance().getFileUtils().loadContent(file);
            final QuestPlayer questPlayer = (QuestPlayer)Main.getInstance().getFileUtils().deserializeObject(json, QuestPlayer.class);
            for (final Map.Entry<Integer, Quest> map : this.getQuest().entrySet()) {
                if (map != null && questPlayer.getQuestById(map.getValue().getId()) == null) {
                    final Quest quest = map.getValue();
                    quest.setStatus(false);
                    quest.setAvancement(0);
                    questPlayer.addNewQuest(map.getValue());
                }
            }
            this.getQuestPlayer().put(player.getName(), questPlayer);
        }
        else {
            final QuestPlayer questPlayer2 = new QuestPlayer(player.getName());
            for (final Map.Entry<Integer, Quest> map2 : this.getQuest().entrySet()) {
                if (map2 != null) {
                    final Quest quest2 = map2.getValue();
                    quest2.setStatus(false);
                    quest2.setAvancement(0);
                    questPlayer2.addNewQuest(map2.getValue());
                }
            }
            this.getQuestPlayer().put(player.getName(), questPlayer2);
        }
    }
    
    public void saveQuestPlayer(final Player player) {
        final File file = new File(Main.getInstance().getQuestManager().getQuestPlayerFolder(), player.getName() + ".json");
        final QuestManager manager = Main.getInstance().getQuestManager();
        if (this.getQuestPlayer().get(player.getName()) == null) {
            final QuestPlayer questPlayer = new QuestPlayer(player.getName());
            final String json = Main.getInstance().getFileUtils().serializeObject(questPlayer);
            Main.getInstance().getFileUtils().save(file, json);
        }
        else {
            final QuestPlayer questPlayer = this.getQuestPlayer().get(player.getName());
            final String json = Main.getInstance().getFileUtils().serializeObject(questPlayer);
            Main.getInstance().getFileUtils().save(file, json);
        }
    }
    
    public void saveQuest(final Quest quest) {
        final File file = new File(Main.getInstance().getQuestManager().getQuestFolder(), quest.getId() + ".json");
        final QuestManager manager = Main.getInstance().getQuestManager();
        final String json = Main.getInstance().getFileUtils().serializeObject(quest);
        Main.getInstance().getFileUtils().save(file, json);
    }
    
    public void saveAllQuestPlayer() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player != null) {
                this.saveQuestPlayer(player);
            }
        }
    }
    
    public void addNewQuestToQuestPlayer(final Quest quest) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player != null) {
                this.getQuestPlayer().get(player.getName()).addNewQuest(quest);
                this.saveQuestPlayer(player);
            }
        }
    }
    
    public void addNewQuest(final Quest quest) {
        this.saveQuest(quest);
        this.getQuest().put(quest.getId(), quest);
        this.addNewQuestToQuestPlayer(quest);
    }
    
    public void setActiveQuest(final int id, final Player player) {
        final QuestPlayer questPlayer = this.getQuestPlayer().get(player.getName());
        if (questPlayer != null) {
            final Quest quest = questPlayer.getQuestById(id);
            if (quest != null) {
                questPlayer.setActiveQuest(quest);
                ToolBox.sendMessage(player, "Quété e en cours: " + quest.getName());
                if (quest.getType().equalsIgnoreCase("NEAR")) {
                    CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", quest.getName(), ""), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                }
                else {
                    CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", quest.getName(), quest.getAvancement() + "/" + quest.getAmount()), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                }
            }
            else {
                System.out.println("Aucune quete trouv\u00e9e!");
            }
        }
        else {
            System.out.println("Aucun joueur trouv\u00e9e!");
        }
    }
    
    public boolean isPlayerNearLocation(final Player player, final Location loc, final int radius) {
        return player.getLocation().distance(loc) < radius;
    }
    
    public static void sendReward(final Quest quest, final Player player) {
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), quest.getRecompense().replace("@player", player.getName()));
    }
}
