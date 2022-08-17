package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.Mmoplugin;
import org.bukkit.entity.Player;

public abstract class ActiveSkill implements Skill {
    private Mmoplugin plugin;
    private Boolean isRun = true;
    private Player player;
    private ActiveSkillType type;
    private int Used;

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
    }

}
