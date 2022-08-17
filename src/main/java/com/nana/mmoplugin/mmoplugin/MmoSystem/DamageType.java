package com.nana.mmoplugin.mmoplugin.MmoSystem;

public enum DamageType {
    NORMAL("普通","护甲 + "),
    CUTTING("切割","撕裂抵抗 + "),
    MAGIC("魔法","魔法抵抗 + ");

    private String damageTypeName;
    private String damageArmorName;

    DamageType(String damageTypeName, String damageArmorName) {
        this.damageTypeName = damageTypeName;
        this.damageArmorName = damageArmorName;
    }

    public String getDamageTypeName() {
        return damageTypeName;
    }

    public void setDamageTypeName(String damageTypeName) {
        this.damageTypeName = damageTypeName;
    }

    public String getDamageArmorName() {
        return damageArmorName;
    }

    public void setDamageArmorName(String damageArmorName) {
        this.damageArmorName = damageArmorName;
    }
}
