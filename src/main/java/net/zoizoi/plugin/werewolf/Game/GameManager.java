package net.zoizoi.plugin.werewolf.Game;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
  private List<Game> gameList = new ArrayList<Game>();
  public int AddGame(){
    gameList.add(new Game());
    return gameList.toArray().length - 1;
  }
  public Game getGame(int ID){
    return gameList.get(ID);
  }
}
