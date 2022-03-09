package su.binair.andasia.utils.koth;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class KothUtils
{
    public static Player pickRandomPlayer(final List<Player> list) {
        return Bukkit.getOnlinePlayers()[new Random().nextInt(list.size())];
    }
    
    public static List<Player> getEntities(final Location locA, final Location locB) {
        final Player[] entities = Bukkit.getOnlinePlayers();
        final List<Player> list = new ArrayList<Player>();
        for (final Entity entity : entities) {
            if (entity != null) {
                final Location loc = entity.getLocation();
                if (!isIn(loc, locA, locB)) {
                    list.remove(entity);
                }
            }
        }
        return list;
    }
    
    public static boolean isIn(final Location loc, final Location locA, final Location locB) {
        final double minX = Math.min(locA.getX(), locB.getX());
        final double maxX = Math.max(locA.getX(), locB.getX());
        final double minY = Math.min(locA.getY(), locB.getY());
        final double maxY = Math.max(locA.getY(), locB.getY());
        final double minZ = Math.min(locA.getZ(), locB.getZ());
        final double maxZ = Math.max(locA.getZ(), locB.getZ());
        return loc.getX() > minX && loc.getX() < maxX && loc.getY() > minY && loc.getY() < maxY && loc.getZ() > minZ && loc.getZ() < maxZ;
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
