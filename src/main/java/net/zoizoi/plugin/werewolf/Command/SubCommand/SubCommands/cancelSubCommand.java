package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import net.zoizoi.plugin.werewolf.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class cancelSubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, JavaPlugin plugin, GameManager gameManager, int GameID){
    if (!gameManager.getGame(GameID).isReady) {
      if (gameManager.getGame(GameID).DeletePlayer(player)) {
        player.sendMessage("人狼ゲームから離脱しました");
        TextUtils.sendHoverText(player, ChatColor.RED + "＞＞＞再参加する場合はこちら＜＜＜", "人狼ゲームに参加する", "/wolf join");
        player.setPlayerListName(player.getName());
        Location quitLobby = new Location(player.getWorld(),
          PluginConfig.config.getDouble("Location.quitLobby.x"),
          PluginConfig.config.getDouble("Location.quitLobby.y"),
          PluginConfig.config.getDouble("Location.quitLobby.z"));
        player.teleport(quitLobby);
      } else {
        player.sendMessage("ゲームに参加していません");
      }
    } else {
      player.sendMessage("ゲームが開始されているので離脱できません");
    }
    return true;
  }
}
