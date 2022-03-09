package su.binair.andasia;

import org.bukkit.plugin.java.*;
import su.binair.andasia.listener.drops.EntityDropListener;
import su.binair.objects.*;
import su.binair.andasia.utils.obsidian.*;
import su.binair.andasia.utils.playersdata.*;
import su.binair.andasia.utils.totem.*;
import su.binair.andasia.utils.koth.*;
import su.binair.andasia.utils.jobs.*;
import su.binair.andasia.utils.quest.*;
import su.binair.andasia.utils.*;
import org.bukkit.entity.*;
import su.binair.andasia.utils.anvil.*;
import su.binair.andasia.utils.discord.*;
import su.binair.utils.*;
import su.binair.andasia.utils.generations.*;
import su.binair.andasia.utils.cristalFaction.*;
import su.binair.andasia.listener.security.*;
import org.bukkit.event.*;
import su.binair.andasia.listener.misc.*;
import su.binair.andasia.effects.*;
import su.binair.andasia.listener.tool.*;
import su.binair.andasia.listener.portal.*;
import su.binair.andasia.listener.reload.*;
import su.binair.andasia.listener.combat.*;
import su.binair.andasia.listener.player.*;
import su.binair.andasia.listener.commands.*;
import su.binair.andasia.listener.generation.*;
import su.binair.andasia.listener.structure.*;
import su.binair.andasia.listener.naturalspawn.*;
import su.binair.andasia.listener.turret.*;
import su.binair.andasia.listener.chunkminer.*;
import su.binair.andasia.listener.stacker.*;
import su.binair.andasia.listener.staff.*;
import su.binair.andasia.listener.freeze.*;
import su.binair.andasia.listener.uuid.*;
import su.binair.andasia.listener.modules.*;
import su.binair.andasia.listener.combattag.*;
import su.binair.andasia.listener.obsidian.*;
import su.binair.andasia.listener.totem.*;
import su.binair.andasia.listener.koth.*;
import su.binair.andasia.listener.jobs.*;
import su.binair.andasia.listener.claim.*;
import su.binair.andasia.listener.cristalFaction.*;
import su.binair.andasia.listener.quest.*;
import su.binair.andasia.listener.grappling.*;
import su.binair.andasia.listener.food.*;
import su.binair.andasia.listener.BoneMealListener;
import su.binair.andasia.listener.chat.*;
import su.binair.andasia.utils.stacker.*;
import org.bukkit.scheduler.*;
import su.binair.andasia.runnable.*;
import org.bukkit.command.*;
import su.binair.andasia.listener.chat.messages.*;
import su.binair.andasia.commands.*;
import org.bukkit.plugin.*;
import org.bukkit.inventory.*;
import com.massivecraft.factions.*;
import su.binair.database.*;
import java.util.*;

public class Main extends JavaPlugin
{
    public static boolean isDiscord;
    public static Main plugin;
    public static Main instance;
    public HashMap<String, YmlConfiguration> configs;
    private StorageHandler obsidian_storage;
    private RealPlayersStorage player_storage;
    private TotemManager totem_manager;
    private KothManager koth_manager;
    private JobsManager jobs_manager;
    private QuestManager quest_manager;
    private FileUtils fileUtils;
    public static HashMap<Player, Inventory> reserve;
    public static String ip;
    public static String base;
    public static String user;
    public static String pass;
    public static String port;
    private AnvilUtils anvilUtils;
    
    public Main() {
        this.configs = new HashMap<String, YmlConfiguration>();
        this.obsidian_storage = new StorageHandler();
    }
    
    public void onEnable() {
        DiscordUtils.sendDiscordMessage("AndaBot", DiscordEnum.EVENTS.getType(), "Information", "Le serveur vient de se lancer!");
        Main.instance = this;
        (Main.plugin = this).registerConfigs();
        registerCommands();
        registerEvents();
        registerRunnables();
        final int structuresAmount = StructureUtils.loadStructures();
        this.totem_manager = new TotemManager();
        this.koth_manager = new KothManager();
        this.jobs_manager = new JobsManager();
        this.fileUtils = new FileUtils();
        this.quest_manager = new QuestManager();
        this.anvilUtils = new AnvilUtils();
        CristalUtils.crystalRecompense = (List<ItemStack>)this.getConfig("cristal").getConfig().getList("recompenses");
    }
    
    public static void registerEvents() {
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new CrashListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new SpamListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new BottleListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new DeathListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new FeatherFallingListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new InvisibilityListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new PickaxeListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new PortalListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new ReloadListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new DuplicateSwordListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new VisionListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new MiscCommands(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new GenerationCommand(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new StructureChunkListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new SpawnListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new TurretListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new ChunkMinerListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new StackerListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new StaffListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new FreezeListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new UUIDListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new ModuleDrawListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new CombatTagListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new ObsidianListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new TotemListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new KothListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new JobsListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new ClaimListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new FactionCristalListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new QuestListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new GrapplingListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new FoodListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new EntityDropListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents((Listener)new BoneMealListener(), getPlugin());
    }
    
    public static void registerRunnables() {
        getPlugin().getServer().getScheduler().runTaskTimer(getPlugin(), (Runnable)new TripleAliageHelmet(getInstance()), 10L, 10L);
        getPlugin().getServer().getScheduler().runTaskTimer(getPlugin(), (Runnable)new TripleAliageChestplate(getInstance()), 10L, 10L);
        getPlugin().getServer().getScheduler().runTaskTimer(getPlugin(), (Runnable)new TripleAliageLeggings(getInstance()), 10L, 10L);
        getPlugin().getServer().getScheduler().runTaskTimer(getPlugin(), (Runnable)new TripleAliageBoots(getInstance()), 10L, 10L);
        getPlugin().getServer().getScheduler().runTaskTimer(getPlugin(), (Runnable)new FullArmoredArmor(getInstance()), 10L, 10L);
        getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), (BukkitRunnable)new StackerTask(), (long)getInstance().getConfig("stacker").getConfig().getInt("UpdateTickDelay"), (long)getInstance().getConfig("stacker").getConfig().getInt("UpdateTickDelay"));
        getPlugin().getServer().getScheduler().runTaskTimer(getPlugin(), (BukkitRunnable)new AutoMessage(), 1L, 3600L);
        getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), (BukkitRunnable)new ClearLag(), 1L, 6000L);
        getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), (BukkitRunnable)new EverySecondsTask(), 20L, 20L);
        getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), (BukkitRunnable)new JobsSaveTask(), 1L, 6000L);
        getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), (BukkitRunnable)new SaveRunnable(), 1L, 3600L);
        getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), (BukkitRunnable)new FactionCristalRunnable(), 20L, 20L);
        getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), (BukkitRunnable)new RadioactiveWalkRnunable(), 1L, 20L);
    }
    
    public static void registerCommands() {
        ((JavaPlugin)getPlugin()).getCommand("bottlexp").setExecutor((CommandExecutor)new BottleCommand());
        ((JavaPlugin)getPlugin()).getCommand("msg").setExecutor((CommandExecutor)new CmdMessage());
        ((JavaPlugin)getPlugin()).getCommand("r").setExecutor((CommandExecutor)new CmdReply());
        ((JavaPlugin)getPlugin()).getCommand("poubelle").setExecutor((CommandExecutor)new PoubelleCommand());
        ((JavaPlugin)getPlugin()).getCommand("structure").setExecutor((CommandExecutor)new StructureCommand());
        ((JavaPlugin)getPlugin()).getCommand("randomtp").setExecutor((CommandExecutor)new RandomTPCommand());
        ((JavaPlugin)getPlugin()).getCommand("staff").setExecutor((CommandExecutor)new StaffCommand());
        ((JavaPlugin)getPlugin()).getCommand("freeze").setExecutor((CommandExecutor)new FreezeCommand());
        ((JavaPlugin)getPlugin()).getCommand("stacker").setExecutor((CommandExecutor)new StackerCommand());
        ((JavaPlugin)getPlugin()).getCommand("clearlag").setExecutor((CommandExecutor)new ClearLagCommand());
        ((JavaPlugin)getPlugin()).getCommand("combattag").setExecutor((CommandExecutor)new CombatTagCommand());
        ((JavaPlugin)getPlugin()).getCommand("totem").setExecutor((CommandExecutor)new TotemCommand());
        ((JavaPlugin)getPlugin()).getCommand("koth").setExecutor((CommandExecutor)new KothCommand());
        ((JavaPlugin)getPlugin()).getCommand("jobs").setExecutor((CommandExecutor)new JobsCommand());
        ((JavaPlugin)getPlugin()).getCommand("quest").setExecutor((CommandExecutor)new QuestCommand());
        ((JavaPlugin)getPlugin()).getCommand("rename").setExecutor((CommandExecutor)new RenameCommand());
    }
    
    public void registerConfigs() {
        getPlugin().saveDefaultConfig();
        this.configs.put("antispam", new YmlConfiguration((Plugin)this, "antispam"));
        if (!this.configs.get("antispam").isExist()) {
            this.configs.get("antispam").getConfig().set("message", (Object)"&aTu dois attendre encore &f&l%time% &asecondes.");
            this.configs.get("antispam").getConfig().set("time", (Object)"2");
            this.configs.get("antispam").save();
        }
        this.configs.put("bottlexp", new YmlConfiguration((Plugin)this, "bottlexp"));
        if (!this.configs.get("bottlexp").isExist()) {
            this.configs.get("bottlexp").getConfig().set("bottlexp", (Object)"&aTu viens de mettre ton XP en &f&lBottle.");
            this.configs.get("bottlexp").getConfig().set("bottlexp10", (Object)"&aTu dois avoir minimums &f&l10 &alevels.");
            this.configs.get("bottlexp").getConfig().set("bottlename", (Object)"&eNiveaux: ");
            this.configs.get("bottlexp").getConfig().set("bottlelore", (Object)"&eClique droit");
            this.configs.get("bottlexp").getConfig().set("bottle_not_enought", (Object)"&cTu as pas assez de levels!");
            this.configs.get("bottlexp").save();
        }
        this.configs.put("deathmessage", new YmlConfiguration((Plugin)this, "deathmessage"));
        if (!this.configs.get("deathmessage").isExist()) {
            this.configs.get("deathmessage").getConfig().set("dead", (Object)"&7[&4&l\u2718&7] &f&l%victim% &aest mort tous seul...");
            this.configs.get("deathmessage").getConfig().set("dead_by", (Object)"&7[&4&l\u2718&7] &f&l%victim% &a\u00e0 été \u00e9 tu\u00e9 par &f&l%killer%.");
            this.configs.get("deathmessage").save();
        }
        this.configs.put("knockback", new YmlConfiguration((Plugin)this, "knockback"));
        if (!this.configs.get("knockback").isExist()) {
            this.configs.get("knockback").getConfig().set("horizontal", (Object)"1.0");
            this.configs.get("knockback").getConfig().set("vertical", (Object)"1.0");
            this.configs.get("knockback").save();
        }
        this.configs.put("fortune", new YmlConfiguration((Plugin)this, "fortune"));
        if (!this.configs.get("fortune").isExist()) {
            this.configs.get("fortune").getConfig().set("fortune_prevent", (Object)"&cTu ne peux pas fusionner un livre Fortune.");
            this.configs.get("fortune").save();
        }
        this.configs.put("stafflist", new YmlConfiguration((Plugin)this, "stafflist"));
        if (!this.configs.get("stafflist").isExist()) {
            final List<String> staffs = new ArrayList<String>();
            staffs.add("B_nair");
            staffs.add("Tensa");
            this.configs.get("stafflist").getConfig().set("staffs", (Object)staffs);
            this.configs.get("stafflist").save();
        }
        this.configs.put("automessage", new YmlConfiguration((Plugin)this, "automessage"));
        if (!this.configs.get("automessage").isExist()) {
            final List<String> list = new ArrayList<String>();
            list.add("N'oubliez pas de voter pour le serveur !");
            list.add("Nous poss\u00e9dons une boutique en ligne !");
            this.configs.get("automessage").getConfig().set("messages", (Object)list);
            this.configs.get("automessage").save();
        }
        this.configs.put("filons", new YmlConfiguration((Plugin)this, "filons"));
        if (!this.configs.get("filons").isExist()) {
            final List<String> list = new ArrayList<String>();
            list.add("filons_cobalt");
            list.add("filons_scandium");
            list.add("filons_uranium");
            this.configs.get("filons").getConfig().set("filons", (Object)list);
            this.configs.get("filons").save();
        }
        this.configs.put("stacker", new YmlConfiguration((Plugin)this, "stacker"));
        if (!this.configs.get("stacker").isExist()) {
            this.configs.get("stacker").getConfig().set("StackRadius", (Object)1);
            this.configs.get("stacker").getConfig().set("UpdateTickDelay", (Object)20);
            this.configs.get("stacker").getConfig().set("StackNumberColor", (Object)"a");
            final List<String> toStack = new ArrayList<String>();
            toStack.add("BAT");
            toStack.add("BLAZE");
            toStack.add("CAVE_SPIDER");
            toStack.add("CHICKEN");
            toStack.add("COW");
            toStack.add("CREEPER");
            toStack.add("ENDERMAN");
            toStack.add("GHAST");
            toStack.add("GIANT");
            toStack.add("HORSE");
            toStack.add("IRON_GOLEM");
            toStack.add("MAGMA_CUBE");
            toStack.add("MUSHROOM_COW");
            toStack.add("OCELOT");
            toStack.add("PIG");
            toStack.add("PIG_ZOMBIE");
            toStack.add("SHEEP");
            toStack.add("SILVERFISH");
            toStack.add("SKELETON");
            toStack.add("SLIME");
            toStack.add("SNOWMAN");
            toStack.add("SPIDER");
            toStack.add("SQUID");
            toStack.add("WITCH");
            toStack.add("WOLF");
            toStack.add("ZOMBIE");
            this.configs.get("stacker").getConfig().set("MobTypes", (Object)toStack);
            this.configs.get("stacker").save();
        }
        this.configs.put("combat_tag", new YmlConfiguration((Plugin)this, "combat_tag"));
        if (!this.configs.get("combat_tag").isExist()) {
            final List<String> list = new ArrayList<String>();
            list.add("/tpyes");
            list.add("/etpyes");
            list.add("/home");
            list.add("/ehome");
            list.add("/spawn");
            list.add("/espawn");
            this.configs.get("combat_tag").getConfig().set("combat_allowed_cmds", (Object)list);
            this.configs.get("combat_tag").getConfig().set("combat_time", (Object)30);
            this.configs.get("combat_tag").save();
        }
        this.configs.put("obsidian", new YmlConfiguration((Plugin)this, "obsidian"));
        if (!this.configs.get("obsidian").isExist()) {
            this.configs.get("obsidian").getConfig().set("blocks.49", (Object)8);
            this.configs.get("obsidian").getConfig().set("blocks.116", (Object)4);
            this.configs.get("obsidian").getConfig().set("blocks.130", (Object)4);
            this.configs.get("obsidian").getConfig().set("radius", (Object)2);
            this.configs.get("obsidian").getConfig().set("liquid_multiplier", (Object)0);
            this.configs.get("obsidian").getConfig().set("durability_checker", (Object)489);
            this.configs.get("obsidian").getConfig().set("drop_chance", (Object)0);
            this.configs.get("obsidian").getConfig().set("regen.frequence", (Object)10);
            this.configs.get("obsidian").getConfig().set("regen.amount", (Object)1);
            this.configs.get("obsidian").getConfig().set("explosions.PRIMED_TNT", (Object)1);
            this.configs.get("obsidian").getConfig().set("explosions.CREEPER", (Object)1);
            this.configs.get("obsidian").getConfig().set("explosions.GHAST", (Object)1);
            this.configs.get("obsidian").getConfig().set("explosions.WITHER", (Object)1);
            this.configs.get("obsidian").getConfig().set("explosions.MINECART_TNT", (Object)1);
            this.configs.get("obsidian").save();
        }
        this.configs.put("totem", new YmlConfiguration((Plugin)this, "totem"));
        if (!this.configs.get("totem").isExist()) {
            this.configs.get("totem").getConfig().set("recompense.name", (Object)"64 blocs de Stone");
            this.configs.get("totem").getConfig().set("recompense.commande", (Object)"give @player 1 64");
            this.configs.get("totem").save();
        }
        this.configs.put("koth", new YmlConfiguration((Plugin)this, "koth"));
        if (!this.configs.get("koth").isExist()) {
            this.configs.get("koth").getConfig().set("recompense.name", (Object)"64 blocs de Stone");
            this.configs.get("koth").getConfig().set("recompense.commande", (Object)"give @player 1 64");
            this.configs.get("koth").getConfig().set("manager.location1", (Object)"null");
            this.configs.get("koth").getConfig().set("manager.location2", (Object)"null");
            this.configs.get("koth").getConfig().set("manager.capture_time", (Object)"300");
            this.configs.get("koth").save();
        }
        this.configs.put("jobs", new YmlConfiguration((Plugin)this, "jobs"));
        if (!this.configs.get("jobs").isExist()) {
            this.configs.get("jobs").save();
        }
        this.configs.put("minage", new YmlConfiguration((Plugin)this, "minage"));
        if (!this.configs.get("minage").isExist()) {
            this.configs.get("minage").getConfig().set("Configuration.database.ip", (Object)"localhost");
            this.configs.get("minage").getConfig().set("Configuration.database.base", (Object)"minage");
            this.configs.get("minage").getConfig().set("Configuration.database.user", (Object)"root");
            this.configs.get("minage").getConfig().set("Configuration.database.pass", (Object)"");
            this.configs.get("minage").getConfig().set("Configuration.database.port", (Object)"3306");
            this.configs.get("minage").save();
        }
        this.configs.put("cristal", new YmlConfiguration((Plugin)this, "cristal"));
        if (!this.configs.get("cristal").isExist()) {
            final List<ItemStack> items = new ArrayList<ItemStack>();
            this.configs.get("cristal").getConfig().set("recompenses", (Object)items);
            this.configs.get("cristal").save();
        }
        this.configs.put("distributeur", new YmlConfiguration((Plugin)this, "distributeur"));
        if (!this.configs.get("distributeur").isExist()) {
            this.configs.get("distributeur").getConfig().set("location", (Object)"null");
            this.configs.get("distributeur").save();
        }
    }
    
    public void onDisable() {
        DiscordUtils.sendDiscordMessage("AndaBot", DiscordEnum.EVENTS.getType(), "Information", "Le serveur vient de se stopper!");
        TotemManager.disableTotem();
        JobsManager.disable();
        this.getQuestManager().getQuesUtils().saveAllQuestPlayer();
        Factions.i.saveToDisc();
        for (final Map.Entry<Player, Inventory> entry : getReserve().entrySet()) {
            DatabaseUtils.saveInventory((Player)entry.getKey(), (Inventory)entry.getValue());
        }
    }
    
    public static Plugin getPlugin() {
        return (Plugin)Main.plugin;
    }
    
    public static Main getInstance() {
        return Main.instance;
    }
    
    public YmlConfiguration getConfig(final String config) {
        return this.configs.get(config);
    }
    
    public StorageHandler getStorage() {
        return this.obsidian_storage;
    }
    
    public RealPlayersStorage getRealPlayersStorage() {
        return this.player_storage;
    }
    
    public TotemManager getTotemManager() {
        return this.totem_manager;
    }
    
    public KothManager getKothManager() {
        return this.koth_manager;
    }
    
    public JobsManager getJobsmanager() {
        return this.jobs_manager;
    }
    
    public QuestManager getQuestManager() {
        return this.quest_manager;
    }
    
    public FileUtils getFileUtils() {
        return this.fileUtils;
    }
    
    public AnvilUtils getAnvilUtils() {
        return this.anvilUtils;
    }
    
    public static HashMap<Player, Inventory> getReserve() {
        return Main.reserve;
    }
    
    static {
        Main.isDiscord = false;
        Main.reserve = new HashMap<Player, Inventory>();
    }
}
