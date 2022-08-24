package com.nana.mmoplugin.mmoplugin.Skill.Passive;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;
import com.nana.mmoplugin.mmoplugin.MmoSystem.DamageType;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.CuttingDamageEvent;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack.NormalDamageEvent;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import com.nana.mmoplugin.mmoplugin.Skill.Define.PassiveSkill;
import com.nana.mmoplugin.mmoplugin.Skill.Define.PassiveSkillType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class BrambleBody extends PassiveSkill {

    public BrambleBody(Mmoplugin plugin) {
        super(plugin);
        this.setType(PassiveSkillType.BRAMBLE_BODY);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void attackMeNormal(NormalDamageEvent event){

        LivingEntity Attacked = event.getAttacked();


        // 判断实体是否持有该被动
        if (!this.hasTrigger(Attacked)){
            return;
        }

        Double originDamage = event.getDamage();

        Damage damage = new Damage(Attacked, event.getAttacker(), 0.0,getPlugin());
        damage.setDamage(originDamage/3);
        damage.setDamageType(DamageType.CUTTING);
        damage.attack();

        if (Attacked.getType().equals(EntityType.PLAYER)) {
            Attacked.sendMessage(this.getType().getName() + "已触发");
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void attackMeCutting(CuttingDamageEvent event){

        LivingEntity Attacked = event.getAttacked();

        // 判断实体是否持有该被动
        if (!this.hasTrigger(Attacked)){
            return;
        }

        Double originDamage = event.getDamage();

        Damage damage = new Damage(Attacked, event.getAttacker(), 0.0,getPlugin());
        damage.setDamage(originDamage/3);
        damage.setDamageType(DamageType.CUTTING);
        damage.attack();

        if (Attacked.getType().equals(EntityType.PLAYER)) {
            Attacked.sendMessage(this.getType().getName() + "已触发");
        }

    }
}
