package net.zoizoi.plugin.werewolf.utils;

import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedHashMap;
import java.util.UUID;

public class WaiterMode {
    // private Main plugin;
    private static LinkedHashMap<UUID, Boolean> PlayerList = new LinkedHashMap<>();
/*
    public WaiterMode(Main Plugin) {
        plugin = Plugin;
    }

 */

    public static boolean isWaiter(UUID uuid) {
        if (!PlayerList.keySet().contains(uuid)) {
            return false;
        }
        return PlayerList.get(uuid);
    }

    public static void setWaiter(Main plugin, UUID uuid, boolean value) {
        if (!PlayerList.keySet().contains(uuid)) {
            PlayerList.put(uuid, value);
        } else {
            PlayerList.replace(uuid, value);
        }

        Player player = plugin.getServer().getPlayer(uuid);
        if (value) {
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,2147483647 ,1));
        }
    }
}
