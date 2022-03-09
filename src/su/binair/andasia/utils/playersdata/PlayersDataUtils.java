package su.binair.andasia.utils.playersdata;

import java.util.*;
import java.io.*;
import org.bukkit.*;

public class PlayersDataUtils
{
    public static LinkedList<OfflinePlayer> getPlayers() {
        final LinkedList<OfflinePlayer> players = new LinkedList<OfflinePlayer>();
        File[] listFiles;
        for (int length = (listFiles = getPlayersDataFolder().listFiles()).length, i = 0; i < length; ++i) {
            final File file = listFiles[i];
            if (file.getName().endsWith(".dat")) {
                final String uuidstring = file.getName().substring(0, file.getName().length() - 4);
                try {
                    players.add(Bukkit.getOfflinePlayer(UUID.fromString(uuidstring)));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return players;
    }
    
    public static File getPlayerFile(final OfflinePlayer player) {
        return new File(getPlayersDataFolder(), String.valueOf(player.getUniqueId().toString()) + ".dat");
    }
    
    private static File getPlayersDataFolder() {
        return new File(Bukkit.getWorlds().get(0).getWorldFolder(), "playerdata");
    }
}
