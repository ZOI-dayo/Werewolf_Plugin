package net.zoizoi.plugin.werewolf.utls;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

public class TextUtils {
  public static void sendHoverText(Player p, String text, String hoverText, String command){
    //////////////////////////////////////////
    //      ホバーテキストとイベントを作成する
    HoverEvent hoverEvent = null;
    if(hoverText != null){
      BaseComponent[] hover = new ComponentBuilder(hoverText).create();
      hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover);
    }

    //////////////////////////////////////////
    //   クリックイベントを作成する
    ClickEvent clickEvent = null;
    if(command != null){
      clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND,command);
    }

    BaseComponent[] message = new ComponentBuilder(text).event(hoverEvent).event(clickEvent). create();
    p.spigot().sendMessage(message);
  }
}
