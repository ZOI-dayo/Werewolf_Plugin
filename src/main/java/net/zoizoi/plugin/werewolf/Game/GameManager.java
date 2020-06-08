package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
  Main plugin;
  public GameManager(Main plugin) {
    this.plugin = plugin;
  }
  private static List<Game> gameList = new ArrayList<Game>();
  // private Game game;

  public int AddGame() {
    // game = new Game();
    gameList.add(new Game(plugin));
    return gameList.toArray().length - 1;
    // return 0;
  }

  public Game getGame(int ID) {
    return gameList.get(ID);
    // return game;
  }
}