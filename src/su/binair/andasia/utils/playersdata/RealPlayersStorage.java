package su.binair.andasia.utils.playersdata;

import org.bukkit.*;
import java.util.*;
import org.bukkit.entity.*;

public class RealPlayersStorage
{
    private HashMap<String, PlayerData> realplayers;
    
    public RealPlayersStorage() {
        this.realplayers = new HashMap<String, PlayerData>();
        for (final OfflinePlayer player : PlayersDataUtils.getPlayers()) {
            if (player.getName() == null) {
                continue;
            }
            if (!this.hasRealPlayer(player)) {
                this.addRealPlayer(player);
            }
            else {
                if (player.getLastPlayed() <= this.realplayers.get(player.getName().toLowerCase()).getLastPlayed()) {
                    continue;
                }
                this.addRealPlayer(player);
            }
        }
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player player2 = onlinePlayers[i];
            this.addRealPlayer((OfflinePlayer)player2);
        }
    }
    
    public boolean hasRealPlayer(final OfflinePlayer player) {
        return this.realplayers.containsKey(player.getName().toLowerCase());
    }
    
    public void addRealPlayer(final OfflinePlayer player) {
        this.realplayers.put(player.getName().toLowerCase(), new PlayerData(player));
    }
    
    public boolean isPlayerReal(final OfflinePlayer player) {
        if (this.hasRealPlayer(player)) {
            final PlayerData playerdata = this.realplayers.get(player.getName().toLowerCase());
            return playerdata.getName().equals(player.getName());
        }
        return false;
    }
    
    public String getRealPlayerValidName(final OfflinePlayer player) {
        return this.realplayers.get(player.getName().toLowerCase()).getName();
    }
    
    private class PlayerData
    {
        private String name;
        private long lastPlayed;
        
        public PlayerData(final OfflinePlayer player) {
            this.name = player.getName();
            this.lastPlayed = player.getLastPlayed();
        }
        
        public String getName() {
            return this.name;
        }
        
        public long getLastPlayed() {
            return this.lastPlayed;
        }
    }
}
