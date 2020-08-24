package net.zoizoi.plugin.werewolf.System;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginConfig {
  public static FileConfiguration config;
  // private static JavaPlugin plugin;
  public static void InitConfig(JavaPlugin plugin) {
    // PluginConfig.plugin = plugin;
    // config.ymlが存在しない場合はファイルに出力します。
    plugin.saveDefaultConfig();
    // config.ymlを読み込みます。
    config = plugin.getConfig();
  }
  public static void ReloadConfig(JavaPlugin plugin){
    // config.ymlが存在しない場合はファイルに出力します。
    plugin.saveDefaultConfig();
    // config.ymlを読み込みます。
    config = plugin.getConfig();
  }
}
