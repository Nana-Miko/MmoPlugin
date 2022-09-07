package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;
import com.nana.mmoplugin.mmoplugin.MmoSystem.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.HashSet;
import java.util.Set;

public abstract class DamageSkill extends ActiveSkill {
    private double damageMultipler;
    private DamageType damageType = DamageType.NORMAL;
    private Set<Entity> attackedEntity = new HashSet<>();

    public void addAttackedEntity(Entity entity){attackedEntity.add(entity);}
    public Boolean IsAttacked(Entity entity){return attackedEntity.contains(entity);}

    public DamageSkill(MmoPlugin plugin) {
        super(plugin);
    }


    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public double getDamageMultipler() {
        return damageMultipler;
    }

    public void setDamageMultipler(double damageMultipler) {
        this.damageMultipler = damageMultipler;
    }

    protected void skillDamage(LivingEntity livingEntity){
        Damage damage = new Damage(getPlayer(),livingEntity,getDamageMultipler(),getPlugin());
        damage.attack();
    }
}
