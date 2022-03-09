package su.binair.andasia.utils.totem;

import org.bukkit.*;
import su.binair.andasia.*;
import su.binair.andasia.Main;
import su.binair.api.*;
import su.binair.utils.*;
import org.bukkit.scheduler.*;

public class TotemTask extends BukkitRunnable
{
    public static boolean countdown;
    public static int id;
    
    public void run() {
        startCountdown();
    }
    
    public static void startCountdown() {
        TotemTask.countdown = true;
        final BukkitScheduler scheduler = Bukkit.getScheduler();
        Main.getInstance();
        TotemTask.id = scheduler.scheduleSyncRepeatingTask(Main.getPlugin(), (Runnable)new Runnable() {
            private int time = 300;
            
            @Override
            public void run() {
                Main.getInstance().getTotemManager();
                TotemManager.setTaskId(TotemTask.id);
                switch (this.time) {
                    case 300: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §65 minutes §e!"));
                        break;
                    }
                    case 240: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §64 minutes §e!"));
                        break;
                    }
                    case 180: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §63 minutes §e!"));
                        break;
                    }
                    case 120: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §62 minutes §e!"));
                        break;
                    }
                    case 60: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §61 minutes §e!"));
                        break;
                    }
                    case 30: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §630 secondes §e!"));
                        break;
                    }
                    case 10: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §610 secondes §e!"));
                        break;
                    }
                    case 5: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §65 secondes §e!"));
                        break;
                    }
                    case 4: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §64 secondes §e!"));
                        break;
                    }
                    case 3: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §63 secondes §e!"));
                        break;
                    }
                    case 2: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §62 secondes §e!"));
                        break;
                    }
                    case 1: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du totem §a/warp totem §edans §61 seconde §e!"));
                        break;
                    }
                    case 0: {
                        TotemUtils.create();
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§e§lLe totem §a/warp totem §e§lest lanc! Que le combat commence !"));
                        break;
                    }
                }
                --this.time;
                if (this.time < 0) {
                    TotemTask.countdown = false;
                }
            }
        }, 20L, 20L);
    }
}
