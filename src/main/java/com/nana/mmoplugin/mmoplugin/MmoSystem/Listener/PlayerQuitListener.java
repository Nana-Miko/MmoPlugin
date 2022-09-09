package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.PlayerStorageListener;
import com.nana.mmoplugin.mmoplugin.util.Lock.ClassLock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends MmoListener {

    public PlayerQuitListener(MmoPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void DealEvent(PlayerQuitEvent event) {
        for (MmoListenerType mlt :
                MmoListenerType.values()) {
            MmoListener mmoListener = getPlugin().getListener(mlt);
            if (mmoListener instanceof PlayerStorageListener) {
                PlayerStorageListener playerStorageListener = (PlayerStorageListener) mmoListener;
                ClassLock lock = new ClassLock(playerStorageListener);
                try {
                    lock.getLock();
                } catch (ClassLock.LockException e) {
                    e.printStackTrace();
                    continue;
                }
                if (playerStorageListener.unregisterPlayer(event.getPlayer())) {
                    getPlugin().getLogger().info("已将 " + event.getPlayer().getName() + " 从监听器 " + mlt + " 中移除");
                }
                lock.release();
            }

        }
        if (getPlugin().getDamageScoreBoardManager().hasDamageScoreBoard(event.getPlayer())) {
            getPlugin().getDamageScoreBoardManager().removeDamageScoreBoard(event.getPlayer());
            getPlugin().getLogger().info("已将 " + event.getPlayer().getName() + " 从伤害面板管理器中移除");
        }
    }
}
