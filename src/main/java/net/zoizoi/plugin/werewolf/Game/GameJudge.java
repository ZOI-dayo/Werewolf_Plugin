package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameJudge implements Listener {
  private Main plugin;
  private GameManager gameManager;
  private int GameID;

  public GameJudge(Main plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent e) {
    this.gameManager = SubCommandMaster.gameManager;
    this.GameID = SubCommandMaster.GameID;
    plugin.getLogger().info("Stop");
    Player death = e.getEntity();
    if (gameManager.getGame(GameID).PlayerDie(death)) {
      gameManager.getGame(GameID).Stop();
      for (Player player : gameManager.getGame(GameID).getPlayers().keySet()) {
        player.setGameMode(GameMode.SPECTATOR);
        plugin.getLogger().info(plugin.config.getString("japanese.camp." + gameManager.getGame(GameID).getResult()));
        player.sendTitle(plugin.config.getString("japanese.camp." + gameManager.getGame(GameID).getResult()) + "の勝利", "", 10, 250, 10);
        player.sendMessage("+-----------+");
        player.sendMessage("| "+plugin.config.getString("japanese.camp." + gameManager.getGame(GameID).getResult()) + "の勝利 |");
        player.sendMessage("+-----------+");
      }
    } else {
      Location quitLobby = new Location(death.getWorld(),
        plugin.config.getDouble("Location.quitLobby.x"),
        plugin.config.getDouble("Location.quitLobby.y"),
        plugin.config.getDouble("Location.quitLobby.z"));
      death.teleport(quitLobby);
      death.sendMessage("あなたは死にました");
      death.sendMessage("DiscordのVCを切ってください");
    }
  }
}
