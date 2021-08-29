package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import net.zoizoi.plugin.werewolf.utils.ScoreboardUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

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
        for (UUID uuid : gameManager.getGame(GameID).getPlayers().keySet()) {
          Player player = plugin.getServer().getPlayer(uuid);
          player.setPlayerListName(player.getName() + "");

          ScoreboardUtils.deletePersonalScoreboard(player);
          player.getInventory().clear();

          player.setGameMode(GameMode.SPECTATOR);

          plugin.getLogger().info(PluginConfig.config.getString("japanese.camp." + gameManager.getGame(GameID).getResult()));
          player.sendTitle(PluginConfig.config.getString("japanese.camp." + gameManager.getGame(GameID).getResult()) + "の勝利", "", 10, 250, 10);
          player.sendMessage("+-----------+");
          player.sendMessage("| " + PluginConfig.config.getString("japanese.camp." + gameManager.getGame(GameID).getResult()) + "の勝利 |");
          player.sendMessage("+-----------+");
          player.sendMessage("");
          player.sendMessage("今回の役職配分");
          player.sendMessage("");
          for (UUID uuid1 : gameManager.getGame(GameID).getPlayers().keySet()) {
	      player.sendMessage(plugin.getServer().getPlayer(uuid).getName() + " : " + gameManager.getGame(GameID).getPlayers().get(uuid1).getJob().getJobNameJapanese());
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
        // WaiterMode.setWaiter(plugin, death.getUniqueId(), true);
        death.setGameMode(GameMode.SPECTATOR);
        death.sendMessage("あなたは死にました");
        death.sendMessage("DiscordのVCを切ってください");
      }
    }
  }
}
