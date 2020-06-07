package net.zoizoi.plugin.werewolf;

import net.zoizoi.plugin.werewolf.Command.CommandMaster;
import net.zoizoi.plugin.werewolf.Command.CommandTabComplete;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

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
}
