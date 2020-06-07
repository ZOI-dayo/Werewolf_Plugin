package net.zoizoi.plugin.werewolf.Game;

public class Player {
  private org.bukkit.entity.Player player;
  private boolean life;
  private Job job;

  public Player(int ID, org.bukkit.entity.Player player, Boolean life, Job job) {
    this.player = player;
    this.life = life;
    this.job = job;
  }

  public org.bukkit.entity.Player getPlayer() {
    return this.player;
  }
  public boolean getLife() {
    return this.life;
  }
  public Job getJob() {
    return this.job;
  }
}
