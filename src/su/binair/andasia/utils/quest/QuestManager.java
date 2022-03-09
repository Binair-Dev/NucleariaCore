package su.binair.andasia.utils.quest;

import java.io.*;
import su.binair.andasia.*;
import java.util.*;

public class QuestManager
{
    private File questFolder;
    private File questPlayerFolder;
    private QuestUtils qu;
    
    public QuestManager() {
        this.qu = new QuestUtils();
        final File questFolder = new File(Main.getInstance().getDataFolder(), "/quests/");
        if (!questFolder.exists()) {
            questFolder.mkdir();
        }
        this.questFolder = questFolder;
        final File questPlayerFolder = new File(this.getQuestFolder(), "/players/");
        if (!questPlayerFolder.exists()) {
            questPlayerFolder.mkdir();
        }
        this.questPlayerFolder = questPlayerFolder;
        this.loadQuests();
    }
    
    public QuestUtils getQuesUtils() {
        return this.qu;
    }
    
    public File getQuestFolder() {
        return this.questFolder;
    }
    
    public File getQuestPlayerFolder() {
        return this.questPlayerFolder;
    }
    
    public void saveQuests() {
        for (final Map.Entry<Integer, Quest> map : this.getQuesUtils().getQuest().entrySet()) {
            final File file = new File(this.getQuestFolder(), map.getValue().getId() + ".json");
            final String json = Main.getInstance().getFileUtils().serializeObject(map.getValue());
            Main.getInstance().getFileUtils().save(file, json);
        }
    }
    
    public void loadQuests() {
        for (final File file : this.getQuestFolder().listFiles()) {
            if (file != null && !file.getName().equalsIgnoreCase("players")) {
                final String json = Main.getInstance().getFileUtils().loadContent(file);
                final Quest quest = (Quest)Main.getInstance().getFileUtils().deserializeObject(json, Quest.class);
                this.getQuesUtils().quest.put(quest.getId(), quest);
                this.getQuesUtils().generateNewQuestID();
            }
        }
    }
}
