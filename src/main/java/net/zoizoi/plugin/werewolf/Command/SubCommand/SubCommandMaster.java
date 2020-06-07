package net.zoizoi.plugin.werewolf.Command.SubCommand;

import net.zoizoi.plugin.werewolf.Game.Game;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.utls.TextUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.entity.*;

public class SubCommandMaster {
  Main plugin;
  GameManager gameManager = new GameManager();
  int GameID;

  public SubCommandMaster(Main plugin) {
    this.plugin = plugin;
  }

  public boolean OnCommand(Player player, Command command, String label, String[] args) {
    if (args[0].equals("host")) {
      GameID = gameManager.AddGame();
      player.sendMessage("人狼ゲームの募集を開始しました");
      World world = player.getWorld();
      for (Player p : world.getPlayers()) {
        if (p != player) {
          p.sendTitle("人狼ゲームに参加できます", "/wolf join で参加", 10, 50, 10);
          p.sendMessage("人狼ゲームの募集が開始されました");
          TextUtils.sendHoverText(p, ChatColor.RED + "＞＞＞このメッセージを押して参加＜＜＜", "人狼ゲームに参加する", "/wolf join");
        }
      }
      return true;
    } else if (args[0].equals("join")) {
      gameManager.getGame(GameID).AddPlayer(player);
      player.sendMessage("人狼ゲームに参加しました");
      TextUtils.sendHoverText(player, ChatColor.RED + "＞＞＞このメッセージを押してキャンセル＜＜＜", "人狼ゲームから離脱する", "/wolf cancel");
      player.sendMessage("ゲームの開始を待っています");
      player.setPlayerListName(ChatColor.RED + player.getName());
      player.sendTitle("ゲームに参加しました", "ゲームの開始を待っています", 10, 50, 10);
      player.setGameMode(GameMode.ADVENTURE);
      Location location = new Location(player.getWorld(),
        plugin.config.getDouble("Location.Lobby.x"),
        plugin.config.getDouble("Location.Lobby.y"),
        plugin.config.getDouble("Location.Lobby.z"));
      player.teleport(location);
      return true;
    } else if (args[0].equals("cancel")) {
      gameManager.getGame(GameID).DeletePlayer(player);
      player.sendMessage("人狼ゲームから離脱しました");
      TextUtils.sendHoverText(player, ChatColor.RED + "＞＞＞再参加する場合はこちら＜＜＜", "人狼ゲームに参加する", "/wolf join");
      player.setPlayerListName(player.getName());
      return true;
    }else if(args[0].equals("ready")) {
    }else if(args[0].equals("start")) {
    }else if(args[0].equals("job")) {
    }
    return false;
  }
}
