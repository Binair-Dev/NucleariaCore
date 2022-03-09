package su.binair.andasia.utils.distributeur;

import java.util.*;
import su.binair.andasia.*;
import su.binair.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class DistributeurUtils
{
    public static void spawnDistributeur() {
        final World world = Bukkit.getWorld("radioactive");
        final int x = new Random().nextInt(550);
        final int z = new Random().nextInt(460) + 160;
        final int y = 255;
        if (!Main.getInstance().getServer().getWorlds().contains(world)) {
            return;
        }
        final Location oldDistrib = stringToLoc(Main.getInstance().getConfig("distributeur").getConfig().getString("location"));
        try {
            if (!oldDistrib.getChunk().isLoaded()) {
                oldDistrib.getChunk().load();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (Main.getInstance().getConfig("distributeur").getConfig().getString("location") != null) {
            oldDistrib.getBlock().setType(Material.AIR);
        }
        final Location loc = new Location(world, (double)x, (double)y, (double)z);
        if (!loc.getChunk().isLoaded()) {
            loc.getChunk().load();
        }
        while (loc.getBlock().getType() == Material.AIR) {
            loc.subtract(0.0, 1.0, 0.0);
        }
        loc.add(0.0, 1.0, 0.0).getBlock().setType(Material.DISTRIBUTEUR);
        Main.getInstance().getConfig("distributeur").getConfig().set("location", (Object)locToString(loc));
        Main.getInstance().getConfig("distributeur").save();
        for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission("andasia.distributeur")) {
                ToolBox.sendMessage(player, "Le distributeur vient d'apparaitre en: X: " + loc.getX() + " Y: " + loc.getY() + " Z: " + loc.getZ());
            }
        }
    }
    
    public static String locToString(final Location l) {
        final String ret = String.valueOf(l.getWorld().getName()) + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
        return ret;
    }
    
    public static Location stringToLoc(final String s) {
        final String[] a = s.split(",");
        final World w = Bukkit.getServer().getWorld(a[0]);
        final float x = Float.parseFloat(a[1]);
        final float y = Float.parseFloat(a[2]);
        final float z = Float.parseFloat(a[3]);
        return new Location(w, (double)x, (double)y, (double)z);
    }
}
