package su.binair.andasia.utils.teleport;

import java.util.*;
import org.bukkit.entity.*;
import su.binair.utils.*;
import su.binair.objects.*;
import org.bukkit.*;

public class TeleportUtils
{
    public static HashMap<OfflinePlayer, Integer> randomtp;
    
    public static void randomTeleportPlayer(final Player player) {
        if (TeleportUtils.randomtp.containsKey(Bukkit.getOfflinePlayer(player.getUniqueId()))) {
            final String[] list = { ToolBox.getBeautyStart() + "§aVous devez encore attendre: " + ToolBox.format("§e" + ToolBox.secondsToHour((long)TeleportUtils.randomtp.get(Bukkit.getOfflinePlayer(player.getName())))) };
            ToolBox.sendCustomComposedMessage(player, list);
        }
        else {
            final Location location = new SafeTeleportLocation().getLocation();
            final Chunk chunk = location.getChunk();
            if (!chunk.isLoaded()) {
                chunk.load();
            }
            player.teleport(location.add(0.0, 1.0, 0.0));
            final String[] list2 = { ToolBox.getBeautyStart() + "§a§lVous venez d'été re t\u00e9l\u00e9port\u00e9 en", ToolBox.getBeautyStart() + "§cX: §e" + location.getX(), ToolBox.getBeautyStart() + "§cY: §e" + location.getY(), ToolBox.getBeautyStart() + "§cZ: §e" + location.getZ() };
            ToolBox.sendCustomComposedMessage(player, list2);
            TeleportUtils.randomtp.put(Bukkit.getOfflinePlayer(player.getName()), ToolBox.getMinutesInSeconds(180));
        }
    }
    
    static {
        TeleportUtils.randomtp = new HashMap<OfflinePlayer, Integer>();
    }
}
