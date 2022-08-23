package com.nana.mmoplugin.mmoplugin.Arms.Define;

public enum ArmsType {
    SWORD("单手剑", 1.0, 0.75, ArmsCatchType.SINGLE_HAND),
    GIANT_SWORD("巨剑", 1.5, 1.5, ArmsCatchType.BOTH_HAND),
    DOUBLE_BLADES("双刀", 0.65, 0.5, ArmsCatchType.BOTH_HAND),
    BOW("弓", 1.0, 1.0, ArmsCatchType.BOTH_HAND),
    STAVE("法杖", 1.0, 1.0, ArmsCatchType.SINGLE_HAND),
    ;

    private final String name;
    private final ArmsCatchType catchType;
    // 普攻的伤害倍率
    private final Double PanelDamagePercentage;
    private final Long attackCd;

    ArmsType(String name, Double panelDamagePercentage, Double attackCd, ArmsCatchType catchType) {
        attackCd = attackCd * 1000;
        this.name = name;
        this.catchType = catchType;
        this.attackCd = attackCd.longValue();
        PanelDamagePercentage = panelDamagePercentage;
    }

    public String getName() {
        return name;
    }

    public Long getAttackCd() {
        return attackCd;
    }

    public ArmsCatchType getCatchType() {
        return catchType;
    }

    public Double getPanelDamagePercentage() {
        return PanelDamagePercentage;
    }
}
