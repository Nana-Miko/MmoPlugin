package com.nana.mmoplugin.mmoplugin.Arms.Staves.Magic;

import com.nana.mmoplugin.mmoplugin.Arms.Staves.Define.StaveActive;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage.Damage;
import com.nana.mmoplugin.mmoplugin.util.AsyncUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FireBall extends StaveActive {
    public FireBall(LivingEntity caster, MmoPlugin plugin) {
        super(caster, plugin);

    }

    @Override
    public void Active() {
        Vector vector = getCaster().getLocation().getDirection().normalize().multiply(1);
        Fireball fireball = (Fireball) AsyncUtil.SpawnEntityAsync(getCaster().getWorld(), getCaster().getEyeLocation().add(vector), EntityType.SMALL_FIREBALL, getPlugin());
        fireball.setDirection(vector);

        Set<LivingEntity> attackedSet = new HashSet();
        attackedSet.add(getCaster());
        int Count = 0;

        while (!fireball.isDead() && Count < 1000) {
            List<LivingEntity> livingEntityList = AsyncUtil.getNearbyLivingEntitiesAsync(fireball, 1, getPlugin());
            //System.out.println(livingEntityList);
            for (LivingEntity en :
                    livingEntityList) {
                if (attackedSet.contains(en)) {
                    continue;
                }
                Damage damage = new Damage(getCaster(), en, 0.0, getPlugin());
                damage.attack();
                attackedSet.add(en);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Count = Count + 10;
        }
        AsyncUtil.EntityRemoveAsync(fireball, getPlugin());

    }

}
