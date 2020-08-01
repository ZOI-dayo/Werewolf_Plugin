package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.*;

public class Game {
  JavaPlugin plugin;
  private LinkedHashMap<Player, GamePlayer> playerList = new LinkedHashMap<Player, GamePlayer>();
  private String Result;
  private LinkedHashMap<Player, GamePlayer> villagePlayerList = new LinkedHashMap<Player, GamePlayer>();
  private LinkedHashMap<Player, GamePlayer> wolfPlayerList = new LinkedHashMap<Player, GamePlayer>();
  private LinkedHashMap<Player, GamePlayer> betrayerPlayerList = new LinkedHashMap<Player, GamePlayer>();

  public boolean isCreated = false;
  public boolean isReady = false;
  public boolean isRunning = false;
  public boolean isStopped = false;

  public Game(JavaPlugin plugin) {
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

  public void Start(JavaPlugin plugin) {
    isRunning = true;

    List<Player> shuffledPlayerList = new ArrayList<Player>(playerList.keySet());
    Collections.shuffle(shuffledPlayerList);
    for (int i = 0; i < shuffledPlayerList.size(); i++) {
      plugin.getLogger().info("" + shuffledPlayerList.size());
      plugin.getLogger().info("i = " + i);
      plugin.getLogger().info("Citizen = " + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen"));
      plugin.getLogger().info("Citizen = " + PluginConfig.config.getInt("member.2.Citizen"));
      if (i < PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")) {
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Citizen"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは市民です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 市民 です");
        villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      } else if (i < PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
        + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")) {
        plugin.getLogger().info("Prophet");
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Prophet"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは予言者です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 予言者 です");
        villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      } else if (i < PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
        + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")
        + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Necromancer")) {
        plugin.getLogger().info("Necromancer");
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Necromancer"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは霊媒師です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 霊媒師 です");
        villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      } else if (i < PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
        + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")
        + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Necromancer")
        + PluginConfig.config.getInt("member." + shuffledPlayerList.size() + ".Werewolf")) {
        plugin.getLogger().info("Werewolf");
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Werewolf"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは人狼です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 人狼 です");
        wolfPlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      } else {
        plugin.getLogger().info("Betrayer");
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Betrayer"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは狂人です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 狂人 です");
        betrayerPlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      }
      for (Player p : playerList.keySet())
        plugin.getLogger().info("Name = " + p.getName());
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
    for (Player player : playerList.keySet()) {
      player.setPlayerListName(player.getName());
    }
  }

  public String getResult() {
    return Result;
  }
}
