package com.nana.mmoplugin.mmoplugin.Arms.Define;

public enum ArmsType {
    SWORD("单手剑", ArmsCatchType.SINGLE_HAND),
    GIANT_SWORD("巨剑", ArmsCatchType.BOTH_HAND),
    DOUBLE_BLADES("双刀", ArmsCatchType.BOTH_HAND),
    BOW("弓", ArmsCatchType.BOTH_HAND),
    ;

    private final String name;
    private final ArmsCatchType catchType;

    ArmsType(String name, ArmsCatchType catchType) {
        this.name = name;
        this.catchType = catchType;
    }

    public String getName() {
        return name;
    }

    public ArmsCatchType getCatchType() {
        return catchType;
    }
}
