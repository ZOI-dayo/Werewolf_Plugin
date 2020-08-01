package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class readySubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, JavaPlugin plugin, GameManager gameManager, int GameID){
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
    } else {
      player.sendMessage("ゲームはすでに準備されています");
    }
    return true;
  }
}
