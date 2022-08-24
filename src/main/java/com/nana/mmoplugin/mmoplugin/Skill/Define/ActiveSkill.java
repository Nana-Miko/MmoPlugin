package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.Arms.Define.ArmsRouse;
import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ActiveSkill implements Skill {
    private Mmoplugin plugin;
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

    public Mmoplugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Mmoplugin plugin) {
        this.plugin = plugin;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ActiveSkill(Mmoplugin plugin) {
        this.plugin = plugin;
        setArmsRouse(getPlayer().getEquipment().getItemInMainHand());
    }

    @Override
    public void setArmsRouse(ItemStack itemStack) {
        armsRouse = ArmsRouse.getArmsRouse(itemStack);
    }

    @Override
    public ArmsRouse getArmsRouse() {
        return armsRouse;
    }
}
