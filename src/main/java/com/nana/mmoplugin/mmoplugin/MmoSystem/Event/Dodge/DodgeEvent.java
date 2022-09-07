package com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Dodge;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Define.MmoEvent;
import org.bukkit.entity.Player;

public class DodgeEvent extends MmoEvent {
    private Player player;


    public DodgeEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
