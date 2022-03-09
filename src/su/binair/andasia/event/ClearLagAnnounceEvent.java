package su.binair.andasia.event;

import org.bukkit.event.*;

public class ClearLagAnnounceEvent extends Event
{
    private static final HandlerList HANDLERS;
    private int delay;
    
    public ClearLagAnnounceEvent(final int delay) {
        this.delay = delay;
    }
    
    public HandlerList getHandlers() {
        return ClearLagAnnounceEvent.HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return ClearLagAnnounceEvent.HANDLERS;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    static {
        HANDLERS = new HandlerList();
    }
}
