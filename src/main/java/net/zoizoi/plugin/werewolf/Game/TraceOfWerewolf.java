package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TraceOfWerewolf extends JavaPlugin implements Listener {
    //何らかのイベントがあった場合
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        // ウサギの皮を持っているなら{}の中を実行する
        if (p.getInventory().getItemInMainHand().getType() == Material.RABBIT_HIDE) {
            // そのウサギの皮の名前が"人狼の痕跡"なら{}の中を実行する
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == "人狼の痕跡")
                // 右クリックのイベントの場合(左クリックなどを除く)
                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    // つまり人狼の痕跡を持ち、右クリックされたとき
                    GameManager gameManager = SubCommandMaster.gameManager;
                    for(GamePlayer gamePlayer : gameManager.getGame(SubCommandMaster.GameID).getPlayers().values()){
                        if(gamePlayer.getJob().getJobName() == "WereWolf"){
                            p.sendMessage(gamePlayer.getPlayer().getDisplayName() + "さんは人狼です!");
                            break;
                        }
                }
        }
    }
        return;
}}
