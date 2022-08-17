package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class PassiveSkill extends BukkitRunnable implements Skill {
    private PassiveSkillType type;
    private Set<Entity> triggerEntity = new HashSet<>();
    private Mmoplugin plugin;

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
}
