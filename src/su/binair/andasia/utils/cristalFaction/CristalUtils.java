package su.binair.andasia.utils.cristalFaction;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_7_R4.*;
import org.bukkit.inventory.ItemStack;
import su.binair.blocks.factionCristal.*;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import su.binair.utils.*;
import java.util.*;

public class CristalUtils
{
    public static List<ItemStack> crystalRecompense;
    public static List<Player> isCreating;
    
    public static void fill(final World world, final int x, final int y, final int z, final net.minecraft.server.v1_7_R4.ItemStack itemStack) {
        if (!world.getChunkAt(new Location(world, (double)x, (double)y, (double)z)).isLoaded()) {
            world.getChunkAt(new Location(world, (double)x, (double)y, (double)z)).load();
        }
        final TileEntity tent = ((CraftWorld)world).getHandle().getTileEntity(x, y, z);
        if (tent instanceof TileEntityFactionCristal) {
            final TileEntityFactionCristal crystal = (TileEntityFactionCristal)tent;
            crystal.addItemStackToInventory(itemStack);
        }
    }
    
    public static Inventory openChoiceInventory(final ItemStack item) {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 27, ToolBox.format("§eCristal Choix"));
        final String[] x = new String[0];
        final ItemStack delete = InventoryUtils.getCustomItemStack(Material.WOOL, 5, ToolBox.format("§aRetour"), x);
        final ItemStack annuler = InventoryUtils.getCustomItemStack(Material.WOOL, 14, ToolBox.format("§cSupprimer"), x);
        inventory.setItem(11, delete);
        inventory.setItem(13, item);
        inventory.setItem(15, annuler);
        return inventory;
    }
    
    static {
        CristalUtils.crystalRecompense = new ArrayList<ItemStack>();
        CristalUtils.isCreating = new ArrayList<Player>();
    }
}
