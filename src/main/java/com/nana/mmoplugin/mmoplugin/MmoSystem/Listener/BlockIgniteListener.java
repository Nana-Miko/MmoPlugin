package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener;

import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockIgniteListener implements Listener {
    private Mmoplugin plugin;

    public BlockIgniteListener(Mmoplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void DealEvent(BlockIgniteEvent event) {
        event.setCancelled(true);
    }
}
