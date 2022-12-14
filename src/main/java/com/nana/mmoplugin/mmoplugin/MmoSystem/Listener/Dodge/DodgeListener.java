package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Dodge.DodgeEvent;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.PlayerStorageListener;
import com.nana.mmoplugin.mmoplugin.util.Lock.ClassLock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class DodgeListener extends MmoListener implements PlayerStorageListener {
    private ClassLock user = null;
    private Set<UUID> canDodgeSet = new HashSet<>();
    private Map<UUID, Vector> SpeedMap = new HashMap<>();
    private Set<UUID> DodgeIng = new HashSet<>();

    public Set<UUID> getDodgeIng() {
        return DodgeIng;
    }


    public void addSpeed(UUID uid, Vector speed) {

        SpeedMap.put(uid, speed);


    }

    public void addCanDodgeSet(UUID uid) {
        canDodgeSet.add(uid);
    }

    public void removeSpeed(UUID uid) {
        if (SpeedMap.containsKey(uid)) {
            SpeedMap.remove(uid);
        }
    }

    public Set<UUID> getCanDodgeSet() {
        return canDodgeSet;
    }

    @EventHandler
    public void DealEvent(DodgeEvent event){
        Player player = event.getPlayer();
        UUID uid = player.getUniqueId();
        if (!player.isOnGround()){return;}
        if (!SpeedMap.containsKey(uid) || !canDodgeSet.contains(uid)){return;}
        DodgeIng.add(uid);
        player.setVelocity(new Vector(0,0,0));
        player.setVelocity(SpeedMap.get(player.getUniqueId()).multiply(10));
        canDodgeSet.remove(uid);
        SpeedMap.remove(uid);


        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                DodgeIng.remove(uid);
            }
        };
        task.runTaskLaterAsynchronously(getPlugin(), 10);

    }

    @Override
    public Boolean unregisterPlayer(Player player) {
        UUID uid = player.getUniqueId();
        Boolean flag = false;
        if (canDodgeSet.contains(uid)) {
            canDodgeSet.remove(uid);
            flag = true;
        }
        if (SpeedMap.containsKey(uid)) {
            SpeedMap.remove(uid);
            flag = true;
        }
        if (DodgeIng.contains(uid)) {
            DodgeIng.remove(uid);
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
