package com.nana.mmoplugin.mmoplugin.Arms.Define;

import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.MmoAttributeType;

public enum ArmsCatchType implements MmoAttributeType {
    SINGLE_HAND,
    BOTH_HAND,
    ;

    @Override
    public String getTypeName() {
        return "武器单双手类型";
    }
}
