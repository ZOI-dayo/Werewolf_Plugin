package net.zoizoi.plugin.werewolf.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

public class ScoreboardUtils {
    // private static Scoreboard scoreboard;
    private static LinkedHashMap<UUID, Scoreboard> scoreboards = new LinkedHashMap<>();

    public static void createPersonalScoreboard(Player player, String displayName) {
        Scoreboard scoreboard;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(player.getUniqueId().toString().substring(0, 16), "dummy", displayName);
        scoreboard.registerNewTeam(player.getUniqueId().toString().substring(0, 16));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        scoreboards.put(player.getUniqueId(), scoreboard);
    }

    public static void editPersonalScoreboard(Player player, ArrayList<String> value) {
        Scoreboard scoreboard = scoreboards.get(player.getUniqueId());
        Objective objective = scoreboard.getObjective(player.getUniqueId().toString().substring(0, 16));
        Team team = scoreboard.getTeam(player.getUniqueId().toString().substring(0, 16));
        for (String str : value) {
            if (!team.hasEntry(str)) {
                team.addEntry(str);
            }
        }
        for (int i = 0; i < value.size(); i++) {
            objective.getScore(value.get(i)).setScore(i * -1);
        }
        scoreboards.put(player.getUniqueId(), scoreboard);
    }

    public static void showPersonalScoreboard(Player player) {
        Scoreboard scoreboard = scoreboards.get(player.getUniqueId());
        player.setScoreboard(scoreboard);
    }

    public static void deletePersonalScoreboard(Player player) {
        Scoreboard scoreboard = scoreboards.get(player.getUniqueId());
        Object[] entries = scoreboard
                .getTeam(player
                        .getUniqueId()
                        .toString()
                        .substring(0, 16))
                .getEntries()
                .toArray();
        for (Object obj : entries) {
            scoreboard.resetScores((String) obj);
        }
        scoreboards.put(player.getUniqueId(), scoreboard);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}

