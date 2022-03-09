package su.binair.andasia.event;

import org.bukkit.event.*;
import org.bukkit.entity.*;

public class CombatTagListener extends Event
{
    private static final HandlerList HANDLERS;
    private int delay;
    private Player player;
    private Player opponent;
    
    public CombatTagListener(final int delay, final Player player, final Player opponent) {
        this.delay = delay;
        this.player = player;
        this.opponent = opponent;
    }
    
    public HandlerList getHandlers() {
        return CombatTagListener.HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return CombatTagListener.HANDLERS;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Player getOpponent() {
        return this.opponent;
    }
    
    static {
        HANDLERS = new HandlerList();
    }
}
