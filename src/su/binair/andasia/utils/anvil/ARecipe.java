package su.binair.andasia.utils.anvil;

import org.bukkit.inventory.*;

public class ARecipe
{
    private ItemStack item1;
    private ItemStack item2;
    private ItemStack result;
    
    public ARecipe(final ItemStack item1, final ItemStack item2, final ItemStack result) {
        this.item1 = item1;
        this.item2 = item2;
        this.result = result;
    }
    
    public ItemStack getItem1() {
        return this.item1;
    }
    
    public ItemStack getItem2() {
        return this.item2;
    }
    
    public ItemStack getResult() {
        return this.result;
    }
}
