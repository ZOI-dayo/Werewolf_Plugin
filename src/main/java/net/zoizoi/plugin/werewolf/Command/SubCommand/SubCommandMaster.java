package net.zoizoi.plugin.werewolf.Command.SubCommand;

import net.zoizoi.plugin.werewolf.utls.TextUtils;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

import static org.bukkit.Bukkit.getLogger;

public class SubCommandMaster {
  public boolean OnCommand(Player player, Command command, String label, String[] args) {
    if (args[0].equals("host")) {
      player.sendMessage("人狼ゲームの募集を開始しました");
      World world = player.getWorld();
      for (Player p : world.getPlayers()) {
        if (p != player) {
          p.sendTitle("人狼ゲームに参加できます", "/wolf join で参加", 10, 25, 10);
          p.sendMessage("人狼ゲームの募集が開始されました");
          TextUtils.sendHoverText(p, "このメッセージを押して参加", "人狼ゲームに参加する", "/wolf join");
        }
      }
      return true;
    } else if (args[0].equals("join")) {
      return true;
    }

    getLogger().info("ehwuf");
    return false;
  }
}
