package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsRouse;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public abstract class PassiveSkill extends BukkitRunnable implements Skill, Listener {
    private PassiveSkillType type;
    private Set<Entity> triggerEntity = new HashSet<>();
    private MmoPlugin plugin;
    private ArmsRouse armsRouse;

    @Override
    public void run() {
        return;
    }

    public MmoPlugin getPlugin() {
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

    public Boolean skillRun(ArmsRouse armsRouse) {
        Boolean runFlag = false;
        switch (armsRouse) {
            case ZERO_STAR:
                runFlag = skillRunZeroStar();
                break;
            case ONE_STAR:
                runFlag = skillRunOneStar();
                break;
            case TWO_STAR:
                runFlag = skillRunTwoStar();
                break;
            case THREE_STAR:
                runFlag = skillRunThreeStar();
                break;
            case FOUR_STAR:
                runFlag = skillRunFourStar();
                break;
            case FIVE_STAR:
                runFlag = skillRunFiveStar();
                break;
        }

        return runFlag;
    }

    @Override
    public Boolean skillRunZeroStar() {
        return true;
    }

    @Override
    public Boolean skillRunOneStar() {
        return skillRunZeroStar();
    }

    @Override
    public Boolean skillRunTwoStar() {
        return skillRunZeroStar();
    }

    @Override
    public Boolean skillRunThreeStar() {
        return skillRunZeroStar();
    }

    @Override
    public Boolean skillRunFourStar() {
        return skillRunZeroStar();
    }

    @Override
    public Boolean skillRunFiveStar() {
        return skillRunZeroStar();
    }


    public PassiveSkill(MmoPlugin plugin) {
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
