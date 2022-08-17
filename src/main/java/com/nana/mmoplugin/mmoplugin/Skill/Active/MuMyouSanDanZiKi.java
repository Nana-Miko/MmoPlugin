package com.nana.mmoplugin.mmoplugin.Skill.Active;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.Skill.Define.DamageSkill;
import com.nana.mmoplugin.mmoplugin.util.AsyncUtil;
import com.nana.mmoplugin.mmoplugin.util.vectorUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MuMyouSanDanZiKi extends DamageSkill {
    public MuMyouSanDanZiKi(Mmoplugin plugin) {
        super(plugin);
    }

    @Override
    public Boolean skillRun() {
        switch (this.getUsed()){
            case 1:{this.setDamageMultipler(0.5);break;}
            case 2:{this.setDamageMultipler(0.7);break;}
            case 3:{this.setDamageMultipler(1.0);break;}
        }



        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                move();
            }
        };
        task.runTaskAsynchronously(getPlugin());

        if (this.getUsed()>=3){return true;}
        return false;
    }

    private void move(){
        Player player = getPlayer();


        Location location = player.getLocation().clone();
        Vector Direction = location.getDirection();
        Vector DirectionC = Direction.clone().normalize();
        Vector speed = Direction.add(DirectionC.multiply(2));



        player.setVelocity(speed);

        Set<LivingEntity> entitySet = new HashSet<>();

        long time = System.currentTimeMillis()/1000;
        while (true){
            if (System.currentTimeMillis()/1000-time>0.5){
                player.setVelocity(speed.zero());
                break;
            }
            List<LivingEntity> livingEntityList = AsyncUtil.getNearbyLivingEntitiesAsync(player,1,getPlugin());

            for (LivingEntity en :
                    livingEntityList) {
                if (entitySet.contains(en)){continue;}
                skillDamage(en);
            }

            entitySet.addAll(livingEntityList);


            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }






    }

}
