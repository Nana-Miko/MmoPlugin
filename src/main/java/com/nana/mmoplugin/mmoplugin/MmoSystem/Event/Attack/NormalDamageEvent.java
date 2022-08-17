package com.nana.mmoplugin.mmoplugin.MmoSystem.Event.Attack;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class NormalDamageEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();

    private Boolean Cancelled = false;
    private LivingEntity Attacker;
    private LivingEntity Attacked;
    private Double Damage;
    private Double Multiplier;

    public NormalDamageEvent(LivingEntity attacker, LivingEntity attacked, Double damage,Double multiplier) {
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
