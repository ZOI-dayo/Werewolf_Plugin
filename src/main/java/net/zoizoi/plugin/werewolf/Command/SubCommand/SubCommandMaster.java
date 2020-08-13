package net.zoizoi.plugin.werewolf.Command.SubCommand;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands.*;
import net.zoizoi.plugin.werewolf.Game.Game;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Game.GamePlayer;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.utils.TextUtils;
import net.zoizoi.plugin.werewolf.utils.WaiterMode;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class SubCommandMaster {
    public static Main plugin;
    public static GameManager gameManager;
    public static int GameID = 0;

    public SubCommandMaster(Main plugin) {
        this.plugin = plugin;
        gameManager = new GameManager(plugin);
    }

    public boolean OnCommand(Player player, Command command, String label, String[] args) {
        if (args[0].equals("host")) {

            hostSubCommand host = new hostSubCommand();
            return host.OnCommand(player, command, label, args, plugin, gameManager, GameID);

        } else if (args[0].equals("join")) {

            joinSubCommand join = new joinSubCommand();
            return join.OnCommand(player, command, label, args, plugin, gameManager, GameID);

        } else if (args[0].equals("cancel")) {

            cancelSubCommand cancel = new cancelSubCommand();
            return cancel.OnCommand(player, command, label, args, plugin, gameManager, GameID);

        } else if (args[0].equals("ready")) {

            readySubCommand ready = new readySubCommand();
            return ready.OnCommand(player, command, label, args, plugin, gameManager, GameID);

        } else if (args[0].equals("start")) {

            startSubCommand start = new startSubCommand();
            return start.OnCommand(player, command, label, args, plugin, gameManager, GameID);

        } else if (args[0].equals("job")) {

            jobSubCommand job = new jobSubCommand();
            return job.OnCommand(player, command, label, args, plugin, gameManager, GameID);

        } else if (args[0].equals("reset")) {

            resetSubCommand reset = new resetSubCommand();
            return reset.OnCommand(player, command, label, args, plugin, gameManager, GameID);

        } else if (args[0].equals("work")) {

            workSubCommand work = new workSubCommand();
            return work.OnCommand(player, command, label, args, plugin, gameManager, GameID);
        } else if (args[0].equals("reloadConfig")) {

            reloadConfigSubCommand reloadConfig = new reloadConfigSubCommand();
            return reloadConfig.OnCommand(player, plugin);
        } else if (args[0].equals("waiter")) {
            // Waiterテスト用
            /*
            WaiterMode.setWaiter(plugin, player.getUniqueId(), true);
            return true;
             */
        }
        return false;
    }

}
