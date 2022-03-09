package su.binair.andasia.utils.combattag;

import java.util.*;
import org.bukkit.entity.*;
import su.binair.andasia.*;

public class CombatUtils
{
    public static HashMap<Player, Integer> tagged;
    
    public static void addTagg(final Player player) {
        CombatUtils.tagged.put(player, Main.getInstance().getConfig("combat_tag").getConfig().getInt("combat_time"));
    }
    
    static {
        CombatUtils.tagged = new HashMap<Player, Integer>();
    }
}
