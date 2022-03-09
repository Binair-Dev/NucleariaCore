package su.binair.andasia.utils.koth;

import su.binair.andasia.utils.discord.*;
import org.bukkit.*;
import su.binair.andasia.*;
import su.binair.andasia.utils.time.*;
import org.bukkit.command.*;
import su.binair.utils.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.scoreboard.*;
import org.bukkit.scheduler.*;

public class KothInstantTask extends BukkitRunnable
{
    private static KothManager koth;
    public static boolean countdown;
    public static int id;

    public void run() {
        startCountdown();
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
                        if (p != null && KothUtils.isIn(p.getLocation(), KothInstantTask.koth.getPoint1(), KothInstantTask.koth.getPoint2())) {
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
                        KothInstantTask.koth.updateCaptureTime();
                    }
                    final List<Player> inKothZone = KothManager.getInKothZone();
                    if (!inKothZone.contains(Bukkit.getPlayer(KothManager.getCapturer()))) {
                        KothManager.setCapturer("Aucun");
                        KothManager.setCaptureTimeLeft(KothInstantTask.koth.getCaptureTime());
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
        KothInstantTask.koth = Main.getInstance().getKothManager();
    }
}
