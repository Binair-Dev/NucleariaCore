package su.binair.andasia.utils.obsidian;

class BlockStatus
{
    private float damage;
    private boolean modified;
    
    BlockStatus(final float damage) {
        this.modified = true;
        this.damage = damage;
    }
    
    float getDamage() {
        return this.damage;
    }
    
    void setDamage(final float damage) {
        this.damage = damage;
    }
    
    boolean isModified() {
        return this.modified;
    }
    
    void setModified(final boolean modified) {
        this.modified = modified;
    }
}
