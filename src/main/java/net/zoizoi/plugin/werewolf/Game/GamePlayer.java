package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {
    Main plugin;
    // private org.bukkit.entity.Player player;
    private UUID uuid;
    private boolean life = true;
    private Job job;
    private boolean isWorked = false;

    public GamePlayer(Main plugin, UUID uuid, Boolean life, Job job) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.life = life;
        this.job = job;
    }

    public GamePlayer(Main plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.life = true;
        this.job = Job.Citizen;
    }

    public org.bukkit.entity.Player getPlayer() {
        return plugin.getServer().getPlayer(uuid);
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
