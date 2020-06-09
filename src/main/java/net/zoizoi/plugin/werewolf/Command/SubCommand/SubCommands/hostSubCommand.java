package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.utls.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class hostSubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID) {
    GameID = gameManager.AddGame();
    player.sendMessage("" + GameID);
    player.sendMessage("人狼ゲームの募集を開始しました");
    World world = player.getWorld();
    for (Player p : world.getPlayers()) {
      if (p != player) {
        p.sendTitle("人狼ゲームに参加できます", "/wolf join で参加", 10, 50, 10);
        p.sendMessage("人狼ゲームの募集が開始されました");
        TextUtils.sendHoverText(p, ChatColor.GREEN + "＞＞＞このメッセージを押して参加＜＜＜", "人狼ゲームに参加する", "/wolf join");
      } else {
        p.performCommand("wolf join");
      }
    }
    return true;
  }
}
