package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands.Start.StartItems;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Game.GamePlayer;
import net.zoizoi.plugin.werewolf.Game.Job;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.utils.ItemUtils;
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

import java.util.*;

public class startSubCommand {
    public int StartCountDownTime = 5;
    public int leftTime = 20;

    public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID) {
        // 0~4秒後
        Runnable StartCountDown = new Runnable() {
            @Override
            public void run() {
                for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
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
            }
        }, (5 * 20));

        // 無敵時間
        BossBar invincibleTime = plugin.getServer().createBossBar("無敵時間", BarColor.RED, BarStyle.SOLID);
        for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
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
                ItemStack TraceOfWerewolf;
                TraceOfWerewolf = ItemUtils.CreateItem(Material.RABBIT_HIDE, 1); // ItemStack(Material.RABBIT_HIDE, 1);
                ItemUtils.setName(TraceOfWerewolf, "人狼の痕跡");
                /*
                for (GamePlayer GamePlayer_Betrayer : gameManager.getGame(GameID).getPlayers().values()) {
                    if (GamePlayer_Betrayer.getJob().getJobName() == "Betrayer") {
                        Player Player_Betrayer = GamePlayer_Betrayer.getPlayer();
                        Player_Betrayer.getInventory().addItem(TraceOfWerewolf);
                    }
                }
                 */
                LinkedHashMap<GamePlayer, Job> jobPlayerList = gameManager.getGame(GameID).getJobPlayerList();
                if (jobPlayerList.containsValue(new Job("Betrayer"))) {
                    jobPlayerList.forEach((gamePlayer, job) -> {
                        if (gamePlayer.getJob().getJobName() == "Betrayer") {
                            gamePlayer.getPlayer().getInventory().addItem(TraceOfWerewolf);
                        }
                    });
                }
            }
        }, (95 * 20));
        return true;
    }
}
