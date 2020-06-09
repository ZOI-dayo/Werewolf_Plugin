package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class resetSubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID) {
    gameManager.DeleteGame(GameID);
    player.sendMessage("ゲームを消去しました");
    if (gameManager.getGame(GameID) != null) {
      for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
        Location quitLobby = new Location(player.getWorld(),
          plugin.config.getDouble("Location.quitLobby.x"),
          plugin.config.getDouble("Location.quitLobby.y"),
          plugin.config.getDouble("Location.quitLobby.z"));
        p.teleport(quitLobby);
      }
    }
    return true;
  }
}
