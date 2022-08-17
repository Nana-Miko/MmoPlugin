package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Dodge.DodgeEvent;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;

public class SneakListener implements Listener {

    private Mmoplugin plugin;

    public void addSneakPlayer(UUID uid){SneakPlayerSet.add(uid);}
    public void removeSneakPlayer(UUID uid){SneakPlayerSet.remove(uid);}
    public void addSneakTime(UUID uid,Long time){SneakPlayerTimeMap.put(uid,time);}
    public void removeSneakTime(UUID uid){SneakPlayerTimeMap.remove(uid);}
    public Long getSneakTime(UUID uid){return SneakPlayerTimeMap.get(uid);}
    private Set<UUID> SneakPlayerSet = new HashSet<>();
    private Map<UUID,Long> SneakPlayerTimeMap = new HashMap<>();

    public Set<UUID> getSneakPlayerSet() {
        return SneakPlayerSet;
    }

    public SneakListener(Mmoplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void DealEvent(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if (!event.isSneaking()){
            this.removeSneakPlayer(player.getUniqueId());
            DodgeListener dodgeListener = (DodgeListener)plugin.getListener("DodgeListener");
            if (System.currentTimeMillis()-getSneakTime(player.getUniqueId())<200){

                removeSneakTime(player.getUniqueId());
                dodgeListener.addCanDodgeSet(player.getUniqueId());
                plugin.getServer().getPluginManager().callEvent(new DodgeEvent(player));
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
