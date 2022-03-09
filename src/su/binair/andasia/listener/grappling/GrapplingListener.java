package su.binair.andasia.listener.grappling;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import su.binair.andasia.utils.grappling.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.event.entity.*;
import org.bukkit.util.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.craftbukkit.v1_7_R4.entity.*;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.potion.*;
import su.binair.utils.*;

public class GrapplingListener implements Listener
{
    Map<Player, GrapplingEntity> hooks;
    
    public GrapplingListener() {
        this.hooks = new HashMap<Player, GrapplingEntity>();
    }
    
    @EventHandler
    public void onSlot(final PlayerItemHeldEvent e) {
        if (this.hooks.containsKey(e.getPlayer())) {
            this.hooks.get(e.getPlayer()).remove();
            this.hooks.remove(e.getPlayer());
        }
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        if (this.hooks.containsKey(e.getPlayer()) && !e.getPlayer().getItemInHand().getType().equals(Material.GRAPPIN)) {
            this.hooks.get(e.getPlayer()).remove();
            this.hooks.remove(e.getPlayer());
        }
    }
    
    @EventHandler
    public void onLeash(final PlayerLeashEntityEvent e) {
        final Player p = e.getPlayer();
        if (e.getPlayer().getItemInHand().getType().equals(Material.GRAPPIN)) {
            e.setCancelled(true);
            e.getPlayer().updateInventory();
            e.setCancelled(true);
            if (!this.hooks.containsKey(p)) {
                return;
            }
            if (!this.hooks.get(p).isHooked()) {
                return;
            }
            final double d;
            final double t = d = this.hooks.get(p).getBukkitEntity().getLocation().distance(p.getLocation());
            final double v_x = (1.0 + 0.07 * t) * (this.hooks.get(p).getBukkitEntity().getLocation().getX() - p.getLocation().getX()) / t;
            final double v_y = (1.0 + 0.03 * t) * (this.hooks.get(p).getBukkitEntity().getLocation().getY() - p.getLocation().getY()) / t;
            final double v_z = (1.0 + 0.07 * t) * (this.hooks.get(p).getBukkitEntity().getLocation().getZ() - p.getLocation().getZ()) / t;
            final Vector v = p.getVelocity();
            v.setX(v_x);
            v.setY(v_y);
            v.setZ(v_z);
            p.setVelocity(v);
        }
    }
    
    @EventHandler
    public void onClick(final PlayerInteractEvent e)
    {
        final Player p = e.getPlayer();
        if (e.getPlayer().getItemInHand().getType().equals((Object)Material.GRAPPIN))
        {
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
            {
                if (this.hooks.containsKey(p))
                {
                    this.hooks.get(p).remove();
                }
                final GrapplingEntity nmsHook = new GrapplingEntity(p.getWorld(), (EntityHuman)((CraftPlayer)p).getHandle());
                nmsHook.spawn(p.getEyeLocation().add(p.getLocation().getDirection().getX(), p.getLocation().getDirection().getY(), p.getLocation().getDirection().getZ()));
                nmsHook.move(p.getLocation().getDirection().getX() * 5.0, p.getLocation().getDirection().getY() * 5.0, p.getLocation().getDirection().getZ() * 5.0);
                this.hooks.put(p, nmsHook);
                p.addPotionEffect(new PotionEffect(PotionEffectType.FEATHER_FALLING, 1200, 1));
            }
            else
            {
                if (!this.hooks.containsKey(p))
                {
                    return;
                }
                if (!this.hooks.get(p).isHooked())
                {
                    ToolBox.sendMessage(p, "§cTon grappin n'a pas trouv\u00e9 de point d'impact!");
                    return;
                }
                final double d;
                final double t = d = this.hooks.get(p).getBukkitEntity().getLocation().distance(p.getLocation());
                final double v_x = (1.0 + 0.07 * t) * (this.hooks.get(p).getBukkitEntity().getLocation().getX() - p.getLocation().getX()) / t;
                final double v_y = (1.0 + 0.03 * t) * (this.hooks.get(p).getBukkitEntity().getLocation().getY() - p.getLocation().getY()) / t;
                final double v_z = (1.0 + 0.07 * t) * (this.hooks.get(p).getBukkitEntity().getLocation().getZ() - p.getLocation().getZ()) / t;
                final Vector v = p.getVelocity();
                v.setX(v_x);
                v.setY(v_y);
                v.setZ(v_z);
                p.setVelocity(v);
            }
        }
    }
}
