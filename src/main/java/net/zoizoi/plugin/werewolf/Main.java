package net.zoizoi.plugin.werewolf;

import net.zoizoi.plugin.werewolf.Command.CommandMaster;
import net.zoizoi.plugin.werewolf.Game.GameJudge;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Main extends JavaPlugin {
  public FileConfiguration config;

  @Override
  public void onEnable() {
    // Plugin startup logic
    // config.ymlが存在しない場合はファイルに出力します。
    saveDefaultConfig();
    // config.ymlを読み込みます。
    config = getConfig();

    getLogger().info("WereWolf Pluginが読み込まれました");
    getCommand("wolf").setExecutor(new CommandMaster(this));
    getCommand("wolf").setTabCompleter(this);
    getServer().getPluginManager().registerEvents(new GameJudge(this), this);
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
    if (args.length == 1) {
      List<String> commands = new ArrayList<>(Arrays.asList("host", "join", "cancel", "ready", "start", "job", "reset", "work"));
      List<String> Answer = new ArrayList<>();
      if (args[0].length() == 0) { // /wolfまで
        return commands;
      } else if (args[1].length() == 0) { // wolf XXX
        //入力されている文字列と先頭一致
        for (String S : commands) {
          if (S.startsWith(args[0])) {
            Answer.add(S);
          }
        }
        return Answer;
      } else if (args[2].length() == 0) { // wolf XXX YYY
        return super.onTabComplete(sender, command, alias, args);
      }
    }
    //JavaPlugin#onTabComplete()を呼び出す
    return super.onTabComplete(sender, command, alias, args);
  }
}

