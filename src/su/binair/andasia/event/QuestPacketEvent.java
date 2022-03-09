package su.binair.andasia.event;

import org.bukkit.event.*;
import org.bukkit.entity.*;

public class QuestPacketEvent extends Event
{
    private static final HandlerList HANDLERS;
    private Player player;
    private int questId;
    
    public QuestPacketEvent(final Player player, final int questId) {
        this.player = player;
        this.questId = questId;
    }
    
    public HandlerList getHandlers() {
        return QuestPacketEvent.HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return QuestPacketEvent.HANDLERS;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public int getQuestId() {
        return this.questId;
    }
    
    static {
        HANDLERS = new HandlerList();
    }
}
