package com.nana.mmoplugin.mmoplugin.Arms.Bow.Arrow;

import com.nana.mmoplugin.mmoplugin.Arms.Bow.Define.ArrowActive;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.util.AsyncUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class TrackingArrow extends ArrowActive {


    public TrackingArrow(LivingEntity shooter, Entity arrow, MmoPlugin plugin) {
        super(shooter, arrow, plugin);
    }

    @Override
    public void Track(LivingEntity shooter,Entity arrow){


        while (!arrow.isOnGround()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            List<Entity> entityList = AsyncUtil.getNearbyEntitiesAsync(arrow,5,getPlugin());
            if (entityList==null || entityList.isEmpty()){continue;}

            Boolean TrackFlag = false;

            for (Entity en:
                    entityList) {
                try {
                    if (en.equals(shooter)){continue;}
                    LivingEntity entity = (LivingEntity) en;
                    arrow.setVelocity(entity.getEyeLocation().toVector().subtract(arrow.getLocation().toVector()).normalize().multiply(arrow.getVelocity().length()));
                    TrackFlag = true;
                    break;
                }
                catch (ClassCastException e){ continue;}
            }
            if (TrackFlag==true){break;}
        }
    }
}
