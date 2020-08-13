package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.System.PluginConfig;

public class reloadConfigSubCommand {
    public boolean OnCommand() {
        PluginConfig.ReloadConfig();
        return true;
    }
}
