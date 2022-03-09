package su.binair.andasia.utils.generations;

import java.util.*;
import su.binair.objects.*;
import su.binair.andasia.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import su.binair.andasia.object.structure.*;
import su.binair.andasia.listener.structure.*;

public class StructureUtils
{
    public static HashMap<String, YmlConfigurationInFolder> structures;
    
    public static int loadStructures() {
        final StringBuilder sb = new StringBuilder();
        Main.getInstance();
        final File dataFolder = new File(sb.append(Main.getPlugin().getDataFolder()).append("/structures").toString());
        final File[] files = dataFolder.listFiles();
        int amount = 0;
        for (final File f : files) {
            if (f != null) {
                ++amount;
                final YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
                final String name = config.getString("name");
                final String schematicName = config.getString("schematic_name");
                final double rarety = config.getDouble("rarety");
                final CustomPopulator pop = new CustomPopulator(name, schematicName, rarety);
                StructureChunkListener.pops.put(name, pop);
                StructureUtils.structures.put(name, new YmlConfigurationInFolder(Main.getPlugin(), name));
            }
        }
        System.out.println("(Andasia) " + amount + " structures charg\u00e9es.");
        return amount;
    }
    
    public static boolean createNewStructure(final String name, final String schematicName, final double rarety) {
        Main.getInstance();
        final YmlConfigurationInFolder config = new YmlConfigurationInFolder(Main.getPlugin(), name);
        config.getConfig().set("name", (Object)name);
        config.getConfig().set("schematic_name", (Object)schematicName);
        config.getConfig().set("rarety", (Object)rarety);
        final CustomPopulator pop = new CustomPopulator(name, schematicName, rarety);
        StructureChunkListener.pops.put(name, pop);
        StructureUtils.structures.put(name, config);
        return config.save();
    }
    
    public static boolean removeStructure(final String name) {
        final YmlConfigurationInFolder config = StructureUtils.structures.get(name);
        if (StructureUtils.structures.get(name) != null) {
            StructureChunkListener.pops.remove(name);
            StructureUtils.structures.remove(name);
            return config.getFile().delete();
        }
        return false;
    }
    
    static {
        StructureUtils.structures = new HashMap<String, YmlConfigurationInFolder>();
    }
}
