package su.binair.andasia.object.bungeeMining;

import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class PlayerReserve
{
    private String playerName;
    private List<ItemStack> myReserve;
    
    public PlayerReserve(final String playerName) {
        this.playerName = playerName;
        this.myReserve = new ArrayList<ItemStack>();
    }
    
    public String getPlayerName() {
        return this.playerName;
    }
    
    public List<ItemStack> getMyReserve() {
        return this.myReserve;
    }
    
    public Player getPlayer() {
        return Bukkit.getPlayer(this.getPlayerName());
    }
    
    public void addItem(final ItemStack item) {
        this.getMyReserve().add(item);
    }
    
    public void remItem(final ItemStack item) {
        this.getMyReserve().remove(item);
    }
    
    public void clearItems() {
        this.getMyReserve().clear();
    }
}
