package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import org.bukkit.entity.Player;

public class reloadConfigSubCommand {
    public boolean OnCommand(Player player,Main plugin) {
        PluginConfig.ReloadConfig();
        player.sendMessage("コンフィグを再読み込みしました");
        plugin.getLogger().info("WereWolf_Plugin : コンフィグを再読み込みしました");
        return true;
    }
}
