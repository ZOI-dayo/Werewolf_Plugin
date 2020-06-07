package net.zoizoi.plugin.werewolf.Game;

public class Player {
  private org.bukkit.entity.Player player;
  private boolean life = true;
  private Job job;

  public Player(org.bukkit.entity.Player player, Boolean life, Job job) {
    this.player = player;
    this.life = life;
    this.job = job;
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
}
