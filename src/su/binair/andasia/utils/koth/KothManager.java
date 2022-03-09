package su.binair.andasia.utils.koth;

import org.bukkit.entity.*;
import su.binair.andasia.*;
import org.bukkit.scheduler.*;
import su.binair.utils.*;
import org.bukkit.*;
import su.binair.andasia.runnable.*;
import java.util.*;

public class KothManager
{
    private static KothManager instance;
    private static Location point1;
    private static Location point2;
    private int captureTime;
    private static List<Player> inKothZone;
    private static String capturer;
    private static int id;
    private static int captureTimeLeft;
    
    public KothManager() {
        KothManager.instance = this;
        if (!Main.getInstance().getConfig("koth").getConfig().getString("manager.location1").equalsIgnoreCase("null")) {
            KothManager.point1 = KothUtils.stringToLoc(Main.getInstance().getConfig("koth").getConfig().getString("manager.location1"));
        }
        if (!Main.getInstance().getConfig("koth").getConfig().getString("manager.location2").equalsIgnoreCase("null")) {
            KothManager.point2 = KothUtils.stringToLoc(Main.getInstance().getConfig("koth").getConfig().getString("manager.location2"));
        }
        this.setCaptureTime(Integer.valueOf(Main.getInstance().getConfig("koth").getConfig().getString("manager.capture_time")));
        setCaptureTimeLeft(this.getCaptureTime());
        setCapturer("Aucun");
    }
    
    public static KothManager getInstance() {
        return KothManager.instance;
    }
    
    public Location getPoint1() {
        return KothManager.point1;
    }
    
    public Location getPoint2() {
        return KothManager.point2;
    }
    
    public int getCaptureTime() {
        return this.captureTime;
    }
    
    public static boolean isRunning() {
        return getTaskId() != 0;
    }
    
    public static void setInKothZone(final List<Player> inKothZone) {
        KothManager.inKothZone = inKothZone;
    }
    
    public static List<Player> getInKothZone() {
        return KothManager.inKothZone;
    }
    
    public static void setCapturer(final String capturer) {
        KothManager.capturer = capturer;
    }
    
    public static void setTaskId(final int id) {
        KothManager.id = id;
    }
    
    public static void setCaptureTimeLeft(final int captureTimeLeft) {
        KothManager.captureTimeLeft = captureTimeLeft;
    }
    
    public static int getCaptureTimeLeft() {
        return KothManager.captureTimeLeft;
    }
    
    public static String getCapturer() {
        return KothManager.capturer;
    }
    
    public static int getTaskId() {
        return KothManager.id;
    }
    
    public static boolean saveLocations(final int amount, final Location loc) {
        if (amount == 1) {
            KothManager.point1 = loc;
            Main.getInstance().getConfig("koth").getConfig().set("manager.location1", (Object)KothUtils.locToString(loc));
            return Main.getInstance().getConfig("koth").save();
        }
        KothManager.point2 = loc;
        Main.getInstance().getConfig("koth").getConfig().set("manager.location2", (Object)KothUtils.locToString(loc));
        return Main.getInstance().getConfig("koth").save();
    }
    
    public static void setPoint1(final Location point1) {
        KothManager.point1 = point1;
    }
    
    public static void setPoint2(final Location point2) {
        KothManager.point2 = point2;
    }
    
    public void setCaptureTime(final int captureTime) {
        this.captureTime = captureTime;
    }
    
    public int updateCaptureTime() {
        return KothManager.captureTimeLeft--;
    }
    
    public static void clearKothPlayerList() {
        KothManager.inKothZone.clear();
    }
    
    public void startNormalKoth() {
        Main.getInstance();
        final BukkitScheduler scheduler = Main.getPlugin().getServer().getScheduler();
        Main.getInstance();
        scheduler.runTaskAsynchronously(Main.getPlugin(), (BukkitRunnable)new KothTask());
    }
    
    public void startInstantKoth() {
        Main.getInstance();
        final BukkitScheduler scheduler = Main.getPlugin().getServer().getScheduler();
        Main.getInstance();
        scheduler.runTaskAsynchronously(Main.getPlugin(), (BukkitRunnable)new KothInstantTask());
        final String[] list = { ToolBox.getBeautyStart() + "§e§lLe koth §a/warp koth §e§lest lanc! Que le combat commence !" };
        ToolBox.broadcast(list);
    }
    
    public void stopKoth() {
        final String[] list = { ToolBox.getBeautyStart() + "§eLe §6KOTH §evient  d'été re §carret\u00e9§e." };
        ToolBox.broadcast(list);
        Bukkit.getScheduler().cancelTask(getTaskId());
        setTaskId(0);
        setCaptureTimeLeft(this.getCaptureTime());
        Main.getInstance();
        final BukkitScheduler scheduler = Main.getPlugin().getServer().getScheduler();
        Main.getInstance();
        scheduler.scheduleSyncDelayedTask(Main.getPlugin(), (BukkitRunnable)new ScoreboardClear());
    }
    
    static {
        KothManager.inKothZone = new ArrayList<Player>();
    }
}
