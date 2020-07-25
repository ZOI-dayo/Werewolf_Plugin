package net.zoizoi.plugin.werewolf.System;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class PluginConfig {
  public static FileConfiguration config;
  private static Plugin plugin;
  public PluginConfig(Plugin plugin) {
    this.plugin = plugin;
    // config.ymlが存在しない場合はファイルに出力します。
    plugin.saveDefaultConfig();
    // config.ymlを読み込みます。
    config = plugin.getConfig();
  }
  public static void ReloadConfig(){
    // config.ymlが存在しない場合はファイルに出力します。
    plugin.saveDefaultConfig();
    // config.ymlを読み込みます。
    config = plugin.getConfig();
  }
}
