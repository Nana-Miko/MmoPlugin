package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Dodge.DodgeEvent;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;

public class SneakListener extends MmoListener {


    public void addSneakPlayer(UUID uid) {
        SneakPlayerSet.add(uid);
    }

    public void removeSneakPlayer(UUID uid) {
        SneakPlayerSet.remove(uid);
    }

    public void addSneakTime(UUID uid, Long time) {
        SneakPlayerTimeMap.put(uid, time);
    }

    public void removeSneakTime(UUID uid) {
        SneakPlayerTimeMap.remove(uid);
    }

    public Long getSneakTime(UUID uid) {
        return SneakPlayerTimeMap.get(uid);
    }

    private Set<UUID> SneakPlayerSet = new HashSet<>();
    private Map<UUID, Long> SneakPlayerTimeMap = new HashMap<>();

    public Set<UUID> getSneakPlayerSet() {
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
            }
            else {dodgeListener.removeSpeed(player.getUniqueId());}

        }
        else{
            this.addSneakPlayer(player.getUniqueId());
            this.addSneakTime(player.getUniqueId(),System.currentTimeMillis());
        }
        return;

    }
}
