package su.binair.andasia.utils.generations;

import org.bukkit.Location;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.*;
import su.binair.objects.*;
import su.binair.utils.*;
import com.sk89q.worldedit.*;
import java.io.*;

public class PopulatorUtils
{
    public static void generateMobs(final World world, final Chunk chunk, final double rarety, final EntityType type) {
        final Random rand = new Random();
        final double random = new Double(rand.nextInt(200));
        if (random > 0.0 && random < rarety) {
            mobPopulator(chunk, rand, type, world);
        }
    }
    
    private static void mobPopulator(final Chunk chunk, final Random rand, final EntityType type, final World world) {
        if (world.equals(Bukkit.getWorld("radioactive"))) {
            final int entityNumber = new Random().nextInt(5);
            if (entityNumber > 0) {
                try {
                    for (int x = 0; x < entityNumber; ++x) {
                        final Location chunkRandomLoc = chunk.getBlock(rand.nextInt(16), rand.nextInt(256), rand.nextInt(16)).getLocation();
                        final Location loc = new SafeRadioactiveLocation((int)chunkRandomLoc.getX(), (int)chunkRandomLoc.getZ()).getLocation();
                        world.spawnEntity(loc, type);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void generateStructure(final World world, final Chunk chunk, final double rarety, final String schematicname) {
        final Random rand = new Random();
        final double random = new Double(rand.nextInt(200));
        if (random > 0.0 && random < rarety) {
            generate(chunk, rand, schematicname, world);
        }
    }
    
    public static void generate(final Chunk chunk, final Random rand, final String name, final World world) {
        if (world.equals(Bukkit.getWorld("world"))) {
            final Location chunkRandomLoc = chunk.getBlock(rand.nextInt(16), rand.nextInt(256), rand.nextInt(16)).getLocation();
            final Location loc = new SafeCoordinatesLocation((int)chunkRandomLoc.getX(), (int)chunkRandomLoc.getZ()).getLocation();
            if (loc != null) {
                try {
                    SchematicUtils.pasteSchematic(world, name, (int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
                }
                catch (MaxChangedBlocksException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (world.equals(Bukkit.getWorld("radioactive"))) {
            final Location chunkRandomLoc = chunk.getBlock(rand.nextInt(16), rand.nextInt(256), rand.nextInt(16)).getLocation();
            final Location loc = new SafeRadioactiveLocation((int)chunkRandomLoc.getX(), (int)chunkRandomLoc.getZ()).getLocation();
            if (loc != null) {
                try {
                    SchematicUtils.pasteSchematic(world, name, (int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
                }
                catch (MaxChangedBlocksException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
