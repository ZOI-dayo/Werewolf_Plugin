package net.zoizoi.plugin.werewolf;

import net.zoizoi.plugin.werewolf.Command.CommandMaster;
import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Game.Game;
import net.zoizoi.plugin.werewolf.Game.GameJudge;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

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
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (!command.getName().equalsIgnoreCase("wolf")) {
        return super.onTabComplete(sender, command, alias, args);
      } else {
        List<String> commands = new ArrayList<>(Arrays.asList("host", "join", "cancel", "ready", "start", "job", "reset", "work"));
        if (SubCommandMaster.gameManager.isHosted) {
          if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).isRunning) { // ゲーム中
            commands = new ArrayList<>(Arrays.asList("job", "reset", "work"));
          } else if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).isStopped) { // 終了後
            commands = new ArrayList<>(Arrays.asList("reset"));
          } else if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).isReady) { // Ready後、ゲーム前
            commands = new ArrayList<>(Arrays.asList("start", "reset"));
          } else if (SubCommandMaster.gameManager.isHosted) { // Host後、Ready前
            if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).getPlayers().containsKey(player)) {
              commands = new ArrayList<>(Arrays.asList("cancel", "ready", "reset"));
            } else {
              commands = new ArrayList<>(Arrays.asList("join", "ready", "reset"));
            }
          } else { // Host前
            commands = new ArrayList<>(Arrays.asList("host"));
          }
        } else {
          commands = new ArrayList<>(Arrays.asList("host"));
        }

        List<String> Answer = new ArrayList<>();
        getLogger().info("" + args.length);
        if (args.length == 1) {
          if (args[0].length() == 0) { // /wolfまで
            return commands;
          } else { // wolf XXX
            //入力されている文字列と先頭一致
            for (String S : commands) {
              if (S.startsWith(args[0])) {
                Answer.add(S);
              }
            }
            return Answer;
          }
        } else if (args.length == 2) { // wolf XXX YYY
          if (args[0].equals("work")) {
            getLogger().info("null!!!!!!!");
            return null;
          } else {
            return new ArrayList<>();
          }
        } else {
          return new ArrayList<>();
        }
      }
    } else {
      return super.onTabComplete(sender, command, alias, args);
    }
  }
}

//TODO #1 /wolf stop の追加
//TODO #2 /wolf reset の処理を改善する
//TODO #3 /wolf reset を常に補完に出す
//TODO #4 死んだ後の処理
//TODO -- #5 ロビーにいる間無敵にする
//TODO -- #6 占い、霊媒の回数制限
//TODO -- #7 開始直後無敵
//TODO - #8 ゲーム中の満腹度に関して
//TODO #9 館の保護
//TODO #10 バージョン管理
//TODO #11 README.mdの更新
//TODO #12 館のチェストに個人所有することの是非
//TODO -- #13 ReadyなしでStartできる
//TODO - 複数ゲーム時
