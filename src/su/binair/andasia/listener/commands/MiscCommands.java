package su.binair.andasia.listener.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_7_R4.EnumChatFormat;
import su.binair.utils.ToolBox;

public class MiscCommands implements Listener
{
    @EventHandler
    public void discord(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/discord")) {
            e.setCancelled(true);
            final String[] messages = { ToolBox.getBeautyStart() + "discord.nuclearia.fr" };
            ToolBox.broadcast(messages);
        }
    }

    @EventHandler
    public void site(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/site")) {
            e.setCancelled(true);
            final String[] messages = { ToolBox.getBeautyStart() + "https://nuclearia.fr/" };
            ToolBox.broadcast(messages);
        }
    }

    @EventHandler
    public void vote(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/vote")) {
            e.setCancelled(true);
            final String[] messages = { ToolBox.getBeautyStart() + "https://nuclearia.fr/vote/" };
            ToolBox.broadcast(messages);
        }
    }
    
    @EventHandler
    public void boutique(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/boutique")) {
            e.setCancelled(true);
            final String[] messages = { ToolBox.getBeautyStart() + "https://nuclearia.fr/shop" };
            ToolBox.broadcast(messages);
        }
    }
}
