package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class startSubCommand {
  public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID){
    gameManager.getGame(GameID).Start(plugin);
    // ステージへテレポート
    Location gameStage = new Location(player.getWorld(),
      plugin.config.getDouble("Location.gameStage.x"),
      plugin.config.getDouble("Location.gameStage.y"),
      plugin.config.getDouble("Location.gameStage.z"));
    // ゲームに参加しているプレイヤー全員に対して
    for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
      Inventory inventory = p.getInventory();
      inventory.clear();
      ItemStack SuperBow = new ItemStack(Material.BOW);
      SuperBow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE,10000);
      SuperBow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE,10000);
      ItemMeta SuperBowMeta = SuperBow.getItemMeta();
      SuperBowMeta.setDisplayName("一撃必殺弓");
      inventory.addItem(SuperBow);
      //
      ItemStack Arrow = new ItemStack(Material.ARROW,16);
      inventory.addItem(Arrow);
      //
      ItemStack Bread = new ItemStack(Material.BREAD,32);
      inventory.addItem(Bread);
      //
      p.sendTitle("開始", "ゲームが開始されました", 10, 50, 10);
      p.teleport(gameStage);
    }
    return true;
  }
}
