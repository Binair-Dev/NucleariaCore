package su.binair.andasia.utils.turrets;

import org.bukkit.entity.*;
import su.binair.main.entity.*;
import su.binair.utils.*;

public class TurretUtils
{
    public static void addWhitelist(final Player player, final LivingEntity livingEntity, final String user) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(livingEntity);
        final String whitelist = turret.getWhitelist();
        final String[] wl = whitelist.split(", ");
        if (whitelist.contains(user)) {
            final String[] list = { ToolBox.getBeautyStart() + "§cLe joueur " + user + " fait déjà partie de la whitelist de votre Tourelle§a. " + getWhitelistLimit(livingEntity) };
            ToolBox.sendCustomComposedMessage(player, list);
        }
        else {
            if (wl.length <= 15) {
                turret.addWhitelist(user);
                final String[] list = { ToolBox.getBeautyStart() + "§aLe joueur §c" + user + " §abien été ajouta la §cwhitelist §ade votre §cTourelle§a. " + getWhitelistLimit(livingEntity) };
                ToolBox.sendCustomComposedMessage(player, list);
            }
            if (wl.length >= 15) {
                final String[] list = { ToolBox.getBeautyStart() + "§aLe joueur §c" + user + " §an'a pas pu été re ajouta la §cwhitelist §ade votre §cTourelle§a. " + getWhitelistLimit(livingEntity) };
                ToolBox.sendCustomComposedMessage(player, list);
            }
        }
    }
    
    public static void remWhitelist(final Player player, final LivingEntity livingEntity, final String user) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(livingEntity);
        final String whitelist = turret.getWhitelist();
        final String[] wl = whitelist.split(", ");
        if (!whitelist.contains(user)) {
            final String[] list = { ToolBox.getBeautyStart() + "§cLe joueur " + user + " ne fais pas partie de la whitelist de votre Tourelle§a. " + getWhitelistLimit(livingEntity) };
            ToolBox.sendCustomComposedMessage(player, list);
        }
        else {
            if (wl.length <= 15) {
                turret.remWhitelist(user);
                final String[] list = { ToolBox.getBeautyStart() + "§aLe joueur §c" + user + " §abien été retira la §cwhitelist §ade votre §cTourelle§a. " + getWhitelistLimit(livingEntity) };
                ToolBox.sendCustomComposedMessage(player, list);
            }
            if (wl.length >= 15) {
                final String[] list = { ToolBox.getBeautyStart() + "§aLe joueur §c" + user + " §an'a pas pu été re retira la §cwhitelist §ade votre §cTourelle§a. " + getWhitelistLimit(livingEntity) };
                ToolBox.sendCustomComposedMessage(player, list);
            }
        }
    }
    
    public static void addDamage(final LivingEntity entity, final String user, final int amount) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(entity);
        turret.setDamage(turret.getDamage() + amount);
    }
    
    public static void addRange(final LivingEntity entity, final String user, final int amount) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(entity);
        turret.setRange(turret.getRange() + amount);
    }
    
    public static void addLife(final LivingEntity entity, final String user, final int amount) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(entity);
        turret.setLife(turret.getLife() + amount);
    }
    
    public static void addAmmo(final LivingEntity entity, final int amount) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(entity);
        turret.addAmmo(amount);
    }
    
    
    public static void setDamage(final LivingEntity entity, final String user, final int amount) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(entity);
        turret.setDamage(amount);
    }
    
    public static void setRange(final LivingEntity entity, final String user, final int amount) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(entity);
        turret.setRange(amount);
    }
    
    public static void setLife(final LivingEntity entity, final String user, final int amount) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(entity);
        turret.setLife(amount);
    }
    
    public static String getWhitelistLimit(final LivingEntity livingEntity) {
        final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(livingEntity);
        final String whitelist = turret.getWhitelist();
        final String[] wl = whitelist.split(", ");
        return "§8(§e" + wl.length + "§8/§e15§8)";
    }
    
    public static int getLevel(final LivingEntity livingEntity)
    {
    	final EntityTurret turret = (EntityTurret)EntityUtils.getNMSEntity(livingEntity);
        double damage = turret.getDamage();
        
        if(damage == 1.0)
        	return 1;
        if(damage == 4.0)
        	return 2;
        if(damage == 8.0)
        	return 3;
        return 0;
    }
}
