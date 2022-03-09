package su.binair.andasia.listener.generation;

import org.bukkit.event.player.*;
import su.binair.andasia.*;
import su.binair.andasia.Main;
import su.binair.objects.*;
import su.binair.api.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import su.binair.utils.*;
import java.io.*;
import org.bukkit.*;
import java.util.*;

public class GenerationCommand implements Listener
{
    public static List<String> filons;
    public static HashMap<Location, Material> blockToReplace;
    
    @EventHandler
    public void gen(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final World w = p.getWorld();
        final String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/generation") && p.hasPermission("andasia.ore.generate")) {
            final String[] messagesx = { ToolBox.getBeautyStart() + "§e§lPour faire spawn un filon, fait:", ToolBox.getBeautyStart() + "      §c/§2generation spawn", ToolBox.getBeautyStart() + "      §c/§2generation spawn <montant> (§cATTENTION: §eEviter de faire + de 500 a la fois.)" };
            ToolBox.sendCustomComposedMessage(p, messagesx);
            if (args[2] == null && args[1] != null && args[1].equalsIgnoreCase("spawn") && p.hasPermission("andasia.ore.generate")) {
                e.setCancelled(true);
                GenerationCommand.filons = (List<String>) Main.getInstance().getConfig("filons").getConfig().getList("filons");
                final Location location = new SafeTeleportLocation().getLocation();
                if (GenerationCommand.filons != null && FactionAPI.isUnclaimedLocation(location) && p.isOp()) {
                    final String[] messages = { ToolBox.getBeautyStart() + "§e§lUn filon de minerais a trouv\u00e9:", ToolBox.getBeautyStart() + "    §6X: §e" + location.getX(), ToolBox.getBeautyStart() + "    §6Y: §e" + location.getY(), ToolBox.getBeautyStart() + "    §6Z: §e" + location.getZ() };
                    ToolBox.broadcast(messages);
                    SchematicUtils.pasteSchematicFAWE(location.getBlockX(), location.getBlockY(), location.getBlockZ(), getRandomFilon(true));
                }
            }
            if (args[1] != null && args[1].equalsIgnoreCase("spawn") && args[2] != null && p.hasPermission("andasia.ore.generate")) {
                e.setCancelled(true);
                if (isNumeric(args[2])) {
                    final double d = Double.parseDouble(args[2]);
                    final int amount = (int)d;
                    GenerationCommand.filons = (List<String>)Main.getInstance().getConfig("filons").getConfig().getList("filons");
                    for (int x = 0; x < amount; ++x) {
                        final Location location2 = new SafeTeleportLocation().getLocation();
                        if (GenerationCommand.filons != null && FactionAPI.isUnclaimedLocation(location2) && p.isOp()) {
                            SchematicUtils.pasteSchematicFAWE(location2.getBlockX(), location2.getBlockY(), location2.getBlockZ(), getRandomFilon(true));
                        }
                    }
                    ToolBox.sendMessage(e.getPlayer(), "G\u00e9n\u00e9ration des minerais termin\u00e9es!");
                }
            }
        }
    }
    
    public static String getRandomFilon(final boolean isUsingAPI) {
        final Random random = new Random();
        final int choice = random.nextInt(GenerationCommand.filons.size());
        final String chosen = GenerationCommand.filons.get(choice);
        if (isUsingAPI) {
            return chosen;
        }
        return chosen + ".schematic";
    }
    
    public static void pasteSchematic(final Player player, final Chunk chunk, final World world) {
        try {
            final InputStream is = Main.getInstance().getClass().getClassLoader().getResourceAsStream(getRandomFilon(false));
            final SchematicManager man = new SchematicManager();
            man.loadGzipedSchematic(is);
            final int width = man.getWidth();
            final int height = man.getHeight();
            final int length = man.getLength();
            final int start = 10;
            final int end = start + height;
            for (int x = 0; x < width; ++x) {
                for (int z = 0; z < length; ++z) {
                    final int realX = x + chunk.getX() * 16;
                    final int realZ = z + chunk.getZ() * 16;
                    for (int y = start; y <= end && y < 255; ++y) {
                        final int realY = y - start;
                        final int id = man.getBlockIdAt(realX, y, realZ);
                        final byte data = man.getMetadataAt(x, realY, y);
                        if (id > -1 && world.getBlockAt(realX, y, realZ) != null) {
                            GenerationCommand.blockToReplace.put(new Location(world, (double)realX, (double)y, (double)realZ), world.getBlockAt(realX, y, realZ).getType());
                            world.getBlockAt(realX, y, realZ).setTypeIdAndData(id, data, true);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            final String[] list = { "§cImpossible de charger la Sch\u00e9matique.." };
            ToolBox.sendCustomComposedMessage(player, list);
            e.printStackTrace();
        }
    }
    
    private void deleteSchematic() {
        final World w = Bukkit.getWorld("world");
        for (final Map.Entry<Location, Material> map : GenerationCommand.blockToReplace.entrySet()) {
            w.getBlockAt((Location)map.getKey()).setType((Material)map.getValue());
        }
    }
    
    public static boolean isNumeric(final String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    static {
        GenerationCommand.filons = new ArrayList<String>();
        GenerationCommand.blockToReplace = new HashMap<Location, Material>();
    }
}
