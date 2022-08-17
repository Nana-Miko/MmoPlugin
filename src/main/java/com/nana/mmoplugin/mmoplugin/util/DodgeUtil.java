package com.nana.mmoplugin.mmoplugin.util;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Dodge.DodgeListener;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.*;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class DodgeUtil {

    public static Boolean IsDodgeIng(UUID uid, Mmoplugin plugin){
        DodgeListener dodgeListener = (DodgeListener) plugin.getListener("DodgeListener");
        return dodgeListener.getDodgeIng().contains(uid);
    }
    public static void DodgeEffect(Entity entity){
        Location location = entity.getLocation();
        World world = entity.getWorld();
        world.playEffect(location.add(0,1,0), Effect.SMOKE,5);
        world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT,1,3);
        //world.spawnParticle(Particle.SMOKE_LARGE,entity.getLocation().add(0,2,0),3);
    }
}
