package su.binair.andasia.utils.staff;

import org.bukkit.entity.*;
import org.bukkit.*;
import su.binair.utils.*;
import java.util.*;

public class StaffUtils
{
    public static List<Player> inStaff;
    public static List<Player> inFreeze;
    public static List<Player> inVanish;
    
    public static Player pickRandomPlayer() {
        return Bukkit.getOnlinePlayers()[new Random().nextInt(Bukkit.getOnlinePlayers().length)];
    }
    
    public static void freezePlayer(final Player target) {
        if (target != null) {
            if (StaffUtils.inFreeze.contains(target)) {
                StaffUtils.inFreeze.remove(target);
                ToolBox.sendMessage(target, "§eTu as été §aUnFreeze §epar un membre du staff, bon jeu a toi !");
                target.setWalkSpeed(0.2f);
            }
            else {
                target.setWalkSpeed(0.0f);
                StaffUtils.inFreeze.add(target);
                ToolBox.sendMessage(target, "§eTu as été freeze par un membre du staff, viens §cimm\u00e9diatement \u00e9esur Teamspeak: §2§lts.andasia.fr§e.");
            }
        }
    }
    
    static {
        StaffUtils.inStaff = new ArrayList<Player>();
        StaffUtils.inFreeze = new ArrayList<Player>();
        StaffUtils.inVanish = new ArrayList<Player>();
    }
}
