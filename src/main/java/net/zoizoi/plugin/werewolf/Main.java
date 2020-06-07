package net.zoizoi.plugin.werewolf;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

  @Override
  public void onEnable() {
    // Plugin startup logic
    getLogger().info("WereWolf Pluginが読み込まれました");
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
