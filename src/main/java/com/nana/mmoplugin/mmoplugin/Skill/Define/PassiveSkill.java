package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsRouse;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public abstract class PassiveSkill extends BukkitRunnable implements Skill {
    private PassiveSkillType type;
    private Set<Entity> triggerEntity = new HashSet<>();
    private Mmoplugin plugin;
    private ArmsRouse armsRouse;

    @Override
    public void run() {
        return;
    }

    public Mmoplugin getPlugin() {
        return plugin;
    }

    public PassiveSkillType getType() {
        return type;
    }

    public void setType(PassiveSkillType type) {
        this.type = type;
    }

    public Boolean hasTrigger(Entity entity) {
        return this.triggerEntity.contains(entity);
    }

    public void putTrigger(Entity entity) {
        triggerEntity.add(entity);
    }

    public void removeTrigger(Player player) {
        triggerEntity.remove(player);
    }


    @Override
    public Boolean skillRun() {
        return null;
    }


    public PassiveSkill(Mmoplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setArmsRouse(ItemStack itemStack) {
        armsRouse = ArmsRouse.getArmsRouse(itemStack);
    }

    @Override
    public ArmsRouse getArmsRouse() {
        return armsRouse;
    }

    public ArmsRouse getArmsRouse(LivingEntity livingEntity) {
        return ArmsRouse.getArmsRouse(livingEntity.getEquipment().getItemInMainHand());
    }
}
