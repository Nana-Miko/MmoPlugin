package com.nana.mmoplugin.mmoplugin.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class MmoComponent {
    private TextComponent textComponent;

    public MmoComponent(String text) {
        textComponent = new TextComponent(text);
        textComponent.setBold(true);
    }

    public void showText(Player player, ChatMessageType chatMessageType) {
        player.spigot().sendMessage(chatMessageType, textComponent);
    }
}
