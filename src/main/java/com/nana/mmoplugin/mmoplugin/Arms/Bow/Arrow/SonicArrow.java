package com.nana.mmoplugin.mmoplugin.Arms.Bow.Arrow;

import com.nana.mmoplugin.mmoplugin.Arms.Bow.Define.ArrowActive;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack.AttackListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class SonicArrow extends ArrowActive {

    public SonicArrow(LivingEntity shooter, Entity arrow, MmoPlugin plugin) {
        super(shooter, arrow, plugin);
    }

    @Override
    public void Track(LivingEntity shooter, Entity arrow) {

        Vector vector = arrow.getVelocity();
        AbstractArrow abstractArrow = (AbstractArrow) arrow;

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 箭矢无重力,速度置0
        arrow.setGravity(false);
        arrow.setVelocity(vector.clone().normalize().multiply(0.1));

        // 浮空2s
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        arrow.setGravity(true);
        abstractArrow.setPierceLevel(20);
        arrow.setVelocity(vector.clone().normalize().multiply(10));

        AttackListener attackListener = (AttackListener) getPlugin().getListener(MmoListenerType.ATTACK);
        attackListener.addArrow(shooter,arrow,2*vector.length());




    }
}
