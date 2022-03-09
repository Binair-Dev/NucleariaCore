package su.binair.andasia.runnable;

import org.bukkit.scheduler.*;
import su.binair.andasia.utils.combattag.*;
import su.binair.andasia.utils.teleport.TeleportUtils;
import su.binair.utils.*;
import su.binair.andasia.utils.teleport.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.*;
import java.util.*;

public class EverySecondsTask extends BukkitRunnable
{
    public void run() {
        for (final Map.Entry<Player, Integer> map : CombatUtils.tagged.entrySet()) {
            if (map != null) {
                int cd = map.getValue();
                --cd;
                CombatUtils.tagged.put(map.getKey(), cd);
                if (map.getValue() != 0) {
                    continue;
                }
                CombatUtils.tagged.remove(map.getKey());
                ToolBox.sendMessage((Player)map.getKey(), "§aTu n'es plus en combat, tu peux maintenant te d\u00e9connecter !");
            }
        }
        for (final Map.Entry<OfflinePlayer, Integer> map2 : TeleportUtils.randomtp.entrySet()) {
            if (map2 != null) {
                int cd = map2.getValue();
                --cd;
                TeleportUtils.randomtp.put(map2.getKey(), cd);
                if (map2.getValue() != 0) {
                    continue;
                }
                TeleportUtils.randomtp.remove(map2.getKey());
            }
        }
        for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.isInsideVehicle() && player.getVehicle().getType() == EntityType.PIG) {
                final Pig pig = (Pig)player.getVehicle();
                if (pig.hasPotionEffect(PotionEffectType.INVISIBILITY) && pig.getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) {
                    pig.remove();
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    player.removePotionEffect(PotionEffectType.FEATHER_FALLING);
                }
            }
        }
    }
}
