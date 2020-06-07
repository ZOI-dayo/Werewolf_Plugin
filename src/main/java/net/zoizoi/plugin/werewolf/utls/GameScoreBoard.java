package net.zoizoi.plugin.werewolf.utls;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class GameScoreBoard {
  private Scoreboard scoreboard;
  private Objective objective;
  public GameScoreBoard(Scoreboard scoreboard,DisplaySlot displaySlot){
    this.scoreboard = scoreboard;
    this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    objective = this.scoreboard.registerNewObjective("test", "dummy", "display");
    objective.setDisplaySlot(displaySlot);
  }
  public Scoreboard getScoreboard() {
    return scoreboard;
  }
  public Objective getObjective(){
    return objective;
  }
  public void SetScore(String Entry,int Score){
    objective.getScore(Entry).setScore(Score);
  }
}