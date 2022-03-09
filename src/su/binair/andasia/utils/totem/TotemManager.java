package su.binair.andasia.utils.totem;

import su.binair.andasia.*;
import org.bukkit.configuration.file.*;
import org.bukkit.*;
import java.io.*;

public class TotemManager
{
    private static FileConfiguration config;
    private static FileConfiguration stats;
    private static File filestorage;
    public static File filestats;
    private static int taskId;
    
    public TotemManager() {
        this.createConfig();
        setTaskId(0);
    }
    
    public static FileConfiguration getStats() {
        return TotemManager.stats;
    }
    
    public void createConfig() {
        final File folder = new File(Main.getInstance().getDataFolder(), "totem");
        if (!folder.exists()) {
            folder.mkdir();
        }
        TotemManager.filestorage = new File(String.valueOf(folder.getPath()) + "/setpoint.yml");
        TotemManager.filestats = new File(String.valueOf(folder.getPath()) + "/stats.yml");
        if (!TotemManager.filestats.exists()) {
            try {
                TotemManager.filestats.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!TotemManager.filestorage.exists()) {
            try {
                TotemManager.filestorage.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        TotemManager.config = (FileConfiguration)YamlConfiguration.loadConfiguration(TotemManager.filestorage);
        TotemManager.stats = (FileConfiguration)YamlConfiguration.loadConfiguration(TotemManager.filestats);
        if (TotemManager.config.contains("Location")) {
            TotemUtils.location = TotemUtils.stringToLoc(TotemManager.config.getString("Location"));
        }
    }
    
    public static void setLocation(final Location loc) {
        TotemUtils.location = loc;
        TotemManager.config.set("Location", (Object)TotemUtils.locToString(loc));
        try {
            TotemManager.config.save(TotemManager.filestorage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void disableTotem() {
        TotemUtils.destroy();
    }
    
    public static FileConfiguration getConfig() {
        return TotemManager.config;
    }
    
    public static int getTaskId() {
        return TotemManager.taskId;
    }
    
    public static void setTaskId(final int taskId) {
        TotemManager.taskId = taskId;
    }
}
