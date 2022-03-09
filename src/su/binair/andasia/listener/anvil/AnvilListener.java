package su.binair.andasia.listener.anvil;

import org.bukkit.entity.*;
import su.binair.andasia.*;
import su.binair.andasia.utils.anvil.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.event.inventory.*;
import java.util.*;
import org.bukkit.event.*;

public class AnvilListener implements Listener
{
    @EventHandler
    public void onAnvilItemPlace(final InventoryClickEvent event) {
        final Player player = (Player)event.getWhoClicked();
        final Inventory inv = event.getInventory();
        final InventoryType invType = inv.getType();
        final ClickType click = event.getClick();
        final ItemStack clicked = event.getCurrentItem();
        if (player == null) {
            return;
        }
        if (!invType.equals((Object)InventoryType.ANVIL)) {
            return;
        }
        if (inv.getItem(0) == null) {
            return;
        }
        if (inv.getItem(1) == null) {
            return;
        }
        final ItemStack item1 = inv.getItem(0);
        final ItemStack item2 = inv.getItem(1);
        ARecipe anvilRecipes = null;
        for (final ARecipe anvilRecipe : Main.getInstance().getAnvilUtils().getAnvilrecipes()) {
            if (item1.getType() == anvilRecipe.getItem1().getType() && item2.getType() == anvilRecipe.getItem2().getType()) {
                inv.setItem(2, anvilRecipe.getResult());
                anvilRecipes = anvilRecipe;
                player.updateInventory();
            }
        }
        if (clicked != null && inv.getItem(2) != null && inv.getItem(2).getType() == clicked.getType() && anvilRecipes != null) {
            if (event.getSlot() == 2) {
                inv.setItem(0, new ItemStack(Material.AIR));
                inv.setItem(1, new ItemStack(Material.AIR));
                player.getInventory().addItem(new ItemStack[] { anvilRecipes.getResult() });
                player.closeInventory();
                player.updateInventory();
            }
        }
    }
}
