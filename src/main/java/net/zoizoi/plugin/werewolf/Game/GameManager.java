package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {
    Main plugin;
    public boolean isHosted = false;

    public GameManager(Main Plugin) {
        plugin = Plugin;
    }

    private static List<Game> gameList = new ArrayList<Game>();
    // private Game game;

    public int AddGame() {
        // game = new Game();
        gameList.add(new Game(plugin));
        isHosted = true;
        return gameList.toArray().length - 1;
        // return 0;
    }

    public Game getGame(int ID) {
        if (0 < gameList.size()) {
            return gameList.get(ID);
        } else {
            return null;
        }
        // return game;
    }

    public void DeleteGame(int ID) {
        for (UUID uuid : gameList.get(ID).getPlayers().keySet()) {
            Player p = plugin.getServer().getPlayer(uuid);
            p.setPlayerListName(p.getName());
        }
        gameList.remove(ID);
        isHosted = false;
    }
}