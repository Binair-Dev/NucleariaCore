package su.binair.andasia.listener.totem;

import org.bukkit.event.block.*;
import com.massivecraft.factions.*;
import su.binair.utils.*;
import su.binair.andasia.*;
import org.bukkit.command.*;
import org.bukkit.*;
import su.binair.andasia.utils.totem.*;
import java.io.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.configuration.file.*;
import org.bukkit.event.*;

public class TotemListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTotemBreak(final BlockBreakEvent event) {
        final Player p = event.getPlayer();
        final Block breakedb = event.getBlock();
        final FPlayer toi = (FPlayer)FPlayers.i.get(p);
        final String fname = toi.getFaction().getTag();
        if (TotemUtils.totemblocks.contains(breakedb.getLocation())) {
            if (p.getItemInHand().getType().equals((Object)Material.DIAMOND_SWORD) || p.getItemInHand().getType().equals((Object)Material.COBALT_SWORD) || p.getItemInHand().getType().equals((Object)Material.SCANDIUM_SWORD) || p.getItemInHand().getType().equals((Object)Material.URANIUM_SWORD) || p.getItemInHand().getType().equals((Object)Material.TRIPLEALIAGE_SWORD) || p.getItemInHand().getType().equals((Object)Material.DUPLICABLE_SWORD)) {
                TotemUtils.totemblocks.remove(breakedb.getLocation());
                if (TotemUtils.totemblocks.size() == 4) {
                    final String[] list = { "§eLe joueur §6" + p.getName() + "§e de la faction §6" + fname + " §ed\u00e9bute la destruction du totem §8(§6plus que §c" + TotemUtils.totemblocks.size() + "§6 blocs§8)§e." };
                    ToolBox.broadcast(list);
                    TotemUtils.currentfac = fname;
                    event.setCancelled(false);
                    breakedb.setType(Material.AIR);
                }
                else if (TotemUtils.currentfac == fname) {
                    final String[] list = { "§eLe joueur §6" + p.getName() + "§e de la faction §6" + fname + " §ecasse un block du totem §8(§6plus que §c" + TotemUtils.totemblocks.size() + "§6 blocs§8)§e." };
                    ToolBox.broadcast(list);
                    event.setCancelled(false);
                    breakedb.setType(Material.AIR);
                    if (TotemUtils.totemblocks.size() == 0) {
                        final String commande = Main.getInstance().getConfig("totem").getConfig().getString("recompense.commande").replace("@player", p.getName());
                        final String recompense = Main.getInstance().getConfig("totem").getConfig().getString("recompense.name");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), commande);
                        final String[] listx = { "§eLe joueur §6" + p.getName() + "§e de la faction §6" + fname + " §eremporte: §a" + recompense + "§e." };
                        ToolBox.broadcast(listx);
                    }
                }
                else {
                    final String[] list = { "§eLe joueur §6" + p.getName() + "§e de la faction §6" + fname + " §ebloque la §c" + TotemUtils.currentfac + "§e." };
                    ToolBox.broadcast(list);
                    TotemUtils.destroy();
                    TotemUtils.create();
                    event.setCancelled(true);
                    final Firework f = (Firework)event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), (Class)Firework.class);
                    final FireworkMeta fm = f.getFireworkMeta();
                    fm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(FireworkEffect.Type.STAR).withColor(Color.RED).withFade(Color.BLUE).build());
                    fm.setPower(0);
                    f.setFireworkMeta(fm);
                }
                if (TotemUtils.totemblocks.isEmpty()) {
                    final Firework f2 = (Firework)event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), (Class)Firework.class);
                    final FireworkMeta fm2 = f2.getFireworkMeta();
                    fm2.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.GREEN).withFade(Color.ORANGE).build());
                    fm2.setPower(0);
                    f2.setFireworkMeta(fm2);
                    int current = 0;
                    Main.getInstance().getTotemManager();
                    if (TotemManager.getStats().contains(fname)) {
                        Main.getInstance().getTotemManager();
                        current = TotemManager.getStats().getInt(fname);
                    }
                    ++current;
                    final String[] list2 = { "§eLa faction §a" + fname + " §eremporte le §aTotem§e.", "§eLa faction §a" + fname + " §eont déjà remporté §a" + current + " §etotem(s)§e." };
                    ToolBox.broadcast(list2);
                    TotemUtils.destroy();
                    Main.getInstance().getTotemManager();
                    TotemManager.getStats().set(fname, (Object)current);
                    try {
                        Main.getInstance().getTotemManager();
                        final FileConfiguration stats = TotemManager.getStats();
                        Main.getInstance().getTotemManager();
                        stats.save(TotemManager.filestats);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                event.setCancelled(true);
                ToolBox.sendMessage(p, "§cVous devez casser le totem avec une §aépée§c!");
            }
        }
    }
}
