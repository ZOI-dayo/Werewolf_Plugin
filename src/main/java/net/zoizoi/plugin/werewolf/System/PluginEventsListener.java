package net.zoizoi.plugin.werewolf.System;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Game.GameJudge;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Game.GamePlayer;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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

    // 人狼の痕跡
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        // コンパスを持っているなら{}の中を実行する
        if (item.getType() == Material.COMPASS) {
            // そのウサギの皮の名前が"人狼の痕跡"なら{}の中を実行する
            if (item.getItemMeta().getDisplayName() == "人狼の痕跡") {
                // 右クリックのイベントの場合(左クリックなどを除く)
                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    // つまり人狼の痕跡を持ち、右クリックされたとき
                    GameManager gameManager = SubCommandMaster.gameManager;
                    for (GamePlayer gamePlayer : gameManager.getGame(SubCommandMaster.GameID).getPlayers().values()) {
                        if (gamePlayer.getJob().getJobName() == "WereWolf") {
                            /*
                            p.sendMessage(gamePlayer.getPlayer().getDisplayName() + "さんは人狼です!");
                            p.getInventory().setItemInMainHand(null);
                             */
                            p.setCompassTarget(gamePlayer.getPlayer().getLocation());
                            p.sendMessage("今人狼がいた場所をコンパスで示しました。");
                            break;
                        }
                    }
                }
            }
        }
    }
}
