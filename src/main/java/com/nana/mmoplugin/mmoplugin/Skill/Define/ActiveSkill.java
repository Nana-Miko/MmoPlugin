package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsRouse;
import com.nana.mmoplugin.mmoplugin.MmoPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ActiveSkill implements Skill {
    private MmoPlugin plugin;
    private Boolean isRun = true;
    private Player player;
    private ActiveSkillType type;
    private int Used;
    private ArmsRouse armsRouse;

    public int getUsed() {
        return Used;
    }

    public void setUsed(int used) {
        Used = used;
    }

    public Boolean getRun() {
        return isRun;
    }

    public void setRun(Boolean run) {
        isRun = run;
    }

    public ActiveSkillType getType() {
        return type;
    }

    public void setType(ActiveSkillType type) {
        this.type = type;
    }

    public MmoPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(MmoPlugin plugin) {
        this.plugin = plugin;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        setArmsRouse(getPlayer().getEquipment().getItemInMainHand());
    }

    public ActiveSkill(MmoPlugin plugin) {
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

    @Override
    public Boolean skillRun() {
        Boolean runFlag = false;
        switch (getArmsRouse()) {
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
}
