package com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Define.MmoEvent;
import org.bukkit.entity.LivingEntity;

public class CuttingDamageEvent extends MmoEvent {
    private LivingEntity Attacker;
    private LivingEntity Attacked;
    private Double Damage;
    private Double Multiplier;

    public CuttingDamageEvent(LivingEntity attacker, LivingEntity attacked, Double damage, Double multiplier) {
        Attacker = attacker;
        Attacked = attacked;
        Damage = damage;
        Multiplier = multiplier;
    }

    public LivingEntity getAttacker() {
        return Attacker;
    }

    public LivingEntity getAttacked() {
        return Attacked;
    }

    public Double getDamage() {
        return Damage;
    }

    public Double getMultiplier() {
        return Multiplier;
    }





}
