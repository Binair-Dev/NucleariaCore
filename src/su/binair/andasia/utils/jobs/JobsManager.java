package su.binair.andasia.utils.jobs;

import java.io.*;
import su.binair.andasia.*;
import su.binair.andasia.runnable.*;
import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.craftbukkit.libs.com.google.gson.*;
import su.binair.andasia.object.jobs.*;

public class JobsManager
{
    private Gson gson;
    private File jobsDir;
    private File jobsActionDir;
    private File jobsCraftDir;
    
    public JobsManager() {
        this.gson = this.createGsonInstance();
        final File jobsFile = new File(Main.getInstance().getDataFolder(), "/jobs/");
        if (!jobsFile.exists()) {
            jobsFile.mkdir();
        }
        this.jobsDir = jobsFile;
        final File jobsActionDir = new File(this.getJobsDir(), "/action/");
        if (!jobsActionDir.exists()) {
            jobsActionDir.mkdir();
        }
        this.jobsActionDir = jobsActionDir;
        final File jobsCraftDir = new File(this.getJobsDir(), "/crafts/");
        if (!jobsCraftDir.exists()) {
            jobsCraftDir.mkdir();
        }
        this.jobsCraftDir = jobsCraftDir;
        for (final File file : this.getJobsActionDir().listFiles()) {
            final String json = JobsFileUtils.loadContent(file);
            final JobsAction jobAction = this.deserializeJobsAction(json);
            JobsUtils.jobsAction.put(jobAction.getName(), jobAction);
        }
        for (final File file : this.getJobsCraftDir().listFiles()) {
            final String json = JobsFileUtils.loadContent(file);
            final JobsLockedCraft jobAction2 = this.deserializeJobsLockedCraft(json);
            JobsUtils.lockedCraft.put(jobAction2.getItem(), jobAction2);
        }
        Main.getInstance();
        final BukkitScheduler scheduler = Main.getPlugin().getServer().getScheduler();
        Main.getInstance();
        scheduler.runTask(Main.getPlugin(), (BukkitRunnable)new JobsLoadTask());
    }
    
    public static void disable() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            JobsUtils.saveJobsForPlayer(player);
        }
    }
    
    private Gson createGsonInstance() {
        return new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
    }
    
    public String serializeJobsPlayer(final JobsPlayer player) {
        return this.gson.toJson((Object)player);
    }
    
    public JobsPlayer deserializeJobsPlayer(final String json) {
        return (JobsPlayer)this.gson.fromJson(json, (Class)JobsPlayer.class);
    }
    
    public String serializeJobsAction(final JobsAction player) {
        return this.gson.toJson((Object)player);
    }
    
    public JobsAction deserializeJobsAction(final String json) {
        return (JobsAction)this.gson.fromJson(json, (Class)JobsAction.class);
    }
    
    public String serializeJobsLockedCraft(final JobsLockedCraft player) {
        return this.gson.toJson((Object)player);
    }
    
    public JobsLockedCraft deserializeJobsLockedCraft(final String json) {
        return (JobsLockedCraft)this.gson.fromJson(json, (Class)JobsLockedCraft.class);
    }
    
    public File getJobsDir() {
        return this.jobsDir;
    }
    
    public File getJobsActionDir() {
        return this.jobsActionDir;
    }
    
    public File getJobsCraftDir() {
        return this.jobsCraftDir;
    }
}
