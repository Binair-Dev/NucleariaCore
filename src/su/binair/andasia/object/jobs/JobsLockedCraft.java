package su.binair.andasia.object.jobs;

import org.bukkit.*;

public class JobsLockedCraft
{
    private Material item;
    private String metier;
    private int level;
    
    public JobsLockedCraft(final Material item, final String metier, final int level) {
        this.item = item;
        this.metier = metier;
        this.level = level;
    }
    
    public Material getItem() {
        return this.item;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public String getMetier() {
        return this.metier;
    }
}
