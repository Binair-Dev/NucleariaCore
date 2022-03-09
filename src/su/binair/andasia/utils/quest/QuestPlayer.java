package su.binair.andasia.utils.quest;

import java.util.*;

public class QuestPlayer
{
    private String name;
    private HashMap<Integer, Quest> quests;
    private Quest activeQuest;
    
    public QuestPlayer(final String name) {
        this.quests = new HashMap<Integer, Quest>();
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public HashMap<Integer, Quest> getQuests() {
        return this.quests;
    }
    
    public void addNewQuest(final Quest q) {
        this.getQuests().put(q.getId(), q);
    }
    
    public void setActiveQuest(final Quest activeQuest) {
        this.activeQuest = activeQuest;
    }
    
    public Quest getActiveQuest() {
        return this.activeQuest;
    }
    
    public Quest getQuestById(final int id) {
        for (final Map.Entry<Integer, Quest> map : this.getQuests().entrySet()) {
            if (map != null && map.getValue().getId() == id) {
                return map.getValue();
            }
        }
        return null;
    }
}
