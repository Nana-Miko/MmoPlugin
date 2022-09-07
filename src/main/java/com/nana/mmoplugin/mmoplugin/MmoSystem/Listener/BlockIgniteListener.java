package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockIgniteListener extends MmoListener {
    private MmoPlugin plugin;

    public BlockIgniteListener(MmoPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void DealEvent(BlockIgniteEvent event) {
        event.setCancelled(true);
    }
}
