package net.zoizoi.plugin.werewolf.System;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PluginTabCompleter implements TabCompleter {
  JavaPlugin plugin;
  public PluginTabCompleter(JavaPlugin plugin){
    this.plugin = plugin;
  }

  @Override
  // Tabキーでの補完処理
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    // プレイヤーがコマンドを打っている場合
    if (sender instanceof Player) {
      // 打っているプレイヤーを「player」で扱えるようにしておく
      Player player = (Player) sender;
      // wolfコマンド以外の場合
      if (!command.getName().equalsIgnoreCase("wolf")) {
        // 後の処理はSpigotに任せて処理を終わる
        return plugin.onTabComplete(sender, command, alias, args);
      } else { // wolfコマンドの場合
        // (wolf の次に続く奴のメモ)
        List<String> commands = new ArrayList<>(Arrays.asList("host", "join", "cancel", "ready", "start", "job", "reset", "work", "reloadConfig"));
        // もう誰かが/wolf hostしてる場合
        if (SubCommandMaster.gameManager.isHosted) {
          if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).isRunning) { // ゲーム中の場合
            // 候補に job,reset,work を追加
            commands = new ArrayList<>(Arrays.asList("job", "work"));
          } else if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).isStopped) { // 終了後の場合
            // 候補に reset を追加
            commands = new ArrayList<>(Arrays.asList("reset"));
          } else if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).isReady) { // Ready後&ゲーム前 の場合
            // 候補に start,reset を追加
            commands = new ArrayList<>(Arrays.asList("start"));
          } else if (SubCommandMaster.gameManager.isHosted) { // Host後&Ready前 の場合
            // コマンドを打っている人がゲームに参加している場合
            if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).getPlayers().containsKey(player.getUniqueId())) {
              // 候補に cancel,ready,reset を追加
              commands = new ArrayList<>(Arrays.asList("cancel", "ready"));
            } else { // コマンドを打っている人がゲームに参加していない場合
              // 候補に join,ready,reset を追加
              commands = new ArrayList<>(Arrays.asList("join", "ready"));
            }
          } else { // どれでもない場合(エラーになるかもしれないから一応置いてるけど多分いらない)
            // 候補に host を追加
            commands = new ArrayList<>(Arrays.asList("host"));
          }
        } else { // まだ誰も/wolf hostしていない場合
          // 候補に host と reloadConfig を追加
          commands = new ArrayList<>(Arrays.asList("host", "reloadConfig"));
        }

        // 候補の中で、実際に表示するものを選別する
        // (コマンドの途中まで書いてる時別のコマンドが補完に出ないようにするため)

        // 実際に表示するものを入れる「リスト」 String型の変数をいっぱい入れられる
        List<String> Answer = new ArrayList<>();
        // デバッグ用、いらない
        //plugin.getLogger().info("" + args.length);
        // /wolf の後、スペースがある時
        // つまり"/wolf "とか"/wolf h"とかの時。
        if (args.length == 1) {
          // "/wolf "のとき
          if (args[0].length() == 0) {
            // 候補全部出す
            return commands;
          } else { // "/wolf XXX"となっている時
            // 入力されている文字列と先頭一致 (例えば"/wolf h"と入れたらcancelは出ないけどhostは出る。前の方が一致してるやつを出してくれる)
            for (String S : commands) {
              if (S.startsWith(args[0])) {
                Answer.add(S);
              }
            }
            return Answer;
          }
        } else if (args.length == 2) { // "wolf XXX YYY"となっている時(/wolf の後に2つある)
          // XXXが"work"のとき
          if (args[0].equals("work")) {
            // デバッグ用、いらない
            //plugin.getLogger().info("null!!!!!!!");
            // Spigotに任せる。(プレイヤー名の一覧が出る)
            return null;
          } else { // XXXが"work"でない時
            // 空のリストを返す。(何も候補に出ない)
            return new ArrayList<>();
          }
        } else { // それ以外の、よくわからん時 (例えば"/wolf work 〇〇 aa"って打ったときはここに来る)
          // 空のリストを返す。(何も候補に出ない)
          return new ArrayList<>();
        }
      }
    } else { // wolfコマンド以外の時
      // 後の処理はSpigotに任せて処理を終わる
      return plugin.onTabComplete(sender, command, alias, args);
    }
  }
}
