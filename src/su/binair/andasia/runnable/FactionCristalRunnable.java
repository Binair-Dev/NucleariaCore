package su.binair.andasia.runnable;

import org.bukkit.scheduler.*;
import java.text.*;
import su.binair.api.*;
import com.massivecraft.factions.*;
import su.binair.andasia.utils.cristalFaction.*;
import org.bukkit.inventory.*;
import org.bukkit.craftbukkit.v1_7_R4.inventory.*;
import org.bukkit.*;
import java.util.*;

public class FactionCristalRunnable extends BukkitRunnable
{
    public void run() {
        final Date date = new Date();
        final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        final String hour = formatter.format(date);
        for (final Faction fac : FactionAPI.getAllFaction()) {
            if (fac != null && fac.getOnlinePlayers().size() > 0 && hour.equalsIgnoreCase("21:00:00") && fac.getHome() != null) {
                final int random = new Random().nextInt(CristalUtils.crystalRecompense.size() - 1);
                final net.minecraft.server.v1_7_R4.ItemStack item = CraftItemStack.asNMSCopy((ItemStack)CristalUtils.crystalRecompense.get(random));
                CristalUtils.fill(Bukkit.getWorld("world"), fac.getHome().getBlockX(), fac.getHome().getBlockY() - 1, fac.getHome().getBlockZ(), item);
            }
        }
    }
}
