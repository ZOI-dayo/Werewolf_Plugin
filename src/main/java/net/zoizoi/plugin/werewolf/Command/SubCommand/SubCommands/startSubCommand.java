package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
      // 弓
      ItemStack SuperBow = new ItemStack(Material.BOW);
      SuperBow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE,10000);
      SuperBow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE,10000);
      ItemMeta SuperBowMeta = SuperBow.getItemMeta();
      SuperBowMeta.setDisplayName("一撃必殺弓");
      inventory.addItem(SuperBow);
      // 矢
      ItemStack Arrow = new ItemStack(Material.ARROW,16);
      inventory.addItem(Arrow);
      // ステーキ
      ItemStack Steak = new ItemStack(Material.COOKED_BEEF,32);
      inventory.addItem(Steak);
      // ランダムアイテム配布
      List<ItemStack> RundomItems = new ArrayList<ItemStack>();
      // ちくわ
      ItemStack Tikuwa = new ItemStack(Material.STICK);
      ItemMeta TikuwaMeta = Tikuwa.getItemMeta();
      TikuwaMeta.setDisplayName("ちくわ");
      RundomItems.add(Tikuwa);
      // 跳躍5ポーション
      ItemStack Potion_JUMP_5 = new ItemStack(Material.POTION);
      PotionMeta Potion_JUMP_5_Meta = (PotionMeta)Potion_JUMP_5.getItemMeta();
      Potion_JUMP_5_Meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP,10,5),true);
      RundomItems.add(Potion_JUMP_5);
      // 移動速度上昇5
      ItemStack Potion_SPEED_5 = new ItemStack(Material.POTION);
      PotionMeta Potion_SPEED_5_Meta = (PotionMeta)Potion_SPEED_5.getItemMeta();
      Potion_SPEED_5_Meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED,10,5),true);
      RundomItems.add(Potion_SPEED_5);
      // 一撃必殺ピッケル
      ItemStack SuperPickaxe = new ItemStack(Material.STONE_PICKAXE);
      SuperPickaxe.addEnchantment(Enchantment.DAMAGE_ALL,10000);
      ItemMeta SuperPickaxeMeta = SuperPickaxe.getItemMeta();
      SuperPickaxeMeta.setDisplayName("一撃必殺ピッケル");

      List<Player> shuffledPlayerList = new ArrayList<Player>(gameManager.getGame(GameID).getPlayers().keySet());
      Collections.shuffle(shuffledPlayerList);
      for (int i = 0; i < shuffledPlayerList.size(); i++) {
        shuffledPlayerList.get(i).getInventory().addItem();
      }
      //
      p.sendTitle("開始", "ゲームが開始されました", 10, 50, 10);
      p.teleport(gameStage);
    }
    return true;
  }
}
