package su.binair.andasia.event;

import org.bukkit.event.*;
import org.bukkit.entity.*;
import su.binair.andasia.utils.quest.*;

public class QuestFinishEvent extends Event
{
    private static final HandlerList HANDLERS;
    private Player player;
    private Quest quest;
    
    public QuestFinishEvent(final Player player, final Quest quest) {
        this.player = player;
        this.quest = quest;
    }
    
    public HandlerList getHandlers() {
        return QuestFinishEvent.HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return QuestFinishEvent.HANDLERS;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Quest getQuest() {
        return this.quest;
    }
    
    static {
        HANDLERS = new HandlerList();
    }
}
