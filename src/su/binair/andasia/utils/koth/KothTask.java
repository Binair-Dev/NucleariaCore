package su.binair.andasia.utils.koth;

import org.bukkit.*;
import su.binair.andasia.*;
import su.binair.andasia.Main;
import su.binair.api.*;
import org.bukkit.scheduler.*;
import su.binair.andasia.utils.discord.*;
import su.binair.utils.*;
import su.binair.andasia.utils.time.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.scoreboard.*;

public class KothTask extends BukkitRunnable
{
    private static KothManager koth;
    public static boolean countdown;
    public static int id;
    public static int countdown_id;
    
    public void run() {
        startAnnounceCountdown();
    }
    
    public static void startAnnounceCountdown() {
        KothTask.countdown = true;
        final BukkitScheduler scheduler = Bukkit.getScheduler();
        Main.getInstance();
        KothTask.countdown_id = scheduler.scheduleSyncRepeatingTask(Main.getPlugin(), (Runnable)new Runnable() {
            private int time = 300;
            
            @Override
            public void run() {
                Main.getInstance().getKothManager();
                KothManager.setTaskId(KothTask.countdown_id);
                switch (this.time) {
                    case 300: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §65 minutes §e!"));
                        break;
                    }
                    case 240: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §64 minutes §e!"));
                        break;
                    }
                    case 180: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §63 minutes §e!"));
                        break;
                    }
                    case 120: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §62 minutes §e!"));
                        break;
                    }
                    case 60: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §61 minutes §e!"));
                        break;
                    }
                    case 30: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §630 secondes §e!"));
                        break;
                    }
                    case 10: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §610 secondes §e!"));
                        break;
                    }
                    case 5: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §65 secondes §e!"));
                        break;
                    }
                    case 4: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §64 secondes §e!"));
                        break;
                    }
                    case 3: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §63 secondes §e!"));
                        break;
                    }
                    case 2: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §62 secondes §e!"));
                        break;
                    }
                    case 1: {
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§eApparition du koth §a/warp koth §edans §61 seconde §e!"));
                        break;
                    }
                    case 0: {
                        KothTask.startCountdown();
                        Bukkit.broadcastMessage(PrefixAPI.getPrefix() + ToolBox.format("§e§lLe koth §a/warp koth §e§lest lanc! Que le combat commence !"));
                        break;
                    }
                }
                --this.time;
                if (this.time < 0) {
                    KothTask.countdown = false;
                }
            }
        }, 20L, 20L);
    }
    
    public static void startCountdown() {
        DiscordUtils.sendDiscordMessage("AndaBot", DiscordEnum.EVENTS.getType(), "Koth", "Le Koth vient de commencer!");
        KothInstantTask.countdown = true;
        final BukkitScheduler scheduler = Bukkit.getScheduler();
        Main.getInstance();
        KothInstantTask.id = scheduler.scheduleSyncRepeatingTask(Main.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                if (KothManager.getCaptureTimeLeft() > 0) {
                    final Player[] playerList = Bukkit.getServer().getOnlinePlayers();
                    KothManager.getInKothZone().clear();
                    KothManager.setTaskId(KothInstantTask.id);
                    for (final Player p : playerList) {
                        if (p != null && KothUtils.isIn(p.getLocation(), KothTask.koth.getPoint1(), KothTask.koth.getPoint2())) {
                            KothManager.getInKothZone().add(p);
                        }
                    }
                    if (KothManager.getCapturer().equalsIgnoreCase("Aucun")) {
                        if (KothManager.getInKothZone().size() > 0) {
                            KothManager.setCapturer(KothUtils.pickRandomPlayer(KothManager.getInKothZone()).getName());
                        }
                        else {
                            KothManager.setCapturer("Aucun");
                        }
                    }
                    else {
                        KothTask.koth.updateCaptureTime();
                    }
                    final List<Player> inKothZone = KothManager.getInKothZone();
                    if (!inKothZone.contains(Bukkit.getPlayer(KothManager.getCapturer()))) {
                        KothManager.setCapturer("Aucun");
                        KothManager.setCaptureTimeLeft(KothTask.koth.getCaptureTime());
                    }
                    final Scoreboard sb = ScoreboardUtils.createScoreboard("§6§lKOTH");
                    ScoreboardUtils.addLineToScoreboard(sb, "§7§l§aJoueur", 5);
                    final Scoreboard scoreboard = sb;
                    final StringBuilder append = new StringBuilder().append("§2");
                    ScoreboardUtils.addLineToScoreboard(scoreboard, append.append(KothManager.getCapturer()).toString(), 4);
                    ScoreboardUtils.addLineToScoreboard(sb, "", 3);
                    ScoreboardUtils.addLineToScoreboard(sb, "§7§l§aTemps", 2);
                    final Scoreboard scoreboard2 = sb;
                    final StringBuilder append2 = new StringBuilder().append("§2");
                    ScoreboardUtils.addLineToScoreboard(scoreboard2, append2.append(TimeUtils.secondToMinSec(KothManager.getCaptureTimeLeft())).toString(), 1);
                    ScoreboardUtils.displayScoreboard(sb);
                }
                else {
                    final String recompense_name = Main.getInstance().getConfig("koth").getConfig().getString("recompense.name");
                    final String string = Main.getInstance().getConfig("koth").getConfig().getString("recompense.commande");
                    final String s = "@player";
                    final String recompense = string.replace(s, KothManager.getCapturer());
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), recompense);
                    final String[] array2 = { null };
                    final int n = 0;
                    final StringBuilder append3 = new StringBuilder().append(ToolBox.getBeautyStart()).append("§eLe joueur §6");
                    array2[n] = append3.append(KothManager.getCapturer()).append(" §evient de r\u00e9mporter le §6KOTH §eet remporte §a").append(recompense_name).append("§e.").toString();
                    final String[] perm = array2;
                    ToolBox.broadcast(perm);
                    Main.getInstance().getKothManager().stopKoth();
                }
            }
        }, 20L, 20L);
    }
    
    static {
        KothTask.koth = Main.getInstance().getKothManager();
    }
}
