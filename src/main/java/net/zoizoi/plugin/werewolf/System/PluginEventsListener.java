package net.zoizoi.plugin.werewolf.System;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Game.GameJudge;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Game.GamePlayer;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class PluginEventsListener implements Listener {
  private Plugin plugin;

  public PluginEventsListener(Plugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent e) {
    GameJudge gameJudge = new GameJudge(plugin);
    gameJudge.onDeath(e);
  }
}
