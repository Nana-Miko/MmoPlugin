package com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Dodge;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DodgeEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private Boolean Cancelled = false;
    private Player player;
    @Override
    public boolean isCancelled() {
        return Cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.Cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    public static HandlerList getHandlerList(){
        return handlerList;
    }

    public DodgeEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
