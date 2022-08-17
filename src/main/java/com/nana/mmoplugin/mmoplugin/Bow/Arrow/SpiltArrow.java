package com.nana.mmoplugin.mmoplugin.Bow.Arrow;

import com.nana.mmoplugin.mmoplugin.Bow.Define.ArrowActive;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack.AttackListener;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.util.AsyncUtil;
import com.nana.mmoplugin.mmoplugin.util.vectorUtil;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class SpiltArrow extends ArrowActive {

    public SpiltArrow(LivingEntity shooter, Entity arrow, Mmoplugin plugin) {
        super(shooter, arrow, plugin);
    }

    @Override
    public void Track(LivingEntity shooter, Entity arrow) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (arrow.isDead() || arrow.isOnGround()){return;}
        Location location = arrow.getLocation();
        Vector vector = arrow.getVelocity();
        Vector eyeVector = shooter.getEyeLocation().getDirection().multiply(vector.length()*5);

        Arrow arrow1 = AsyncUtil.SpawnArrowAsync(arrow.getWorld(),location,vector.clone().normalize(),(float)vector.length(),10,getPlugin());
        Arrow arrow2 = AsyncUtil.SpawnArrowAsync(arrow.getWorld(),location,vector.clone().normalize(),(float)vector.length(),10,getPlugin());

        arrow.setVelocity(eyeVector);
        arrow1.setVelocity(vectorUtil.rotateAroundAxisY(eyeVector.clone(),7));
        arrow2.setVelocity(vectorUtil.rotateAroundAxisY(eyeVector.clone(),-7));
        AttackListener attackListener = (AttackListener) getPlugin().getListener("AttackListener");
        //添加伤害判定(速度的数值约为拉弓力度的1/3)
        attackListener.addArrow(shooter,arrow1,vector.length()/3);
        attackListener.addArrow(shooter,arrow2,vector.length()/3);

    }
}
