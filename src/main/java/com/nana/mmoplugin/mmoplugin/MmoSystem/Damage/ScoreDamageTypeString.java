package com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.MmoAttributeType;
import org.bukkit.ChatColor;

public enum ScoreDamageTypeString implements MmoAttributeType {
    TOTAL_DAMAGE(ChatColor.GREEN, "总伤害"),
    DPS(ChatColor.LIGHT_PURPLE, "DPS"),
    LAST_DAMAGE(ChatColor.DARK_AQUA, "最后一次伤害"),
    SUCKED_BLEED(ChatColor.DARK_RED, "已吸取的生命值"),
    CRITICAL(ChatColor.YELLOW, "暴击增伤"),
    BLOCKED(ChatColor.GRAY, "被缓和的伤害"),
    NORMAL(ChatColor.WHITE, "普通伤害"),
    CUTTING(ChatColor.RED, "切割伤害"),
    MAGIC(ChatColor.BLUE, "魔法伤害"),
    ;
    private ChatColor color;
    private String name;

    ScoreDamageTypeString(ChatColor color, String name) {
        this.color = color;
        this.name = name;
    }

    @Override
    public String toString() {
        return color + name + ":";
    }

    @Override
    public String getTypeName() {
        return "伤害计分板条目";
    }
}
