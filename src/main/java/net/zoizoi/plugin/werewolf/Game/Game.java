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
  private List<GamePlayer> playerList = new ArrayList<GamePlayer>();
  private String Result;
  private List<GamePlayer> villagePlayerList = new ArrayList<GamePlayer>();
  private List<GamePlayer> wolfPlayerList = new ArrayList<GamePlayer>();
  private List<GamePlayer> betrayerPlayerList = new ArrayList<GamePlayer>();

  public boolean isReady = false;
  public boolean isRunning = false;

  public Game() {
    Result = "";
  }

  public void AddPlayer(Player player) {
    Job job = new Job("None");
    GamePlayer gamePlayer = new GamePlayer(player,true,job);
    playerList.add(gamePlayer);
  }
  public void DeletePlayer(org.bukkit.entity.Player player) {
    playerList.remove(player);
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
        playerList.get(i).setJob(new Job("Citizen"));
        villagePlayerList.add(playerList.get(i));
      } else if (i < plugin.config.getInt("member." + playerList.toArray().length + "Citizen")
        + plugin.config.getInt("member." + playerList.toArray().length + "Prophet")) {
        playerList.get(i).setJob(new Job("Prophet"));
        villagePlayerList.add(playerList.get(i));
      } else if (i < plugin.config.getInt("member." + playerList.toArray().length + "Citizen")
        + plugin.config.getInt("member." + playerList.toArray().length + "Prophet")
        + plugin.config.getInt("member." + playerList.toArray().length + "Necromancer")) {
        playerList.get(i).setJob(new Job("Necromancer"));
        villagePlayerList.add(playerList.get(i));
      } else if (i < plugin.config.getInt("member." + playerList.toArray().length + "Citizen")
        + plugin.config.getInt("member." + playerList.toArray().length + "Prophet")
        + plugin.config.getInt("member." + playerList.toArray().length + "Necromancer")
        + plugin.config.getInt("member." + playerList.toArray().length + "Werewolf")) {
        playerList.get(i).setJob(new Job("Werewolf"));
        wolfPlayerList.add(playerList.get(i));
      } else {
        playerList.get(i).setJob(new Job("Betrayer"));
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
    if(villagePlayerList.toArray().length == 0){
      this.Result = "Village";
      return true;
    }else if(wolfPlayerList.toArray().length == 0){
      this.Result = "Wolf";
      return true;
    }else{
      return false;
    }
  }
  public void Stop(){
    isRunning = false;
  }
}
