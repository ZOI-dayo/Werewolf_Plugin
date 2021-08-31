package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.Objects;

public class jobSubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID){
      player.sendMessage("あなたの役職は " + gameManager.getGame(GameID).getPlayers().get(player.getUniqueId()).getJob().getJobNameJapanese() + " です");
      player.sendMessage(Objects.requireNonNull(PluginConfig.config.getString("japanese.jobsExp." + gameManager.getGame(GameID).getPlayers().get(player.getUniqueId()).getJob().getJobName())));
      return true;
  }
}
