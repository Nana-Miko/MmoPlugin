package com.nana.mmoplugin.mmoplugin.Skill.Define;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.MmoAttributeType;

public enum ActiveSkillSingleType implements MmoAttributeType {
    // 一段技能
    ONCE,
    //多段技能
    MORE;

    @Override
    public String getTypeName() {
        return "技能段数类型";
    }
}
