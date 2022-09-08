package com.nana.mmoplugin.mmoplugin.MmoSystem.Damage;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Objects;

public class DamageScore {
    private Objective sideBarObjective;
    private Scoreboard scoreboard;
    private Long lastTime = new Long(0);
    private NumberFormat nf = NumberFormat.getNumberInstance();
    // 攻击开始的时间
    private Long attackTime = System.currentTimeMillis();
    private Double normalValue = 0.0;
    private Double cuttingValue = 0.0;
    private Double magicValue = 0.0;
    private Double suckedBleedValue = 0.0;
    private Double criticalValue = 0.0;
    private Double blockedValue = 0.0;
    private Double lastValue = 0.0;
    private Double dpsValue = 0.0;
    private Double totalValue = 0.0;

    public DamageScore(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        this.sideBarObjective = scoreboard.getObjective("side-bar");
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.DOWN);
    }

    public void unregister() {
        sideBarObjective.unregister();
    }

    private void Default(Long time) {
        if (time - lastTime > 3000) {
            totalValue = 0.0;
            normalValue = 0.0;
            cuttingValue = 0.0;
            magicValue = 0.0;
            dpsValue = 0.0;
            suckedBleedValue = 0.0;
            criticalValue = 0.0;
            blockedValue = 0.0;
            attackTime = time;
        }
        lastTime = time;
    }

    private String format(Double value) {
        return nf.format(value);
    }

    public void addDamageValue(Double damage, DamageType damageType) {
        Long time = System.currentTimeMillis();

        Default(time);

        switch (damageType) {
            case NORMAL:
                normalValue = normalValue + damage;
                break;
            case CUTTING:
                cuttingValue = cuttingValue + damage;
                break;
            case MAGIC:
                magicValue = magicValue + damage;
                break;
        }

        totalValue = totalValue + damage;
        lastValue = damage;

        Double temp;
        if (time - attackTime > 50) {
            Double temp1 = time.doubleValue() - attackTime.doubleValue();
            temp = totalValue / (temp1 / 1000);
        } else {
            temp = totalValue / 0.05;
        }

        dpsValue = Double.parseDouble(String.format("%.2f", temp));

        if (Objects.nonNull(sideBarObjective)) {
            sideBarObjective.unregister();
        }

        sideBarObjective = scoreboard.registerNewObjective("side-bar", "dummy", ChatColor.GOLD + "伤害面板");
        sideBarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        sideBarObjective.getScore(ScoreDamageTypeString.NORMAL + format(normalValue)).setScore(8);
        sideBarObjective.getScore(ScoreDamageTypeString.CUTTING + format(cuttingValue)).setScore(7);
        sideBarObjective.getScore(ScoreDamageTypeString.MAGIC + format(magicValue)).setScore(6);
        sideBarObjective.getScore(ScoreDamageTypeString.SUCKED_BLEED + format(suckedBleedValue)).setScore(5);
        sideBarObjective.getScore(ScoreDamageTypeString.CRITICAL + format(criticalValue)).setScore(4);
        sideBarObjective.getScore(ScoreDamageTypeString.BLOCKED + format(blockedValue)).setScore(3);
        sideBarObjective.getScore(ScoreDamageTypeString.LAST_DAMAGE + format(lastValue)).setScore(2);
        sideBarObjective.getScore(ScoreDamageTypeString.DPS + format(dpsValue)).setScore(1);
        sideBarObjective.getScore(ScoreDamageTypeString.TOTAL_DAMAGE + format(totalValue)).setScore(0);


    }

    public void addAttributeValue(Double attribute, ScoreDamageTypeString scoreDamageTypeString) {

        Default(System.currentTimeMillis());

        switch (scoreDamageTypeString) {
            case SUCKED_BLEED:
                suckedBleedValue = suckedBleedValue + attribute;
                break;
            case CRITICAL:
                criticalValue = criticalValue + attribute;
                break;
            case BLOCKED:
                blockedValue = blockedValue + attribute;
                break;
        }

    }
}
