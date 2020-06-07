package net.zoizoi.plugin.werewolf.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandTabComplete extends JavaPlugin {
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
