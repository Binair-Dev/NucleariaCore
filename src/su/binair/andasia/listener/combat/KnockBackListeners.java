package su.binair.andasia.listener.combat;

import org.bukkit.enchantments.Enchantment;
import su.binair.objects.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.enchantments.*;
import org.bukkit.util.*;
import org.bukkit.craftbukkit.v1_7_R4.entity.*;
import net.minecraft.server.v1_7_R4.*;
import java.lang.reflect.*;

public class KnockBackListeners implements Listener
{
    private double horizontalModifier;
    private double verticalModifier;
    
    public KnockBackListeners(final YmlConfiguration m) {
        this.horizontalModifier = 0.0;
        this.verticalModifier = 0.0;
        this.horizontalModifier = m.getConfig().getDouble("horizontal");
        this.verticalModifier = m.getConfig().getDouble("vertical");
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onVelocity(final PlayerVelocityEvent event) {
        final Player player = event.getPlayer();
        if (player.getLastDamageCause() == null) {
            return;
        }
        if (!(player.getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
            return;
        }
        if (((EntityDamageByEntityEvent)player.getLastDamageCause()).getDamager() instanceof Player) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(final EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        final Player damaged = (Player)event.getEntity();
        final Player damager = (Player)event.getDamager();
        if (damaged.getNoDamageTicks() > damaged.getMaximumNoDamageTicks() / 2.0) {
            return;
        }
        final double mSprint = damaged.isSprinting() ? 0.8 : 0.5;
        final double mAirtime = damaged.isOnGround() ? 1.0 : 0.5;
        final double mKnockbackEnchant = (damager.getItemInHand() == null) ? 0.0 : (damager.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK) * 0.2);
        final Vector knockback = damaged.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();
        knockback.setX((knockback.getX() * mSprint + mKnockbackEnchant) * this.horizontalModifier);
        knockback.setY(0.35 * mAirtime * this.verticalModifier);
        knockback.setZ((knockback.getZ() * mSprint + mKnockbackEnchant) * this.horizontalModifier);
        this.sendVelocityPacket(damaged, knockback);
    }
    
    public void sendVelocityPacket(final Player player, final Vector vector) {
        try {
            final PacketPlayOutEntityVelocity packetVelocity = PacketPlayOutEntityVelocity.class.getConstructor(Integer.TYPE, Double.TYPE, Double.TYPE, Double.TYPE).newInstance(player.getEntityId(), vector.getX(), vector.getY(), vector.getZ());
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packetVelocity);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }
}
