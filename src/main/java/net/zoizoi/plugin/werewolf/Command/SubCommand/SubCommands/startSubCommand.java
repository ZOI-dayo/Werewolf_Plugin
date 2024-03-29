package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands.Start.StartItems;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Game.GamePlayer;
import net.zoizoi.plugin.werewolf.Game.Job;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import net.zoizoi.plugin.werewolf.utils.ItemUtils;
import net.zoizoi.plugin.werewolf.utils.ScoreboardUtils;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class startSubCommand {
    public int StartCountDownTime = 5;
    public int leftTime = 20;

    public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID) {
        gameManager.getGame(GameID).isRunning = true;
        // 0~4秒後
        Runnable StartCountDown = new Runnable() {
            @Override
            public void run() {
                for (UUID uuid : gameManager.getGame(GameID).getPlayers().keySet()) {
                    Player p = plugin.getServer().getPlayer(uuid);
                    p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
                    p.sendMessage(StartCountDownTime + "秒前...");
                    p.sendTitle(StartCountDownTime + "秒前", "", 10, 20, 10);
                }
                StartCountDownTime--;
            }
        };
        for (int i = 0; i < 5; i++) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, StartCountDown, (i * 20));
        }

        // 5秒後
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                StartItems startItems = new StartItems();
                startItems.GiveItems(plugin, player, gameManager, GameID);
                // net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands.Start.StartItems へ移転
                ArrayList<GamePlayer> Werewolfs = new ArrayList<>();
                for (GamePlayer gp : gameManager.getGame(GameID).getPlayers().values()) {
                    if(gp.getJob() == Job.Werewolf){
                        Werewolfs.add(gp);
                    }
                }
                for (GamePlayer gp : gameManager.getGame(GameID).getPlayers().values()) {
                    Player p = gp.getPlayer();
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,400,1));
                    ScoreboardUtils.createPersonalScoreboard(p,"あなたの役職:"+gp.getJob().getJobNameJapanese());

                    ArrayList<String> value = new ArrayList<>();
                    value.add("---参加者---");
                    for (UUID uuid : gameManager.getGame(GameID).getPlayers().keySet()) {
                        Player pl = plugin.getServer().getPlayer(uuid);
                        if(gp.getJob() == Job.Werewolf && Werewolfs.contains(pl)){
                            value.add(ChatColor.RED + pl.getName());
                        }else{
                            value.add(pl.getName());
                        }
                    }
                    // value.add(" ");
                    // value.add(PluginConfig.config.getString("japanese.jobsExp." + gameManager.getGame(GameID).getPlayers().get(p).getJob().getJobName()));

                    ScoreboardUtils.editPersonalScoreboard(p,value);

                    ScoreboardUtils.showPersonalScoreboard(p);
                }

                /*
                for(GamePlayer gp : Werewolfs){
                    Player p = gp.getPlayer();
                    ArrayList<String> value = new ArrayList<>();
                    value.add(" ");
                    value.add("人狼一覧:");
                    Werewolfs.forEach(gamePlayer -> {
                        value.add(gamePlayer.getPlayer().getName());
                    });
                    value.forEach(p::sendMessage);
                    ScoreboardUtils.editPersonalScoreboard(p,value);
                    ScoreboardUtils.showPersonalScoreboard(p);
                }
                */
            }
        }, (5 * 20));

        // 無敵時間
        BossBar invincibleTime = plugin.getServer().createBossBar("無敵時間", BarColor.RED, BarStyle.SOLID);
        for (UUID uuid : gameManager.getGame(GameID).getPlayers().keySet()) {
            Player p = plugin.getServer().getPlayer(uuid);
            invincibleTime.addPlayer(p);
        }
        Runnable TimeCount = new Runnable() {
            @Override
            public void run() {
                invincibleTime.setProgress((float) leftTime / 20);
                leftTime--;
            }
        };
        for (int i = 0; i < 20; i++) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, TimeCount, (5 + i) * 20);
            plugin.getLogger().info("" + (5 + i) * 20);
        }

        // 25秒後
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                invincibleTime.setProgress(0);
                invincibleTime.removeAll();
                player.getWorld().setPVP(true);
            }
        }, (25 * 20));
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                //処理
                // 狂人用 人狼の痕跡
                plugin.getLogger().info("人狼の痕跡配布");
                ItemStack TraceOfWerewolf;
                TraceOfWerewolf = ItemUtils.CreateItem(Material.COMPASS, 1); // ItemStack(Material.RABBIT_HIDE, 1);
                ItemUtils.setName(TraceOfWerewolf, "人狼の痕跡");
		if (gameManager.getGame(GameID) != null) {
		    for (GamePlayer GamePlayer_Betrayer : gameManager.getGame(GameID).getPlayers().values()) {
			if (GamePlayer_Betrayer.getJob().getJobName() == "Betrayer") {
			    Player Player_Betrayer = GamePlayer_Betrayer.getPlayer();
			    Player_Betrayer.getInventory().addItem(TraceOfWerewolf);
			}
		    }
                }
                /*
                LinkedHashMap<GamePlayer, Job> jobPlayerList = gameManager.getGame(GameID).getJobPlayerList();
                if (jobPlayerList.containsValue(new Job("Betrayer"))) {
                    plugin.getLogger().info("狂人がいます!");
                    jobPlayerList.forEach((gamePlayer, job) -> {
                        plugin.getLogger().info(gamePlayer.getPlayer().getDisplayName()+"を検査");
                        if (gamePlayer.getJob().getJobName() == "Betrayer") {
                            plugin.getLogger().info("こいつだ!");
                            gamePlayer.getPlayer().getInventory().addItem(TraceOfWerewolf);
                        }
                    });
                }

                 */
            }
        }, (95 * 20));
        return true;
    }
}
