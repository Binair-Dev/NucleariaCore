package su.binair.andasia.utils.uuid;

import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import su.binair.utils.*;
import org.bukkit.*;
import java.io.*;

public class UUIDUtils
{
    public static int isOnline;
    
    public static void kickSpoofed(final Player player, final PlayerLoginEvent event, final String uuid) {
        event.disallow(PlayerLoginEvent.Result.KICK_BANNED, ToolBox.format("§cTon acc\u00e9s au serveur a été refus\u00e9!"));
    }
    
    public static boolean onlineMode() {
        if (UUIDUtils.isOnline == 2) {
            return Bukkit.getServer().getOnlineMode();
        }
        return UUIDUtils.isOnline == 1;
    }
    
    public synchronized void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        final DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        try {
            final String data = in.readLine().replaceAll("UUIDSpoofFix", "");
            if (data.equals("true")) {
                UUIDUtils.isOnline = 1;
            }
            else {
                UUIDUtils.isOnline = 0;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
