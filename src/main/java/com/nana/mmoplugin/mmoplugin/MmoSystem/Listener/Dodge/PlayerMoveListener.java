package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge;


import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;


public class PlayerMoveListener implements Listener {
    private Mmoplugin plugin;



    public PlayerMoveListener(Mmoplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void DealEvent(PlayerMoveEvent event){

        Player player = event.getPlayer();

        SneakListener sneakListener = (SneakListener) plugin.getListener("SneakListener");
        if (!sneakListener.getSneakPlayerSet().contains(player.getUniqueId())){return;}

        DodgeListener dodgeListener = (DodgeListener) plugin.getListener("DodgeListener");
        if (dodgeListener.getDodgeIng().contains(player.getUniqueId())){return;}


        Location To = event.getTo().clone();
        Location From = event.getFrom().clone();

        Vector vector = To.clone().subtract(From).toVector();

        dodgeListener.addSpeed(player.getUniqueId(),vector);

    }
}
