package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
  Main plugin;
  private List<GamePlayer> playerList = new ArrayList<GamePlayer>();
  private String Result;
  private List<GamePlayer> villagePlayerList = new ArrayList<GamePlayer>();
  private List<GamePlayer> wolfPlayerList = new ArrayList<GamePlayer>();
  private List<GamePlayer> betrayerPlayerList = new ArrayList<GamePlayer>();

  public boolean isReady = false;
  public boolean isRunning = false;

  public Game(Main plugin) {
    this.plugin = plugin;
    Result = "";
  }

  public boolean AddPlayer(Player player) {
    Job job = new Job(plugin, "None");
    GamePlayer gamePlayer = new GamePlayer(plugin, player);
    if (playerList.contains(gamePlayer)) {
      return false;
    } else {
      playerList.add(gamePlayer);
      return true;
    }
  }

  public boolean DeletePlayer(Player player) {
    GamePlayer gamePlayer = new GamePlayer(plugin, player);
    if (playerList.contains(gamePlayer)) {
      playerList.remove(player);
      return true;
    } else {
      return false;
    }
  }


  public List<GamePlayer> getPlayers() {
    return playerList;
  }

  public void Start(Main plugin) {
    isRunning = true;
    Random random = new Random();
    int randomValue = random.nextInt(10);
    Collections.shuffle(playerList);
    for (int i = 0; i < playerList.toArray().length; i++) {
      if (i < plugin.config.getInt("member." + playerList.toArray().length + "Citizen")) {
        playerList.get(i).setJob(new Job(plugin, "Citizen"));
        playerList.get(i).getPlayer().sendTitle("ゲームが開始されました", "あなたは市民です", 10, 50, 10);
        playerList.get(i).getPlayer().sendMessage("ゲームが開始されました");
        playerList.get(i).getPlayer().sendMessage("あなたは 市民 です");
        villagePlayerList.add(playerList.get(i));
      } else if (i < plugin.config.getInt("member." + playerList.toArray().length + "Citizen")
        + plugin.config.getInt("member." + playerList.toArray().length + "Prophet")) {
        playerList.get(i).setJob(new Job(plugin, "Prophet"));
        playerList.get(i).getPlayer().sendTitle("ゲームが開始されました", "あなたは予言者です", 10, 50, 10);
        playerList.get(i).getPlayer().sendMessage("ゲームが開始されました");
        playerList.get(i).getPlayer().sendMessage("あなたは 予言者 です");
        villagePlayerList.add(playerList.get(i));
      } else if (i < plugin.config.getInt("member." + playerList.toArray().length + "Citizen")
        + plugin.config.getInt("member." + playerList.toArray().length + "Prophet")
        + plugin.config.getInt("member." + playerList.toArray().length + "Necromancer")) {
        playerList.get(i).setJob(new Job(plugin, "Necromancer"));
        playerList.get(i).getPlayer().sendTitle("ゲームが開始されました", "あなたは霊媒師です", 10, 50, 10);
        playerList.get(i).getPlayer().sendMessage("ゲームが開始されました");
        playerList.get(i).getPlayer().sendMessage("あなたは 霊媒師 です");
        villagePlayerList.add(playerList.get(i));
      } else if (i < plugin.config.getInt("member." + playerList.toArray().length + "Citizen")
        + plugin.config.getInt("member." + playerList.toArray().length + "Prophet")
        + plugin.config.getInt("member." + playerList.toArray().length + "Necromancer")
        + plugin.config.getInt("member." + playerList.toArray().length + "Werewolf")) {
        playerList.get(i).setJob(new Job(plugin, "Werewolf"));
        playerList.get(i).getPlayer().sendTitle("ゲームが開始されました", "あなたは人狼です", 10, 50, 10);
        playerList.get(i).getPlayer().sendMessage("ゲームが開始されました");
        playerList.get(i).getPlayer().sendMessage("あなたは 人狼 です");
        wolfPlayerList.add(playerList.get(i));
      } else {
        playerList.get(i).setJob(new Job(plugin, "Betrayer"));
        playerList.get(i).getPlayer().sendTitle("ゲームが開始されました", "あなたは狂人です", 10, 50, 10);
        playerList.get(i).getPlayer().sendMessage("ゲームが開始されました");
        playerList.get(i).getPlayer().sendMessage("あなたは 狂人 です");
        betrayerPlayerList.add(playerList.get(i));
      }

    }
  }

  public boolean PlayerDie(GamePlayer gamePlayer) {
    gamePlayer.setLife(false);
    switch (gamePlayer.getJob().getJobName()) {
      case "Citizen":
      case "Prophet":
      case "Necromancer":
        villagePlayerList.remove(gamePlayer);
        break;
      case "Werewolf":
        wolfPlayerList.remove(gamePlayer);
        break;
      case "Betrayer":
        betrayerPlayerList.remove(gamePlayer);
        break;
      default:
        break;
    }
    if (villagePlayerList.toArray().length == 0) {
      this.Result = "Village";
      return true;
    } else if (wolfPlayerList.toArray().length == 0) {
      this.Result = "Wolf";
      return true;
    } else {
      return false;
    }
  }

  public void Stop() {
    isRunning = false;
  }
}
