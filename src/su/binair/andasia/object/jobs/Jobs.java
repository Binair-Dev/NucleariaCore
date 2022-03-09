package su.binair.andasia.object.jobs;

import org.bukkit.entity.*;
import su.binair.andasia.*;
import su.binair.andasia.event.*;
import org.bukkit.event.*;

public class Jobs
{
    private int maxLevel;
    private int myLevel;
    private double myExperience;
    private String name;
    private int logoId;
    
    public Jobs(final String name, final int logoId) {
        this.maxLevel = 20;
        this.myLevel = 0;
        this.myExperience = 0.0;
        this.name = name;
        this.logoId = logoId;
    }
    
    public int getLogoId() {
        return this.logoId;
    }
    
    public double getMyExperience() {
        return this.myExperience;
    }
    
    public int getMyLevel() {
        return this.myLevel;
    }
    
    public int getMaxLevel() {
        return this.maxLevel;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getExperienceAmount(final int level) {
        final int multiplier = 3568;
        return level * multiplier;
    }
    
    public void addExperience(final double amount, final Player player) {
        if (this.myLevel < this.maxLevel) {
            final double max = this.getExperienceAmount(this.myLevel);
            this.myExperience += amount;
            if (this.myExperience >= max) {
                Main.getInstance().getServer().getPluginManager().callEvent((Event)new JobsLevelupEvent(this.myLevel, player, this.getName()));
                ++this.myLevel;
                this.myExperience = 0.0;
            }
        }
    }
    
    public void setLogoId(final int logoId) {
        this.logoId = logoId;
    }
}
