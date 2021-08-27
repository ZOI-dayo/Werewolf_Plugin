package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.*;

public class Game {
    Main plugin;
    private LinkedHashMap<UUID, GamePlayer> playerList = new LinkedHashMap<>();
    private LinkedHashMap<GamePlayer, Job> jobPlayerList = new LinkedHashMap<>();
    private String Result;
    private LinkedHashMap<UUID, GamePlayer> villagePlayerList = new LinkedHashMap<>();
    private LinkedHashMap<UUID, GamePlayer> wolfPlayerList = new LinkedHashMap<>();
    private LinkedHashMap<UUID, GamePlayer> betrayerPlayerList = new LinkedHashMap<>();

    public boolean isCreated = false;
    public boolean isReady = false;
    public boolean isRunning = false;
    public boolean isStopped = false;

    public Game(Main plugin) {
        this.plugin = plugin;
        Result = "";
        isCreated = true;
    }

    public boolean AddPlayer(Player player) {
        plugin.getLogger().info("Add Player : " + player.getName());
        if (playerList.containsKey(player.getUniqueId())) {
            // 既に含まれている場合
            return false;
        } else {
            /*
            for (Player p : playerList.keySet()) {
                p.sendMessage("ゲームに" + player.getName() + "さんが参加しました");
            }
             */
            GamePlayer gamePlayer = new GamePlayer(plugin, player.getUniqueId());
            playerList.put(player.getUniqueId(), gamePlayer);
            return true;
        }
    }

    public boolean DeletePlayer(Player player) {
        if (playerList.containsKey(player)) {
            playerList.remove(player);
            return true;
        } else {
            return false;
        }
    }


    public HashMap<UUID, GamePlayer> getPlayers() {
        return playerList;
    }
    public boolean isContains(UUID uuid){
        return playerList.containsKey(uuid);
    }

    public void Start(Main plugin) {
        // isRunning = true;

        List<UUID> shuffledPlayerList = new ArrayList<>(playerList.keySet());
        Collections.shuffle(shuffledPlayerList);

        for (int i = 0; i < shuffledPlayerList.size(); i++) {
            plugin.getLogger().info("" + shuffledPlayerList.size());
            plugin.getLogger().info("i = " + i);
            plugin.getLogger().info("Citizen = " + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen"));
            plugin.getLogger().info("Citizen = " + PluginConfig.config.getInt("member.2.Citizen"));

            Job job;

            if (i < PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")) {
                job = Job.Citizen;

                villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));

            } else if (i < PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
                    + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")) {
                job = Job.Prophet;
                villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
            } else if (i < PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
                    + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")
                    + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Necromancer")) {
                job = Job.Necromancer;
                villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
            } else if (i < PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
                    + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")
                    + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Necromancer")
                    + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Werewolf")) {
                job = Job.Werewolf;
                wolfPlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
            } else {
                job = Job.Betrayer;
                betrayerPlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
            }

            playerList.get(shuffledPlayerList.get(i)).setJob(job);
            plugin.getServer().getPlayer(shuffledPlayerList.get(i)).sendTitle("ゲームが開始されました", "あなたは" + job.getJobNameJapanese() + "です", 10, 50, 10);
            plugin.getServer().getPlayer(shuffledPlayerList.get(i)).sendMessage("ゲームが開始されました");
            plugin.getServer().getPlayer(shuffledPlayerList.get(i)).sendMessage("あなたは " + job.getJobNameJapanese() + " です");
            jobPlayerList.put(playerList.get(shuffledPlayerList.get(i)), job);
        }

    }

    public boolean PlayerDie(Player player) {
        GamePlayer gamePlayer = playerList.get(player.getUniqueId());

        gamePlayer.setLife(false);
        switch (gamePlayer.getJob().getJobName()) {
            case "Citizen":
            case "Prophet":
            case "Necromancer":
                villagePlayerList.remove(player.getUniqueId());
                break;
            case "Werewolf":
                wolfPlayerList.remove(player.getUniqueId());
                break;
            case "Betrayer":
                betrayerPlayerList.remove(player.getUniqueId());
                break;
            default:
                plugin.getLogger().info("130: Default");
                break;
        }
        if (villagePlayerList.size() <= wolfPlayerList.size()) {
            this.Result = "Wolf";
            return true;
        } else if (wolfPlayerList.size() == 0) {
            this.Result = "Village";
            return true;
        } else {
            return false;
        }
    }

    public void Stop() {
        isRunning = false;
        isStopped = true;
    }

    public String getResult() {
        return Result;
    }


    public LinkedHashMap<GamePlayer, Job> getJobPlayerList() {
        return jobPlayerList;
    }
}
