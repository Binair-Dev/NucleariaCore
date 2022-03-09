package su.binair.andasia.utils.grappling;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.*;
import org.bukkit.entity.Entity;
import su.binair.utils.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_7_R4.entity.*;
import net.minecraft.server.v1_7_R4.*;

public class GrapplingEntity extends EntityFishingHook
{
    private Snowball sb;
    private EntitySnowball controller;
    public int a;
    public EntityHuman owner;
    public Entity hooked;
    public boolean lastControllerDead;
    public boolean isHooked;
    
    public GrapplingEntity(final World world, final EntityHuman entityhuman) {
        super((net.minecraft.server.v1_7_R4.World)((CraftWorld)world).getHandle(), entityhuman);
        this.owner = entityhuman;
    }
    
    protected void c() {
    }
    
    public void h() {
        final Player player = (Player)this.owner.getBukkitEntity();
        if (!this.lastControllerDead && this.controller.dead) {
            ToolBox.sendMessage(player, "§aTon grappin a trouv\u00e9 un point d'impact!");
        }
        this.lastControllerDead = this.controller.dead;
        for (final Entity entity : this.controller.world.getWorld().getEntities()) {
            if (!(entity instanceof Firework) && entity.getEntityId() != this.getBukkitEntity().getEntityId() && entity.getEntityId() != this.owner.getBukkitEntity().getEntityId() && entity.getEntityId() != this.controller.getBukkitEntity().getEntityId()) {
                if (entity.getLocation().distance(this.controller.getBukkitEntity().getLocation()) >= 2.0) {
                    if (!(entity instanceof Player)) {
                        continue;
                    }
                    if (((Player)entity).getEyeLocation().distance(this.controller.getBukkitEntity().getLocation()) >= 2.0) {
                        continue;
                    }
                }
                this.controller.die();
                this.hooked = entity;
                this.isHooked = true;
                this.locX = entity.getLocation().getX();
                this.locY = entity.getLocation().getY();
                this.locZ = entity.getLocation().getZ();
                this.motX = 0.0;
                this.motY = 0.04;
                this.motZ = 0.0;
            }
        }
        try {
            this.locX = this.hooked.getLocation().getX();
            this.locY = this.hooked.getLocation().getY();
            this.locZ = this.hooked.getLocation().getZ();
            this.motX = 0.0;
            this.motY = 0.04;
            this.motZ = 0.0;
            this.isHooked = true;
        }
        catch (Exception e) {
            if (this.controller.dead) {
                this.isHooked = true;
            }
            this.locX = this.controller.locX;
            this.locY = this.controller.locY;
            this.locZ = this.controller.locZ;
        }
    }
    
    public void die() {
    }
    
    public void remove() {
        super.die();
    }
    
    public void spawn(final Location location) {
        this.sb = (Snowball)this.owner.getBukkitEntity().launchProjectile((Class)Snowball.class);
        this.controller = ((CraftSnowball)this.sb).getHandle();
        final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { this.controller.getId() });
        Player[] onlinePlayers;
        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
        }
        ((CraftWorld)location.getWorld()).getHandle().addEntity((net.minecraft.server.v1_7_R4.Entity)this);
    }
    
    public boolean isHooked() {
        return this.isHooked;
    }
    
    public void setHookedEntity(final Entity damaged) {
        this.hooked = damaged;
    }
}
