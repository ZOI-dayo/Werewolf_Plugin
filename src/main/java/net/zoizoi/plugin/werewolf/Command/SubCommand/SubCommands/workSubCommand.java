package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class workSubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID) {
    if (args.length < 2) {
      player.sendMessage("オプションが不足しています");
      return false;
    } else {
      if (gameManager.getGame(GameID).getPlayers().get(player).isWorked()) {
        player.sendMessage("あなたは既に仕事を行っています");
      } else {
        Player target = plugin.getServer().getPlayer(args[1]);
        player.sendMessage(gameManager.getGame(GameID).getPlayers().get(player).getJob().Work(gameManager.getGame(GameID).getPlayers().get(target)));
        gameManager.getGame(GameID).getPlayers().get(player).setWorked(true);
      }
    }
    return true;
  }
}
