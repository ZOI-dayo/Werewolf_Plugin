package net.zoizoi.plugin.werewolf.Command.SubCommand;

import net.zoizoi.plugin.werewolf.Game.Game;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Game.GamePlayer;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.utls.TextUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SubCommandMaster {
  public static Main plugin;
  public static GameManager gameManager = new GameManager(plugin);
  public static int GameID;

  public SubCommandMaster(Main plugin) {
    this.plugin = plugin;
  }

  public boolean OnCommand(Player player, Command command, String label, String[] args) {
    if (args[0].equals("host")) {
      GameID = gameManager.AddGame();
      player.sendMessage("" + GameID);
      player.sendMessage("人狼ゲームの募集を開始しました");
      World world = player.getWorld();
      for (Player p : world.getPlayers()) {
        if (p != player) {
          p.sendTitle("人狼ゲームに参加できます", "/wolf join で参加", 10, 50, 10);
          p.sendMessage("人狼ゲームの募集が開始されました");
          TextUtils.sendHoverText(p, ChatColor.RED + "＞＞＞このメッセージを押して参加＜＜＜", "人狼ゲームに参加する", "/wolf join");
        } else {
          p.performCommand("wolf join");
        }
      }
      return true;
    } else if (args[0].equals("join")) {
      if (!gameManager.getGame(GameID).isReady) {
        if (gameManager.getGame(GameID).AddPlayer(player)) {
          player.sendMessage("人狼ゲームに参加しました");
          TextUtils.sendHoverText(player, ChatColor.RED + "＞＞＞このメッセージを押してキャンセル＜＜＜", "人狼ゲームから離脱する", "/wolf cancel");
          player.sendMessage("ゲームの開始を待っています");
          player.setPlayerListName(ChatColor.RED + player.getName());
          player.sendTitle("ゲームに参加しました", "ゲームの開始を待っています", 10, 50, 10);
          player.setGameMode(GameMode.ADVENTURE);
          Location Lobby = new Location(player.getWorld(),
            plugin.config.getDouble("Location.Lobby.x"),
            plugin.config.getDouble("Location.Lobby.y"),
            plugin.config.getDouble("Location.Lobby.z"));
          player.teleport(Lobby);
          return true;
        } else {
          player.sendMessage("既に参加しています");
          return true;
        }
      } else {
        player.sendMessage("ゲームが開始されているので参加できません");
        return true;
      }
    } else if (args[0].equals("cancel")) {
      if (!gameManager.getGame(GameID).isReady) {
        if (gameManager.getGame(GameID).DeletePlayer(player)) {
          player.sendMessage("人狼ゲームから離脱しました");
          TextUtils.sendHoverText(player, ChatColor.RED + "＞＞＞再参加する場合はこちら＜＜＜", "人狼ゲームに参加する", "/wolf join");
          player.setPlayerListName(player.getName());
          Location quitLobby = new Location(player.getWorld(),
            plugin.config.getDouble("Location.quitLobby.x"),
            plugin.config.getDouble("Location.quitLobby.y"),
            plugin.config.getDouble("Location.quitLobby.z"));
          player.teleport(quitLobby);
          return true;
        } else {
          player.sendMessage("ゲームに参加していません");
        }
      } else {
        player.sendMessage("ゲームが開始されているので離脱できません");
      }
    } else if (args[0].equals("ready")) {
      if (!gameManager.getGame(GameID).isReady) {
        gameManager.getGame(GameID).isReady = true;
        World world = player.getWorld();
        for (Player p : world.getPlayers()) {
          if (!gameManager.getGame(GameID).getPlayers().containsKey(p)) {
            p.sendTitle("人狼ゲームの募集が締め切られました", "", 10, 50, 10);
            p.sendMessage("人狼ゲームの募集が締め切られました");
          } else {
            p.sendTitle("ゲームの準備をしています", "", 10, 50, 10);
            p.sendMessage("ゲームの準備をしています");
          }
        }
        return true;
      } else {
        player.sendMessage("ゲームはすでに準備されています");
        return true;
      }
    } else if (args[0].equals("start")) {
      gameManager.getGame(GameID).Start(plugin);
      Location gameStage = new Location(player.getWorld(),
        plugin.config.getDouble("Location.gameStage.x"),
        plugin.config.getDouble("Location.gameStage.y"),
        plugin.config.getDouble("Location.gameStage.z"));
      for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
        p.sendTitle("開始", "ゲームが開始されました", 10, 50, 10);
        p.teleport(gameStage);
      }
      return true;
    } else if (args[0].equals("job")) {
      for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
        if (p.equals(player)) {
          player.sendMessage("あなたの役職は " + gameManager.getGame(GameID).getPlayers().get(p).getJob().getJobNameJapanese() + " です");
        }
      }
      return true;
    } else if (args[0].equals("reset")) {
      gameManager.DeleteGame(GameID);
      player.sendMessage("ゲームを消去しました");
      return true;
    } else if (args[0].equals("work")) {
      if (args.length < 2) {
        player.sendMessage("オプションが不足しています");
        return false;
      } else {
        Player target = plugin.getServer().getPlayer(args[1]);
        gameManager.getGame(GameID).getPlayers().get(player).getJob().Work(gameManager.getGame(GameID).getPlayers().get(target));
      }
      return true;
    }
    return false;
  }

}
