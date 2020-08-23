package net.zoizoi.plugin.werewolf;

import net.zoizoi.plugin.werewolf.Command.CommandMaster;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import net.zoizoi.plugin.werewolf.System.PluginEventsListener;
import net.zoizoi.plugin.werewolf.System.PluginTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
  // public FileConfiguration config;

  @Override
  public void onEnable() {
    // Plugin 読み込み時の処理

    /*
    // config.ymlが存在しない場合はファイルに出力します。
    saveDefaultConfig();
    // config.ymlを読み込みます。
    config = getConfig();

     */
    PluginConfig.InitConfig(this);

    // コンソールに出力
    getLogger().info("WereWolf Pluginが読み込まれました");
    // wolfコマンドをどこで処理するかSpigotに教える この場合CommandMaster
    getCommand("wolf").setExecutor(new CommandMaster(this));
    // wolfコマンドのTabキーでの補完をどこで処理するかSpigotに教える この場合PluginTabCompleter
    getCommand("wolf").setTabCompleter(new PluginTabCompleter(this));
    //getCommand("wolf").setTabCompleter(this); // バックアップ
    // マイクラ内でのイベントをどこで処理するかSpigotに教える この場合GameJudge
    getServer().getPluginManager().registerEvents(new PluginEventsListener(this), this);
    // getServer().getPluginManager().registerEvents(new GameJudge(this), this);
  }

  @Override
  public void onDisable() {
    // Plugin 終了時の処理
    getLogger().info("プラグインが無効になったよ!");
  }
  // net.zoizoi.plugin.werewolf.System.PluginTabCompleterへ移転
  /*
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
        return super.onTabComplete(sender, command, alias, args);
      } else { // wolfコマンドの場合
        // (wolf の次に続く奴のメモ)
        List<String> commands = new ArrayList<>(Arrays.asList("host", "join", "cancel", "ready", "start", "job", "reset", "work"));
        // もう誰かが/wolf hostしてる場合
        if (SubCommandMaster.gameManager.isHosted) {
          if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).isRunning) { // ゲーム中の場合
            // 候補に job,reset,work を追加
            commands = new ArrayList<>(Arrays.asList("job", "reset", "work"));
          } else if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).isStopped) { // 終了後の場合
            // 候補に reset を追加
            commands = new ArrayList<>(Arrays.asList("reset"));
          } else if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).isReady) { // Ready後&ゲーム前 の場合
            // 候補に start,reset を追加
            commands = new ArrayList<>(Arrays.asList("start", "reset"));
          } else if (SubCommandMaster.gameManager.isHosted) { // Host後&Ready前 の場合
            // コマンドを打っている人がゲームに参加している場合
            if (SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).getPlayers().containsKey(player)) {
              // 候補に cancel,ready,reset を追加
              commands = new ArrayList<>(Arrays.asList("cancel", "ready", "reset"));
            } else { // コマンドを打っている人がゲームに参加していない場合
              // 候補に join,ready,reset を追加
              commands = new ArrayList<>(Arrays.asList("join", "ready", "reset"));
            }
          } else { // どれでもない場合(エラーになるかもしれないから一応置いてるけど多分いらない)
            // 候補に host を追加
            commands = new ArrayList<>(Arrays.asList("host"));
          }
        } else { // まだ誰も/wolf hostしていない場合
          // 候補に host を追加
          commands = new ArrayList<>(Arrays.asList("host"));
        }

        // 候補の中で、実際に表示するものを選別する
        // (コマンドの途中まで書いてる時別のコマンドが補完に出ないようにするため)

        // 実際に表示するものを入れる「リスト」 String型の変数をいっぱい入れられる
        List<String> Answer = new ArrayList<>();
        // デバッグ用、いらない
        getLogger().info("" + args.length);
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
            getLogger().info("null!!!!!!!");
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
      return super.onTabComplete(sender, command, alias, args);
    }
  }
  */
}