package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import org.bukkit.event.Listener;

public class MmoListener implements Listener {


    public MmoPlugin getPlugin() {
        MmoPlugin plugin = MmoPlugin.getInstance();
        return plugin;
    }


}
