package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define;

import com.nana.mmoplugin.mmoplugin.util.Lock.CanLock;
import org.bukkit.entity.Player;

public interface PlayerStorageListener extends CanLock {
    Boolean unregisterPlayer(Player player);
}
