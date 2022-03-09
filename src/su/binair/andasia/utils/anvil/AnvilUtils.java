package su.binair.andasia.utils.anvil;

import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class AnvilUtils
{
    List<ARecipe> anvilrecipes;
    private AnvilUtils instance;
    
    public AnvilUtils() {
        this.anvilrecipes = new ArrayList<ARecipe>();
        this.instance = this;
        this.anvilrecipes.add(new ARecipe(new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.DIAMOND)));
    }
    
    public List<ARecipe> getAnvilrecipes() {
        return this.anvilrecipes;
    }
}
