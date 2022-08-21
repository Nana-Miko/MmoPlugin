package com.nana.mmoplugin.mmoplugin.Arms.Define;

public enum ArmsType {
    SWORD("单手剑", 1.0, ArmsCatchType.SINGLE_HAND),
    GIANT_SWORD("巨剑", 1.5, ArmsCatchType.BOTH_HAND),
    DOUBLE_BLADES("双刀", 0.65, ArmsCatchType.BOTH_HAND),
    BOW("弓", 1.0, ArmsCatchType.BOTH_HAND),
    ;

    private final String name;
    private final ArmsCatchType catchType;
    // 普攻的伤害倍率
    private final Double PanelDamagePercentage;

    ArmsType(String name, Double panelDamagePercentage, ArmsCatchType catchType) {
        this.name = name;
        this.catchType = catchType;
        PanelDamagePercentage = panelDamagePercentage;
    }

    public String getName() {
        return name;
    }

    public ArmsCatchType getCatchType() {
        return catchType;
    }

    public Double getPanelDamagePercentage() {
        return PanelDamagePercentage;
    }
}
