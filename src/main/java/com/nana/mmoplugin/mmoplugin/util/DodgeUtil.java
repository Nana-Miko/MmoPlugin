package com.nana.mmoplugin.mmoplugin.util;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.DodgeListener;
import com.nana.mmoplugin.mmoplugin.util.Define.MmoUtil;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class DodgeUtil implements MmoUtil {

    public static Boolean IsDodgeIng(UUID uid, MmoPlugin plugin) {
        DodgeListener dodgeListener = (DodgeListener) plugin.getListener(MmoListenerType.DODGE);
        return dodgeListener.getDodgeIng().contains(uid);
    }

    public static void DodgeEffect(Entity entity) {
        Location location = entity.getLocation();
        World world = entity.getWorld();
        world.playEffect(location.add(0, 1, 0), Effect.SMOKE, 5);
        world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 3);
        //world.spawnParticle(Particle.SMOKE_LARGE,entity.getLocation().add(0,2,0),3);
    }
}
