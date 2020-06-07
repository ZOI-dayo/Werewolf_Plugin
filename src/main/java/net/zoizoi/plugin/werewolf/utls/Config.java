package net.zoizoi.plugin.werewolf.utls;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
  private final Plugin plugin;
  private FileConfiguration config = null;

  public Config(Plugin plugin) {
    this.plugin = plugin;
    // ロードする
    load();
  }

  /**
   * 設定をロードします
   */
  public void load() {
    // 設定ファイルを保存
    plugin.saveDefaultConfig();
    if (config != null) { // configが非null == リロードで呼び出された
      plugin.reloadConfig();
    }
    config = plugin.getConfig();

    // TODO:ここに設定をロードする処理
  }
}
