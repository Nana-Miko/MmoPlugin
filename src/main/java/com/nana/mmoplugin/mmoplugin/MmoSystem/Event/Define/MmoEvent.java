package com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Define;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MmoEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private Boolean Cancelled = false;

    public MmoEvent() {
    }

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

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
