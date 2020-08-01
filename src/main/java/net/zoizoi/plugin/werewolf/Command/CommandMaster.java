package net.zoizoi.plugin.werewolf.Command;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class CommandMaster implements CommandExecutor {
  Main plugin;
  SubCommandMaster subCommandMaster;
  public CommandMaster(Main plugin) {
    this.plugin = plugin;
    subCommandMaster = new SubCommandMaster(plugin);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("wolf")) {
      if (!(sender instanceof Player)) {
        getLogger().info("このコマンドはゲーム内から実行してください。");
        return false;
      } else {
        Player player = (Player) sender;
        if (args.length < 1) {
          player.sendMessage("オプションが不足しています");
          return false;
        } else {
          getLogger().info(args[0]);
          return subCommandMaster.OnCommand(player, command, label, args);
        }
      }
    }
    return true;
  }
}