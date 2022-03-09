package su.binair.andasia.utils.obsidian;

import java.util.concurrent.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import su.binair.andasia.*;

public class StorageHandler
{
    public ConcurrentHashMap<String, BlockStatus> damage;
    
    public StorageHandler() {
        this.damage = new ConcurrentHashMap<String, BlockStatus>();
    }
    
    public String generateHash(final Location loc) {
        return String.valueOf(loc.getWorld().getUID().toString()) + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
    }
    
    public Location generateLocation(final String string) {
        try {
            final String[] s = string.split(":");
            return new Location(Bukkit.getWorld(UUID.fromString(s[0])), (double)Integer.parseInt(s[1]), (double)Integer.parseInt(s[2]), (double)Integer.parseInt(s[3]));
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public boolean isValidBlock(final Block block) {
        return Main.getInstance().getConfig("obsidian").getConfig().getConfigurationSection("blocks").getKeys(false).contains(Integer.toString(block.getTypeId()));
    }
    
    public float getTotalDurability(final Block block) throws UnknownBlockTypeException {
        if (this.isValidBlock(block)) {
            return (float)Main.getInstance().getConfig("obsidian").getConfig().getDouble("blocks." + block.getTypeId());
        }
        throw new UnknownBlockTypeException();
    }
    
    public float getRemainingDurability(final Block block) throws UnknownBlockTypeException {
        final String hash = this.generateHash(block.getLocation());
        return this.getTotalDurability(block) - (this.damage.containsKey(hash) ? this.damage.get(hash).getDamage() : 0.0f);
    }
    
    public boolean addDamage(final Block block, final float addDamage) throws UnknownBlockTypeException {
        if (addDamage <= 0.0f || this.getTotalDurability(block) < 0.0f) {
            return false;
        }
        final String hash = this.generateHash(block.getLocation());
        final float totalDamage = this.damage.containsKey(hash) ? (addDamage + this.damage.get(hash).getDamage()) : addDamage;
        if (totalDamage >= this.getTotalDurability(block) - 0.001f) {
            this.damage.remove(hash);
            return true;
        }
        this.damage.put(hash, new BlockStatus(totalDamage));
        return false;
    }
    
    public void renderCracks(final Block block) {
        if (Main.getInstance().getConfig("obsidian").getConfig().getBoolean("block.cracks")) {
            try {
                final float totalDurability = this.getTotalDurability(block);
                if (totalDurability <= 0.0f) {
                    return;
                }
                final int n = 10 - (int)Math.ceil(this.getRemainingDurability(block) / this.getTotalDurability(block) * 10.0f);
            }
            catch (UnknownBlockTypeException ex) {}
        }
    }
}
