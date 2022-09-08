package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Dodge.DodgeEvent;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.PlayerStorageListener;
import com.nana.mmoplugin.mmoplugin.util.Lock.ClassLock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;

public class SneakListener extends MmoListener implements PlayerStorageListener {

    private ClassLock user = null;

    public void addSneakPlayer(UUID uid) {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return;
        }
        SneakPlayerSet.add(uid);
        lock.release();
    }

    public void removeSneakPlayer(UUID uid) {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return;
        }
        SneakPlayerSet.remove(uid);
        lock.release();
    }

    public void addSneakTime(UUID uid, Long time) {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return;
        }
        SneakPlayerTimeMap.put(uid, time);
        lock.release();
    }

    public void removeSneakTime(UUID uid) {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return;
        }
        SneakPlayerTimeMap.remove(uid);
        lock.release();
    }

    public Long getSneakTime(UUID uid) {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return null;
        }
        lock.release();
        return SneakPlayerTimeMap.get(uid);
    }

    private Set<UUID> SneakPlayerSet = new HashSet<>();
    private Map<UUID, Long> SneakPlayerTimeMap = new HashMap<>();

    public Set<UUID> getSneakPlayerSet() {
        ClassLock lock = new ClassLock(this);
        try {
            lock.getLock();
        } catch (ClassLock.LockException e) {
            e.printStackTrace();
            return null;
        }
        lock.release();
        return SneakPlayerSet;
    }

    public SneakListener(MmoPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void DealEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!event.isSneaking()) {
            this.removeSneakPlayer(player.getUniqueId());
            DodgeListener dodgeListener = (DodgeListener) getPlugin().getListener(MmoListenerType.DODGE);
            if (System.currentTimeMillis() - getSneakTime(player.getUniqueId()) < 200) {

                removeSneakTime(player.getUniqueId());
                dodgeListener.addCanDodgeSet(player.getUniqueId());
                getPlugin().getServer().getPluginManager().callEvent(new DodgeEvent(player));
            } else {
                dodgeListener.removeSpeed(player.getUniqueId());
            }

        } else {
            this.addSneakPlayer(player.getUniqueId());
            this.addSneakTime(player.getUniqueId(), System.currentTimeMillis());
        }
        return;

    }

    @Override
    public Boolean unregisterPlayer(Player player) {
        UUID uid = player.getUniqueId();
        Boolean flag = false;
        if (SneakPlayerSet.contains(uid)) {
            SneakPlayerSet.remove(uid);
            flag = true;
        }
        if (SneakPlayerTimeMap.containsKey(uid)) {
            SneakPlayerTimeMap.remove(uid);
            flag = true;
        }
        return flag;
    }

    @Override
    public void setUser(ClassLock locker) {
        user = locker;
    }

    @Override
    public ClassLock getUser() {
        return user;
    }
}
