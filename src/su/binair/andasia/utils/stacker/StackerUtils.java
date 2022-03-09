package su.binair.andasia.utils.stacker;

import org.apache.commons.lang.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import su.binair.andasia.*;

public enum StackerUtils
{;
    public static int INVALID_STACK;
    public static ChatColor stackNumberColor;

    public static boolean attemptUnstackOne(final LivingEntity livingEntity) {
        final ChatColor stackNumberColor = StackerUtils.stackNumberColor;
        final String displayName = livingEntity.getCustomName();
        int stackAmt = parseAmount(displayName);
        livingEntity.setHealth(0.0);
        if (stackAmt <= 1) {
            return false;
        }
        --stackAmt;
        final String newDisplayName = String.format(stackNumberColor + "§a\u03a7%1$d", stackAmt);
        final LivingEntity dupEntity = (LivingEntity)livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), livingEntity.getType());
        dupEntity.setCustomName(newDisplayName);
        dupEntity.setCustomNameVisible(true);
        return true;
    }

    public static boolean stack(final LivingEntity target, final LivingEntity stackee) {
        if (target.getType() != stackee.getType()) {
            return false;
        }
        final ChatColor stackNumberColor = StackerUtils.stackNumberColor;
        final String displayName = target.getCustomName();
        final int alreadyStacked = parseAmount(displayName);
        int stackDelta = 1;
        if (isStacked(stackee)) {
            stackDelta = parseAmount(stackee.getCustomName());
        }
        if (alreadyStacked > -2 && stackDelta > -2) {
            if (alreadyStacked == StackerUtils.INVALID_STACK) {
                stackee.remove();
                final String newDisplayName = String.format(stackNumberColor + "§a\u03a7%1$d", 1 + stackDelta);
                target.setCustomName(newDisplayName);
                target.setCustomNameVisible(true);
            }
            else {
                stackee.remove();
                final String newDisplayName = String.format(stackNumberColor + "§a\u03a7%1$d", alreadyStacked + stackDelta);
                target.setCustomName(newDisplayName);
            }
        }
        return true;
    }

    public static int parseAmount(final String displayName) {
        if (displayName == null) {
            return StackerUtils.INVALID_STACK;
        }
        if (StringUtils.equalsIgnoreCase(displayName, ChatColor.stripColor(displayName))) {
            return -2;
        }
        final ChatColor stackNumberColor = StackerUtils.stackNumberColor;
        final String nameColor = ChatColor.getLastColors(displayName);
        if (nameColor.equals('§' + stackNumberColor.getChar())) {
            return StackerUtils.INVALID_STACK;
        }
        final String cleanDisplayName = ChatColor.stripColor(displayName);
        final String cleanDisplayName2 = cleanDisplayName.replace("\u03a7", "");
        if (!cleanDisplayName2.matches("[0-9]+")) {
            return 0;
        }
        if (cleanDisplayName2.length() > 4) {
            return 0;
        }
        return Integer.parseInt(cleanDisplayName2);
    }

    public static boolean isStacked(final LivingEntity entity) {
        return parseAmount(entity.getCustomName()) != StackerUtils.INVALID_STACK;
    }

    private static ChatColor getChatColor(final String code, final ChatColor defaultValue) {
        final ChatColor color = ChatColor.getByChar(code);
        if (color == null) {
            return defaultValue;
        }
        return color;
    }

    public static void compileEntityTypesList(final List<String> list) {
        if (list == null) {
            return;
        }
        for (final String entityName : list) {
            try {
                final EntityType entityType = EntityType.valueOf(entityName.toUpperCase());
                StackerTask.mobTypes.add(entityType);
            }
            catch (IllegalArgumentException ex) {}
        }
    }

    public static int killStackedMobs() {
        int amount = 0;
        for (final World w : Bukkit.getWorlds()) {
            for (final Entity entities : w.getEntities()) {
                if (entities instanceof LivingEntity) {
                    final LivingEntity ent = (LivingEntity)entities;
                    if (ent.getCustomName() == null || !ent.getCustomName().contains("\u03a7")) {
                        continue;
                    }
                    ++amount;
                    ent.remove();
                }
            }
        }
        return amount;
    }

    static {
        StackerUtils.stackNumberColor = getChatColor(Main.getInstance().getConfig("stacker").getConfig().getString("StackNumberColor"), ChatColor.GOLD);
        StackerUtils.INVALID_STACK = -1;
    }
}
