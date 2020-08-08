package net.zoizoi.plugin.werewolf.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUtils {
  public static ItemStack CreateItem(final Material material, final int amount){
    ItemStack itemStack = new ItemStack(material,amount);
    return itemStack;
  }
  public static ItemStack CreatePotion(final PotionEffectType potionEffectType, final int duration, final int amplifier, final int amount){
    ItemStack itemStack = new ItemStack(Material.POTION,amount);
    PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
    potionMeta.addCustomEffect(new PotionEffect(potionEffectType, duration, amplifier), true);
    itemStack.setItemMeta(potionMeta);
    return itemStack;
  }
  public static ItemStack CreateLingeringPotion(final PotionEffectType potionEffectType, final int duration, final int amplifier, final int amount){
    ItemStack itemStack = new ItemStack(Material.LINGERING_POTION,amount);
    PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
    potionMeta.addCustomEffect(new PotionEffect(potionEffectType, duration, amplifier), true);
    itemStack.setItemMeta(potionMeta);
    return itemStack;
  }
  public static ItemStack CreateSplashPotion(final PotionEffectType potionEffectType, final int duration, final int amplifier, final int amount){
    ItemStack itemStack = new ItemStack(Material.SPLASH_POTION,amount);
    PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
    potionMeta.addCustomEffect(new PotionEffect(potionEffectType, duration, amplifier), true);
    itemStack.setItemMeta(potionMeta);
    return itemStack;
  }

  public static ItemStack setName(ItemStack itemStack,String Name){
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setDisplayName(Name);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }
  public static ItemStack addEnchantment(ItemStack itemStack, Enchantment enchantment,int level){
    itemStack.addUnsafeEnchantment(enchantment,level);
    return itemStack;
  }
  public static ItemStack setDamage(ItemStack itemStack,int Damage){
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta instanceof Damageable) {
      ((Damageable) itemMeta).setDamage(Damage);
    }
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }
}
