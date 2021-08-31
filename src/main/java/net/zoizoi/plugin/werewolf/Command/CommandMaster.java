package net.zoizoi.plugin.werewolf.Command;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPlayerExact;
import static org.bukkit.Bukkit.getOnlinePlayers;

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
	Player player = null;
	if (sender instanceof BlockCommandSender) {
	    Location location = ((BlockCommandSender)sender).getBlock().getLocation().add(0.5, 1, 0.5);
	    double distance = Double.POSITIVE_INFINITY;
	    for (Player p : getOnlinePlayers()) {
		double d = p.getLocation().distanceSquared(location);
		if (d < distance) {
		    distance = d;
		    player = p;
		}
	    }
	} else if (sender instanceof Player) {
	    player = (Player) sender;
	} else {
	    getLogger().info("このコマンドはゲーム内から実行してください。");
	    return false;
	}

	if (player == null) {
	    getLogger().info("プレーヤーが特定できません");
	    return false;
	} else if (args.length < 1) {
	    player.sendMessage("オプションが不足しています");
	    return false;
	}
	return subCommandMaster.OnCommand(player, command, label, args);
    }
    return true;
  }
}