package net.zoizoi.plugin.werewolf.System;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Game.GameJudge;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Game.GamePlayer;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.utils.WaiterMode;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.Plugin;

public class PluginEventsListener implements Listener {
    private Main plugin;

    public PluginEventsListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        GameJudge gameJudge = new GameJudge(plugin);
        gameJudge.onDeath(e);
    }

    @EventHandler
    public void FoodLevelChangeEvent(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            if(SubCommandMaster.gameManager.getGame(SubCommandMaster.GameID).getPlayers().containsKey(player)){
                e.setCancelled(true);
            }
        }
    }

    // Waiter
    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (WaiterMode.isWaiter(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    // Waiter
    @EventHandler
    public void EntityInteractEvent(EntityInteractEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (WaiterMode.isWaiter(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    // Waiter
    @EventHandler
    public void EntityPickupItemEvent(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (WaiterMode.isWaiter(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    // Waiter
    @EventHandler
    public void EntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() instanceof Player) {
            Player player = (Player) e.getTarget();
            if (WaiterMode.isWaiter(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    // Waiter
    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (WaiterMode.isWaiter(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }
}
