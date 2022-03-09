package su.binair.andasia.utils.totem;

import su.binair.andasia.utils.discord.*;
import su.binair.utils.*;
import org.bukkit.block.*;
import su.binair.andasia.*;
import org.bukkit.scheduler.*;
import java.util.*;
import org.bukkit.*;

public class TotemUtils
{
    public static boolean isCreated;
    public static Location location;
    public static ArrayList<Location> totemblocks;
    public static String currentfac;
    
    public static void create() {
        DiscordUtils.sendDiscordMessage("AndaBot", DiscordEnum.EVENTS.getType(), "Totem", "Le Totem vient de commencer !");
        TotemUtils.isCreated = true;
        final Location tmp = TotemUtils.location;
        Block tmpb = tmp.getBlock();
        for (int a = 0; a != 5; ++a) {
            tmpb.setType(Material.QUARTZ_BLOCK);
            TotemUtils.totemblocks.add(tmpb.getLocation());
            tmpb = tmpb.getRelative(BlockFace.UP);
        }
    }
    
    public static void delete() {
        TotemUtils.isCreated = false;
        final Location tmp = TotemUtils.location;
        Block tmpb = tmp.getBlock();
        for (int a = 0; a != 5; ++a) {
            tmpb.setType(Material.AIR);
            TotemUtils.totemblocks.add(tmpb.getLocation());
            tmpb = tmpb.getRelative(BlockFace.UP);
        }
        TotemUtils.currentfac = " ";
        TotemUtils.totemblocks.clear();
        final BukkitScheduler scheduler = Bukkit.getScheduler();
        Main.getInstance().getTotemManager();
        scheduler.cancelTask(TotemManager.getTaskId());
    }
    
    public static void cancel() {
        TotemUtils.isCreated = false;
    }
    
    public static void destroy() {
        TotemUtils.isCreated = false;
        for (final Location loc : TotemUtils.totemblocks) {
            loc.getBlock().setType(Material.AIR);
        }
        TotemUtils.currentfac = " ";
        TotemUtils.totemblocks.clear();
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
    
    static {
        TotemUtils.totemblocks = new ArrayList<Location>();
    }
}
