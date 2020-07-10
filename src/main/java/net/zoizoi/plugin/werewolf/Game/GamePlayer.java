package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.entity.Player;

public class GamePlayer {
  Main plugin;
  private org.bukkit.entity.Player player;
  private boolean life = true;
  private Job job;
  private boolean isWorked = false;

  public GamePlayer(Main plugin, Player player, Boolean life, Job job) {
    this.plugin = plugin;
    this.player = player;
    this.life = life;
    this.job = job;
  }
  public GamePlayer(Main plugin,Player player){
    this.plugin = plugin;
    this.player = player;
    this.life = true;
    this.job = new Job(plugin,"None");
  }

  public org.bukkit.entity.Player getPlayer() {
    return this.player;
  }
  public void setLife(boolean life) {
    this.life = life;
  }
  public boolean getLife() {
    return this.life;
  }

  public void setJob(Job job) {
    this.job = job;
  }

  public Job getJob() {
    return this.job;
  }

  public boolean isWorked() {
    return isWorked;
  }
  public void setWorked(boolean isWorked) {
    this.isWorked = isWorked;
  }
}
