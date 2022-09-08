package com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Bolt;

import com.nana.mmoplugin.mmoplugin.Arms.CrossBow.Define.BoltActive;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.Damage;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.DamageType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Attack.AttackListener;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Listener.Define.MmoListenerType;
import com.nana.mmoplugin.mmoplugin.util.AsyncUtil;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class BreakArmour extends BoltActive {
    public BreakArmour(LivingEntity shooter, MmoPlugin plugin) {
        super(shooter, plugin);
    }

    @Override
    public void Active() {
        LivingEntity shooter = getShooter();

        Vector vector = shooter.getLocation().getDirection().normalize().multiply(0.5);
        Location location = shooter.getEyeLocation().add(vector);
        Vector eyeVector = shooter.getEyeLocation().getDirection();
        AbstractArrow abstractArrow = AsyncUtil.SpawnArrowAsync(shooter.getWorld(), location, vector.clone().normalize(), 2, 10, getPlugin());
        abstractArrow.setPierceLevel(2);
        abstractArrow.setVelocity(eyeVector.add(eyeVector.normalize().multiply(2)));
        AttackListener attackListener = (AttackListener) getPlugin().getListener(MmoListenerType.ATTACK);
        attackListener.addArrow(shooter, abstractArrow, 5.0);

        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                Long time = System.currentTimeMillis();
                Set<LivingEntity> livingEntitySet = new HashSet<>();
                livingEntitySet.add(shooter);
                while (true) {
                    if (System.currentTimeMillis() - time > 1000 * 10) {
                        break;
                    }
                    if (abstractArrow.isInBlock() || abstractArrow.isOnGround()) {
                        break;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //abstractArrow.playEffect(EntityEffect.ARROW_PARTICLES);
                    for (LivingEntity e :
                            AsyncUtil.getNearbyLivingEntitiesAsync(abstractArrow, 2, getPlugin())) {
                        if (livingEntitySet.contains(e)) {
                            continue;
                        }
                        Damage damage = new Damage(getShooter(), e, -0.5, getPlugin());
                        damage.setDamageType(DamageType.MAGIC);
                        damage.attack();
                        livingEntitySet.add(e);
                    }


                }
            }
        };

        bukkitRunnable.runTaskAsynchronously(getPlugin());

    }
}
