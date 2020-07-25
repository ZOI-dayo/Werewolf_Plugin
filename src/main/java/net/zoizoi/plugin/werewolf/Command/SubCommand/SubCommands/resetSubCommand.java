package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class resetSubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, Plugin plugin, GameManager gameManager, int GameID) {
    gameManager.DeleteGame(GameID);
    player.sendMessage("ゲームを消去しました");
    if (gameManager.getGame(GameID) != null) {
      for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
        Location quitLobby = new Location(player.getWorld(),
          PluginConfig.config.getDouble("Location.quitLobby.x"),
          PluginConfig.config.getDouble("Location.quitLobby.y"),
          PluginConfig.config.getDouble("Location.quitLobby.z"));
        p.teleport(quitLobby);
      }
    }
    return true;
  }
}
