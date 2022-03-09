package su.binair.andasia.listener.jobs;

import su.binair.andasia.utils.jobs.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import su.binair.utils.*;
import su.binair.packets.network.*;
import org.bukkit.plugin.messaging.*;
import su.binair.packets.*;
import org.bukkit.block.*;
import su.binair.andasia.object.jobs.*;
import java.util.*;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import su.binair.andasia.event.*;
import org.bukkit.command.*;
import org.bukkit.event.player.*;
import su.binair.andasia.*;
import org.bukkit.*;
import org.bukkit.event.entity.*;

public class JobsListener implements Listener
{
    @EventHandler
    public void onJobsJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        JobsUtils.createOrLoadPlayerJobs(player);
    }
    
    @EventHandler
    public void onJobsDisconnect(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        JobsUtils.saveJobsForPlayer(player);
    }
    
    @EventHandler
    public void onJobBlockbreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();
        final Player player = event.getPlayer();
        final JobsPlayer jobsPlayer = JobsUtils.jobs.get(player.getName());
        if (player.getItemInHand() != null && JobsUtils.lockedCraft.containsKey(player.getItemInHand())) {
            final JobsLockedCraft locked = JobsUtils.lockedCraft.get(player.getItemInHand());
            final Jobs job = jobsPlayer.getJobByName(locked.getMetier());
            if (job.getMyLevel() < locked.getLevel()) {
                event.setCancelled(true);
                ToolBox.sendMessage(player, "§cTu ne peux pas utiliser cet item avant d'avoir atteind le niveau §a" + locked.getLevel() + " §cdu mété ier de §a" + locked.getMetier() + "§c.");
            }
        }
        final int id = block.getTypeId();
        for (final Map.Entry<String, JobsAction> map : JobsUtils.jobsAction.entrySet()) {
            if (map != null && map.getValue().getType().equalsIgnoreCase("BREAK")) {
                final JobsAction action = map.getValue();
                if (action.getId() != id) {
                    continue;
                }
                final double xpAmount = action.getExperienceAmount();
                final Jobs jb = jobsPlayer.getJobByName(action.getJobId());
                if (jb == null) {
                    continue;
                }
                jb.addExperience(action.getExperienceAmount(), player);
                final String jobName = action.getJobId();
                final double myActualXP = jb.getMyExperience();
                final double maxXP = jb.getExperienceAmount(jb.getMyLevel());
                final String announce = ToolBox.format("§8§l[§c" + jobName + "§8§l] §6" + myActualXP + "§8/§6" + maxXP);
                final ActionBarPacket packet = new ActionBarPacket(announce, 100, false);
                CustomPacketHandler.dispatch((CustomPacket)packet, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
            }
        }
    }
    
    @EventHandler
    public void onJobBlockPlace(final BlockPlaceEvent event) {
        final Block block = event.getBlock();
        final Player player = event.getPlayer();
        final JobsPlayer jobsPlayer = JobsUtils.jobs.get(player.getName());
        if (player.getItemInHand() != null && JobsUtils.lockedCraft.containsKey(player.getItemInHand())) {
            final JobsLockedCraft locked = JobsUtils.lockedCraft.get(player.getItemInHand());
            final Jobs job = jobsPlayer.getJobByName(locked.getMetier());
            if (job.getMyLevel() < locked.getLevel()) {
                event.setCancelled(true);
                ToolBox.sendMessage(player, "§cTu ne peux pas utiliser cet item avant d'avoir atteind le niveau §a" + locked.getLevel() + " §cdu mété ier de §a" + locked.getMetier() + "§c.");
            }
        }
        final int id = block.getTypeId();
        for (final Map.Entry<String, JobsAction> map : JobsUtils.jobsAction.entrySet()) {
            if (map != null && map.getValue().getType().equalsIgnoreCase("PLACE")) {
                final JobsAction action = map.getValue();
                if (action.getId() != id) {
                    continue;
                }
                final double xpAmount = action.getExperienceAmount();
                final Jobs jb = jobsPlayer.getJobByName(action.getJobId());
                if (jb == null) {
                    continue;
                }
                jb.addExperience(action.getExperienceAmount(), player);
                final String jobName = action.getJobId();
                final double myActualXP = jb.getMyExperience();
                final double maxXP = jb.getExperienceAmount(jb.getMyLevel());
                final String announce = ToolBox.format("§8§l[§c" + jobName + "§8§l] §6" + myActualXP + "§8/§6" + maxXP);
                final ActionBarPacket packet = new ActionBarPacket(announce, 100, false);
                CustomPacketHandler.dispatch((CustomPacket)packet, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
            }
        }
    }
    
    @EventHandler
    public void onJobKillEvent(final EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            final Player player = event.getEntity().getKiller();
            final JobsPlayer jobsPlayer = JobsUtils.jobs.get(player.getName());
            final int id = event.getEntityType().getTypeId();
            for (final Map.Entry<String, JobsAction> map : JobsUtils.jobsAction.entrySet()) {
                if (map != null && map.getValue().getType().equalsIgnoreCase("KILL")) {
                    final JobsAction action = map.getValue();
                    if (action.getId() != id) {
                        continue;
                    }
                    final double xpAmount = action.getExperienceAmount();
                    final Jobs jb = jobsPlayer.getJobByName(action.getJobId());
                    if (jb == null) {
                        continue;
                    }
                    jb.addExperience(action.getExperienceAmount(), player);
                    final String jobName = action.getJobId();
                    final double myActualXP = jb.getMyExperience();
                    final double maxXP = jb.getExperienceAmount(jb.getMyLevel());
                    final String announce = ToolBox.format("§8§l[§c" + jobName + "§8§l] §6" + myActualXP + "§8/§6" + maxXP);
                    final ActionBarPacket packet = new ActionBarPacket(announce, 100, false);
                    CustomPacketHandler.dispatch((CustomPacket)packet, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                }
            }
        }
    }
    
    @EventHandler
    public void onJobCraft(final CraftItemEvent event) {
        final Player player = (Player)event.getWhoClicked();
        final JobsPlayer jobsPlayer = JobsUtils.jobs.get(player.getName());
        final ItemStack item = event.getRecipe().getResult().clone();
        final int id = item.getTypeId();
        final int amount = item.getAmount();
        for (final Map.Entry<String, JobsAction> map : JobsUtils.jobsAction.entrySet()) {
            if (map != null && map.getValue().getType().equalsIgnoreCase("CRAFT")) {
                final JobsAction action = map.getValue();
                if (action.getId() != id) {
                    continue;
                }
                final double xpAmount = action.getExperienceAmount();
                final Jobs jb = jobsPlayer.getJobByName(action.getJobId());
                if (jb == null) {
                    continue;
                }
                final double total = new Double(amount) * action.getExperienceAmount();
                jb.addExperience(total, player);
                final String jobName = action.getJobId();
                final double myActualXP = jb.getMyExperience();
                final double maxXP = jb.getExperienceAmount(jb.getMyLevel());
                final String announce = ToolBox.format("§8§l[§c" + jobName + "§8§l] §6" + myActualXP + "§8/§6" + maxXP);
                final ActionBarPacket packet = new ActionBarPacket(announce, 100, false);
                CustomPacketHandler.dispatch((CustomPacket)packet, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
            }
        }
    }
    
    @EventHandler
    public void onJobsLevelUpEvent(final JobsLevelupEvent event) {
        final int level = event.getLevel();
        if (level == 0) {
            return;
        }
        final Player player = event.getPlayer();
        String reward = JobsUtils.getRewardFor(event.getJobsName(), event.getLevel());
        ToolBox.sendMessage(player, "§e§lF\u00e9licitation ! §eTu viens de monter de niveau pour le mété ier de: §a§l" + event.getJobsName() + "§e.");
        if (reward != null && reward.length() > 0) {
            reward = reward.replace("@player", player.getName());
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), reward);
        }
        else {
            ToolBox.sendMessage(player, "§cD\u00e9sol\u00e9, aucune r\u00e9compense disponible pour ce mété ier!");
        }
    }
    
    @EventHandler
    public void onJobsRewardAwaitin(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (JobsUtils.inRewardAwaiting.get(player) != null) {
            final String informations = JobsUtils.inRewardAwaiting.get(player);
            final String[] list = informations.split("-");
            final String metier = list[0];
            final int level = Integer.valueOf(list[1]);
            if (metier != null && level != 0) {
                Main.getInstance().getConfig("jobs").getConfig().set("Rewards." + metier + "." + level, (Object)event.getMessage());
                Main.getInstance().getConfig("jobs").save();
                ToolBox.sendMessage(player, "§e§lF\u00e9licitation ! §eTu viens de cr\u00e9er la r\u00e9compense du niveau §c" + level + " §edu mété ier de: §c" + metier + "§e.");
                JobsUtils.inRewardAwaiting.remove(player);
                event.setCancelled(true);
            }
            else {
                JobsUtils.inRewardAwaiting.remove(player);
                ToolBox.sendMessage(player, "§cMauvais format, Veuillez r\u00e9essayer !");
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onJobCraftLocked(final CraftItemEvent event) {
        final Player player = (Player)event.getWhoClicked();
        final Material item = event.getRecipe().getResult().getType();
        final JobsPlayer jobPlayer = JobsUtils.jobs.get(player.getName());
        if (JobsUtils.lockedCraft.containsKey(item)) {
            final JobsLockedCraft locked = JobsUtils.lockedCraft.get(item);
            final Jobs job = jobPlayer.getJobByName(locked.getMetier());
            if (job.getMyLevel() < locked.getLevel()) {
                event.setCancelled(true);
                ToolBox.sendMessage(player, "§cTu ne peux pas fabriquer cet item avant d'avoir atteind le niveau §a" + locked.getLevel() + " §cdu mété ier de §a" + locked.getMetier() + "§c.");
            }
        }
    }
    
    @EventHandler
    public void onEntityHit(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            final Player player = (Player)event.getDamager();
            final JobsPlayer jobPlayer = JobsUtils.jobs.get(player.getName());
            if (player.getItemInHand() != null && JobsUtils.lockedCraft.containsKey(player.getItemInHand())) {
                final JobsLockedCraft locked = JobsUtils.lockedCraft.get(player.getItemInHand());
                final Jobs job = jobPlayer.getJobByName(locked.getMetier());
                if (job.getMyLevel() < locked.getLevel()) {
                    event.setCancelled(true);
                    ToolBox.sendMessage(player, "§cTu ne peux pas utiliser cet item avant d'avoir atteind le niveau §a" + locked.getLevel() + " §cdu mété ier de §a" + locked.getMetier() + "§c.");
                }
            }
        }
    }
}
