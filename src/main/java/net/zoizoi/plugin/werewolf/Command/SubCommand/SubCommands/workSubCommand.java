package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.UUID;

public class workSubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID) {
    if (args.length < 2) {
      player.sendMessage("オプションが不足しています");
      return false;
    }
    if (gameManager.getGame(GameID).getPlayers().get(player.getUniqueId()).isWorked()) {
	player.sendMessage("あなたは既に仕事を行っています");
    } else {
	Player target = plugin.getServer().getPlayer(args[1]);
	if (target == null) {
	    player.sendMessage("プレイヤー名が間違えています");
	} else {
	    player.sendMessage(gameManager.getGame(GameID).getPlayers().get(player.getUniqueId()).getJob().Work(gameManager.getGame(GameID).getPlayers().get(target.getUniqueId())));
	    gameManager.getGame(GameID).getPlayers().get(player.getUniqueId()).setWorked(true);
	}
    }
    return true;
  }
}
