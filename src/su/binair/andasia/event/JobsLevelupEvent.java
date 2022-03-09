package su.binair.andasia.event;

import org.bukkit.event.*;
import org.bukkit.entity.*;

public class JobsLevelupEvent extends Event
{
    private static final HandlerList HANDLERS;
    private int level;
    private Player player;
    private String jobsName;
    
    public JobsLevelupEvent(final int level, final Player player, final String jobsName) {
        this.level = level;
        this.player = player;
        this.jobsName = jobsName;
    }
    
    public String getJobsName() {
        return this.jobsName;
    }
    
    public HandlerList getHandlers() {
        return JobsLevelupEvent.HANDLERS;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public static HandlerList getHandlerList() {
        return JobsLevelupEvent.HANDLERS;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    static {
        HANDLERS = new HandlerList();
    }
}
