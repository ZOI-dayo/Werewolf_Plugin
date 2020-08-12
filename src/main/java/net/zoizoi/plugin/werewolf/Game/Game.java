package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.*;

public class Game {
    Main plugin;
    private LinkedHashMap<Player, GamePlayer> playerList = new LinkedHashMap<Player, GamePlayer>();
    private LinkedHashMap<GamePlayer, Job> jobPlayerList = new LinkedHashMap<>();
    private String Result;
    private LinkedHashMap<Player, GamePlayer> villagePlayerList = new LinkedHashMap<Player, GamePlayer>();
    private LinkedHashMap<Player, GamePlayer> wolfPlayerList = new LinkedHashMap<Player, GamePlayer>();
    private LinkedHashMap<Player, GamePlayer> betrayerPlayerList = new LinkedHashMap<Player, GamePlayer>();

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
        if (playerList.containsKey(player)) {
            // 既に含まれている場合
            return false;
        } else {
            for (Player p : playerList.keySet()) {
                p.sendMessage("ゲームに" + player.getName() + "さんが参加しました");
            }
            GamePlayer gamePlayer = new GamePlayer(plugin, player);
            playerList.put(player, gamePlayer);
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


    public HashMap<Player, GamePlayer> getPlayers() {
        return playerList;
    }

    public void Start(Main plugin) {
        isRunning = true;

        List<Player> shuffledPlayerList = new ArrayList<Player>(playerList.keySet());
        Collections.shuffle(shuffledPlayerList);

        for (int i = 0; i < shuffledPlayerList.size(); i++) {
            plugin.getLogger().info("" + shuffledPlayerList.size());
            plugin.getLogger().info("i = " + i);
            plugin.getLogger().info("Citizen = " + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen"));
            plugin.getLogger().info("Citizen = " + plugin.config.getInt("member.2.Citizen"));

            Job job;

            if (i < plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")) {
                job = new Job(plugin, "Citizen");

                villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));

            } else if (i < plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
                    + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")) {
                job = new Job(plugin, "Prophet");
                villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
            } else if (i < plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
                    + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")
                    + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Necromancer")) {
                job = new Job(plugin, "Necromancer");
                villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
            } else if (i < plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
                    + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")
                    + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Necromancer")
                    + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Werewolf")) {
                job = new Job(plugin, "Werewolf");
                wolfPlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
            } else {
                job = new Job(plugin, "Betrayer");
                betrayerPlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
            }

            playerList.get(shuffledPlayerList.get(i)).setJob(job);
            shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは" + job.getJobNameJapanese() + "です", 10, 50, 10);
            shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
            shuffledPlayerList.get(i).sendMessage("あなたは " + job.getJobNameJapanese() + " です");
            jobPlayerList.put(playerList.get(shuffledPlayerList.get(i)), job);
        }

    }

    public boolean PlayerDie(Player player) {
        GamePlayer gamePlayer = playerList.get(player);

        gamePlayer.setLife(false);
        switch (gamePlayer.getJob().getJobName()) {
            case "Citizen":
            case "Prophet":
            case "Necromancer":
                villagePlayerList.remove(player);
                break;
            case "Werewolf":
                wolfPlayerList.remove(player);
                break;
            case "Betrayer":
                betrayerPlayerList.remove(player);
                break;
            default:
                plugin.getLogger().info("130: Default");
                break;
        }
        if (villagePlayerList.size() == 0) {
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
}
