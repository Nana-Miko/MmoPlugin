package com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DamageScoreBoard {
    private Map<Player, DamageScore> openPlayerScoreBoard = new HashMap<>();

    // 注册侧边计分板
    public void addDamageScoreBoard(Player player) {
        Scoreboard scoreboard = player.getServer().getScoreboardManager().getNewScoreboard();
        Objective sideBarObjective = scoreboard.getObjective("side-bar");
        if (Objects.nonNull(sideBarObjective)) {
            sideBarObjective.unregister();
        }
        sideBarObjective = scoreboard.registerNewObjective("side-bar", "dummy", ChatColor.GOLD + "伤害面板");
        sideBarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);


        openPlayerScoreBoard.put(player, new DamageScore(scoreboard));
        player.setScoreboard(scoreboard);


    }

    public void removeDamageScoreBoard(Player player) {
        if (hasDamageScoreBoard(player)) {
            openPlayerScoreBoard.get(player).unregister();
            openPlayerScoreBoard.remove(player);
        }
    }

    public Boolean hasDamageScoreBoard(Player player) {
        return openPlayerScoreBoard.containsKey(player);
    }

    public DamageScore getDamageScore(Player player) {
        return openPlayerScoreBoard.get(player);
    }


}
