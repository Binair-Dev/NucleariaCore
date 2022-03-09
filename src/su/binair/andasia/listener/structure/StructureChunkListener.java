package su.binair.andasia.listener.structure;

import su.binair.andasia.object.structure.*;
import org.bukkit.event.world.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;
import su.binair.andasia.utils.generations.*;
import java.util.*;
import su.binair.andasia.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class StructureChunkListener implements Listener
{
    public static HashMap<String, CustomPopulator> pops;
    
    @EventHandler
    public void onChunkLoad(final ChunkPopulateEvent e) {
        if (e.getWorld().equals(Bukkit.getWorld("world"))) {
            new BukkitRunnable() {
                public void run() {
                    for (final Map.Entry<String, CustomPopulator> pop : StructureChunkListener.pops.entrySet()) {
                        PopulatorUtils.generateStructure(e.getWorld(), e.getChunk(), pop.getValue().getRarety(), pop.getValue().getSchematicName());
                    }
                }
            }.runTaskAsynchronously(Main.getPlugin());
        }
        else {
            new BukkitRunnable() {
                public void run() {
                    PopulatorUtils.generateStructure(e.getWorld(), e.getChunk(), 3.0, "radiaoactive_plants");
                    PopulatorUtils.generateStructure(e.getWorld(), e.getChunk(), 25.0, "tree1");
                    PopulatorUtils.generateStructure(e.getWorld(), e.getChunk(), 20.0, "tree2");
                    PopulatorUtils.generateStructure(e.getWorld(), e.getChunk(), 15.0, "tree3");
                }
            }.runTaskAsynchronously(Main.getPlugin());
            PopulatorUtils.generateMobs(e.getWorld(), e.getChunk(), 7.0, EntityType.SCIENTIST);
            PopulatorUtils.generateMobs(e.getWorld(), e.getChunk(), 7.0, EntityType.INDIGENE);
            PopulatorUtils.generateMobs(e.getWorld(), e.getChunk(), 5.0, EntityType.MUTATEDENDERMAN);
            PopulatorUtils.generateMobs(e.getWorld(), e.getChunk(), 5.0, EntityType.MUTATEDZOMBIE);
        }
    }
    
    static {
        StructureChunkListener.pops = new HashMap<String, CustomPopulator>();
    }
}
