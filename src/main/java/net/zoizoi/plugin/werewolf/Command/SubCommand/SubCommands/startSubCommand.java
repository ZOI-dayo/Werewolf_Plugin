package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommandMaster;
import net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands.Start.StartItems;
import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Game.GamePlayer;
import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class startSubCommand {
  public int StartCountDownTime = 5;
  public int leftTime = 20;

  public boolean OnCommand(Player player, Command command, String label, String[] args, Plugin plugin, GameManager gameManager, int GameID) {
    // 0~4秒後
    Runnable StartCountDown = new Runnable() {
      @Override
      public void run() {
        for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
          p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
          p.sendMessage(StartCountDownTime + "秒前...");
          p.sendTitle(StartCountDownTime + "秒前", "", 10, 20, 10);
        }
        StartCountDownTime--;
      }
    };
    for (int i = 0; i < 5; i++) {
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, StartCountDown, (i * 20));
    }
    /*
    // 0秒後
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      public void run() {
        for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
          p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
          p.sendMessage("五秒前...");
          p.sendTitle("五秒前", "", 10, 20, 10);
        }
      }
    }, (0 * 20));
    // 1秒後
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      public void run() {
        for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
          p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
          p.sendMessage("四秒前...");
          p.sendTitle("四秒前", "", 10, 20, 10);
        }
      }
    }, (1 * 20));
    // 2秒後
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      public void run() {
        for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
          p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
          p.sendMessage("三秒前...");
          p.sendTitle("三秒前", "", 10, 20, 10);
        }
      }
    }, (2 * 20));
    // 3秒後
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      public void run() {
        for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
          p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
          p.sendMessage("二秒前...");
          p.sendTitle("二秒前", "", 10, 20, 10);
        }
      }
    }, (3 * 20));
    // 4秒後
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      public void run() {
        for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
          p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
          p.sendMessage("一秒前...");
          p.sendTitle("一秒前", "", 10, 20, 10);
        }
      }
    }, (4 * 20));
    */
    // 5秒後
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      public void run() {
        StartItems startItems = new StartItems();
        startItems.GiveItems(plugin, player, gameManager, GameID);
        // net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands.Start.StartItems へ移転
        /*
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
          SuperBow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 10000);
          SuperBow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10000);
          ItemMeta SuperBowMeta = SuperBow.getItemMeta();
          SuperBowMeta.setDisplayName("一撃必殺の弓");
          SuperBow.setItemMeta(SuperBowMeta); // Set Meta
          inventory.addItem(SuperBow);
          // 矢
          ItemStack Arrow = new ItemStack(Material.ARROW, 16);
          inventory.addItem(Arrow);
          // ステーキ
          ItemStack Steak = new ItemStack(Material.COOKED_BEEF, 32);
          inventory.addItem(Steak);
        }
        // ランダムアイテム配布
        List<ItemStack> RundomItems = new ArrayList<ItemStack>();
        // ちくわ
        ItemStack Tikuwa = new ItemStack(Material.STICK, 1);
        ItemMeta TikuwaMeta = Tikuwa.getItemMeta();
        TikuwaMeta.setDisplayName("ちくわ");
        Tikuwa.setItemMeta(TikuwaMeta); // Set Meta
        RundomItems.add(Tikuwa);
        // 跳躍5ポーション
        ItemStack Potion_JUMP_5 = new ItemStack(Material.POTION, 1);
        PotionMeta Potion_JUMP_5_Meta = (PotionMeta) Potion_JUMP_5.getItemMeta();
        Potion_JUMP_5_Meta.setDisplayName("跳躍力上昇のポーション");
        Potion_JUMP_5_Meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 600, 4), true);
        Potion_JUMP_5.setItemMeta(Potion_JUMP_5_Meta); // Set Meta
        RundomItems.add(Potion_JUMP_5);
        // 移動速度上昇5
        ItemStack Potion_SPEED_5 = new ItemStack(Material.POTION, 1);
        PotionMeta Potion_SPEED_5_Meta = (PotionMeta) Potion_SPEED_5.getItemMeta();
        Potion_SPEED_5_Meta.setDisplayName("移動速度上昇のポーション");
        Potion_SPEED_5_Meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 600, 4), true);
        Potion_SPEED_5.setItemMeta(Potion_SPEED_5_Meta); // Set Meta
        RundomItems.add(Potion_SPEED_5);
        // 一撃必殺ピッケル
        ItemStack SuperPickaxe = new ItemStack(Material.STONE_PICKAXE, 1);
        SuperPickaxe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10000);
        ItemMeta SuperPickaxeMeta = SuperPickaxe.getItemMeta();
        SuperPickaxeMeta.setDisplayName("一撃必殺のピッケル");
        if (SuperPickaxeMeta instanceof Damageable) {
          ((Damageable) SuperPickaxeMeta).setDamage(131);
        }
        SuperPickaxe.setItemMeta(SuperPickaxeMeta); // Set Meta
        RundomItems.add(SuperPickaxe);
        // 不死のトーテム
        ItemStack Totem = new ItemStack(Material.TOTEM_OF_UNDYING, 1);
        RundomItems.add(Totem);
        // 透明ポーション
        ItemStack Potion_Invisibility = new ItemStack(Material.POTION, 1);
        PotionMeta Potion_Invisibility_Meta = (PotionMeta) Potion_Invisibility.getItemMeta();
        Potion_Invisibility_Meta.setDisplayName("透明化のポーション");
        Potion_Invisibility_Meta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1), true);
        Potion_Invisibility.setItemMeta(Potion_Invisibility_Meta); // Set Meta
        RundomItems.add(Potion_Invisibility);
        // 一撃残留ポーション
        ItemStack Potion_Damage_Lingering = new ItemStack(Material.LINGERING_POTION, 1);
        PotionMeta Potion_Damage_Lingering_Meta = (PotionMeta) Potion_Damage_Lingering.getItemMeta();
        Potion_Damage_Lingering_Meta.setDisplayName("一撃必殺の残留ポーション");
        Potion_Damage_Lingering_Meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1), true);
        Potion_Damage_Lingering.setItemMeta(Potion_Damage_Lingering_Meta); // Set Meta
        RundomItems.add(Potion_Damage_Lingering);
        // すごいちくわ
        ItemStack SuperTikuwa = new ItemStack(Material.STICK, 1);
        SuperTikuwa.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
        ItemMeta SuperTikuwaMeta = SuperTikuwa.getItemMeta();
        SuperTikuwaMeta.setDisplayName("すごいちくわ");
        SuperTikuwa.setItemMeta(SuperTikuwaMeta); // Set Meta
        RundomItems.add(SuperTikuwa);
        // 盲目残留ポーション(x3)
        ItemStack Potion_Blindness_Lingering = new ItemStack(Material.LINGERING_POTION, 3);
        PotionMeta Potion_Blindness_Lingering_Meta = (PotionMeta) Potion_Blindness_Lingering.getItemMeta();
        Potion_Blindness_Lingering_Meta.setDisplayName("盲目の残留ポーション");
        Potion_Blindness_Lingering_Meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1), true);
        Potion_Blindness_Lingering.setItemMeta(Potion_Blindness_Lingering_Meta); // Set Meta
        RundomItems.add(Potion_Blindness_Lingering);
        //

        List<Player> shuffledPlayerList = new ArrayList<Player>(gameManager.getGame(GameID).getPlayers().keySet());
        Collections.shuffle(shuffledPlayerList);
        for (int i = 0; i < shuffledPlayerList.size(); i++) {
          shuffledPlayerList.get(i).getInventory().addItem(RundomItems.get(i));
          // player.sendMessage("i : " + i + " , " + shuffledPlayerList.get(i).getName() + "に" + RundomItems.get(i) + "を与えました");
        }
        //
        for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
          p.sendTitle("開始", "ゲームが開始されました", 10, 50, 10);
          p.teleport(gameStage);
          p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
          p.setHealth(20);
        }
        */
      }
      // }
    }, (5 * 20));

    BossBar invincibleTime = plugin.getServer().createBossBar("無敵時間", BarColor.RED, BarStyle.SOLID);
    for (Player p : gameManager.getGame(GameID).getPlayers().keySet()) {
      invincibleTime.addPlayer(p);
    }
    Runnable TimeCount = new Runnable() {
      @Override
      public void run() {
        invincibleTime.setProgress((float) leftTime / 20);
        leftTime--;
      }
    };
    for (int i = 0; i < 20; i++) {
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, TimeCount, (5 + i) * 20);
      plugin.getLogger().info("" + (5 + i) * 20);
    }

    // 25秒後
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      public void run() {
        invincibleTime.setProgress(0);
        invincibleTime.removeAll();
        player.getWorld().setPVP(true);
      }
    }, (25 * 20));
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      public void run() {
        //処理
        // 狂人用　人狼の痕跡
        ItemStack TraceOfWerewolf = new ItemStack(Material.RABBIT_HIDE, 1);
        for (GamePlayer GamePlayer_Betrayer : gameManager.getGame(GameID).getPlayers().values()) {
          if (GamePlayer_Betrayer.getJob().getJobName() == "Betrayer") {
            Player Player_Betrayer = GamePlayer_Betrayer.getPlayer();
            Player_Betrayer.getInventory().addItem(TraceOfWerewolf);
          }
        }

      }
    }, (95 * 20));
    return true;
  }
}
