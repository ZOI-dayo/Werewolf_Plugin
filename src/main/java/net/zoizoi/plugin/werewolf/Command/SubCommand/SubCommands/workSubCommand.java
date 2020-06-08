package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class workSubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID){
    if (args.length < 2) {
      player.sendMessage("オプションが不足しています");
      return false;
    } else {
      Player target = plugin.getServer().getPlayer(args[1]);
      gameManager.getGame(GameID).getPlayers().get(player).getJob().Work(gameManager.getGame(GameID).getPlayers().get(target));
    }
    return true;
  }
}
