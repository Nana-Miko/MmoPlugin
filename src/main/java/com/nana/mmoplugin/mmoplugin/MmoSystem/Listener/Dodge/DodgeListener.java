package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Dodge.DodgeEvent;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class DodgeListener extends MmoListener {
    private Set<UUID> canDodgeSet = new HashSet<>();
    private Map<UUID, Vector> SpeedMap = new HashMap<>();
    private Set<UUID> DodgeIng = new HashSet<>();

    public Set<UUID> getDodgeIng() {
        return DodgeIng;
    }

    public DodgeListener(MmoPlugin plugin) {
        super(plugin);
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
}
