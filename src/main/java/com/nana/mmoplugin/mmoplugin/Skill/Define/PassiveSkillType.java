package com.nana.mmoplugin.mmoplugin.Skill.Define;


import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.MmoAttributeType;
import com.nana.mmoplugin.mmoplugin.Skill.Passive.BrambleBody;
import com.nana.mmoplugin.mmoplugin.Skill.Passive.IronBody;

public enum PassiveSkillType implements MmoAttributeType {
    IRON_BODY("铜墙铁壁", IronBody.class, 0),
    BRAMBLE_BODY("荆棘之甲", BrambleBody.class, 1),
    ;

    private String name;
    private int id;
    private Class<? extends PassiveSkill> clazz;

    PassiveSkillType(String name, Class<? extends PassiveSkill> clazz, int id) {
        this.name = name;
        this.id = id;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Class<? extends PassiveSkill> getClazz() {
        return clazz;
    }

    @Override
    public String getTypeName() {
        return "被动技能类型";
    }
}
