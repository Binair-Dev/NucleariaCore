package su.binair.andasia.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import su.binair.andasia.*;
import su.binair.andasia.Main;
import su.binair.utils.*;
import java.util.*;
import su.binair.api.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.*;

public class BottleCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (label.equalsIgnoreCase("bottlexp")) {
            if (this.hasAvaliableSlot(p)) {
                if (args.length == 0) {
                    final int count = p.getLevel();
                    if (count != 0 && count > 10) {
                        p.setExp(0.0f);
                        p.setLevel(0);
                        final ItemStack stack = new ItemStack(Material.EXP_BOTTLE);
                        final ItemMeta meta = stack.getItemMeta();
                        meta.setDisplayName(String.valueOf(String.valueOf(String.valueOf(ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottlename")))) + count);
                        meta.setLore((List)Arrays.asList(ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottlelore")));
                        stack.setItemMeta(meta);
                        p.getInventory().addItem(new ItemStack[] { stack });
                    }
                    else {
                        p.sendMessage(PrefixAPI.getPrefix() + ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottle_not_enought"));
                    }
                }
                else if (args.length == 1) {
                    final int number = Integer.parseInt(args[0]);
                    if (number != 0 && number >= 10) {
                        if (p.getLevel() < number) {
                            p.sendMessage(PrefixAPI.getPrefix() + ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottle_not_enought"));
                        }
                        else {
                            p.setLevel(p.getLevel() - number);
                            final ItemStack stack = new ItemStack(Material.EXP_BOTTLE);
                            final ItemMeta meta = stack.getItemMeta();
                            meta.setDisplayName(String.valueOf(String.valueOf(String.valueOf(ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottlename")))) + number);
                            meta.setLore((List)Arrays.asList(ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottlelore")));
                            stack.setItemMeta(meta);
                            p.getInventory().addItem(new ItemStack[] { stack });
                        }
                    }
                    else {
                        p.sendMessage(PrefixAPI.getPrefix() + ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottlexp10"));
                    }
                }
            }
            else {
                p.sendMessage(PrefixAPI.getPrefix() + ToolBox.getMessage(Main.getInstance().getConfig("bottlexp"), "bottlexp_full"));
            }
        }
        return false;
    }
    
    public boolean hasAvaliableSlot(final Player player) {
        final Inventory inv = (Inventory)player.getInventory();
        for (final ItemStack item : inv.getContents()) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }
}
