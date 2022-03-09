package su.binair.andasia.runnable;

import org.bukkit.scheduler.*;
import su.binair.andasia.*;
import com.massivecraft.factions.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import su.binair.database.*;
import java.util.*;

public class SaveRunnable extends BukkitRunnable
{
    public void run() {
        Main.getInstance().getQuestManager().getQuesUtils().saveAllQuestPlayer();
        Factions.i.saveToDisc();
        for (final Map.Entry<Player, Inventory> entry : Main.getReserve().entrySet()) {
            DatabaseUtils.saveInventory((Player)entry.getKey(), (Inventory)entry.getValue());
        }
    }
}
