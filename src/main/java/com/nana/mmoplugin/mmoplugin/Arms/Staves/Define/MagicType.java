package com.nana.mmoplugin.mmoplugin.Arms.Staves.Define;

import com.nana.mmoplugin.mmoplugin.Arms.Staves.Magic.FireBall;
import com.nana.mmoplugin.mmoplugin.MmoSystem.Define.MmoAttributeType;

public enum MagicType implements MmoAttributeType {
    FIRE_BALL("火球", 2.0, FireBall.class),
    ;
    private String name;
    private Long cd;
    private Class<? extends StaveActive> clazz;

    MagicType(String name, Double cd, Class<? extends StaveActive> clazz) {
        cd = cd * 1000;
        this.name = name;
        this.cd = cd.longValue();
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Long getCd() {
        return cd;
    }

    public Class<? extends StaveActive> getClazz() {
        return clazz;
    }

    @Override
    public String getTypeName() {
        return "法杖法术类型";
    }
}
