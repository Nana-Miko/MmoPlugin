package com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CuttingDamageEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private Boolean Cancelled = false;
    private LivingEntity Attacker;
    private LivingEntity Attacked;
    private Double Damage;
    private Double Multiplier;

    public CuttingDamageEvent(LivingEntity attacker, LivingEntity attacked, Double damage,Double multiplier) {
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

    @Override
    public boolean isCancelled() {
        return Cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.Cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    public static HandlerList getHandlerList(){
        return handlerList;
    }




}
