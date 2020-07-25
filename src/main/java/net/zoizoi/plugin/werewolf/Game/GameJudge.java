package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class GameJudge{
  private Main plugin;
  private GameManager gameManager;
  private int GameID;

  public GameJudge(Main plugin) {
    this.plugin = plugin;
  }

  public void onDeath(PlayerDeathEvent e) {
    this.gameManager = SubCommandMaster.gameManager;
    this.GameID = SubCommandMaster.GameID;
    if (gameManager.getGame(GameID).isRunning) {
      plugin.getLogger().info("Stop");
      Player death = e.getEntity();
      if (gameManager.getGame(GameID).PlayerDie(death)) {
        gameManager.getGame(GameID).Stop();
        for (Player player : gameManager.getGame(GameID).getPlayers().keySet()) {
          player.getInventory().clear();
          player.setGameMode(GameMode.SPECTATOR);
          plugin.getLogger().info(plugin.config.getString("japanese.camp." + gameManager.getGame(GameID).getResult()));
          player.sendTitle(plugin.config.getString("japanese.camp." + gameManager.getGame(GameID).getResult()) + "の勝利", "", 10, 250, 10);
          player.sendMessage("+-----------+");
          player.sendMessage("| " + plugin.config.getString("japanese.camp." + gameManager.getGame(GameID).getResult()) + "の勝利 |");
          player.sendMessage("+-----------+");
          player.sendMessage("");
          player.sendMessage("今回の役職配分");
          player.sendMessage("");
          for (Player keyPlayer : gameManager.getGame(GameID).getPlayers().keySet()) {
            GamePlayer gamePlayer = gameManager.getGame(GameID).getPlayers().get(keyPlayer);
            player.sendMessage(keyPlayer.getName() + " : " + gamePlayer.getJob().getJobNameJapanese());
          }
          player.sendMessage("");
          // 花火を打ち上げる
          /*
          for (int i = 0; i < 10; i++) {
            Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            FireworkEffect.Builder effect = FireworkEffect.builder();
            effect.with(FireworkEffect.Type.BALL_LARGE);
            effect.withColor(Color.YELLOW);
            effect.withFade(Color.RED);
            effect.flicker(true);
            effect.trail(true);
            fireworkMeta.addEffect(effect.build());
            fireworkMeta.setPower(1);
            firework.setFireworkMeta(fireworkMeta);
          }
          //*/
        }
      } else {
        /*
        Location quitLobby = new Location(death.getWorld(),
          plugin.config.getDouble("Location.quitLobby.x"),
          plugin.config.getDouble("Location.quitLobby.y"),
          plugin.config.getDouble("Location.quitLobby.z"));
        death.teleport(quitLobby);
         */
        death.setGameMode(GameMode.SPECTATOR);
        death.sendMessage("あなたは死にました");
        death.sendMessage("DiscordのVCを切ってください");
      }
    }
  }
}

