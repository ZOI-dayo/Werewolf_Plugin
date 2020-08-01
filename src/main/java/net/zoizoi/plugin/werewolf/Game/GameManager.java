package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
  JavaPlugin plugin;
  public boolean isHosted = false;

  public GameManager(JavaPlugin plugin) {
    this.plugin = plugin;
  }

  private static List<Game> gameList = new ArrayList<Game>();
  // private Game game;

  public int AddGame() {
    // game = new Game();
    gameList.add(new Game(plugin));
    isHosted = true;
    return gameList.toArray().length - 1;
    // return 0;
  }

  public Game getGame(int ID) {
    if (0 < gameList.size()) {
      return gameList.get(ID);
    } else {
      return null;
    }
    // return game;
  }

  public void DeleteGame(int ID) {
    gameList.remove(ID);
    isHosted = false;
  }
}