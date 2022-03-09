package su.binair.andasia.utils.packet;

import org.bukkit.plugin.*;
import org.bukkit.*;
import su.binair.packets.network.*;
import su.binair.packets.*;
import org.bukkit.plugin.messaging.*;

public class PacketUtils
{
    public static void sendActionBarMessage(final String s, final Plugin m) {
        final int duration = 50;
        final boolean rainbow = false;
        final int i = 1;
        final String message = ChatColor.translateAlternateColorCodes('&', s);
        final ActionBarPacket packet = new ActionBarPacket(message, duration, rainbow);
        CustomPacketHandler.dispatch((CustomPacket)packet, (PluginMessageRecipient[])m.getServer().getOnlinePlayers());
    }
}
