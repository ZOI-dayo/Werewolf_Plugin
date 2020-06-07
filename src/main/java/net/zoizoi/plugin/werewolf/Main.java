package net.zoizoi.plugin.werewolf;

import net.zoizoi.plugin.werewolf.Command.CommandMaster;
import net.zoizoi.plugin.werewolf.Command.CommandTabComplete;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Main extends JavaPlugin {

  @Override
  public void onEnable() {
    // Plugin startup logic
    getLogger().info("WereWolf Pluginが読み込まれました");
    getCommand("wolf").setExecutor(new CommandMaster());
    // getCommand("wolf").setTabCompleter(new CommandTabComplete());
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    getLogger().info("プラグインが無効になったよ!");
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    if (!command.getName().equalsIgnoreCase("wolf")) {
      return super.onTabComplete(sender, command, alias, args);
    }
    getLogger().info("a。");
    if (args.length == 1) {
      getLogger().info("b。");
      if (args[0].length() == 0) { // /wolfまで
        return Arrays.asList("host");
      } else {
        //入力されている文字列と先頭一致
        if ("host".startsWith(args[0])) {
          return Collections.singletonList("hoge");
        }
      }
      return new ArrayList<>();
    }
    //JavaPlugin#onTabComplete()を呼び出す
    return super.onTabComplete(sender, command, alias, args);
  }
}
