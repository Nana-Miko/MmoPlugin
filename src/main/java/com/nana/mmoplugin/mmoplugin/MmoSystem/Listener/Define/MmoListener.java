package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import org.bukkit.event.Listener;

public class MmoListener implements Listener {
    private MmoPlugin plugin;

    public MmoPlugin getPlugin() {
        return plugin;
    }

    public MmoListener(MmoPlugin plugin) {
        this.plugin = plugin;
    }
}
