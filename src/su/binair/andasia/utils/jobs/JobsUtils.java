package su.binair.andasia.utils.jobs;

import org.bukkit.entity.*;
import org.bukkit.*;
import su.binair.andasia.*;
import java.util.*;
import java.io.*;
import su.binair.packets.network.*;
import org.bukkit.plugin.messaging.*;
import su.binair.packets.*;
import su.binair.andasia.object.jobs.*;

public class JobsUtils
{
    public static HashMap<String, JobsPlayer> jobs;
    public static HashMap<String, JobsAction> jobsAction;
    public static List<String> jobsName;
    public static HashMap<Player, String> inRewardAwaiting;
    public static HashMap<Material, JobsLockedCraft> lockedCraft;
    
    public static void addNewJob(final String name, final int logoId) {
        for (final Map.Entry<String, JobsPlayer> map : JobsUtils.jobs.entrySet()) {
            if (map.getValue().getJobByName(name) == null) {
                map.getValue().addNewJob(name, logoId);
                if (JobsUtils.jobsName.contains(name)) {
                    continue;
                }
                JobsUtils.jobsName.add(name);
                Main.getInstance().getConfig("jobs").getConfig().set("Jobs", (Object)JobsUtils.jobsName);
                Main.getInstance().getConfig("jobs").getConfig().set("Logo." + name, (Object)logoId);
                Main.getInstance().getConfig("jobs").save();
            }
        }
    }
    
    public static void saveJobsForPlayer(final Player player) {
        final File file = new File(Main.getInstance().getJobsmanager().getJobsDir(), player.getName() + ".json");
        final JobsManager manager = Main.getInstance().getJobsmanager();
        if (JobsUtils.jobs.get(player.getName()) == null) {
            final JobsPlayer jobsPlayer = new JobsPlayer(player.getName());
            final String json = manager.serializeJobsPlayer(jobsPlayer);
            JobsFileUtils.save(file, json);
        }
        else {
            final JobsPlayer jobsPlayer = JobsUtils.jobs.get(player.getName());
            final String json = manager.serializeJobsPlayer(jobsPlayer);
            JobsFileUtils.save(file, json);
        }
    }
    
    public static void createOrLoadPlayerJobs(final Player player) {
        final File file = new File(Main.getInstance().getJobsmanager().getJobsDir(), player.getName() + ".json");
        if (file.exists()) {
            final JobsManager manager = Main.getInstance().getJobsmanager();
            final String json = JobsFileUtils.loadContent(file);
            final JobsPlayer jobPlayer = manager.deserializeJobsPlayer(json);
            for (final String name : Main.getInstance().getConfig("jobs").getConfig().getStringList("Jobs")) {
                if (jobPlayer.getJobByName(name) == null) {
                    jobPlayer.addNewJob(name, Main.getInstance().getConfig("jobs").getConfig().getInt("Logo." + name));
                }
            }
            JobsUtils.jobs.put(player.getName(), jobPlayer);
        }
        else {
            final JobsPlayer jobPlayer2 = new JobsPlayer(player.getName());
            for (final String name2 : Main.getInstance().getConfig("jobs").getConfig().getStringList("Jobs")) {
                if (jobPlayer2.getJobByName(name2) == null) {
                    if (Main.getInstance().getConfig("jobs").getConfig().getInt("Logo." + name2) == 0) {
                        continue;
                    }
                    jobPlayer2.addNewJob(name2, Main.getInstance().getConfig("jobs").getConfig().getInt("Logo." + name2));
                }
                else {
                    jobPlayer2.addNewJob(name2, 1);
                }
            }
            JobsUtils.jobs.put(player.getName(), jobPlayer2);
        }
        final JobsManager manager = Main.getInstance().getJobsmanager();
        final JobsPlayer jobsPlayer = JobsUtils.jobs.get(player.getName());
        final String json2 = manager.serializeJobsPlayer(jobsPlayer);
        JobsFileUtils.save(file, json2);
    }
    
    public static void createOrLoadJobsAction(final JobsAction action) {
        final File file = new File(Main.getInstance().getJobsmanager().getJobsActionDir(), action.getName() + ".json");
        JobsUtils.jobsAction.put(action.getName(), action);
        final JobsManager manager = Main.getInstance().getJobsmanager();
        final JobsAction jobsPlayer = JobsUtils.jobsAction.get(action.getName());
        final String json = manager.serializeJobsAction(jobsPlayer);
        JobsFileUtils.save(file, json);
    }
    
    public static void createOrLoadJobsLockedCraft(final JobsLockedCraft action) {
        final File file = new File(Main.getInstance().getJobsmanager().getJobsCraftDir(), action.getItem().toString() + ".json");
        JobsUtils.lockedCraft.put(action.getItem(), action);
        final JobsManager manager = Main.getInstance().getJobsmanager();
        final JobsLockedCraft jobsPlayer = JobsUtils.lockedCraft.get(action.getItem());
        final String json = manager.serializeJobsLockedCraft(jobsPlayer);
        JobsFileUtils.save(file, json);
    }
    
    public static String getRewardFor(final String name, final int level) {
        return Main.getInstance().getConfig("jobs").getConfig().getString("Rewards." + name + "." + level);
    }
    
    public static void sendPacket(final Player player) {
        final JobsPacket clearPacket = new JobsPacket("clear");
        CustomPacketHandler.dispatch((CustomPacket)clearPacket, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        final JobsPlayer jobPlayer = JobsUtils.jobs.get(player.getName());
        for (final Map.Entry<String, Jobs> j : jobPlayer.getJobs().entrySet()) {
            final Jobs job = j.getValue();
            if (job.getLogoId() == 0) {
                JobsUtils.jobs.remove(jobPlayer);
                jobPlayer.getJobByName(job.getName()).setLogoId(Main.getInstance().getConfig("jobs").getConfig().getInt("Logo." + job.getName()));
                System.out.println("FIX: LogoID: " + Main.getInstance().getConfig("jobs").getConfig().getInt("Logo." + job.getName()));
                System.out.println("FIX: Player:" + player.getName() + ", Job: " + job.getName() + ", New Logo ID: " + job.getLogoId());
                JobsUtils.jobs.put(player.getName(), jobPlayer);
            }
            final JobsPacket packet = new JobsPacket("jobs", job.getName(), job.getLogoId(), job.getMyExperience(), job.getMyLevel());
            CustomPacketHandler.dispatch((CustomPacket)packet, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        }
        for (final Map.Entry<String, JobsAction> x : JobsUtils.jobsAction.entrySet()) {
            final JobsAction action = x.getValue();
            final JobsPacket packet = new JobsPacket("actions", action.getType(), action.getId(), action.getExperienceAmount(), action.getName(), action.getJobId(), action.getDescription());
            CustomPacketHandler.dispatch((CustomPacket)packet, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        }
        for (final Map.Entry<Material, JobsLockedCraft> x2 : JobsUtils.lockedCraft.entrySet()) {
            final JobsLockedCraft action2 = x2.getValue();
            final JobsPacket packet = new JobsPacket("crafts", action2.getItem().getId(), action2.getMetier(), action2.getLevel());
            CustomPacketHandler.dispatch((CustomPacket)packet, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        }
        final JobsPacket packet2 = new JobsPacket("openGui");
        CustomPacketHandler.dispatch((CustomPacket)packet2, new PluginMessageRecipient[] { (PluginMessageRecipient)player });
    }
    
    static {
        JobsUtils.jobs = new HashMap<String, JobsPlayer>();
        JobsUtils.jobsAction = new HashMap<String, JobsAction>();
        JobsUtils.jobsName = (List<String>)Main.getInstance().getConfig("jobs").getConfig().getStringList("Jobs");
        JobsUtils.inRewardAwaiting = new HashMap<Player, String>();
        JobsUtils.lockedCraft = new HashMap<Material, JobsLockedCraft>();
    }
}
