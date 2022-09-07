package com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge;


import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;


public class PlayerMoveListener extends MmoListener {


    public PlayerMoveListener(MmoPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void DealEvent(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        SneakListener sneakListener = (SneakListener) getPlugin().getListener(MmoListenerType.SNEAK);
        if (!sneakListener.getSneakPlayerSet().contains(player.getUniqueId())) {
            return;
        }

        DodgeListener dodgeListener = (DodgeListener) getPlugin().getListener(MmoListenerType.DODGE);
        if (dodgeListener.getDodgeIng().contains(player.getUniqueId())){return;}


        Location To = event.getTo().clone();
        Location From = event.getFrom().clone();

        Vector vector = To.clone().subtract(From).toVector();

        dodgeListener.addSpeed(player.getUniqueId(),vector);

    }
}
