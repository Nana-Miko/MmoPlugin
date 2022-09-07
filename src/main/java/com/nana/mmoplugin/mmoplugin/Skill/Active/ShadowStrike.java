package com.nana.mmoplugin.mmoplugin.Skill.Active;


import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;
import com.nana.mmoplugin.mmoplugin.Skill.Define.DamageSkill;
import com.nana.mmoplugin.mmoplugin.util.AsyncUtil;
import com.nana.mmoplugin.mmoplugin.util.vectorUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.List;

public class ShadowStrike extends DamageSkill {

    public ShadowStrike(MmoPlugin plugin) {
        super(plugin);
    }


    @Override
    public Boolean skillRunZeroStar() {

        this.setDamageMultipler(0.5);

        List<Entity> nearbyEntities = this.getPlayer().getNearbyEntities(10, 10, 10);
        if (nearbyEntities == null || nearbyEntities.isEmpty()) {
            return false;
        }

        Iterator<Entity> iterator = nearbyEntities.iterator();
        while (iterator.hasNext()) {
            try {
                LivingEntity x = (LivingEntity) iterator.next();
            } catch (ClassCastException e) {
                iterator.remove();
            }
        }
        if (nearbyEntities.isEmpty()){return false;}

        BukkitRunnable task0 = new BukkitRunnable() {
            @Override
            public void run() {
                move(nearbyEntities);
            }
        };

        task0.runTaskAsynchronously(this.getPlugin());

        return true;
    }


    private void move(List<Entity> nearbyEntities){

        Location originLocation = this.getPlayer().getLocation().clone();

        vectorUtil.createACircleWithVector(originLocation,10.0, Particle.FLAME,(long)10);

        for (Entity nearbyEntity :
                nearbyEntities) {
            LivingEntity livingEntity;
            try {
                livingEntity = (LivingEntity) nearbyEntity;
            }
            catch (ClassCastException e){continue;}

            Location location = livingEntity.getLocation().clone();
            float entityYaw = location.getYaw();
            Vector addsVector = location.toVector().subtract(new Vector(1,0,1));
            addsVector = vectorUtil.rotateVector(addsVector.clone(),entityYaw,0);
            location.add(addsVector);

            AsyncUtil.teleportAsync(getPlayer(),livingEntity,getPlugin());


            skillDamage(livingEntity);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        AsyncUtil.teleportAsync(getPlayer(),originLocation,getPlugin());


    }

    @Override
    protected void skillDamage(LivingEntity livingEntity) {
        Damage damage = new Damage(this.getPlayer(),livingEntity,this.getDamageMultipler(),this.getPlugin());
        damage.attack();
    }

}
