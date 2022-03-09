package su.binair.andasia.listener.quest;

import su.binair.andasia.*;
import su.binair.utils.*;
import org.bukkit.entity.*;
import su.binair.packets.network.*;
import org.bukkit.plugin.messaging.*;
import su.binair.packets.*;
import su.binair.andasia.event.*;
import su.binair.andasia.utils.quest.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.event.player.*;

public class QuestListener implements Listener
{
    @EventHandler
    public void onQuestAccept(final QuestPacketEvent event) {
        final Player player = event.getPlayer();
        final int id = event.getQuestId();
        final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName());
        if (questPlayer != null) {
            if (questPlayer.getActiveQuest() == null) {
                Main.getInstance().getQuestManager().getQuesUtils().setActiveQuest(id, player);
            }
            else {
                ToolBox.sendMessage(player, "§cD\u00e9sol\u00e9§e, mais tu as déjà \u00e0 une §6quété e §eactive !");
            }
        }
    }
    
    @EventHandler
    public void onChatQuest(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();
        if (Main.getInstance().getQuestManager().getQuesUtils().questCreate.containsKey(player.getName())) {
            event.setCancelled(true);
            if (Main.getInstance().getQuestManager().getQuesUtils().questCreate.get(player.getName()) != null) {
                final Quest quest = Main.getInstance().getQuestManager().getQuesUtils().questCreate.get(player.getName());
                if (quest.getType() == null) {
                    quest.setType(message);
                    if (message.equalsIgnoreCase("NEAR")) {
                        quest.setType(message);
                        ToolBox.sendMessage(player, "§eVeuillez entrer les §6coordonn\u00e9es §een suivant le mod\u00e9le suivant: §aMonde§e, §aX§e, §aY§e, §aZ§e!");
                        return;
                    }
                    quest.setType(message);
                    ToolBox.sendMessage(player, "§eVeuillez entrer §6l'ID §edu §6Block§e/§6Mob §ea §6casser§e/§6tuer§e/§6Item a craft§e!");
                }
                else {
                    if (quest.getEntityBlockId() == null) {
                        quest.setEntityBlockId(message);
                        ToolBox.sendMessage(player, "§eVeuillez entrer le §61 §esi le joueur peut la s\u00e9l\u00e9ctionner!");
                        ToolBox.sendMessage(player, "§eVeuillez entrer le §62 §esi le joueur §cne peut pas §ela s\u00e9l\u00e9ctionner!");
                        return;
                    }
                    if (quest.isSelectable() == 0) {
                        quest.setSelectable(Integer.valueOf(message));
                        ToolBox.sendMessage(player, "§eVeuillez entrer le §6Nom §ede la §6Quété e§e!");
                        return;
                    }
                    if (quest.getName() == null) {
                        quest.setName(message);
                        ToolBox.sendMessage(player, "§eVeuillez entrer la §6Description §ede la §6Quété e§e!");
                        return;
                    }
                    if (quest.getDescription() == null) {
                        quest.setDescription(message);
                        ToolBox.sendMessage(player, "§eVeuillez entrer le §6Nom de la r\u00e9compense §ede la §6Quété e§e!");
                        return;
                    }
                    if (quest.getRecompenseName() == null) {
                        quest.setRecompenseName(message);
                        ToolBox.sendMessage(player, "§eVeuillez entrer la §6Commande de la r\u00e9compense §ede la §6Quété e§e!");
                        return;
                    }
                    if (quest.getRecompense() == null) {
                        quest.setRecompense(message);
                        ToolBox.sendMessage(player, "§eVeuillez entrer le §6Montant de Block§e/§6Mobs \u00e0 tuer §ede la §6Quété e§e!");
                        ToolBox.sendMessage(player, "§cSi le type est: NEAR, mettre le radius de distance!");
                        return;
                    }
                    if (quest.getAmount() == 0) {
                        quest.setAmount(Integer.valueOf(message));
                        final String[] list = { ToolBox.getBeautyStart() + "§6§lID: §c" + quest.getId(), ToolBox.getBeautyStart() + "§6Nom: §c" + quest.getName(), ToolBox.getBeautyStart() + "§6Description: ", ToolBox.getBeautyStart() + "§c" + quest.getDescription(), ToolBox.getBeautyStart() + "§6Type: " + quest.getType(), ToolBox.getBeautyStart() + "§6Nom de la r\u00e9compense:", ToolBox.getBeautyStart() + "§c" + quest.getRecompenseName(), ToolBox.getBeautyStart() + "§6Commande de la r\u00e9compense:", ToolBox.getBeautyStart() + "§c" + quest.getRecompense(), ToolBox.getBeautyStart() + "§6ID du Block§e/§6Mob§e/§6Location:", ToolBox.getBeautyStart() + "§c" + quest.getEntityBlockId() };
                        ToolBox.sendCustomComposedMessage(player, list);
                        Main.getInstance().getQuestManager().getQuesUtils().questCreate.remove(player.getName());
                        Main.getInstance().getQuestManager().getQuesUtils().addNewQuest(quest);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onQuestJoin(final PlayerJoinEvent event) {
        Main.getInstance().getQuestManager().getQuesUtils().createOrLoadQuestPlayer(event.getPlayer());
        final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(event.getPlayer().getName());
        if (questPlayer.getActiveQuest() != null) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    if (questPlayer.getActiveQuest().getType().equalsIgnoreCase("NEAR")) {
                        CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", questPlayer.getActiveQuest().getName(), ""), new PluginMessageRecipient[] { (PluginMessageRecipient)event.getPlayer() });
                    }
                    else {
                        CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", questPlayer.getActiveQuest().getName(), questPlayer.getActiveQuest().getAvancement() + "/" + questPlayer.getActiveQuest().getAmount()), new PluginMessageRecipient[] { (PluginMessageRecipient)event.getPlayer() });
                    }
                }
            }, 40L);
        }
    }
    
    @EventHandler
    public void onQuestQuit(final PlayerQuitEvent event) {
        Main.getInstance().getQuestManager().getQuesUtils().saveQuestPlayer(event.getPlayer());
    }
    
    @EventHandler
    public void onQuestFinishEvent(final QuestFinishEvent event) {
        final Player player = event.getPlayer();
        CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", "", ""), new PluginMessageRecipient[] { (PluginMessageRecipient)event.getPlayer() });
        final Quest quest = event.getQuest();
        ToolBox.sendMessage(player, "§a§lF\u00e9licitation§a, tu viens de finir la quété e suivante: §c" + quest.getName() + "§a!");
        Main.getInstance().getQuestManager().getQuesUtils();
        QuestUtils.sendReward(quest, player);
    }
    
    @EventHandler
    public void onQuestBreak(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName());
        final Quest quest = questPlayer.getActiveQuest();
        if (questPlayer == null || quest == null) {
            return;
        }
        if (!quest.getType().equalsIgnoreCase(QuestType.BREAK.getType())) {
            return;
        }
        final int id = event.getBlock().getTypeId();
        if (Integer.valueOf(quest.getEntityBlockId()) != id) {
            return;
        }
        if (quest.getAvancement() == quest.getAmount() - 1) {
            questPlayer.getQuestById(questPlayer.getActiveQuest().getId()).setStatus(true);
            Bukkit.getServer().getPluginManager().callEvent((Event)new QuestFinishEvent(player, quest));
            questPlayer.setActiveQuest(null);
            Main.getInstance().getQuestManager().getQuesUtils().saveQuestPlayer(player);
        }
        else {
            quest.addAvancement(1);
            CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", quest.getName(), quest.getAvancement() + "/" + quest.getAmount()), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        }
    }
    
    @EventHandler
    public void onQuestPickup(final PlayerPickupItemEvent event) {
        final Player player = event.getPlayer();
        final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName());
        final Quest quest = questPlayer.getActiveQuest();
        if (questPlayer == null || quest == null) {
            return;
        }
        if (!quest.getType().equalsIgnoreCase(QuestType.PICKUP.getType())) {
            return;
        }
        final int id = event.getItem().getItemStack().getTypeId();
        if (Integer.valueOf(quest.getEntityBlockId()) != id) {
            return;
        }
        if (quest.getAvancement() == quest.getAmount() - 1) {
            questPlayer.getQuestById(questPlayer.getActiveQuest().getId()).setStatus(true);
            Bukkit.getServer().getPluginManager().callEvent((Event)new QuestFinishEvent(player, quest));
            questPlayer.setActiveQuest(null);
            Main.getInstance().getQuestManager().getQuesUtils().saveQuestPlayer(player);
        }
        else {
            quest.addAvancement(1);
            CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", quest.getName(), quest.getAvancement() + "/" + quest.getAmount()), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        }
    }
    
    @EventHandler
    public void onQuestKill(final EntityDeathEvent event) {
        final Player player = event.getEntity().getKiller();
        if(player != null)
        {
        	final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName());
            if(questPlayer != null)
            {
            	final Quest quest = questPlayer.getActiveQuest();
                if (questPlayer == null || quest == null) {
                    return;
                }
                if (!quest.getType().equalsIgnoreCase(QuestType.KILL.getType())) {
                    return;
                }
                final int id = event.getEntity().getType().getTypeId();
                if (Integer.valueOf(quest.getEntityBlockId()) != id) {
                    return;
                }
                if (quest.getAvancement() == quest.getAmount() - 1) {
                    questPlayer.getQuestById(questPlayer.getActiveQuest().getId()).setStatus(true);
                    Bukkit.getServer().getPluginManager().callEvent((Event)new QuestFinishEvent(player, quest));
                    questPlayer.setActiveQuest(null);
                    Main.getInstance().getQuestManager().getQuesUtils().saveQuestPlayer(player);
                }
                else {
                    quest.addAvancement(1);
                    CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", quest.getName(), quest.getAvancement() + "/" + quest.getAmount()), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
                }
            }
        }
    }
    
    @EventHandler
    public void onQuestDrop(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName());
        final Quest quest = questPlayer.getActiveQuest();
        if (questPlayer == null || quest == null) {
            return;
        }
        if (!quest.getType().equalsIgnoreCase(QuestType.DROP.getType())) {
            return;
        }
        final int id = event.getItemDrop().getItemStack().getTypeId();
        if (Integer.valueOf(quest.getEntityBlockId()) != id) {
            return;
        }
        if (quest.getAvancement() == quest.getAmount() - 1) {
            questPlayer.getQuestById(questPlayer.getActiveQuest().getId()).setStatus(true);
            Bukkit.getServer().getPluginManager().callEvent((Event)new QuestFinishEvent(player, quest));
            questPlayer.setActiveQuest(null);
            Main.getInstance().getQuestManager().getQuesUtils().saveQuestPlayer(player);
        }
        else {
            quest.addAvancement(1);
            CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", quest.getName(), quest.getAvancement() + "/" + quest.getAmount()), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        }
    }
    
    @EventHandler
    public void onQuestPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName());
        final Quest quest = questPlayer.getActiveQuest();
        if (questPlayer == null || quest == null) {
            return;
        }
        if (!quest.getType().equalsIgnoreCase(QuestType.PLACE.getType())) {
            return;
        }
        final int id = event.getBlock().getTypeId();
        if (Integer.valueOf(quest.getEntityBlockId()) != id) {
            return;
        }
        if (quest.getAvancement() == quest.getAmount() - 1) {
            questPlayer.getQuestById(questPlayer.getActiveQuest().getId()).setStatus(true);
            Bukkit.getServer().getPluginManager().callEvent((Event)new QuestFinishEvent(player, quest));
            questPlayer.setActiveQuest(null);
            Main.getInstance().getQuestManager().getQuesUtils().saveQuestPlayer(player);
        }
        else {
            quest.addAvancement(1);
            CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", quest.getName(), quest.getAvancement() + "/" + quest.getAmount()), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        }
    }
    
    @EventHandler
    public void onQuestMoove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName());
        final Quest quest = questPlayer.getActiveQuest();
        if (questPlayer == null || quest == null) {
            return;
        }
        if (!quest.getType().equalsIgnoreCase(QuestType.NEAR.getType())) {
            return;
        }
        final String[] splittedLoc = quest.getEntityBlockId().replaceAll(" ", "").split(",");
        final World world = Bukkit.getWorld(splittedLoc[0]);
        if (player.getWorld() != world) {
            return;
        }
        final int x = Integer.valueOf(splittedLoc[1]);
        final int y = Integer.valueOf(splittedLoc[2]);
        final int z = Integer.valueOf(splittedLoc[3]);
        final Location target = new Location(world, (double)x, (double)y, (double)z);
        if (Main.getInstance().getQuestManager().getQuesUtils().isPlayerNearLocation(player, target, quest.getAmount())) {
            questPlayer.getQuestById(questPlayer.getActiveQuest().getId()).setStatus(true);
            Bukkit.getServer().getPluginManager().callEvent((Event)new QuestFinishEvent(player, quest));
            questPlayer.setActiveQuest(null);
            Main.getInstance().getQuestManager().getQuesUtils().saveQuestPlayer(player);
        }
    }
    
    @EventHandler
    public void onQuestClickBlock(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName());
        final Quest quest = questPlayer.getActiveQuest();
        if (questPlayer == null || quest == null) {
            return;
        }
        if (!quest.getType().equalsIgnoreCase(QuestType.INTERACT.getType())) {
            return;
        }
        if (event.getClickedBlock() == null) {
            return;
        }
        final int id = event.getClickedBlock().getTypeId();
        if (Integer.valueOf(quest.getEntityBlockId()) != id) {
            return;
        }
        if (quest.getAvancement() == quest.getAmount() - 1) {
            questPlayer.getQuestById(questPlayer.getActiveQuest().getId()).setStatus(true);
            Bukkit.getServer().getPluginManager().callEvent((Event)new QuestFinishEvent(player, quest));
            questPlayer.setActiveQuest(null);
            Main.getInstance().getQuestManager().getQuesUtils().saveQuestPlayer(player);
        }
        else {
            quest.addAvancement(1);
            CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", quest.getName(), quest.getAvancement() + "/" + quest.getAmount()), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        }
    }
    
    @EventHandler
    public void onQuestClickCraft(final CraftItemEvent event) {
        final Player player = (Player)event.getWhoClicked();
        final QuestPlayer questPlayer = Main.getInstance().getQuestManager().getQuesUtils().getQuestPlayer().get(player.getName());
        final Quest quest = questPlayer.getActiveQuest();
        if (questPlayer == null || quest == null) {
            return;
        }
        if (!quest.getType().equalsIgnoreCase(QuestType.CRAFT.getType())) {
            return;
        }
        if (event.getRecipe().getResult().getType() == null) {
            return;
        }
        final int id = event.getRecipe().getResult().getTypeId();
        if (Integer.valueOf(quest.getEntityBlockId()) != id) {
            return;
        }
        if (quest.getAvancement() >= quest.getAmount()) {
            questPlayer.getQuestById(questPlayer.getActiveQuest().getId()).setStatus(true);
            Bukkit.getServer().getPluginManager().callEvent((Event)new QuestFinishEvent(player, quest));
            questPlayer.setActiveQuest(null);
            Main.getInstance().getQuestManager().getQuesUtils().saveQuestPlayer(player);
        }
        else {
        	int amount = getAmountCraftItem(event.getRecipe().getResult().getType(), event);
        	if(amount > 0)
        		quest.addAvancement(amount);
        	else
        		quest.addAvancement(1);
            CustomPacketHandler.dispatch((CustomPacket)new QuestPacket("sendMaj", quest.getName(), quest.getAvancement() + "/" + quest.getAmount()), new PluginMessageRecipient[] { (PluginMessageRecipient)player });
        }
    }
    
    public static int getAmountCraftItem(Material m, CraftItemEvent e)
    {
        if (e.isCancelled()) return 0;
        if(!e.getRecipe().getResult().getType().equals(m)) return 0;
        int amount = e.getRecipe().getResult().getAmount();
        if (e.isShiftClick()) 
        {
            int max = e.getInventory().getMaxStackSize();
            org.bukkit.inventory.ItemStack[] matrix = e.getInventory().getMatrix();
            for (org.bukkit.inventory.ItemStack is: matrix)
            {
                if (is == null || is.getType().equals(Material.AIR))
                    continue;
                int tmp = is.getAmount();
                if (tmp < max && tmp > 0) max = tmp;
            }
            amount *= max;
        }
        return amount;
    }
}
